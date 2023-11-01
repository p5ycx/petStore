package com.agileim.petstore.services;

import com.agileim.petstore.model.dto.BasePetDTO;
import com.agileim.petstore.model.dto.PetDTO;
import com.agileim.petstore.model.entity.Pet;
import com.agileim.petstore.model.mapper.PetMapper;
import com.agileim.petstore.repositories.PetRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetService {

    private static final int MAX_PAGE_SIZE = 100;
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

    public List<PetDTO> getAllPets(Integer offset, Integer limit) {
        if (limit > MAX_PAGE_SIZE) {
            limit = MAX_PAGE_SIZE;
        }
        return repository.findAll(PageRequest.of(offset, limit)).stream().map(mapper::entityToDto).collect(Collectors.toList());
    }
}
