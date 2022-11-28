package com.salesianostriana.dam.monumentAPI.controller;

import com.salesianostriana.dam.monumentAPI.model.Monument;
import com.salesianostriana.dam.monumentAPI.repository.MonumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MonumentController {

    private final MonumentRepository repo;

    @GetMapping("/monumento/")
    public ResponseEntity<List<Monument>> getAllMonuments() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/monumento/{id}")
    public ResponseEntity<Monument> getOneMonumentById(@PathVariable Long id) {
        return ResponseEntity.of(repo.findById(id));
        //Duda: el @PathVariable que tipo es?
    }

    @PostMapping("/monumento/")
    public ResponseEntity<Monument> newMonument(@RequestBody Monument m) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(repo.save(m));
    }

    @PutMapping("/monumento/{id}")
    public ResponseEntity<Monument> editMonument(@RequestBody Monument m, @PathVariable Long id) {

        return ResponseEntity
                .of(repo.findById(id)
                        .map(old -> {
                            old.setID(m.getID());
                            old.setCode(m.getCode());
                            old.setLat(m.getLat());
                            old.setLon(m.getLon());
                            old.setUrl(m.getUrl());
                            old.setDescription(m.getDescription());
                            old.setCityName(m.getCityName());
                            old.setCountryName(m.getCountryName());
                            old.setCode(m.getCode());
                            return Optional.of(repo.save(old));
                        })
                        .orElse(Optional.empty())
                );
    }

    @DeleteMapping("/monumento/{id}")
    public ResponseEntity<Monument> deleteMonument(@PathVariable Long id){
        if (repo.existsById(id))
            repo.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }







}
