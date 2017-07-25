package org.apache.log4j.jdbcplus;

import java.net.URL;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jaffa.config.Config;
import org.jaffa.util.URLHelper;
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
public class JDBCAppenderConfig {

	private static Logger log = Logger.getLogger(JDBCAppenderConfig.class);;

	/**
	 * Initialise log4j using the file specified in the 'framework.Log4JConfig'
	 * property in the config.properties file. This will be set to either
	 * 'none', 'default' or a classpath-relative file name. If there is no
	 * configuration setting 'default' will be assumed.
	 *
	 * For more information look at the documentation in 'config.properties'
	 */
	@PostConstruct
	private void initLog4j() {

		// Read setting from configuration file
		String fileName = (String) Config.getProperty(Config.PROP_LOG4J_CONFIG, "default");

		if (fileName.equalsIgnoreCase("none")) {
			/*
			 * do nothing. Assume that log4j would have been initialised by some
			 * other container
			 */
			log.info("Skipped log4j configuration. Should be done by Web/J2EE Server first!");
		} else if (fileName.equalsIgnoreCase("default")) {
			defaultLog4j();
		} else {
			appLog4j(fileName);
		}
	}

	private void defaultLog4j() {
		BasicConfigurator.configure();
		if (log.isInfoEnabled()) {
			log.info("Configured log4j using the Basic Configurator");
		}
	}

	private void appLog4j(String fileName) {
		try {
			URL u = URLHelper.newExtendedURL(fileName);
			DOMConfigurator.configureAndWatch(u.getPath());
			if (log.isInfoEnabled()) {
				log.info("Configured log4j using the configuration file (relative to classpath): " + fileName);
			}
		} catch (Exception e) {
			log.error("Error in initializing Log4j using the configFile" + fileName + "(relative to classpath):" + e);
			defaultLog4j();
		}
	}

	@PreDestroy
	private void destroyLog4j() {
		LogManager.shutdown();
	}

}
