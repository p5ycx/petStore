package com.agileim.petstore.model.dto;


import java.io.Serial;
import java.io.Serializable;

public class PetDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String tags;

    public PetDTO() {
    }

    public PetDTO(Long id, String name, String tags) {
        this.id = id;
        this.name = name;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
