package com.agileim.petstore.model.dto;


import java.io.Serial;
import java.io.Serializable;

public class PetDTO extends BasePetDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;


    public PetDTO() {
    }

    public PetDTO(Long id, String name, String tags) {
        this.id = id;
        this.setName(name);
        this.setTags(tags);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
