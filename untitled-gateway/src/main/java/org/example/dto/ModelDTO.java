package org.example.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class ModelDTO {

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
