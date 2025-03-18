package org.example.premier_projet_spring.controller;

import jakarta.validation.Valid;
import org.example.premier_projet_spring.dao.PurchaseLineDao;
import org.example.premier_projet_spring.model.PurchaseLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class PurchaseLineController {

    protected PurchaseLineDao purchaseLineDao;

    @Autowired
    public PurchaseLineController(PurchaseLineDao purchaseLineDao) {
        this.purchaseLineDao = purchaseLineDao;
    }

    @GetMapping("/purchaseLine/{id}")

    public ResponseEntity<PurchaseLine> getPurchaseLine(@PathVariable Long id) {

        Optional<PurchaseLine> optionalPurchaseLine = purchaseLineDao.findById(id);
        if (optionalPurchaseLine.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalPurchaseLine.get(), HttpStatus.OK);

    }


    @GetMapping("/purchaseLines")

    public List<PurchaseLine> getAll() {
        return purchaseLineDao.findAll();
    }

    @PostMapping("/purchaseLine")
    public ResponseEntity<PurchaseLine> createPurchaseLine(@RequestBody @Valid PurchaseLine purchaseLine) {
        purchaseLineDao.save(purchaseLine);
        return new ResponseEntity<>(purchaseLine, HttpStatus.CREATED);
    }

    @DeleteMapping("purchaseLine/{id}")
    public ResponseEntity<PurchaseLine> deletePurchaseLine(@PathVariable Long id) {

        Optional<PurchaseLine> optionalPurchaseLine = purchaseLineDao.findById(id);
        if (optionalPurchaseLine.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        purchaseLineDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Put change tout l'objet
    @PutMapping("/purchaseLine/{id}")
    // Patch change une partie de l'objet
    //@PatchMapping("/purchaseLine/{id}")
    public ResponseEntity<PurchaseLine> updatePurchaseLine(@PathVariable Long id, @RequestBody @Valid PurchaseLine purchaseLine) {
        Optional<PurchaseLine> optionalPurchaseLine = purchaseLineDao.findById(id);
        if (optionalPurchaseLine.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        purchaseLine.setId(id);
        purchaseLineDao.save(purchaseLine);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
