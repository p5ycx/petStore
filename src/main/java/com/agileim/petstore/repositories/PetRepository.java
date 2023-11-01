package com.agileim.petstore.repositories;


import com.agileim.petstore.model.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


@Component
public interface PetRepository extends JpaRepository<Pet, Long> {

}
