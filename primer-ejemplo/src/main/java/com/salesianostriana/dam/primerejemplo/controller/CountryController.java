package com.salesianostriana.dam.primerejemplo.controller;

import com.salesianostriana.dam.primerejemplo.model.Country;
import com.salesianostriana.dam.primerejemplo.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CountryController {

    private final CountryRepository repo;

    /*
        @GetMapping("/country")
        public List<Country>findAll(){
        return repo.findAll();
        }
     */
    @GetMapping("/country")
    public ResponseEntity<List<Country>> findAll() {
        return ResponseEntity.ok(repo.findAll());
    }
    @GetMapping("/country/{code}")
    public ResponseEntity <Country> findByCode(@PathVariable String code){
        return ResponseEntity.of(repo.findFirstByCode(code));
    }

}
