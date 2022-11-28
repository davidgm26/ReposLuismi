package com.salesianostriana.dam.primerejemplo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Country {

    @Id @GeneratedValue private Long id;

    private String code,name,currency,capital;
    private int population;

}
