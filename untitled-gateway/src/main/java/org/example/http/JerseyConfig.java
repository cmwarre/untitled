package org.example.http;

import org.example.api.rest.ModelResource;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/system/example/api/*")
public class JerseyConfig extends ResourceConfig {

    public static final String SERVLET_ID = "example";

    public JerseyConfig() {
        register(ModelResource.class);
    }

}
