package com.tapestrysolutions.monitoring.services.repos;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


/**
 * IRepositoryJsonService - Apache CXF service to provide JSON responses for all IRepository objects.
 */
@Path("/repository")
public interface IRepositoryJsonService {

    @GET
    @Path("getAllRepositoryNames")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    String getRepositoryNames();

    @GET
    @Path("getRepository/{repositoryName}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    String getRepository(@PathParam("repositoryName") String name);

}
