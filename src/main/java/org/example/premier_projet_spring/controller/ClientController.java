package org.example.premier_projet_spring.controller;

import jakarta.validation.Valid;
import org.example.premier_projet_spring.dao.ClientDao;
import org.example.premier_projet_spring.model.Client;
import org.example.premier_projet_spring.security.IsAdmin;
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
public class ClientController {

    protected ClientDao clientDao;

    @Autowired
    public ClientController(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @GetMapping("/client/{id}")

    @IsAdmin
    public ResponseEntity<Client> getClient(@PathVariable Long id) {

        Optional<Client> optionalClient = clientDao.findById(id);
        if (optionalClient.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalClient.get(), HttpStatus.OK);
    }


    @GetMapping("/clients")
    @IsSeller
    public List<Client> getAll() {
        return clientDao.findAll();
    }

    @PostMapping("/client")
    public ResponseEntity<Client> createClient(@RequestBody @Valid Client client) {
        
        client.setId(null);
        clientDao.save(client);
        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }

    @DeleteMapping("client/{id}")
    @IsClient
    public ResponseEntity<Client> deleteClient(@PathVariable Long id) {

        Optional<Client> optionalClient = clientDao.findById(id);
        if (optionalClient.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        clientDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Put change tout l'objet
    @PutMapping("/client/{id}")
    // Patch change une partie de l'objet
//    @PatchMapping("/client/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody @Valid Client client) {
        Optional<Client> optionalClient = clientDao.findById(id);
        if (optionalClient.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        client.setId(id);
        clientDao.save(client);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
