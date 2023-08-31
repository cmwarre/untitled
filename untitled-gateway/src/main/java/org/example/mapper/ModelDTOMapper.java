package org.example.mapper;

import org.example.dto.ModelDTO;
import org.example.model.Model;
import org.springframework.stereotype.Component;

@Component
public class ModelDTOMapper {

    public ModelDTOMapper() {}

    public ModelDTO toDTO(Model entity) {
        return new ModelDTO(entity.getName());
    }

    public Model toEntity(ModelDTO dto) {
        return new Model(dto.getName());
    }

}
