package org.example.premier_projet_spring;

import org.example.premier_projet_spring.controller.ProductController;
import org.example.premier_projet_spring.mock.MockProductDao;
import org.example.premier_projet_spring.mock.MockSecurityUtils;
import org.example.premier_projet_spring.model.Product;
import org.example.premier_projet_spring.model.Seller;
import org.example.premier_projet_spring.security.AppUserDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ProductControllerTest {

    ProductController productController;

    @BeforeEach
    void setUp() {
        productController = new ProductController(
                new MockProductDao(), new MockSecurityUtils("ROLE_SELLER"), null
        );
    }


    @Test
    void callFindAll_shouldSend200ok() {
        ResponseEntity<Product> response = productController.getProduct(1L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteExistingProductByCreatorIfSeller_shouldSend204noContent() {
        Seller fakeSeller = new Seller();
        fakeSeller.setId(1L);
        AppUserDetails userDetails = new AppUserDetails(fakeSeller);

        ResponseEntity<Product> response = productController.deleteProduct(1L, userDetails);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteNotExistingProduct_shouldSend404NotFound() {
        Seller fakeSeller = new Seller();
        fakeSeller.setId(1L);
        AppUserDetails userDetails = new AppUserDetails(fakeSeller);

        ResponseEntity<Product> response = productController.deleteProduct(2L, userDetails);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteExistingProductNotBySeller_shouldSend403Forbidden() {
        Seller fakeSeller = new Seller();
        fakeSeller.setId(1L);
        AppUserDetails userDetails = new AppUserDetails(fakeSeller);

        ResponseEntity<Product> response = productController.deleteProduct(2L, userDetails);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void deleteExistingProductByNotCreatorIfSellerButChief_shouldSend204noContent() {
        productController = new ProductController(
                new MockProductDao(), new MockSecurityUtils("ROLE_CHIEF")
        );
        Seller fakeSeller = new Seller();
        fakeSeller.setId(2L);
        AppUserDetails userDetails = new AppUserDetails(fakeSeller);

        ResponseEntity<Product> response = productController.deleteProduct(1L, userDetails);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }


}
