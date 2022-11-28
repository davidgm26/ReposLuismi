package com.salesianostriana.dam.monumentAPI.data;

import com.salesianostriana.dam.monumentAPI.model.Monument;
import com.salesianostriana.dam.monumentAPI.repository.MonumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class initData {

    private final MonumentRepository repo;


    @PostConstruct
    public void initialData(){

        Monument m1 = new Monument();
        m1.setCountryName("España");
        m1.setUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQe4-d5HKkoPocfokLZd9kAe_LUyCLByIN2pA&usqp=CAU");
        m1.setName("Giralda");
        m1.setCode("ES");
        m1.setDescription("La giralda es...");
        m1.setCityName("Sevilla");
        m1.setLat("37.38630627062781");
        m1.setLon("-5.992640522038654");
        repo.save(m1);

        Monument m2 = new Monument();
        m2.setCountryName("España");
        m2.setUrl("https://img.bekiaviajes.com/articulos/portada/79000/79136-h.jpg");
        m2.setName("Catedral de la Almudena");
        m2.setCode("ES");
        m2.setDescription("La catedral de la Almudena...");
        m2.setCityName("Madrid");
        m2.setLat("40.41580611506124");
        m2.setLon("-3.7145198286531604");

        repo.save(m2);

        Monument m3 = new Monument();
        m3.setCountryName("España");
        m3.setUrl("https://upload.wikimedia.org/wikipedia/commons/6/62/Iglesia_de_san_pablo_ubeda_2011.jpg");
        m3.setName("Iglesia de San Pablo");
        m3.setCode("ES");
        m3.setDescription("La iglesia de San Pablo se encuentra en...");
        m3.setCityName("Úbeda");
        m3.setLat("38.01008360530953");
        m3.setLon("-3.3675959017847097");
        repo.save(m3);
    }

}