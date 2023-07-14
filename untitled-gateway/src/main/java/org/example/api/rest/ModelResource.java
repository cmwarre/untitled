package org.example.api.rest;

import com.inductiveautomation.ignition.common.util.LoggerEx;
import org.example.dto.ModelDTO;
import org.example.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<ModelDTO> getAll() {
        var models = modelService.getAll();
        return models.stream().map(ModelDTO::new).collect(Collectors.toList());
    }

    @GET
    @Path("/hello")
    public String sayHello() {
        return "Hello World";
    }

}
