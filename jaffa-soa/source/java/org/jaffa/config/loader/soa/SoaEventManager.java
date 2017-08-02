package org.jaffa.config.loader.soa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.jaffa.loader.IManager;
import org.jaffa.loader.IRepository;
import org.jaffa.soa.services.configdomain.SoaEventInfo;
import org.jaffa.soa.services.configdomain.SoaEvents;
import org.jaffa.util.JAXBHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private IRepository<String, SoaEventInfo> soaEventRepository;

	/**
	 * The location of the schema for the configuration file.
	 */
	private static final String CONFIGURATION_SCHEMA_FILE = "org/jaffa/soa/services/configdomain/soa-events_1_0.xsd";

	@Autowired
	@Qualifier("contextOrder")
	public ArrayList<String> contextOrder;

	/**
	 * gets the contextOrder
	 * 
	 * @return List containing contextOrder
	 */
	public List<String> getContextOrder() {
		return contextOrder;
	}

	/**
	 *
	 * @param contextOrder
	 */
	public void setContextOrder(ArrayList<String> contextOrder) {
		this.contextOrder = contextOrder;
	}

    /**
	 * @return the soaEventRepository
	 */
	public IRepository<String, SoaEventInfo> getSoaEventRepository() {
		return soaEventRepository;
	}

	/**
	 * @param soaEventRepository the soaEventRepository to set
	 */
	public void setSoaEventRepository(IRepository<String, SoaEventInfo> soaEventRepository) {
		this.soaEventRepository = soaEventRepository;
	}

	/**
     * register SoaEventInfo to the repository
     * @param soaEventName key for the repository to be used for registering
     * @param SoaEventInfo registers to the repository
     * @param context with which repository to be associated with
     */
    public void registerSoaEventInfo(String soaEventName, SoaEventInfo soaEventInfo, String context) {
    	soaEventRepository.register(soaEventName, soaEventInfo, context);

    }
    
    /**
     * unregister SoaEventInfo from the repository
     * @param soaEventName key for the repository to be used for registering
     * @param context with which repository to be associated with
     */
    public void unregisterSoaEventInfo(String soaEventName, String context) {
    	soaEventRepository.unregister(soaEventName, context);
    }

    /**
     * retrieves the SoaEventInfo from the repository
     * @param dataBeanClassName key used for the repository
     * @param contextOrderParam Order of the contexts used for retrieval
     * @return SoaEventInfo
     */
	public SoaEventInfo getSoaEventInfo(String soaEventName, List<String> contextOrderParam) {
		if (contextOrderParam == null) {
			contextOrderParam = contextOrder;
		}

		return soaEventRepository.query(soaEventName, contextOrderParam);
	}
    
    /**
     * retrieves all the SoaEventInfo from the repository based on the contextOrder provided
     * assumes defaultContextOrder from the configuration when contextOrder is not provided
     * @param contextOrderParam order of the contexts to be searched
     * @return List of all values
     */
	public SoaEventInfo[] getAllSoaEventInfo(List<String> contextOrderParam) {
		if (contextOrderParam == null){
			contextOrderParam = contextOrder;
		}

		return soaEventRepository.getAllValues(contextOrderParam).toArray(new SoaEventInfo[0]);
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
	public void registerXML(Resource resource, String context) throws JAXBException, SAXException, IOException {
		SoaEvents soaEvents = JAXBHelper.unmarshalConfigFile(SoaEvents.class, resource, CONFIGURATION_SCHEMA_FILE);

		if (soaEvents != null) {
			for (SoaEventInfo soaEventInfo : soaEvents.getSoaEvent()) {
				registerSoaEventInfo(soaEventInfo.getName(), soaEventInfo, context);
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

}
