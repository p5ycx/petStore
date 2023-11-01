package com.agileim.petstore.controllers;

import com.agileim.petstore.model.dto.BasePetDTO;
import com.agileim.petstore.model.dto.PetDTO;
import com.agileim.petstore.services.PetService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetsController {

    private final PetService petsService;

    public PetsController(PetService petsService) {
        this.petsService = petsService;
    }

    @PostMapping
    public PetDTO createPets(@RequestBody(required = true)
                             @Valid BasePetDTO basePet) {
        return petsService.createPet(basePet);
    }

    @GetMapping
    @ResponseBody
    public List<PetDTO> listPets(@RequestParam(value = "limit", defaultValue = "100") Integer limit, @RequestParam(value = "offset", defaultValue = "0") Integer offset) {
        return petsService.getAllPets(offset, limit);
    }

    @GetMapping("/{petId}")
    public PetDTO showPetById(
            @PathVariable String petId) {
        return petsService.getPetById(petId);
    }


}
