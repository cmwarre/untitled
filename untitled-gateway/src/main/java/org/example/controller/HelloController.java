package org.example.controller;

import org.example.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/spring-example/api/hello")
public class HelloController {

    private final ModelService service;

    @Autowired
    public HelloController(ModelService service) {
        this.service = service;
    }

    @GetMapping
    public String hello() {
       return "Hello, world!";
    }

}
