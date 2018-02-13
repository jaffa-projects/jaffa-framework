package com.tapestrysolutions.monitoring.services.repos;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


/**
 * IRepositoryJsonService - Apache CXF service to provide JSON responses for all IRepository objects.
 * @author Matthew Wayles
 * @version 1.0
 */
@Path("/repository")
public interface IRepositoryJsonService {

    @GET
    @Path("allRepositoryNames")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    String getRepositoryNames();

    @GET
    @Path("{repositoryName}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    String getRepository(@PathParam("repositoryName") String name);

    @GET
    @Path("{repositoryName}/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    String getRepositoryValue(@PathParam("repositoryName") String name, @PathParam("id") String id);
}
