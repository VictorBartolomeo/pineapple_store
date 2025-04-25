package org.example.premier_projet_spring;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.example.premier_projet_spring.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    //Ce qui est fait _ ce qui devrait se passer
    @Test
    void createValidProduct_shouldNotThrowException() {
        Product testProduct = new Product();
        testProduct.setName("Test");
        testProduct.setPrice(100);
        testProduct.setCode("1234567890");

        Set<ConstraintViolation<Product>> violations = validator.validate(testProduct);
        assertTrue(violations.isEmpty());
    }

    @Test
    void createValidProductWithoutName_shouldNotBeValid() {
        Product testProduct = new Product();
        testProduct.setPrice(110);
        testProduct.setCode("1234567890");
        Set<ConstraintViolation<Object>> violations = validator.validate(testProduct);

        assertTrue(constraintExist(validator.validate(testProduct), "name", "NotBlank"));
    }

    @Test
    void createProductWithNegativePrice_shouldNotBeValid() {
        Product testProduct = new Product();
        testProduct.setPrice(-110);
        testProduct.setCode("1234567890");
        Set<ConstraintViolation<Object>> violations = validator.validate(testProduct);

        boolean notBlankViolationExist = constraintExist(violations, "price", "DecimalMin");
        assertTrue(notBlankViolationExist);
    }

    private boolean constraintExist(Set<ConstraintViolation<Object>> violations, String fieldname, String constraintName) {
        return violations.stream().filter(v -> v.getPropertyPath().toString().equals(fieldname))
                .map(v -> v.getConstraintDescriptor().getAnnotation().annotationType().getName())
                .filter(s -> s.equals("jakarta.validation.constraints." + constraintName))
                .findFirst()
                .isPresent();
    }

    @Test
    void createProduitWithNegativePrice_shouldNotBeValid() {
        Product testProduct = new Product();
        testProduct.setName("test");
        testProduct.setPrice(-10);

        assertTrue(
                TestUtils.constraintExist(
                        validator.validate(testProduct),
                        "price",
                        "DecimalMin"));

    }

}
