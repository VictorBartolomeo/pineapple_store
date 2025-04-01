package org.example.premier_projet_spring.controller;

import jakarta.validation.Valid;
import org.example.premier_projet_spring.dao.ProductDao;
import org.example.premier_projet_spring.model.Product;
import org.example.premier_projet_spring.model.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class ProductController {

    protected ProductDao productDao;

    @Autowired
    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping("/product/{id}")

    public ResponseEntity<Product> getProduct(@PathVariable Long id) {

        Optional<Product> optionalProduct = productDao.findById(id);
        if (optionalProduct.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalProduct.get(), HttpStatus.OK);

    }


    @GetMapping("/products")
    public List<Product> getAll() {
        return productDao.findAll();
    }

    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product) {

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
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {

        Optional<Product> optionalProduct = productDao.findById(id);
        if (optionalProduct.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        productDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Put change tout l'objet
    @PutMapping("/product/{id}")
    // Patch change une partie de l'objet
//    @PatchMapping("/product/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody @Valid Product product) {
        Optional<Product> optionalProduct = productDao.findById(id);
        if (optionalProduct.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        product.setId(id);
        productDao.save(product);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
