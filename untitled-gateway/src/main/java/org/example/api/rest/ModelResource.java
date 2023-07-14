package org.example.api.rest;

import org.example.dto.ModelDTO;
import org.example.model.Model;
import org.example.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.ws.rs.*;
import java.util.List;
import java.util.stream.Collectors;

@Produces("application/json")
@Consumes("application/json")
@RestController
@Path("/example/api")
public class ModelResource {

    private final ModelService modelService;

    @Autowired
    public ModelResource(ModelService modelService) {
        this.modelService = modelService;
    }

    // Create
    @POST
    @Path("/")
    public ResponseEntity<ModelDTO> create(@Valid @RequestBody ModelDTO modelDTO) {
        Model model = modelService.save(new Model(modelDTO.getName()));
        return new ResponseEntity<>(new ModelDTO(model), HttpStatus.CREATED);
    }

    // Read
    @GET
    @Path("/")
    public List<ModelDTO> getAll() {
        var models = modelService.getAll();
        return models.stream().map(ModelDTO::new).collect(Collectors.toList());
    }

    // Read (one)
    @GET
    @Path("/{id}")
    public ResponseEntity<ModelDTO> getById(@PathParam("id") Long id) {
        Model model = modelService.getById(id);
        if (model != null) {
            return new ResponseEntity<>(new ModelDTO(model), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update
    @PUT
    @Path("/{id}")
    public ResponseEntity<ModelDTO> update(@PathParam("id") Long id, @Valid @RequestBody ModelDTO modelDTO) {
        Model model = modelService.update(id, new Model(modelDTO.getName()));
        if (model != null) {
            return new ResponseEntity<>(new ModelDTO(model), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete
    @DELETE
    @Path("/{id}")
    public ResponseEntity<Void> delete(@PathParam("id") Long id) {
        if (modelService.delete(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GET
    @Path("/hello")
    public ResponseEntity<String> sayHello() {
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }
}