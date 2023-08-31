package org.example.service;

import org.example.dto.ModelDTO;
import org.example.mapper.ModelDTOMapper;
import org.example.model.Model;
import org.example.repository.ModelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModelService {

    private final ModelRepo repo;
    private final ModelDTOMapper mapper;

    @Autowired
    public ModelService(ModelRepo repo, ModelDTOMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public void save(Model model) {
        repo.save(model);
    }

    public List<Model> getAll() {
        return repo.findAll();
    }

    public List<ModelDTO> getAllDTO() {
        return repo.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

}
