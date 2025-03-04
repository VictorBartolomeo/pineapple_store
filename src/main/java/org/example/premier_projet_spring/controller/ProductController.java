package org.example.premier_projet_spring.controller;

import org.example.premier_projet_spring.dao.ProductDao;
import org.example.premier_projet_spring.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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

}
