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

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.messaging.services.configdomain.JmsConfig;
import org.jaffa.modules.messaging.services.configdomain.Param;
import org.jaffa.rules.util.ScriptHelper;
import org.jaffa.util.ExceptionHelper;

public final class InitialContextFactrory {

  private static final Logger LOGGER = Logger
      .getLogger(InitialContextFactrory.class);

  private static final InitialContextFactrory INSTANCE = new InitialContextFactrory();

  /** The context used to obtain various JMS objects using JNDI. */
  private InitialContext context;

  private InitialContextFactrory() {
  }

  public static void cleanInitialContext() {
    INSTANCE.closeInitialContext();
  }

  private void closeInitialContext() {
    if (context != null) {
      try {
        context.close();
      } catch (NamingException e) {
        if (LOGGER.isDebugEnabled())
          LOGGER.debug("Error in closing the JNDI context.", e);
      }
      context = null;
    }
  }

  /**
   * Returns an InitialContext.
   * 
   * @throws FrameworkException
   *           in case any internal error occurs.
   * @throws ApplicationExceptions
   *           Indicates application error(s).
   * @return an InitialContext.
   */
  public static InitialContext obtainInitialContext()
      throws FrameworkException, ApplicationExceptions {
    if (INSTANCE.context == null)
      INSTANCE.createInitialContext();
    return INSTANCE.context;
  }

  /**
   * Returns the value from the input Param object. Uses BSF to evaluate the
   * value, in case its an expression.
   * 
   * @param param
   *          the Param object.
   * @param bean
   *          the bean to be used in the context of BSF.
   * @return the value from the input Param object.
   * @throws FrameworkException
   *           in case any internal error occurs.
   * @throws ApplicationExceptions
   *           Indicates application error(s).
   */
  public static Object obtainParamValue(final Param param, final Object bean)
      throws FrameworkException, ApplicationExceptions {
    return INSTANCE.obtainParamObject(param, bean);
  }

  /**
   * Creates an InitialContext.
   * 
   * @throws FrameworkException
   *           in case any internal error occurs.
   * @throws ApplicationExceptions
   *           Indicates application error(s).
   */
  private void createInitialContext() throws FrameworkException,
      ApplicationExceptions {
    try {
      if (context == null) {
        JmsConfig jmsConfig = ConfigurationService.getInstance().getJmsConfig();
        if (jmsConfig != null && jmsConfig.getJndiContext() != null
            && jmsConfig.getJndiContext().getParam() != null) {
          Properties properties = new Properties();
          for (Param param : jmsConfig.getJndiContext().getParam())
            properties.put(param.getName(), obtainParamValue(param));
          context = new InitialContext(properties);
        } else
          context = new InitialContext();
      }
    } catch (NamingException e) {
      LOGGER.error("Error in creating a JNDI InitialContext", e);
      throw new JaffaMessagingFrameworkException(
          JaffaMessagingFrameworkException.INITIAL_CONTEXT_ERROR, null, e);
    }
  }

  /**
   * Returns the value from the input Param object. Uses BSF to evaluate the
   * value, in case its an expression.
   * 
   * @param param
   *          the Param object.
   * @return the value from the input Param object.
   * @throws FrameworkException
   *           in case any internal error occurs.
   * @throws ApplicationExceptions
   *           Indicates application error(s).
   */
  private Object obtainParamValue(final Param param) throws FrameworkException,
      ApplicationExceptions {
    return obtainParamValue(param, null);
  }

  /**
   * Returns the value from the input Param object. Uses BSF to evaluate the
   * value, in case its an expression.
   * 
   * @param param
   *          the Param object.
   * @param bean
   *          the bean to be used in the context of BSF.
   * @return the value from the input Param object.
   * @throws FrameworkException
   *           in case any internal error occurs.
   * @throws ApplicationExceptions
   *           Indicates application error(s).
   */
  private Object obtainParamObject(final Param param, final Object bean)
      throws FrameworkException, ApplicationExceptions {
    Object value = param.getValue();
    if (param.isExpression()) {
      try {
        value = ScriptHelper.instance(param.getLanguage()).evaluate(null,
            param.getValue(), bean, null, 0, 0);
        if (LOGGER.isDebugEnabled())
          LOGGER.debug("Evaluation of the expression '" + param.getValue()
              + "' is: " + value);
      } catch (Exception e) {
        throw ExceptionHelper.throwAFR(e);
      }
    }
    return value;
  }
}