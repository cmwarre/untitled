package org.example.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public class ModelDTO implements Serializable {

    private String name;

    public ModelDTO() {
    }

    public ModelDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
