package org.jaffa.loader;

import org.jaffa.transaction.services.configdomain.Config;
import org.jaffa.transaction.services.configdomain.TransactionInfo;
import org.jaffa.transaction.services.configdomain.TypeInfo;
import org.jaffa.util.JAXBHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.xml.sax.SAXException;
import org.springframework.core.io.Resource;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pbagirthi on 7/13/2017.
 */
public class TransactionManager implements IManager {

    private static final String DEFAULT_CONFIGURATION_FILE = "jaffa-transaction-config.xml";
    /**
     * The location of the schema for the configuration file.
     */
    private static final String CONFIGURATION_SCHEMA_FILE = "org/jaffa/transaction/services/configdomain/jaffa-transaction-config_1_0.xsd";

    private IRepository<String, TransactionInfo> transactionRepository;
    private IRepository<String, TypeInfo> typeInfoRepository;

    @Autowired
    @Qualifier("contextOrder")
    public ArrayList<String> contextOrder;

    public void registerTransactionInfo(String dataBeanClassName, TransactionInfo transactionInfo, String context) {
        transactionRepository.register(dataBeanClassName, transactionInfo, context);

    }

    public void registerTypeInfo(TypeInfo typeInfo, String context) {
        typeInfoRepository.register(typeInfo.getName(), typeInfo, context);
    }

    public void registerXML(Resource resource, String context) throws JAXBException, SAXException, IOException {

        Config config = JAXBHelper.unmarshalConfigFile(Config.class, resource, CONFIGURATION_SCHEMA_FILE);

        if (config.getTransactionOrType() != null) {
            for (final Object o : config.getTransactionOrType()) {
                if (o.getClass() == TransactionInfo.class) {
                    final TransactionInfo transactionInfo = (TransactionInfo) o;
                    transactionRepository.register(transactionInfo.getDataBean(), transactionInfo, context);
                } else if (o.getClass() == TypeInfo.class) {
                    final TypeInfo typeInfo = (TypeInfo) o;
                    typeInfoRepository.register(typeInfo.getName(), typeInfo, context);
                }
            }
        }
    }

    @Override
    public String getXmlFileName() {
        return DEFAULT_CONFIGURATION_FILE;
    }

    public void unregisterTransactionInfo(String transactionId, String context) {
        transactionRepository.unregister(transactionId, context);
    }

    public void unregisterTypeInfo(String typeInfoId, String context) {
        typeInfoRepository.unregister(typeInfoId, context);
    }

    public void unregisterXML(String uri) {
        //read XML
        //parse XML
        //call unregisterTransactionInfo
        //call unregisterTypeInfo
    }

    public TransactionInfo getTransactionInfo(String transactionId, List<String> contextOrderParam) {
        if (contextOrderParam == null)
            contextOrderParam = contextOrder;

        return transactionRepository.query(transactionId, contextOrderParam);
    }

    public TypeInfo getTypeInfo(String typeId, List<String> contextOrderParam) {
        if (contextOrderParam == null)
            contextOrderParam = contextOrder;

        return typeInfoRepository.query(typeId, contextOrderParam);
    }

    public TransactionInfo[] getAllTransactionInfo(List<String> contextOrderParam) {
        if (contextOrderParam == null)
            contextOrderParam = contextOrder;

        return transactionRepository.getAllValues(contextOrderParam).toArray(new TransactionInfo[0]);
    }

    public String[] getTypeNames() {
        return typeInfoRepository.getAllKeys().toArray(new String[0]);
    }


    public List<String> getContextOrder() {
        return contextOrder;
    }


    /**
     * Returns the TransactionInfo object for the input dataBeanClass, as defined in the configuration file.
     *
     * @param dataBeanClass the class for a dataBean.
     * @return the TransactionInfo object for the input dataBeanClass, as defined in the configuration file.
     */
    public TransactionInfo getTransactionInfo(Class dataBeanClass, List<String> contextOrder) {
        final String dataBeanClassName = dataBeanClass.getName();
        TransactionInfo transactionInfo = getTransactionInfo(dataBeanClassName, contextOrder);
        if (transactionInfo == null) {
            // Lookup the class heirarchy. Add a NULL for the dataBeanClassName, even if a TransactionInfo is not found
            while (dataBeanClass.getSuperclass() != null) {
                dataBeanClass = dataBeanClass.getSuperclass();
                transactionInfo = getTransactionInfo(dataBeanClass.getName(), contextOrder);
                if (transactionInfo != null)
                    break;
            }
            registerTransactionInfo(dataBeanClassName, transactionInfo, null);
        }
        return transactionInfo;
    }

    public void setContextOrder(ArrayList<String> contextOrder) {
        this.contextOrder = contextOrder;
    }

    public IRepository<String, TransactionInfo> getTransactionRepository() {
        return transactionRepository;
    }

    public void setTransactionRepository(IRepository<String, TransactionInfo> transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public IRepository<String, TypeInfo> getTypeInfoRepository() {
        return typeInfoRepository;
    }

    public void setTypeInfoRepository(IRepository<String, TypeInfo> typeInfoRepository) {
        this.typeInfoRepository = typeInfoRepository;
    }
}
