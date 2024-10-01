package dk.cphbusiness.rest;

import dk.cphbusiness.persistence.HibernateConfig;
import dk.cphbusiness.rest.controllers.PoemController;
import dk.cphbusiness.security.SecurityController;
import dk.cphbusiness.security.SecurityRoutes.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

/**
 * Purpose: To handle the routes for the rest api
 *
 * Author: Thomas Hartmann
 */
public class RestRoutes {
    PoemController poemController = PoemController.getInstance(HibernateConfig.getEntityManagerFactory()); // IN memory person collection

    public EndpointGroup getPoemRoutes() {
        return () -> {
            path("/", () -> {
                path("/poem", () -> {
                    get("/", poemController.getAll(), Role.ANYONE);
                    // Populate is a GET request for convenience, but it should be a POST request.
                    get("/populate", poemController.resetData(), Role.ANYONE);
                    get("/{id}", poemController.getById(), Role.ANYONE);
                    post("/", poemController.create(), Role.ANYONE);
                    put("/{id}", poemController.update(), Role.ANYONE);
                    delete("/{id}", poemController.delete(), Role.ANYONE);
                });
            });
        };
    }
}