package org.jaffa.loader.soa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.jaffa.loader.ContextKey;
import org.jaffa.loader.IManager;
import org.jaffa.loader.IRepository;
import org.jaffa.soa.services.configdomain.SoaEventInfo;
import org.jaffa.soa.services.configdomain.SoaEvents;
import org.jaffa.util.JAXBHelper;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;

/**
 * This class is responsible for managing the "soa-events.xml". This class has
 * all the interface methods where consumer can invoke register /unregister the
 * soaEventInfo objects.
 * 
 */
public class SoaEventManager implements IManager {

	/**
	 * The name of the configuration file which this class handles.
	 */
	private static final String DEFAULT_CONFIGURATION_FILE = "soa-events.xml";

	/**
	 * This holds the SoaEventInfo with key of SoaEventName
	 */
    private IRepository<SoaEventInfo> soaEventRepository;

	/**
	 * The location of the schema for the configuration file.
	 */
	private static final String CONFIGURATION_SCHEMA_FILE = "org/jaffa/soa/services/configdomain/soa-events_1_0.xsd";


    /**
	 * @return the soaEventRepository
	 */
	public IRepository<SoaEventInfo> getSoaEventRepository() {
		return soaEventRepository;
	}

	/**
	 * @param soaEventRepository the soaEventRepository to set
	 */
	public void setSoaEventRepository(IRepository<SoaEventInfo> soaEventRepository) {
		this.soaEventRepository = soaEventRepository;
	}

	/**
     * register SoaEventInfo to the repository
     * @param soaEventInfo registers to the repository
     * @param contextKey with which repository to be associated with
     */
    public void registerSoaEventInfo(ContextKey contextKey, SoaEventInfo soaEventInfo) {
    	soaEventRepository.register(contextKey, soaEventInfo);

    }
    
    /**
     * unregister SoaEventInfo from the repository
     * @param contextKey with which repository to be associated with
     */
    public void unregisterSoaEventInfo(ContextKey contextKey) {
    	soaEventRepository.unregister(contextKey);
    }

    /**
     * retrieves the SoaEventInfo from the repository
     * @param soaEventName key used for the repository
     * @return SoaEventInfo
     */
	public SoaEventInfo getSoaEventInfo(String soaEventName) {
		return soaEventRepository.query(soaEventName);
	}
    
    /**
     * retrieves all the SoaEventInfo from the repository based on the contextOrder provided
     * assumes defaultContextOrder from the configuration when contextOrder is not provided
     * @param contextOrderParam order of the contexts to be searched
     * @return List of all values
     */
	public SoaEventInfo[] getAllSoaEventInfo(List<String> contextOrderParam) {
		return soaEventRepository.getValues().toArray(new SoaEventInfo[0]);
	}
	
    /**
     * retrieves all the SoaEventNames in the repository
     * @return Array of all SoaEventNames
     */
    public String[] getSoaEventNames() {
        return soaEventRepository.getAllKeys().toArray(new String[0]);
    }	
    
    /**
     * {@inheritDoc}
     */
	@Override
	public void registerResource(Resource resource, String context, String variation) throws JAXBException, SAXException, IOException {
		SoaEvents soaEvents = JAXBHelper.unmarshalConfigFile(SoaEvents.class, resource, CONFIGURATION_SCHEMA_FILE);

		if (soaEvents != null) {
			for (SoaEventInfo soaEventInfo : soaEvents.getSoaEvent()) {
			    ContextKey contextKey = new ContextKey(soaEventInfo.getName(), resource.getURI().toString(), variation, context);
				registerSoaEventInfo(contextKey, soaEventInfo);
			}
		}
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public String getResourceFileName() {
		return DEFAULT_CONFIGURATION_FILE;
	}

}
