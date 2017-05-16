package org.jaffa.plugins;

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
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;


import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.*;

import org.apache.maven.execution.MavenSession;

import org.apache.maven.project.MavenProject;
import org.jaffa.plugins.util.DwrFragments;
import org.jaffa.plugins.util.AppResourceFragments;
import org.jaffa.plugins.util.JawrResourceFragments;


/**
 * Maven Plugin to merge the resource files during Maven Life Cycle Phase of Process Classes and place them under META-INF
 */
@Mojo(name="fragmentmerge", defaultPhase = LifecyclePhase.PROCESS_CLASSES, requiresDependencyResolution = ResolutionScope.RUNTIME)
public class FragmentMergeMojo extends AbstractMojo{


    private static final String META_INF_LOCATION = File.separator+"META-INF"+File.separator;

    private static final String PROPERTIES_FILE = "resources.properties";

    private static final String DWR_FILE = "dwr.xml";

    private static final String JAWR_FILE = "jawr.properties";


    /**
     * Source for type of resources to look for
     */
    @Parameter
    File resources;


    /**
     * Directory containing the generated JAR.
     */
    @Parameter( defaultValue = "${project.build.directory}", required = true )
    private File outputDirectory;

    /**
     * Directory containing the classes and resource files that should be packaged into the JAR.
     */
    @Parameter( defaultValue = "${project.build.outputDirectory}", required = true )
    private File classesDirectory;

    /**
     * The {@link {MavenProject}.
     */
    @Parameter( defaultValue = "${project}", readonly = true, required = true )
    private MavenProject project;

    /**
     * The {@link MavenSession}.
     */
    @Parameter( defaultValue = "${session}", readonly = true, required = true )
    private MavenSession session;


    /**
     * Execute method for Fragment Merging
     * @throws MojoExecutionException
     */
    public void execute() throws MojoExecutionException {
        getLog().info("Initialize Fragment Merging Process");
        try {

            File target = new File(project.getBuild().getDirectory());

            File resources = new File(classesDirectory + META_INF_LOCATION + PROPERTIES_FILE);
            File dwr = new File(classesDirectory + META_INF_LOCATION + DWR_FILE);
            File jawr = new File(classesDirectory + META_INF_LOCATION + JAWR_FILE);

            if (target.exists()) {
                Collection<File> fragFiles = FileUtils.listFiles(new File(project.getBuild().getDirectory()), new String[]{"pfragment", "xfragment"}, true);

                Iterator<File> iter = fragFiles.iterator();
                while (iter.hasNext()) {
                    File frag = iter.next();
                    if (frag.getName() != null && frag.getName().startsWith("ApplicationResources")) {
                        AppResourceFragments.merge(resources, frag);
                    } else if (frag.getName() != null && frag.getName().startsWith("dwr")) {
                        DwrFragments.merge(dwr, frag);
                    } else if (frag.getName() != null && frag.getName().startsWith("jawr")) {
                        JawrResourceFragments.merge(jawr, frag);
                    }
                    frag.delete();
                }
                //Complete Dwr Merging by placing an end tag
                DwrFragments.merge(dwr,null);

                //Complete Resources Merging by placing an end tag
                AppResourceFragments.merge(resources,null);

                //Complete Resources Merging by placing an end tag
                JawrResourceFragments.merge(jawr, null);
            }
        }catch(IOException io){
            getLog().error(io);
        }
        getLog().info("Completed Fragment Merging Process");
    }
}

