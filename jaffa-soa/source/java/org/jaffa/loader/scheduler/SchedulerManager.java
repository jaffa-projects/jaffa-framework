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

package org.jaffa.loader.scheduler;

import org.apache.log4j.Logger;
import org.jaffa.loader.ContextKey;
import org.jaffa.loader.IManager;
import org.jaffa.loader.IRepository;
import org.jaffa.loader.MapRepository;
import org.jaffa.modules.scheduler.services.configdomain.Config;
import org.jaffa.modules.scheduler.services.configdomain.Task;
import org.jaffa.util.JAXBHelper;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

/**
 * SchedulerManager will read the scheduler configuration file and provide those configurations to client classes.
 */
public class SchedulerManager implements IManager {

    private static final Logger log = Logger.getLogger(SchedulerManager.class);
    /**
     * The name of the configuration file which this class handles.
     */
    private static final String DEFAULT_CONFIGURATION_FILE = "jaffa-scheduler-config.xml";

    /**
     * The default location for the jaffa scheduler configuration file
     */
    private static final String CONFIGURATION_SCHEMA_FILE = "org/jaffa/modules/scheduler/services/configdomain/jaffa-scheduler-config_1_0.xsd";

    private IRepository<Task> schedulerTaskRepository = new MapRepository<>();

    /**
     * Register the scheduler task to the repository.
     *
     * @param schedulerTask
     * @param contextKey
     */
    public void registerSchedulerTask(ContextKey contextKey, Task schedulerTask) {
        schedulerTaskRepository.register(contextKey, schedulerTask);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerXML(Resource resource, String context, String variation) throws JAXBException, SAXException, IOException {

        Config config = JAXBHelper.unmarshalConfigFile(Config.class, resource, CONFIGURATION_SCHEMA_FILE);
        if (config.getTask() != null) {
            for (final Task schedulerTask : config.getTask()) {
                ContextKey contextKey = new ContextKey(schedulerTask.getDataBean(), resource.getURI().toString(), variation, context);
                registerSchedulerTask(contextKey, schedulerTask);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getXmlFileName() {
        return DEFAULT_CONFIGURATION_FILE;
    }

    /**
     * Unregister the scheduler task name from the repository.
     *
     * @param contextKey
     */
    public void unregisterSchedulerTask(ContextKey contextKey) {
        schedulerTaskRepository.unregister(contextKey);
    }

    /**
     * Returns a scheduler task by name
     *
     * @param typeName
     * @return
     */
    public Task getSchedulerTaskByTypeName(String typeName) {
        List<Task> tasks = schedulerTaskRepository.getAllValues();
        for (Task task : tasks) {
            if (typeName.equalsIgnoreCase(task.getType())) return task;
        }
        return null;
    }


    /**
     * Retrieves all the scheduler tasks from the repository based on the contextOrder provided
     * assumes defaultContextOrder from the configuration when contextOrder is not provided
     *
     * @return List of all values
     */
    public Task[] getAllSchedulerTasks() {
        return schedulerTaskRepository.getAllValues().toArray(new Task[0]);
    }


    /**
     * Returns the scheduler task object for the input dataBeanClass, as defined in the configuration file.
     *
     * @param dataBeanClass the class for a dataBean.
     * @return the TransactionInfo object for the input dataBeanClass, as defined in the configuration file.
     */
    public Task getSchedulerTask(String dataBeanClass){
        Task task = schedulerTaskRepository.query(dataBeanClass);
        if (task == null) {
            // Lookup the class hierarchy. Add a NULL for the dataBeanClassName, even if a Task is not found
            synchronized (schedulerTaskRepository) {
                task = schedulerTaskRepository.query(dataBeanClass);
                try{
                    if (task == null) {
                        Class clazz = Class.forName(dataBeanClass);
                        ContextKey superClassContextKey = null;
                        while (clazz.getSuperclass() != null) {
                            clazz = clazz.getSuperclass();
                            task = schedulerTaskRepository.query(clazz.getName());
                            superClassContextKey = schedulerTaskRepository.findKey(clazz.getName());
                            if (task != null)
                                break;
                        }
                        if(superClassContextKey!=null) {
                            schedulerTaskRepository.register(new ContextKey(dataBeanClass, superClassContextKey.getFileName(),
                                    superClassContextKey.getVariation(), superClassContextKey.getVariation()), task);
                        }
                    }
                }catch (ClassNotFoundException e){
                    log.error("Unable to find class definition for "+dataBeanClass, e);
                }
            }
        }
        return task;
    }

    /**
     * @return
     */
    public IRepository<Task> getSchedulerTaskRepository() {
        return schedulerTaskRepository;
    }

    /**
     * @param schedulerTaskRepository
     */
    public void setSchedulerTaskRepository(IRepository<Task> schedulerTaskRepository) {
        this.schedulerTaskRepository = schedulerTaskRepository;
    }
}
