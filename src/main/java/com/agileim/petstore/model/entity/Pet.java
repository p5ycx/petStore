package com.agileim.petstore.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


@Entity
public class Pet {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String tags;

    public Pet(Long id, String name, String tags) {
        this.id = id;
        this.name = name;
        this.tags = tags;
    }

    public Pet() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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

