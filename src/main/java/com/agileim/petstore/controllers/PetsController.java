package com.agileim.petstore.controllers;

import com.agileim.petstore.model.dto.PetDTO;
import com.agileim.petstore.services.PetService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/pets")
public class PetsController {

    private final PetService petsService;

    public PetsController(PetService petsService) {
        this.petsService = petsService;
    }

    @PostMapping
    public PetDTO createPets(@RequestBody(required = true)
                             @Valid PetDTO petModel) {
        return petsService.createPet(petModel);
    }
}
