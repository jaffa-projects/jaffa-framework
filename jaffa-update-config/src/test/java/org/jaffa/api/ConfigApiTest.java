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

import org.junit.Test;
import org.junit.Before;

/**
 * API tests for IConfigApi - These tests still need to be written and comprehensively tested. This class is
 * simply a skeleton to populate with tests when we are tasked to do so.
 */
public class ConfigApiTest {


    private IConfigApi api;
    
    @Before
    public void setup() {
    }

    
    /**
     * Deletes a zip file and rolls back its configurations
     *
     * Removes configurations contained within a ZIP file and deletes the file entirely.
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void deleteConfigZipFileTest() {
        //POST random configs
        //DELETE random configs
        //ASSERT configs aren't there
        //ASSERT ZipFile is deleted from repository's JSON response
    }
    
    /**
     * Retrieve a specific configuration ZIP file
     *
     * Retrieve and download a specific configuration ZIP file from the $DATA_DIRECTORY location.
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getConfigZipFileTest() {
        //GET zip file
        //ASSERT file downloaded
        
        
    }
    
    /**
     * Retrieve the list of ZIP files that contain configurations within a directory
     *
     * This service endpoint scans the filesystem at the $DATA_DIRECTORY location and provides a list of all ZIP files contained within that location. These KIP files will store configuration XML files that will load and overwrite configuration properties depending on Context and Variation salience.
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getConfigZipFileListTest() {
        //GET zip file list from data.directory
        //ASSERT that the list contains the expected items
        
        
    }
    
    /**
     * Upload a new configuration ZIP file.
     *
     * Creates a ZIP file of configurations and populates GOLDesp repositories with the new values (depending on salience).
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void putConfigZipFileTest() {
        //POST a configuration file to a repository
        //ASSERT that the configurations are included in repository's JSON response
        
        
    }
    
}
