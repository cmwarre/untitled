package org.example.api.rest;

import com.inductiveautomation.ignition.common.util.LoggerEx;
import org.example.model.Model;
import org.example.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Controller
@Path("/example/api/")
public class ModelResource {

    private static final LoggerEx logger = LoggerEx.newBuilder().build(ModelResource.class);
    private final ModelService modelService;

    @Autowired
    public ModelResource(ModelService modelService) {
        logger.info("Starting model Resource class");
        this.modelService = modelService;
    }

    @GET
    @Path("/")
    @Produces("application/json")
    public List<Model> getAll() {
        var models = modelService.getAll();
        return models;
    }

    @GET
    @Path("/hello")
    public String test() {
        return "Hello World";
    }

}
