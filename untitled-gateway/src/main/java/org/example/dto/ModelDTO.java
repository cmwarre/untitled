package org.example.dto;

import org.example.model.Model;

import java.io.Serializable;

public class ModelDTO implements Serializable {

    private String name;

    public ModelDTO() {
    }

    public ModelDTO(Model model) {
        this.name = model.getName();
    }

    public ModelDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
