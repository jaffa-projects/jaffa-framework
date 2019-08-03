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

package org.jaffa.loader;

import org.apache.log4j.Logger;
import org.jaffa.api.ConfigApiCore;
import org.jaffa.util.ContextHelper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

/**
 * Loads the Xml Config files and registers them to the Repository.
 */
public class ResourceLoader<T extends IManager> {
    public static final String ARCHIVE_EXTENSION = ".zip";
    private static final String LB_COOKIE_VALUE = "LB_COOKIE_VALUE";
    private static final String GCT_DIRECTORY = "gct.directory";
    private static final String DATA_DIRECTORY = "data.directory";
    public static String customConfigPath;

    /** This static block will instantiate the customConfigPath. */
    static {
        if(System.getProperty(GCT_DIRECTORY)!=null){
            customConfigPath = System.getProperty(GCT_DIRECTORY);
        }else if (System.getProperty(DATA_DIRECTORY)!=null){
            customConfigPath = System.getProperty(DATA_DIRECTORY);
        }
        if(customConfigPath!=null && System.getProperty(LB_COOKIE_VALUE)!=null) {
            customConfigPath+= File.separator + System.getProperty(LB_COOKIE_VALUE);
        }
    }
    /**
     * Create a ResourceLoader logger
     */
    private static Logger logger = Logger.getLogger(ResourceLoader.class);

    /**
     * Create a ManagerRepositoryService singleton to store managers
     */
    private ManagerRepositoryService managerRepositoryService = ManagerRepositoryService.getInstance();

    /**
     * Create a generic manager
     */
    private T manager;

    /**
     * gets the Manager from the ResourceLoader
     * @return Manager Object identified ny T
     */
    public T getManager() {
        return manager;
    }

    /**
     * sets the manager on the ResourceLoader
     * @param manager Object identified by T
     */
    public void setManager(T manager) {
        this.manager = manager;
    }

    /**
     * loads all the Xml files with xml file name in the manager from all the jars
     * where package contains META-INF/*
      */
    @PostConstruct
    public void loadXmls() {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources("classpath*:META-INF/" + manager.getResourceFileName());
            if (resources != null) {
                for (Resource resource : resources) {
                    if (resource == null) {
                        continue;
                    }
                    try {
                        manager.registerResource(resource, ContextHelper.getContextSalience(resource.getURI().toString()),
                                ContextHelper.getVariationSalience(resource.getURI().toString()));
                        managerRepositoryService.add(manager.getClass().getSimpleName(), manager);
                    } catch (Exception e) {
                        logger.error("Exception occurred while registering XML " + resource.getURI().toString() + " exception " + e);
                    }
                }
            }

            if (customConfigPath != null && new File(customConfigPath).exists()) {
                loadAllCustomConfigurations();
            }

         } catch (Exception exc) {
            logger.error(exc.getMessage());
            throw new RuntimeException(exc.getCause());
        }
    }

    /**
     * Loads all custom configurations in the custom config directory.
     * @throws IOException  When a file cannot be accessed or operations cannot be performed on it
     */
    private void loadAllCustomConfigurations() throws IOException {
        // Load all zip files from the custom config directory.
        File customConfigDirectory = new File(customConfigPath);
        for(File file : customConfigDirectory.listFiles()) {
            if (file.getName().endsWith(ARCHIVE_EXTENSION)) {
                loadCustomConfiguration(file);
            }
        }
    }

    /**
     * Loads a single custom configuration compressed file.
     * @param file  The compressed configuration archive
     * @throws IOException  When a file cannot be accessed or operations cannot be performed on it
     */
    private void loadCustomConfiguration(File file) throws IOException {
        File zipRoot = ConfigApiCore.extractToTemporaryDirectory(file);
        if (zipRoot != null) {
            ConfigApiCore.registerResources(zipRoot, ConfigApiCore.getFileContents(file));
            ConfigApiCore.removeDirTree(zipRoot);
        }
        else {
            logger.error(manager.toString() + " cannot load " + file.getName() + " from " + customConfigPath + " because " +
                "the file's directory structure is incorrect. Custom ZIP files must ONLY contain a META-INF directory " +
                "containing all configuration files and a manifest.");
        }
    }
}
