package com.salesianostriana.dam.monumentAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Monument {

    private String countryName,cityName,name,url,code,description,lat,lon;;

    @Id @GeneratedValue
    private Long iD;


}
