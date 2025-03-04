package org.example.premier_projet_spring.controller;

import org.example.premier_projet_spring.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

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
        Product fanta = new Product();
        fanta.setName("Fanta");
        Product pepsi = new Product();
        pepsi.setName("Pepsi");
        Product sprite = new Product();
        sprite.setName("Sprite");
        Product coca = new Product();
        coca.setName("Coca-Cola");
        List<Product> productsList = List.of(fanta, pepsi, sprite, coca);

        return productsList;


    }

}
