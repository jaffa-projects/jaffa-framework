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
 * 1.   Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.   Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 3.   The name "JAFFA" must not be used to endorse or promote products derived from
 *  this Software without prior written permission. For written permission,
 *  please contact mail to: jaffagroup@yahoo.com.
 * 4.   Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *  appear in their names without prior written permission.
 * 5.   Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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

package org.jaffa.modules.scheduler.services;

import org.apache.log4j.Logger;
import org.jaffa.loader.scheduler.SchedulerManager;
import org.jaffa.modules.scheduler.services.configdomain.Task;

/**
 * This class implements the Singleton pattern. Use the getInstance() method to get an instance of this class.
 * It provides methods to extract information from the SchedulerManager class.
 */
public class SchedulerConfiguration {

    private static final Logger log = Logger.getLogger(SchedulerConfiguration.class);

    /**
     * The singleton instance of this class.
     */
    private static SchedulerConfiguration c_singleton;

    /**
     * Provides access to the schedulerManager from the SchedulerConfiguration
     */
    private static SchedulerManager schedulerManager;

    /**
     * Creates an instance of SchedulerConfiguration, if not already instantiated.
     *
     * @return An instance of the SchedulerConfiguration.
     */
    public static synchronized SchedulerConfiguration getInstance() {
        if (c_singleton == null)
            createSchedulerConfigurationInstance();
        return c_singleton;
    }

    /**
     * Returns the Java representation of the Task configuration for the given type.
     *
     * @param type the task type.
     * @return the Java representation of the Task configuration for the given type.
     */
    public Task getTask(String type) {
        return schedulerManager.getSchedulerTaskByTypeName(type,null);
    }

    /**
     * Returns the Java representation of the Task configuration for the given dataBean.
     *
     * @param dataBeanClassName the class name of the dataBean.
     * @return the Java representation of the Task configuration for the given dataBean.
     * @throws ClassNotFoundException if dataBeanClassName is not found on the classpath
     */
    public Task getTaskByDataBean(String dataBeanClassName) throws ClassNotFoundException {
        return schedulerManager.getSchedulerTask(dataBeanClassName, null );
    }

    /**
     * Returns the Java representation of the Task configuration for the given type.
     * @return the Java representation of the Task configuration for the given type.
     */
    public Task[] getTasks() {
        return schedulerManager.getAllSchedulerTasks(null);
    }

    /**
     * Creates the singleton instance of the SchedulerConfiguration
     */
    private static synchronized void createSchedulerConfigurationInstance() {
        if (c_singleton == null) {
            c_singleton = new SchedulerConfiguration();
            if (log.isDebugEnabled())
                log.debug("An instance of the SchedulerConfiguration has been created");
        }
    }

    /**
     * private constructor of the SchedulerConfiguration
     */
    private SchedulerConfiguration() {
    }

    /**
     * Returns the scheduler manager
     * @return
     */
    public static SchedulerManager getSchedulerManager() {
        return schedulerManager;
    }

    /**
     * Sets the scheduler manager
     * @param schedulerManager
     */
    public static void setSchedulerManager(SchedulerManager schedulerManager) {
        SchedulerConfiguration.schedulerManager = schedulerManager;
    }
}
