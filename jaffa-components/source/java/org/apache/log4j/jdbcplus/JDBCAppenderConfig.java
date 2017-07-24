package org.apache.log4j.jdbcplus;

import java.net.URL;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache	.log4j.xml.DOMConfigurator;
import org.jaffa.config.Config;
import org.jaffa.util.URLHelper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * This class initialise the Log4j.
 * 
 * This should be either imported into spring root @Configuration
 * e.g. @Import({ JDBCAppenderConfig.class)
 * 
 * or defined in web.xml
 * 
 * e.g.
 * <context-param> <param-name>contextConfigLocation</param-name>
 *   <param-value>org.apache.log4j.jdbcplus.JDBCAppenderConfig</param-value>
 * </context-param>
 * 
 */
@Configuration
public class JDBCAppenderConfig implements ApplicationContextAware{

	private static Logger log = null;
	private ApplicationContext applicationContext;

	/**
	 * Initialize log4j using the file specified in the 'framework.Log4JConfig'
	 * property in the config.properties file. This will be set to either
	 * 'none', 'default' or a classpath-relative file name. If there is no
	 * configuration setting 'default' will be assumed.
	 *
	 * For more information look at the documentation in 'config.properties'
	 */
	@PostConstruct
	private void initLog4j() {
		if (applicationContext == null) {
			return;
		}
		// Read setting from configuration file
		String fileName = (String) Config.getProperty(Config.PROP_LOG4J_CONFIG, "default");

		if (fileName.equalsIgnoreCase("none")) {
			// do nothing.. Assume that log4j would have been initialized by
			// some other container
			initializeLogField();
			log.info("Skipped log4j configuration. Should be done by Web/J2EE Server first!");
		} else if (fileName.equalsIgnoreCase("default")) {
			defaultLog4j();
		} else {
			try {
				URL u = URLHelper.newExtendedURL(fileName);
				DOMConfigurator.configureAndWatch(u.getPath());
				initializeLogField();
				if (log.isInfoEnabled())
					log.info("Configured log4j using the configuration file (relative to classpath): " + fileName);
			} catch (Exception e) {
				log.error("Error in initializing Log4j using the configFile (relative to classpath): " + fileName);
				e.printStackTrace();
				defaultLog4j();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	private void defaultLog4j() {
		BasicConfigurator.configure();
		initializeLogField();
		if (log.isInfoEnabled()) {
			log.info("Configured log4j using the Basic Configurator");
		}
	}

	private void initializeLogField() {
		if (log == null)
			log = Logger.getLogger(JDBCAppenderConfig.class);
	}

	@PreDestroy
	private void destroyLog4j() {
		LogManager.shutdown();
	}

}
