package org.jaffa.api.services.monitoring.controller;

import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * IRepositoryJsonService - Apache CXF service to provide JSON responses for all IRepository objects.
 * @author Matthew Wayles
 * @version 1.0
 */
@Path("/")
public interface IRepositoryJsonService {

    @GET
    @Path("/")
    Response rootRedirectToServices();

    @GET
    @Path("allRepositoryNames")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    String getRepositoryNames();

    @GET
    @Path("{repositoryName}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    String getRepository(@PathParam("repositoryName") String name,
                         @QueryParam("keyBeginsWith") String keyBeginsWith);

    @GET
    @Path("{repositoryName}/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    String getRepositoryValue(@PathParam("repositoryName") String name,
                              @PathParam("id") String id);
}
