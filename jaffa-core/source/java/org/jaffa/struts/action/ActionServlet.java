package org.jaffa.struts.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.apache.struts.config.FormBeanConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.ModuleConfigFactory;
import org.jaffa.util.OrderedPathMatchingResourcePatternResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.xml.sax.SAXException;

/**
 * Custom Struts Action Servlet to read struts-config from classpath with in
 * jars
 */
public class ActionServlet extends org.apache.struts.action.ActionServlet {

	/** Set up Logging for Log4J */
	private static Logger log = Logger.getLogger(ActionServlet.class);

	protected ModuleConfig initModuleConfig(String prefix, String paths) throws ServletException {
		if (log.isDebugEnabled()) {
			log.debug("Initializing module path '" + prefix + "' configuration from '" + paths + "'");
		}
		
		//to support default behaviour.
		if(!"classpath*:/META-INF/struts-config.xml".equals(paths)){
			return super.initModuleConfig(prefix, paths);
		}

		ModuleConfigFactory factoryObject = ModuleConfigFactory.createFactory();
		ModuleConfig config = factoryObject.createModuleConfig(prefix);

		Digester digester = initConfigDigester();

		PathMatchingResourcePatternResolver resolver = OrderedPathMatchingResourcePatternResolver.getInstance();
		try {
			Resource[] resources = resolver.getResources(paths);
			if (resources != null && resources.length > 0) {
				for (Resource resource : resources) {
					digester.push(config);

					if (resource == null) {
						continue;
					}
					parseModuleConfigFile(digester, resource);
				}
			} else {
				String msg = internal.getMessage("configMissing", paths);
				log.error(msg);
				throw new UnavailableException(msg);
			}

			getServletContext().setAttribute("org.apache.struts.action.MODULE" + config.getPrefix(), config);

			FormBeanConfig[] fbs = config.findFormBeanConfigs();
			for (int i = 0; i < fbs.length; i++) {
				if (fbs[i].getDynamic()) {
					fbs[i].getDynaActionFormClass();
				}
			}
		} catch (IOException ie) {
			throw new ServletException(ie);
		}

		return config;
	}

	/**
	 * parseModuleConfig to load struts-config.xml from each jar in classpath
	 */
	protected void parseModuleConfigFile(Digester digester, Resource resource) throws UnavailableException {

		if (log.isDebugEnabled()) {
			log.debug("Parsing struts-config files from classpath");
		}

		try {
			digester.parse(resource.getInputStream());
		} catch (SAXException | IOException e) {
			String msg = getInternal()!=null ? getInternal().getMessage("configParse", resource.getFilename()) : "Exception while parsing struts-config.xml";
			log.error(msg, e);
			throw new UnavailableException(msg);
		}
	}

}
