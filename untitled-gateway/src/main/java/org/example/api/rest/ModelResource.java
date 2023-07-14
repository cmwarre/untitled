package org.example.api.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/example/api/")
public class ModelResource {

    @GET
    @Path("/hello")
    public String test() {
        return "Hello World";
    }

}
