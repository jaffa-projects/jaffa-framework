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

import org.jaffa.components.navigation.NavCache;
import org.jaffa.config.ApplicationResourceLoader;
import org.jaffa.loader.config.ApplicationResourcesManager;
import org.jaffa.loader.config.ApplicationRulesManager;
import org.jaffa.loader.navigation.NavigationManager;
import org.jaffa.loader.policy.BusinessFunctionManager;
import org.jaffa.loader.policy.RoleManager;
import org.jaffa.security.CheckPolicy;
import org.jaffa.security.PolicyCache;
import org.jaffa.session.ContextManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Contains all the Beans related to the BusinessFunctionManager and RoleManager Loader Architecture for the Jaffa-SOA
 */
@Configuration
public class CoreLoaderConfig {

    /**
     * @return Returns an ResourceLoader for the ApplicationResourcesManager
     */
    @Bean
    public ResourceLoader<ApplicationResourcesManager> applicationResourceManagerPropertiesLoader() {
        ResourceLoader<ApplicationResourcesManager> resourceManagerResourceLoader = new ResourceLoader<>();
        resourceManagerResourceLoader.setManager(applicationResourcesManager());
        return resourceManagerResourceLoader;
    }

    /**
     * @return Returns a ApplicationResourcesManager
     */
    @Bean
    public ApplicationResourcesManager applicationResourcesManager() {
        ApplicationResourcesManager applicationResourcesManager = new ApplicationResourcesManager();
        ApplicationResourceLoader.setApplicationResourcesManager(applicationResourcesManager);
        return applicationResourcesManager;
    }

    /****************************************************/
    /*************  Role & BusinessFunction Manager  ****/
    /****************************************************/

    /**
     * @return Returns an XMLLoader for RoleManager
     */
    @Bean
    public ResourceLoader<RoleManager> roleManagerXmlLoader() {
        ResourceLoader<RoleManager> roleManagerResourceLoader = new ResourceLoader<RoleManager>();
        roleManagerResourceLoader.setManager(roleManager());
        return roleManagerResourceLoader;
    }

    /**
     * @return Returns a RoleManager
     */
    @Bean
    public RoleManager roleManager() {
        RoleManager roleManager = new RoleManager();
        PolicyCache.setRoleManager(roleManager);
        return roleManager;
    }

    /**
     * @return Returns an XMLLoader BusinessFunctionManager
     */
    @Bean
    public ResourceLoader<BusinessFunctionManager> businessFunctionManagerXmlLoader() {
        ResourceLoader<BusinessFunctionManager> businessFunctionManagerResourceLoader = new ResourceLoader<BusinessFunctionManager>();
        businessFunctionManagerResourceLoader.setManager(businessFunctionManager());
        return businessFunctionManagerResourceLoader;
    }

    /**
     * @return Returns a BusinessFunctionManager
     */
    @Bean
    public BusinessFunctionManager businessFunctionManager() {
        BusinessFunctionManager businessFunctionManager = new BusinessFunctionManager();
        CheckPolicy.setBusinessFunctionManager(businessFunctionManager);
        return businessFunctionManager;
    }

    /**
     * @return Returns an XMLLoader NavigationManager
     */
    @Bean
    public ResourceLoader<NavigationManager> navigationManagerXmlLoader() {
        ResourceLoader<NavigationManager> navigationManagerXmlLoader = new ResourceLoader<>();
        navigationManagerXmlLoader.setManager(navigationManager());
        return navigationManagerXmlLoader;
    }

    /**
     * @return Returns a NavigationManager
     */
    @Bean
    public NavigationManager navigationManager() {
        NavigationManager navigationManager = new NavigationManager();
        NavCache.setNavigationManager(navigationManager);
        return navigationManager;
    }

    /**
     * @return Returns an XMLLoader for the ApplicationRulesManager
     */
    @Bean
    public ResourceLoader<ApplicationRulesManager> applicationRulesManagerPropertiesLoader() {
        ResourceLoader<ApplicationRulesManager> rulesManagerResourceLoader = new ResourceLoader<>();
        rulesManagerResourceLoader.setManager(applicationRulesManager());
        return rulesManagerResourceLoader;
    }

    /**
     * @return Returns a ApplicationRulesManager
     */
    @Bean
    public ApplicationRulesManager applicationRulesManager() {
        ApplicationRulesManager applicationRulesManager = new ApplicationRulesManager();
        ContextManager.setApplicationRulesManager(applicationRulesManager);
        return applicationRulesManager;
    }
}

