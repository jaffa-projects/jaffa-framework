package helpers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.jaffa.config.Config;
import org.jaffa.persistence.engines.jdbcengine.configservice.initdomain.Database;
import org.jaffa.persistence.engines.jdbcengine.configservice.initdomain.Init;
import org.jaffa.persistence.engines.jdbcengine.configservice.initdomain.Param;
import org.jaffa.util.JAXBHelper;
import org.jaffa.util.URLHelper;
import org.jaffa.util.XmlHelper;

/*
 * ConnectionHelper.java
 *
 * Created on March 1, 2004, 11:55 AM
 */
/**
 *
 * @author  PaulE
 */
public class ConnectionHelper {

    private static final String SCHEMA = "org/jaffa/persistence/engines/jdbcengine/configservice/initdomain/jdbc-engine-init_1_0.xsd";
    static final String ENGINE = "engine";
    static final String URL = "url";
    static final String DRIVER_CLASS = "driverClass";
    static final String USER = "user";
    static final String PASSWORD = "password";
    static final String MAXIMUM_CONNECTIONS = "maximumConnections";

    public static Connection getConnection() throws Exception {
        Map info = ConnectionHelper.getDatabaseInfo();
        return ConnectionHelper.getConnection((String) info.get(DRIVER_CLASS), (String) info.get(URL), (String) info.get(USER), (String) info.get(PASSWORD));
    }

    public static String getEngineType() throws Exception {
        Map info = ConnectionHelper.getDatabaseInfo();
        return (String) info.get(ENGINE);
    }

    public static int getMaximumConnections() throws Exception {
        Map info = ConnectionHelper.getDatabaseInfo();
        return Integer.parseInt((String) info.get(MAXIMUM_CONNECTIONS));
    }

    private static Map getDatabaseInfo() throws Exception {
        URL initUrl = URLHelper.newExtendedURL((String) Config.getProperty(Config.PROP_JDBC_ENGINE_INIT));
        InputStream stream = null;

        try {
            stream = initUrl.openStream();

            // create a JAXBContext capable of handling classes generated into the package
            JAXBContext jc = JAXBContext.newInstance("org.jaffa.persistence.engines.jdbcengine.configservice.initdomain");

            // create an Unmarshaller
            Unmarshaller u = jc.createUnmarshaller();

            // enable validation
            u.setSchema(JAXBHelper.createSchema(SCHEMA));

            // unmarshal a document into a tree of Java content objects composed of classes from the package.
            Init init = (Init) u.unmarshal(XmlHelper.stripDoctypeDeclaration(stream));
            Database database = null;
            for (Iterator i = init.getDatabase().iterator(); i.hasNext();) {
                database = (Database) i.next();
                if (database.getName().equals("default")) {
                    break;
                }
            }

            if (database == null) {
                throw new Exception("The 'default' database has not been defined in init.xml ");
            }

            Map info = new HashMap();
            info.put(ENGINE, database.getEngine());
            for (Iterator i = database.getConnectionFactory().getParam().iterator(); i.hasNext();) {
                Param param = (Param) i.next();
                if (URL.equals(param.getName())) {
                    info.put(URL, param.getValue());
                } else if (DRIVER_CLASS.equals(param.getName())) {
                    info.put(DRIVER_CLASS, param.getValue());
                } else if (USER.equals(param.getName())) {
                    info.put(USER, param.getValue());
                } else if (PASSWORD.equals(param.getName())) {
                    info.put(PASSWORD, param.getValue());
                } else if (MAXIMUM_CONNECTIONS.equals(param.getName())) {
                    info.put(MAXIMUM_CONNECTIONS, param.getValue());
                }
            }
            return info;
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
            // do nothing
            }
        }
    }

    private static Connection getConnection(String driverClass, String url, String user, String password)
            throws ClassNotFoundException, SQLException {
        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url, user, password);
        connection.setAutoCommit(false);
        return connection;
    }
}
