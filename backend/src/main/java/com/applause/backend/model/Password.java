package com.applause.backend.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Password {


    private String hash;

    public Password() {
    }

    public Password( String hash) {

        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hashPassword) {
        this.hash = hashPassword;
    }
}


