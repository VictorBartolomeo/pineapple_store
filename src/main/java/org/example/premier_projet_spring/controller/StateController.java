package org.example.premier_projet_spring.controller;

import org.example.premier_projet_spring.dao.StateDao;
import org.example.premier_projet_spring.model.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class StateController {

    protected StateDao stateDao;

    @Autowired
    public StateController(StateDao stateDao) {
        this.stateDao = stateDao;
    }

    @GetMapping("/state/{id}")

    public ResponseEntity<State> getState(@PathVariable Long id) {

        Optional<State> optionalState = stateDao.findById(id);
        if (optionalState.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalState.get(), HttpStatus.OK);

    }


    @GetMapping("/states")
    public List<State> getAll() {
        return stateDao.findAll();
    }

    @PostMapping("/state")
    public ResponseEntity<State> createState(@RequestBody State state) {
        stateDao.save(state);
        return new ResponseEntity<>(state, HttpStatus.CREATED);
    }

    @DeleteMapping("state/{id}")
    public ResponseEntity<State> deleteState(@PathVariable Long id) {

        Optional<State> optionalState = stateDao.findById(id);
        if (optionalState.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        stateDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Put change tout l'objet
    @PutMapping("/state/{id}")
    // Patch change une partie de l'objet
//    @PatchMapping("/state/{id}")
    public ResponseEntity<State> updateState(@PathVariable Long id, @RequestBody State state) {
        Optional<State> optionalState = stateDao.findById(id);
        if (optionalState.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        state.setId(id);
        stateDao.save(state);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
