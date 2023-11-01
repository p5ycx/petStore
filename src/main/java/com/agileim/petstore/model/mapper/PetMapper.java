package com.agileim.petstore.model.mapper;


import com.agileim.petstore.model.dto.BasePetDTO;
import com.agileim.petstore.model.dto.PetDTO;
import com.agileim.petstore.model.entity.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "Spring")
public interface PetMapper {

    PetMapper INSTANCE = Mappers.getMapper(PetMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "tags", target = "tags")
    PetDTO entityToDto(Pet pet);


    @Mapping(source = "name", target = "name")
    @Mapping(source = "tags", target = "tags")
    Pet dtoToEntity(PetDTO petDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "tags", target = "tags")
    Pet basePetDTOToEntity(BasePetDTO basePetDTO);

}
