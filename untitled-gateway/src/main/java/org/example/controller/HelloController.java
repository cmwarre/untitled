package org.example.controller;

import jdk.jfr.ContentType;
import org.example.dto.ModelDTO;
import org.example.model.Model;
import org.example.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/spring-example/api/hello") // http://localhost:8088/system/spring-example/api/hello
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
