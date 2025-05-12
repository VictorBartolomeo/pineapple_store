package org.example.premier_projet_spring.controller;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.example.premier_projet_spring.annotation.ValidFile;
import org.example.premier_projet_spring.dao.ProductDao;
import org.example.premier_projet_spring.model.Product;
import org.example.premier_projet_spring.model.Seller;
import org.example.premier_projet_spring.model.State;
import org.example.premier_projet_spring.security.AppUserDetails;
import org.example.premier_projet_spring.security.ISecurityUtils;
import org.example.premier_projet_spring.security.IsClient;
import org.example.premier_projet_spring.security.IsSeller;
import org.example.premier_projet_spring.service.FileService;
import org.example.premier_projet_spring.view.ProductViewClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin
@RestController
public class ProductController {

    protected ProductDao productDao;
    protected ISecurityUtils securityUtils;
    protected FileService fileService;

    @Autowired
    public ProductController(ProductDao productDao, ISecurityUtils securityUtils, FileService fileService) {
        this.productDao = productDao;
        this.securityUtils = securityUtils;
        this.fileService = fileService;
    }

    @GetMapping("/product/{id}")
    @IsClient
    @JsonView(ProductViewClient.class)
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {

        Optional<Product> optionalProduct = productDao.findById(id);
        if (optionalProduct.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalProduct.get(), HttpStatus.OK);
    }


    @GetMapping("/products")
    @IsClient
    @JsonView(ProductViewClient.class)
    public List<Product> getAll() {
        return productDao.findAll();
    }

    @GetMapping("/product/picture/{id}")
    @IsClient
    public ResponseEntity<byte[]> getImageProduit(@PathVariable Long id) {

        Optional<Product> optional = productDao.findById(id);

        if (optional.isPresent()) {

            String nomImage = optional.get().getPictureName();

            try {
                byte[] image = fileService.getImageByName(nomImage);

                HttpHeaders enTete = new HttpHeaders();
                String mimeType = Files.probeContentType(new File(nomImage).toPath());
                enTete.setContentType(MediaType.valueOf(mimeType));

                return new ResponseEntity<>(image, enTete, HttpStatus.OK);

            } catch (FileNotFoundException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } catch (IOException e) {
                System.out.println("Le test du mimetype a echoué");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/product")
    @IsSeller
    public ResponseEntity<Product> createProduct(@RequestPart("product") @Valid Product product,
                                                 @RequestPart(value = "picture", required = false) @ValidFile(acceptedTypes = {"image.jpeg", "image/png", "image/gif"}) MultipartFile picture,
                                                 @AuthenticationPrincipal AppUserDetails userDetails) {


        product.setCreator((Seller) userDetails.getUser());

        //si le produit n'a pas d'état il sera alors considéré comme neuf
        if (product.getState() == null) {
            State stateNew = new State();
            stateNew.setId(1L);
            product.setState(stateNew);
        }
        product.setId(null);


        if (picture != null && !picture.isEmpty()) {
            try {
                String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"));
                String pictureName = date + "_" + product.getId() + "_" + UUID.randomUUID() + "_" + picture.getOriginalFilename();

                fileService.uploadToLocalFileSystem(picture, pictureName, false);

                product.setPictureName(pictureName);

            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        productDao.save(product);
        product.setCreator(null);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @DeleteMapping("product/{id}")
    @IsSeller
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id, @AuthenticationPrincipal AppUserDetails userDetails) {

        Optional<Product> optionalProduct = productDao.findById(id);

        if (optionalProduct.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String role = securityUtils.getRole(userDetails);

        if (!role.equals("ROLE_CHIEF") && optionalProduct.get().getCreator().equals(userDetails.getUser())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        productDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Put change tout l'objet
    @PutMapping("/product/{id}")
    @IsSeller
    // Patch change une partie de l'objet
//    @PatchMapping("/product/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody @Valid Product savedProduct, @AuthenticationPrincipal AppUserDetails userDetails) {
        Optional<Product> optionalProduct = productDao.findById(id);
        if (optionalProduct.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        savedProduct.setCreator(optionalProduct.get().getCreator());
        savedProduct.setId(id);

        productDao.save(savedProduct);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
