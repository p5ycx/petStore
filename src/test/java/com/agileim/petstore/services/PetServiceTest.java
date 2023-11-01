package com.agileim.petstore.services;

import com.agileim.petstore.exceptions.PetNotFoundException;
import com.agileim.petstore.model.dto.BasePetDTO;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        BasePetDTO basePetDTO = createTestBasePetDTO("Rover");
        Pet petEntity = createTestPetEntity(null, "Rover");
        PetDTO savedPetDTO = createTestPetDTO(1L, "Rover");

        when(mapper.basePetDTOToEntity(basePetDTO)).thenReturn(petEntity);
        when(repository.save(petEntity)).thenReturn(petEntity);
        when(mapper.entityToDto(petEntity)).thenReturn(savedPetDTO);

        // When
        PetDTO result = service.createPet(basePetDTO);

        // Then
        assertEquals(savedPetDTO, result);
        assertEquals(1L, result.getId());

        verify(mapper, times(1)).basePetDTOToEntity(basePetDTO);
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

    @Test
    void updatePetDetailsTest_success() {
        // Given
        String petId = "1";
        Pet existingPetEntity = createTestPetEntity(1L, "Rover");
        PetDTO updatedPetDto = createTestPetDTO(1L, "Bruno");
        Pet updatedPetEntity = createTestPetEntity(1L, "Bruno");

        when(repository.findById(1L)).thenReturn(Optional.of(existingPetEntity));
        when(mapper.dtoToEntity(updatedPetDto)).thenReturn(updatedPetEntity);
        when(repository.save(updatedPetEntity)).thenReturn(updatedPetEntity);
        when(mapper.entityToDto(updatedPetEntity)).thenReturn(updatedPetDto);

        // When
        PetDTO result = service.updatePetDetails(petId, updatedPetDto);

        // Then
        assertEquals(updatedPetDto, result);
        assertEquals("Bruno", result.getName());

        verify(repository, times(1)).findById(1L);
        verify(mapper, times(1)).dtoToEntity(updatedPetDto);
        verify(repository, times(1)).save(updatedPetEntity);
        verify(mapper, times(1)).entityToDto(updatedPetEntity);
    }

    @Test
    void updatePetDetailsTest_petNotFound() {
        // Given
        String petId = "2";

        when(repository.findById(2L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(PetNotFoundException.class, () -> service.updatePetDetails(petId, new PetDTO()));
        verify(repository, times(1)).findById(2L);
    }


    @Test
    void getPetByIdTest() {
        // Given
        String id = "1";  // Known pet ID
        Pet petEntity = createTestPetEntity(1L, "Rover");
        PetDTO expectedPetDTO = createTestPetDTO(1L, "Rover");

        when(repository.findById(Long.parseLong(id))).thenReturn(Optional.of(petEntity));
        when(mapper.entityToDto(petEntity)).thenReturn(expectedPetDTO);

        // When
        PetDTO result = service.getPetById(id);

        // Then
        assertEquals(expectedPetDTO, result);

        verify(repository, times(1)).findById(Long.parseLong(id));
        verify(mapper, times(1)).entityToDto(petEntity);
    }

    @Test
    void getPetByIdNotFoundTest() {
        // Given
        String id = "999";  //random id
        when(repository.findById(Long.parseLong(id))).thenReturn(Optional.empty());

        // Then
        assertThrows(PetNotFoundException.class, () -> service.getPetById(id));

        verify(repository, times(1)).findById(Long.parseLong(id));
    }


    private Pet createTestPetEntity(Long id, String name) {
        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(name);
        return pet;
    }

    private BasePetDTO createTestBasePetDTO(String name) {
        BasePetDTO basePetDTO = new BasePetDTO();
        basePetDTO.setName(name);
        return basePetDTO;
    }

    private PetDTO createTestPetDTO(Long id, String name) {
        PetDTO petModel = new PetDTO();
        petModel.setId(id);
        petModel.setName(name);
        return petModel;
    }
}