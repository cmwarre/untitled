package org.example.service;

import org.example.model.Model;
import org.example.repository.ModelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

public class ModelService {

    private final ModelRepo repo;

    public ModelService(ModelRepo repo) {
        this.repo = repo;
    }

    public void save(Model model) {
        repo.save(model);
    }

    public List<Model> getAll() {
        return repo.findAll();
    }

}
