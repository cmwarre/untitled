package org.example.dto;

import org.example.model.Model;

import java.io.Serializable;

public class ModelDTO implements Serializable {

    private Long id;
    private String name;

    public ModelDTO() {
    }

    public ModelDTO(Model model) {
        this.id = model.getId();
        this.name = model.getName();
    }

    public ModelDTO(String name) {
        this.name = name;
    }

    public ModelDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getID() {
        return id;
    }

}
