package org.jaffa.struts.action;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.xml.sax.SAXException;

import javax.servlet.UnavailableException;
import java.io.IOException;

/**
 * Custom Struts Action Servlet to read struts-config from classpath with in jars
 */
public class ActionServlet extends org.apache.struts.action.ActionServlet{

    /** Set up Logging for Log4J */
    private static Logger log = Logger.getLogger(ActionServlet.class);


    @Override
    /**
     * parseModuleConfig to load struts-config.xml from each jar in classpath
     */
    protected void parseModuleConfigFile(Digester digester, String path) throws UnavailableException {

        if(log.isDebugEnabled()){
            log.debug("Parsing struts-config files from classpath");
        }

        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources(path);
            if(resources!=null && resources.length > 0){
                for(Resource resource : resources) {
                    digester.parse(resource.getInputStream());
                }
            }else{
                String msg = internal.getMessage("configMissing", path);
                log.error(msg);
                throw new UnavailableException(msg);
            }
        } catch (SAXException | IOException e) {
            String msg = getInternal().getMessage("configParse", path);
            log.error(msg, e);
            throw new UnavailableException(msg);
        }
    }

}
