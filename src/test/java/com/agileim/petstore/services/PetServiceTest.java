package com.agileim.petstore.services;

import com.agileim.petstore.model.dto.PetDTO;
import com.agileim.petstore.model.entity.Pet;
import com.agileim.petstore.model.mapper.PetMapper;
import com.agileim.petstore.repositories.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PetServiceTest {


    @Mock
    private PetRepository repository;

    @Mock
    private PetMapper mapper;

    @InjectMocks
    private PetService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPetTest() {
        // Given
        PetDTO petDTO = createTestPetDTO(null, "Rover");
        Pet petEntity = createTestPetEntity(1L, "Rover");
        PetDTO savedPetDTO = createTestPetDTO(1L, "Rover");

        when(mapper.dtoToEntity(petDTO)).thenReturn(petEntity);
        when(repository.save(petEntity)).thenReturn(petEntity);
        when(mapper.entityToDto(petEntity)).thenReturn(savedPetDTO);

        // When
        PetDTO result = service.createPet(petDTO);

        // Then
        assertEquals(savedPetDTO, result);
        assertEquals(1L, result.getId());

        verify(mapper, times(1)).dtoToEntity(petDTO);
        verify(repository, times(1)).save(petEntity);
        verify(mapper, times(1)).entityToDto(petEntity);
    }

    private Pet createTestPetEntity(Long id, String name) {
        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(name);
        return pet;
    }

    private PetDTO createTestPetDTO(Long id, String name) {
        PetDTO petModel = new PetDTO();
        petModel.setId(id);
        petModel.setName(name);
        return petModel;
    }
}