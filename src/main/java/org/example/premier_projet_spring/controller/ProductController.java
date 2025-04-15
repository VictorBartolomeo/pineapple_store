package org.example.premier_projet_spring.controller;

import jakarta.validation.Valid;
import org.example.premier_projet_spring.dao.ProductDao;
import org.example.premier_projet_spring.model.Product;
import org.example.premier_projet_spring.model.Seller;
import org.example.premier_projet_spring.model.State;
import org.example.premier_projet_spring.security.AppUserDetails;
import org.example.premier_projet_spring.security.IsClient;
import org.example.premier_projet_spring.security.IsSeller;
import org.example.premier_projet_spring.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class ProductController {

    protected ProductDao productDao;
    protected SecurityUtils securityUtils;

    @Autowired
    public ProductController(ProductDao productDao, SecurityUtils securityUtils) {
        this.productDao = productDao;
        this.securityUtils = securityUtils;
    }

    @GetMapping("/product/{id}")
    @IsClient
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {

        Optional<Product> optionalProduct = productDao.findById(id);
        if (optionalProduct.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalProduct.get(), HttpStatus.OK);
    }


    @GetMapping("/products")
    @IsClient
    public List<Product> getAll() {
        return productDao.findAll();
    }

    @PostMapping("/product")
    @IsSeller
    public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product, @AuthenticationPrincipal AppUserDetails userDetails) {


        product.setCreator((Seller) userDetails.getUser());

        //si le produit n'a pas d'état il sera alors considéré comme neuf
        if (product.getState() == null) {
            State stateNew = new State();
            stateNew.setId(1L);
            product.setState(stateNew);
        }
        product.setId(null);
        productDao.save(product);
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

        if (!role.equals("ROLE_CHIEF") || !optionalProduct.get().getCreator().equals(userDetails.getUser())) {
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
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody @Valid Product product, @AuthenticationPrincipal AppUserDetails userDetails) {
        Optional<Product> optionalProduct = productDao.findById(id);
        if (optionalProduct.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        product.setId(id);
        product.setCreator((Seller) userDetails.getUser());
        productDao.save(product);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
