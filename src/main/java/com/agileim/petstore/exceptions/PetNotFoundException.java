package com.agileim.petstore.exceptions;

public class PetNotFoundException extends RuntimeException {


    public PetNotFoundException() {
        super("Pet not found");
    }
}
