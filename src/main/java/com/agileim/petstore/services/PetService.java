package com.agileim.petstore.services;

import com.agileim.petstore.model.dto.BasePetDTO;
import com.agileim.petstore.model.dto.PetDTO;
import com.agileim.petstore.model.entity.Pet;
import com.agileim.petstore.model.mapper.PetMapper;
import com.agileim.petstore.repositories.PetRepository;
import org.springframework.stereotype.Service;

@Service
public class PetService {

    private final PetRepository repository;

    private final PetMapper mapper;

    public PetService(PetRepository repository, PetMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public PetDTO createPet(BasePetDTO basePetDTO) {
        Pet petEntity = mapper.basePetDTOToEntity(basePetDTO);
        Pet savedPet = repository.save(petEntity);
        return mapper.entityToDto(savedPet);
    }
}
