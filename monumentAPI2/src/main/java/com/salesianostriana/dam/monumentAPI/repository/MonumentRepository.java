package com.salesianostriana.dam.monumentAPI.repository;

import com.salesianostriana.dam.monumentAPI.model.Monument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonumentRepository extends JpaRepository<Monument,Long> {


}
