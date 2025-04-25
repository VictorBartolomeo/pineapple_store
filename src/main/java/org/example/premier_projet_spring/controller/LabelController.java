package org.example.premier_projet_spring.controller;

import org.example.premier_projet_spring.dao.LabelDao;
import org.example.premier_projet_spring.model.Label;
import org.example.premier_projet_spring.security.IsClient;
import org.example.premier_projet_spring.security.IsSeller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class LabelController {

    protected LabelDao labelDao;

    @Autowired
    public LabelController(LabelDao labelDao) {
        this.labelDao = labelDao;
    }

    @GetMapping("/label/{id}")

    public ResponseEntity<Label> getLabel(@PathVariable Long id) {

        Optional<Label> optionalLabel = labelDao.findById(id);
        if (optionalLabel.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalLabel.get(), HttpStatus.OK);

    }


    @GetMapping("/labels")
    @IsClient
    public List<Label> getAll() {
        return labelDao.findAll();
    }

    @PostMapping("/label")
    @IsSeller
    public ResponseEntity<Label> createLabel(@RequestBody Label label) {
        labelDao.save(label);
        return new ResponseEntity<>(label, HttpStatus.CREATED);
    }

    @DeleteMapping("label/{id}")
    public ResponseEntity<Label> deleteLabel(@PathVariable Long id) {

        Optional<Label> optionalLabel = labelDao.findById(id);
        if (optionalLabel.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        labelDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Put change tout l'objet
    @PutMapping("/label/{id}")
    // Patch change une partie de l'objet
//    @PatchMapping("/label/{id}")
    public ResponseEntity<Label> updateLabel(@PathVariable Long id, @RequestBody Label label) {
        Optional<Label> optionalLabel = labelDao.findById(id);
        if (optionalLabel.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        label.setId(id);
        labelDao.save(label);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
