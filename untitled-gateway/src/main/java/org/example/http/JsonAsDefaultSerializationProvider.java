package org.example.http;

import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
@Produces("application/json")
@Consumes("application/json")
public class JsonAsDefaultSerializationProvider extends JacksonJaxbJsonProvider {

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    protected boolean hasMatchingMediaType(MediaType mediaType) {
        return mediaType != null && mediaType.isCompatible(MediaType.APPLICATION_JSON_TYPE);
    }
}
