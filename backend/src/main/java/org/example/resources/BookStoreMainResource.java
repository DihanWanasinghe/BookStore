package org.example.resources;

import jakarta.ws.rs.ApplicationPath;
import org.example.exceptionMappers.NullPointerExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class BookStoreMainResource extends ResourceConfig {
    public BookStoreMainResource() {
        packages("org.example.resources", "org.example.exceptionMappers", "org.example.filters");
    }
}
