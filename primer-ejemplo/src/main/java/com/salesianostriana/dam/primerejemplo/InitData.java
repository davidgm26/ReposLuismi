package com.salesianostriana.dam.primerejemplo;

import com.salesianostriana.dam.primerejemplo.model.Country;
import com.salesianostriana.dam.primerejemplo.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitData {

    private final CountryRepository repo;

    @PostConstruct
    public void init(){

        Country spain = new Country();

        spain.setId(1L);
        spain.setCode("1");

    }
}
