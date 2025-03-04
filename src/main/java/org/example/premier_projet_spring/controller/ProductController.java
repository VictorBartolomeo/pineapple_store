package org.example.premier_projet_spring.controller;

import org.example.premier_projet_spring.dao.ProductDao;
import org.example.premier_projet_spring.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    protected ProductDao productDao;

    @GetMapping("/product")
    public Product getProduct() {

        Product coca = new Product();

        coca.setName("Pepsi");
        coca.setId(1);
        coca.setCode("COCA-PEPSI");
        coca.setDescription("Pepsi, comme du Coca en meilleur");
        coca.setPrice(2.15f);
        return coca;
    }


    @GetMapping("/products")
    public List<Product> getAll() {
        return productDao.findAll();
    }

}
