package dk.cphbusiness;

import dk.cphbusiness.rest.ApplicationConfig;
import dk.cphbusiness.rest.RestRoutes;
import dk.cphbusiness.security.SecurityRoutes;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class Main {
    public static void main(String[] args) {
        ApplicationConfig
                .getInstance()
                .initiateServer()
                .setRoutes(new RestRoutes().getPoemRoutes()) // A different way to get the EndpointGroup.
                .startServer(7007)
                .setCORS()
                .setGeneralExceptionHandling()
//            .setErrorHandling()
                .setApiExceptionHandling();
    }
}