package org.example.premier_projet_spring.controller;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.example.premier_projet_spring.dao.PurchaseDao;
import org.example.premier_projet_spring.model.Purchase;
import org.example.premier_projet_spring.view.PurchaseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class PurchaseController {

    protected PurchaseDao purchaseDao;

    @Autowired
    public PurchaseController(PurchaseDao purchaseDao) {
        this.purchaseDao = purchaseDao;
    }

    @GetMapping("/purchase/{id}")
    @JsonView(PurchaseView.class)
    public ResponseEntity<Purchase> getPurchase(@PathVariable Long id) {

        Optional<Purchase> optionalPurchase = purchaseDao.findById(id);
        if (optionalPurchase.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalPurchase.get(), HttpStatus.OK);

    }

    @GetMapping("/purchases")
    @JsonView(PurchaseView.class)
    public List<Purchase> getAll() {
        return purchaseDao.findAll();
    }

    @PostMapping("/purchase")
    public ResponseEntity<Purchase> createPurchase(@RequestBody @Valid Purchase purchase) {
        purchaseDao.save(purchase);
        return new ResponseEntity<>(purchase, HttpStatus.CREATED);
    }

    @DeleteMapping("purchase/{id}")
    public ResponseEntity<Purchase> deletePurchase(@PathVariable Long id) {

        Optional<Purchase> optionalPurchase = purchaseDao.findById(id);
        if (optionalPurchase.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        purchaseDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Put change tout l'objet
    @PutMapping("/purchase/{id}")
    // Patch change une partie de l'objet
//    @PatchMapping("/purchase/{id}")
    public ResponseEntity<Purchase> updatePurchase(@PathVariable Long id, @RequestBody @Valid Purchase purchase) {
        Optional<Purchase> optionalPurchase = purchaseDao.findById(id);
        if (optionalPurchase.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        purchase.setId(id);
        purchaseDao.save(purchase);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
