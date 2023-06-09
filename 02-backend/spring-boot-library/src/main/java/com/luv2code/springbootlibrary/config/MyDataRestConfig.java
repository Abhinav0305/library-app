package com.luv2code.springbootlibrary.config;

import com.luv2code.springbootlibrary.entity.Book;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private String allowedOrigins = "http://localhost:300";

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors){
        HttpMethod [] unsupportedActions = {HttpMethod.POST,
                HttpMethod.DELETE,
                HttpMethod.PATCH,
                HttpMethod.PUT};
        config.exposeIdsFor(Book.class);

        disableHttpMethods(Book.class, config,unsupportedActions);

        /* Configure Cors Mapping*/
        cors.addMapping(config.getBasePath() + "/**")
                .allowedOrigins(allowedOrigins);
    }

    private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod [] unsupportedMethod){
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedMethod))
                .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedMethod));
    }
}
