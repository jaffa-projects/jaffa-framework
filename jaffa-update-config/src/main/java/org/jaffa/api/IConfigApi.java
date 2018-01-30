/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2002 JAFFA Development Group
 *
 *     This library is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public
 *     License as published by the Free Software Foundation; either
 *     version 2.1 of the License, or (at your option) any later version.
 *
 *     This library is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public
 *     License along with this library; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * Redistribution and use of this software and associated documentation ("Software"),
 * with or without modification, are permitted provided that the following conditions are met:
 * 1.	Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.	Redistributions in binary form must reproduce the above copyright notice,
 * 	this list of conditions and the following disclaimer in the documentation
 * 	and/or other materials provided with the distribution.
 * 3.	The name "JAFFA" must not be used to endorse or promote products derived from
 * 	this Software without prior written permission. For written permission,
 * 	please contact mail to: jaffagroup@yahoo.com.
 * 4.	Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 * 	appear in their names without prior written permission.
 * 5.	Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */
package org.jaffa.api;

import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.io.IOException;

@Path("/")
@Api(value = "/", description = "Product RESTful Implementation Toolkit")
public interface IConfigApi {

    /**
     * Unregister configurations contained in a custom ZIP file from the IRepository
     * implementations, then remove the ZIP file itself
     * @param zipFile   The custom ZIP file located in DATA_DIRECTORY/config to be deleted
     * @return  HTTP Response indicating operation success or failure
     * @throws IOException  When the endpoint has difficulty accessing or removing the file
     */
    @DELETE
    @Path("/config/{zipFile}")
    @Produces({"application/x-www-form-urlencoded"})
    @ApiOperation(value = "Deletes a zip file and rolls back its configurations", tags = {"config",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 400, message = "Bad request. Check $DATA_DIRECTORY variable"),
            @ApiResponse(code = 404, message = "Service not found"),
            @ApiResponse(code = 405, message = "Validation exception")})
    Response deleteCustomConfigFile(@PathParam("zipFile") String zipFile) throws IOException;

    /**
     * Download a ZIP file contained in the server's DATA_DIRECTORY/config location
     * @param zipFile   The custom ZIP file to be downloaded
     * @return  HTTP Response indicating operation success or failure
     * @throws IOException  When the endpoint has difficulty accessing or downloading the file
     */
    @GET
    @Path("/config/{zipFile}")
    @Produces({"application/zip"})
    @ApiOperation(value = "Retrieve a specific configuration ZIP file", tags = {"config",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation"),
            @ApiResponse(code = 400, message = "Bad request. Check $DATA_DIRECTORY variable"),
            @ApiResponse(code = 404, message = "Service not found"),
            @ApiResponse(code = 405, message = "Validation exception")})
    Response getCustomConfigFile(@PathParam("zipFile") String zipFile);

    /**
     * Retrieve a JSON list of available ZIP configurations in the DATA_DIRECTORY/config location
     * @return  HTTP Response indicating operation success or failure
     * @throws IOException  When the endpoint has difficulty accessing or manipulating the file
     */
    @GET
    @Path("/config")
    @Consumes({"application/x-www-form-urlencoded"})
    @Produces({"application/json"})
    @ApiOperation(value = "Retrieve the list of ZIP files that contain configurations within a directory", tags = {"config",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 400, message = "Bad request. Check $DATA_DIRECTORY variable"),
            @ApiResponse(code = 404, message = "Service not found"),
            @ApiResponse(code = 405, message = "Invalid input")})
    Response getCustomConfigFileList() throws IOException;

    /**
     * Upload a ZIP file to the server's DATA_DIRECTORY/config location and register configurations to the IRepository
     * implementations
     * @param zipFile   The custom ZIP file to be downloaded
     * @return  HTTP Response indicating operation success or failure
     * @throws IOException  When the endpoint has difficulty accessing or downloading the file
     */
    @POST
    @Path("/config/{zipFile}")
    @Consumes({"application/octet-stream", "application/zip" })
    @Produces({"application/json"})
    @ApiOperation(value = "Upload a new configuration ZIP file.", tags = {"config"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 400, message = "Bad request. Check $DATA_DIRECTORY variable"),
            @ApiResponse(code = 404, message = "Service not found"),
            @ApiResponse(code = 405, message = "Validation exception")})
    Response postCustomConfigFile(@PathParam("zipFile") String zipFile, byte[] payload) throws IOException;
}

