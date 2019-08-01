/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2014 JAFFA Development Group
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
 * 1.   Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.   Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 3.   The name "JAFFA" must not be used to endorse or promote products derived from
 *  this Software without prior written permission. For written permission,
 *  please contact mail to: jaffagroup@yahoo.com.
 * 4.   Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *  appear in their names without prior written permission.
 * 5.   Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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
package org.jaffa.modules.messaging.services;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.messaging.services.configdomain.JmsConfig;

public final class JaffaConnectionFactory {

  private static final Logger LOGGER = Logger
      .getLogger(ConnectionFactory.class);

  private static final JaffaConnectionFactory INSTANCE = new JaffaConnectionFactory();

  /**
   * A single connection to the JMS Provider will be used for now. If required
   * we can have a pool of connections in the future.
   */
  private Connection connection;

  private JaffaConnectionFactory() {
  }

  /**
   * Obtains the JMS ConnectionFactory from the JNDI context, as defined in the
   * configuration file.
   * 
   * @throws FrameworkException
   *           in case any internal error occurs.
   * @throws ApplicationExceptions
   *           Indicates application error(s).
   * @return the JMS ConnectionFactory from the JNDI context, as defined in the
   *         configuration file.
   */
  public synchronized static ConnectionFactory obtainConnectionFactory()
      throws ApplicationExceptions, FrameworkException {
    return INSTANCE.getConnectionFactory();
  }

  /**
   * Returns a JMS connection.
   * 
   * @throws FrameworkException
   *           in case any internal error occurs.
   * @throws ApplicationExceptions
   *           Indicates application error(s).
   * @return a JMS connection.
   */
  public static synchronized Connection obtainConnection() throws FrameworkException,
      ApplicationExceptions {
    return INSTANCE.getConnection();
  }

  /**
   * Close a JMS Connection.
   */
  public static void closeConnection() {
    INSTANCE.close();
  }

  /**
   * Obtains the JMS ConnectionFactory from the JNDI context, as defined in the
   * configuration file.
   * 
   * @throws FrameworkException
   *           in case any internal error occurs.
   * @throws ApplicationExceptions
   *           Indicates application error(s).
   * @return the JMS ConnectionFactory from the JNDI context, as defined in the
   *         configuration file.
   */
  private ConnectionFactory getConnectionFactory()
      throws ApplicationExceptions, FrameworkException {
    ConnectionFactory factory = null;
    try {
      final InitialContext context =
              InitialContextFactrory.obtainInitialContext();
      final JmsConfig jmsConfig =
              ConfigurationService.getInstance().getJmsConfig();

      if (jmsConfig != null) {
        factory = (ConnectionFactory)
                context.lookup(jmsConfig.getConnectionFactory());
      }
    } catch (NamingException e) {
      LOGGER.error("Error in locating the JMS ConnectionFactory", e);
      throw new JaffaMessagingFrameworkException(
          JaffaMessagingFrameworkException.CONNECTION_FACTORY_NOT_FOUND, null,
          e);
    }
    return factory;
  }

  /**
   * Returns a JMS connection.
   * 
   * @throws FrameworkException
   *           in case any internal error occurs.
   * @throws ApplicationExceptions
   *           Indicates application error(s).
   * @return a JMS connection.
   */
  private Connection getConnection() throws FrameworkException,
      ApplicationExceptions {
    if (connection == null)
      createConnection();
    return connection;
  }

  /**
   * Creates a JMS connection, only if one doesn't already exist.
   * 
   * @throws FrameworkException
   *           in case any internal error occurs.
   * @throws ApplicationExceptions
   *           Indicates application error(s).
   */
  private void createConnection() throws FrameworkException,
      ApplicationExceptions {
    if (connection == null) {
      Connection _connection = null;
      try {
        final ConnectionFactory connectionFactory = JaffaConnectionFactory
            .obtainConnectionFactory();

        final JmsConfig jmsConfig = ConfigurationService.getInstance()
            .getJmsConfig();
        if (jmsConfig == null || jmsConfig.getUser() == null)
          _connection = connectionFactory.createConnection();
        else
          _connection = connectionFactory.createConnection(jmsConfig.getUser(),
              jmsConfig.getPassword());

        // Register a listener to detect connection breakage
        _connection.setExceptionListener(new ExceptionListener() {

          public void onException(JMSException exception) {
            if (LOGGER.isInfoEnabled())
              LOGGER.info(
                  "The ExceptionListener registered with the JMS Connection '"
                      + connection
                      + "' has been invoked. Will recreate the JMS connection",
                  exception);
            try {
              forciblyCreateConnection();
            } catch (Exception e) {
              LOGGER.error("Error in recreating a JMS Connection", e);
            }
          }
        });

        // This will ensure that messages reach the temporary consumers that may
        // be created for deleting/resubmitting
        _connection.start();

        connection = _connection;

      } catch (JMSException e) {
        if (_connection != null) {
          try {
            _connection.close();
          } catch (JMSException e1) {
            LOGGER.error("Error closing a JMS Connection", e1);
          }
        }
        LOGGER.error("Error in creating a JMS Connection", e);
        throw new JaffaMessagingFrameworkException(
            JaffaMessagingFrameworkException.CONNECTION_ERROR, null, e);
      }
    }
  }

  /**
   * Creates a JMS connection, whether one exists currently or not.
   * 
   * @throws FrameworkException
   *           in case any internal error occurs.
   * @throws ApplicationExceptions
   *           Indicates application error(s).
   */
  private void forciblyCreateConnection() throws FrameworkException,
      ApplicationExceptions {
    // Maintain a reference to the previous Connection, if any
    final Connection previousConnection = connection;
    try {
      // Force the creation of a Connection
      connection = null;
      InitialContextFactrory.cleanInitialContext();
      createConnection();

    } finally {
      // Close the previous Connection, if any
      if (previousConnection != null) {
        try {
          previousConnection.close();
        } catch (JMSException e) {
          if (LOGGER.isDebugEnabled())
            LOGGER.debug("Error in closing the previous JMS Connection '"
                + previousConnection + '\'', e);
        }
      }
    }
  }

  /**
   * Close a JMS Connection.
   */
  private void close() {
    if (connection != null) {
      try {
        connection.close();
      } catch (JMSException e1) {
        LOGGER.error("Error closing a JMS Connection", e1);
      }
    }
  }
}