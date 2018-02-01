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

import com.google.gson.Gson;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.core.Response;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.log4j.*;
import org.jaffa.loader.ConfigApiHelper;
import org.jaffa.model.FileContents;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

/**
 * ConfigAPI - A controller for REST service endpoints that provide the ability to dynamically manipulate
 * GOLD configurations and properties through the use of custom compressed files.
 */
@RestController
public class ConfigApi implements IConfigApi {
    private static final String DATA_DIR_ENV_NAME = "data_directory";
    private static final String FILE_EXTENSION = ".zip";
    private static final int BYTE_ARRAY_INIT_LENGTH = 17;
    private static final Logger log = Logger.getLogger(ConfigApi.class);

    private Gson gson = new Gson();
    private File dataDirectory = new File(System.getenv(DATA_DIR_ENV_NAME) +
            File.separator + "config");

    /**
     * Corresponds to DELETE endpoint. Unregister all configurations within the provided compressed file and delete
     * the file itself
     *
     * @param compressedFile The custom compressed file located in DATA_DIRECTORY/config to be deleted
     * @return HTTP Response indicating success or failure
     * @throws IOException When the endpoint has difficulty accessing or removing the file
     */
    public Response deleteCustomConfigFile(String compressedFile) throws IOException {
        String fileToDelete = ConfigApiHelper.verifyExtension(compressedFile, FILE_EXTENSION);
        File filePath = new File(this.dataDirectory + File.separator + fileToDelete);

        //Provide HTTP error if compressed file does not exist on server
        if (!filePath.exists()) {
            log.warn("The requested compressed file" + filePath + " was not found in " + dataDirectory);
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .entity("The requested compressed file could not be found on the server.")
                    .build();
        }

        //Extract files to gain access to configuration resources
        File tempDir = ConfigApiHelper.extractToTemporaryDirectory(filePath);
        ConfigApiHelper.unregisterResources(tempDir, this.getFileContents(filePath).getContextSalience());
        ConfigApiHelper.removeExtractedFiles(tempDir);
        if (!Files.deleteIfExists(filePath.toPath())) {
            log.warn(filePath + " was not successfully deleted");
        }

        Response.ResponseBuilder response = Response.status(Response.Status.OK);
        return response.build();
    }


    /**
     * Corresponds to GET/compressedFile Endpoint - Download the requested compressed file
     * (when authenticated through basic auth) or return a BAD REQUEST error if the file does not exist
     *
     * @param compressedFile The compressed file to download
     * @return The compressed file as an attachment to download
     */
    public Response getCustomConfigFile(@ApiParam(value = "Compressed File to be downloaded", required = true)
                                             String compressedFile) {
        compressedFile = ConfigApiHelper.verifyExtension(compressedFile, FILE_EXTENSION);
        File responseFile = new File(this.dataDirectory  + File.separator + compressedFile);

        return (!responseFile.exists() ?
                Response.status(Response.Status.BAD_REQUEST)
                        .entity("The server processed your request, but cannot find a file located at " + responseFile)
                        .build()
                : Response.status(Response.Status.OK)
                .entity(responseFile)
                .header("Content-Disposition", "attachment; filename=" + compressedFile)
                .build());
    }


    /**
     * Corresponds to GET endpoint - Retrieve a list of all the compressed files contained in the DATA_DIRECTORY property
     * location that match FILE_EXTENSION. In addition to the file names, the method also returns the Context Salience
     * and file contents.
     *
     * @return A JSON representation of the compressed files and their contents
     * @throws IOException Thrown when the directory cannot be accessed or compressed files cannot be read
     */
    public Response getCustomConfigFileList() throws IOException {
        File[] allFilesInDirectory = dataDirectory.listFiles();
        List<FileContents> compressedFilesInDirectory = new ArrayList<>();
        Response.ResponseBuilder response;

        if (allFilesInDirectory != null) {
            for (File file : allFilesInDirectory) {
                if (file.getName().endsWith(FILE_EXTENSION)) {
                    FileContents fileContents = this.getFileContents(file);
                    compressedFilesInDirectory.add(fileContents);
                }
            }
        } else {
            log.warn("DATA_DIRECTORY is not set, or is empty. Please check your DATA_DIRECTORY variable. It" +
            "is currently returning: " + dataDirectory);
        }
        response = Response.ok(gson.toJson(compressedFilesInDirectory));
        response.header("Content-Type", MediaType.APPLICATION_JSON);
        return response.build();
    }


    /**
     * Corresponds to the PUT endpoint - Upload a configuration compressed file to the server
     *
     * @param compressedFile The name of the uploaded compressed file
     * @param payload The binary data from the client's compressed file
     * @return HTTP Response indicating success or failure
     * @throws IOException When the endpoint has difficulty accessing or uploading the file
     */
    public Response postCustomConfigFile(@ApiParam(value = "Name of uploaded compressed file", required = true)
                                              String compressedFile, byte[] payload) throws IOException {
        String fileToPost = ConfigApiHelper.verifyExtension(compressedFile, FILE_EXTENSION);
        File filePath = new File(this.dataDirectory + File.separator + fileToPost);

        //Avoid NoSuchFileExceptions by double-checking that DATA_DIRECTORY exists
        if (!dataDirectory.exists()) {
            dataDirectory.mkdir();
        }

        //Provide HTTP error for empty request
        if (payload.length <= BYTE_ARRAY_INIT_LENGTH) {
            log.warn("A file was not included in your API POST request");
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .entity("A compressed file was not included in the request.")
                    .build();
        }

        //Provide HTTP error for already existing compressed file
        if (filePath.exists()) {
            log.warn("The API POST request failed because " + compressedFile + " exists in this location already");
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity("The file " + compressedFile + " exists in this location already.")
                    .build();
        }

        //Copy files to server and register resources
        Files.copy(new ByteArrayInputStream(payload), filePath.toPath());
        File tempDir = ConfigApiHelper.extractToTemporaryDirectory(filePath);
        ConfigApiHelper.registerResources(tempDir, this.getFileContents(filePath).getContextSalience());
        ConfigApiHelper.removeExtractedFiles(tempDir);

        log.info("The resources in " + filePath + " have finished registering configurations to the repositories");
        Response.ResponseBuilder response = Response.status(Response.Status.OK);
        return response.build();
    }

    /**
     * getFileContents() - When given a compressed file, parse through and return an object containing the
     * filename, context-salience from MANIFEST, and an array of configuration file contents
     * @param file  The compressed file to read
     * @return  An object containing the compressed file contents and additional information
     * @throws IOException  Thrown when the compressed file does not exist or cannot be read
     */
    private FileContents getFileContents(File file) throws IOException {
        String manifestFile = "META-INF/MANIFEST.MF";
        FileContents fileContents = new FileContents();

        ZipFile zipFile = new ZipFile(file);

        fileContents.setName(file.getName());

        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry configFile = entries.nextElement();
            fileContents.addContentsItem(new File(configFile.toString()).getName());
            if (configFile.getName().toUpperCase().equals(manifestFile)) {
                fileContents.setContextSalience(ConfigApiHelper.findContextSalienceInManifest(zipFile));
            }
        }
        zipFile.close();

        return fileContents;
    }
}



