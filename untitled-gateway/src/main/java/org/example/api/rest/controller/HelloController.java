package org.example.api.rest.controller;

import org.example.dto.ModelDTO;
import org.example.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/spring-example/api/hello")
public class HelloController {

    private final ModelService service;

    @Autowired
    public HelloController(ModelService service) {
        this.service = service;
    }

    @GetMapping(value = "/hello")
    public String hello() {
       return "Hello, world!";
    }

    @GetMapping
    public ResponseEntity<List<ModelDTO>> getModel() {
        return ResponseEntity.ok(service.getAllDTO());
    }

}
