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

import org.jaffa.loader.drools.DroolsLoader;
import org.jaffa.loader.drools.DroolsManager;
import org.jaffa.loader.messaging.MessagingManager;
import org.jaffa.loader.scheduler.SchedulerManager;
import org.jaffa.loader.soa.SoaEventManager;
import org.jaffa.loader.transaction.TransactionManager;
import org.jaffa.modules.messaging.services.ConfigurationService;
import org.jaffa.modules.scheduler.services.SchedulerConfiguration;
import org.jaffa.soa.rules.ServiceRulesInterceptor;
import org.jaffa.soa.services.configdomain.SoaEventInfo;
import org.jaffa.transaction.services.configdomain.TransactionInfo;
import org.jaffa.transaction.services.configdomain.TypeInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Contains all the Beans related to the Loader Architecture for the Jaffa-SOA
 */
@Configuration
public class SoaLoaderConfig {

	/****************************************************/
	/*************  Transaction Manager    **************/
	/****************************************************/
    @Bean
    public ResourceLoader<TransactionManager> transactionManagerXmlLoader() {
        ResourceLoader<TransactionManager> transactionManagerResourceLoader = new ResourceLoader<TransactionManager>() ;
        transactionManagerResourceLoader.setManager(jaffaTransactionManager());
        return transactionManagerResourceLoader;
    }

    @Bean
    public TransactionManager jaffaTransactionManager() {
        TransactionManager transactionManager = new TransactionManager();
        org.jaffa.transaction.services.ConfigurationService.setTransactionManager(transactionManager);
        transactionManager.setTransactionRepository(transactionInfoRepository());
        transactionManager.setTypeInfoRepository(typeInfoRepository());
        return transactionManager;
    }

    private MapRepository<TransactionInfo> transactionInfoRepository(){
        MapRepository<TransactionInfo> mapRepository= new MapRepository<>();
        return mapRepository;
    }

    private MapRepository<TypeInfo> typeInfoRepository(){
        MapRepository<TypeInfo> mapRepository= new MapRepository<>();
        return mapRepository;
    }
    
	/****************************************************/
	/*************   Soa Event Manager     **************/
	/****************************************************/
    @Bean
    public ResourceLoader<SoaEventManager> soaEventManagerXmlLoader() {
        ResourceLoader<SoaEventManager> soaEventManagerResourceLoader = new ResourceLoader<SoaEventManager>() ;
        soaEventManagerResourceLoader.setManager(soaEventManager());
        return soaEventManagerResourceLoader;
    }

    @Bean
    public SoaEventManager soaEventManager() {
    	SoaEventManager soaEventManager = new SoaEventManager();
    	org.jaffa.soa.services.ConfigurationService.setSoaEventManager(soaEventManager);
        soaEventManager.setSoaEventRepository(soaEventInfoRepository());
        return soaEventManager;
    }

    /**
     * @return the messaging manager's XML loader
     */
    @Bean
    public ResourceLoader<MessagingManager> messagingManagerXmlLoader() {
        ResourceLoader<MessagingManager> messagingManagerResourceLoader =
                new ResourceLoader<>() ;
        messagingManagerResourceLoader.setManager(messagingManager());
        return messagingManagerResourceLoader;
    }

    /**
     * Creates and initializes the messaging manager.
     * @return the newly created MessagingManager
     */
    @Bean
    public MessagingManager messagingManager() {
        MessagingManager messagingManager = new MessagingManager();
        ConfigurationService.getInstance().setMessagingManager(messagingManager);
        return messagingManager;
    }

    private MapRepository<SoaEventInfo> soaEventInfoRepository(){
        MapRepository<SoaEventInfo> mapRepository= new MapRepository<>();
        return mapRepository;
    }

    /**
     * Loads the SchedulerManager
     *
     * @return
     */
    @Bean
    public ResourceLoader<SchedulerManager> schedulerManagerXmlLoader() {
        ResourceLoader<SchedulerManager> schedulerManagerResourceLoader = new ResourceLoader<>();
        schedulerManagerResourceLoader.setManager(schedulerManager());
        return schedulerManagerResourceLoader;
    }

    /**
     * Initializes the schedulerManager class.
     *
     * @return
     */
    @Bean
    public SchedulerManager schedulerManager() {
        SchedulerManager schedulerManager = new SchedulerManager();
        SchedulerConfiguration.getInstance().setSchedulerManager(schedulerManager);
        return schedulerManager;
    }

    /**
     * Drools Files Configuration
     */

    @Bean
    public DroolsLoader droolLoader(){
        DroolsLoader droolsLoader = new DroolsLoader();
        droolsLoader.setManager(droolManager());
        return droolsLoader;
    }

    @Bean
    public DroolsManager droolManager(){
        DroolsManager droolsManager = new DroolsManager();
        ServiceRulesInterceptor.setDroolsManager(droolsManager);
        return droolsManager;
    }
}

