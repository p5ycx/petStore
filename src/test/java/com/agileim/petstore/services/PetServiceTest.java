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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

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

    @Test
    void getAllPetsTest() {
        // Given
        int offset = 0;
        int limit = 2;

        Pet petEntity1 = createTestPetEntity(1L, "Rover");
        Pet petEntity2 = createTestPetEntity(2L, "Fido");

        PetDTO petDTO1 = createTestPetDTO(1L, "Rover");
        PetDTO petDTO2 = createTestPetDTO(2L, "Fido");

        Page<Pet> petPage = new PageImpl<>(List.of(petEntity1, petEntity2));

        when(repository.findAll(PageRequest.of(offset, limit))).thenReturn(petPage);
        when(mapper.entityToDto(petEntity1)).thenReturn(petDTO1);
        when(mapper.entityToDto(petEntity2)).thenReturn(petDTO2);

        // When
        List<PetDTO> result = service.getAllPets(offset, limit);

        // Then
        assertEquals(2, result.size());
        assertEquals(petDTO1, result.get(0));
        assertEquals(petDTO2, result.get(1));

        verify(repository, times(1)).findAll(PageRequest.of(offset, limit));
        verify(mapper, times(1)).entityToDto(petEntity1);
        verify(mapper, times(1)).entityToDto(petEntity2);
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