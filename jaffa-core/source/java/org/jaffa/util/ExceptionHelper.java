/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2002 JAFFA Development Group
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
 * 1.	Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.	Redistributions in binary form must reproduce the above copyright notice,
 * 	this list of conditions and the following disclaimer in the documentation
 * 	and/or other materials provided with the distribution.
 * 3.	The name "JAFFA" must not be used to endorse or promote products derived from
 * 	this Software without prior written permission. For written permission,
 * 	please contact mail to: jaffagroup@yahoo.com.
 * 4.	Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 * 	appear in their names without prior written permission.
 * 5.	Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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

package org.jaffa.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.jaffa.datatypes.Parser;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.engines.jdbcengine.LockedApplicationException;
import org.jaffa.persistence.engines.jdbcengine.SQLApplicationException;
import org.jaffa.persistence.exceptions.AlreadyLockedObjectException;
import org.jaffa.session.ContextManagerFactory;

/** This has convenience methods for dealing with Exceptions.
 */
public class ExceptionHelper {
    private static Logger log = Logger.getLogger(ExceptionHelper.class);
    private static final String CUSTOM_SQL_ERROR_CODE_RULE_NAME = "jaffa.persistence.jdbcengine.customSqlErrorCodeRange";
    private static Integer c_customSqlErrorCodeStart = null;
    private static Integer c_customSqlErrorCodeEnd = null;

    static {
        if (log.isDebugEnabled())
            log.debug("Initializing the ExceptionHelper");

        // Determine the customSqlErrorRange
        String customSqlErrorCodeRange = (String) ContextManagerFactory.instance().getProperty(CUSTOM_SQL_ERROR_CODE_RULE_NAME);
        if (customSqlErrorCodeRange != null) {
            String[] rangeBoundary = customSqlErrorCodeRange.split(",");
            if (rangeBoundary.length == 2) {
                try {
                    int start = Parser.parseInteger(rangeBoundary[0]).intValue();
                    int end = Parser.parseInteger(rangeBoundary[1]).intValue();
                    if (start <= end) {
                        c_customSqlErrorCodeStart = new Integer(start);
                        c_customSqlErrorCodeEnd = new Integer(end);
                        if (log.isInfoEnabled())
                            log.info("The JdbcEngine will perform conversion of SQLException to ApplicationException when it encounters ErrorCodes in the range " + c_customSqlErrorCodeStart + " to " + c_customSqlErrorCodeEnd);
                    } else
                        log.warn("Invalid value '" + customSqlErrorCodeRange + "' found for the rule '" + CUSTOM_SQL_ERROR_CODE_RULE_NAME + "'. It should be a comma-separated list of 2 integers, the first being <= the second. The JdbcEngine will not perform any conversion of SQLException to ApplicationException");
                } catch (Exception e) {
                    log.warn("Invalid value '" + customSqlErrorCodeRange + "' found for the rule '" + CUSTOM_SQL_ERROR_CODE_RULE_NAME + "'. It should be a comma-separated list of 2 integers. The JdbcEngine will not perform any conversion of SQLException to ApplicationException", e);
                }
            } else
                log.warn("Invalid value '" + customSqlErrorCodeRange + "' found for the rule '" + CUSTOM_SQL_ERROR_CODE_RULE_NAME + "'. It should be a comma-separated list of 2 integers. The JdbcEngine will not perform any conversion of SQLException to ApplicationException");
        } else if (log.isInfoEnabled())
            log.info("No value found for the rule '" + CUSTOM_SQL_ERROR_CODE_RULE_NAME + "'. The JdbcEngine will not perform any conversion of SQLException to ApplicationException");
    }
    
    /** This method will loop through the input exception and its cause, looking for an instance of ApplicationException or ApplicationExceptions.
     * If ApplicationException is found, it'll be returned wrapped inside a new ApplicationExceptions instance.
     * If ApplicationExceptions is found, it'll be returned as is.
     * Else a null will be returned.
     * @param exception The input.
     * @return an instance of ApplicationExceptions if found in the cause stack of the input exception.
     */
    public static ApplicationExceptions extractApplicationExceptions(Throwable exception) {
        return (ApplicationExceptions) extractException(exception, ApplicationExceptions.class);
    }
    
    /** This method will loop through the the input exception and its cause, looking for an instance of FrameworkException.
     * If found, the instance of FrameworkException will be returned.
     * Else a null will be returned.
     * @param exception The input.
     * @return an instance of FrameworkException if found in the cause stack of the input exception.
     */
    public static FrameworkException extractFrameworkException(Throwable exception) {
        return (FrameworkException) extractException(exception, FrameworkException.class);
    }
    
    /** This method will loop through the the input exception and its cause, looking for an instance of the input exceptionClass.
     * If found, that instance will be returned.
     * Else a null will be returned.
     * @param exception The input.
     * @param exceptionClass The class whose instance is to be looked for.
     * @return an instance of the input exceptionClass if found in the cause stack of the input exception.
     */
    public static Throwable extractException(Throwable exception, Class exceptionClass) {
        while (exception != null) {
            if (exceptionClass.isInstance(exception))
                return exception;
            else if (exceptionClass == ApplicationExceptions.class && exception instanceof ApplicationException)
                return new ApplicationExceptions((ApplicationException) exception);
            else if (ApplicationException.class.isAssignableFrom(exceptionClass) && exception instanceof ApplicationExceptions && ((ApplicationExceptions) exception).size() > 0) {
                for (ApplicationException ae : ((ApplicationExceptions) exception).getApplicationExceptionArray()) {
                    Throwable e = extractException(ae, exceptionClass);
                    if (e != null)
                        return e;
                }
            }
            exception = exception.getCause();
        }
        return null;
    }
    
    /** This method will loop through the input exception and its cause, looking for an instance of ApplicationException or ApplicationExceptions or FrameworkException.
     * If ApplicationException is found, it'll be thrown wrapped inside a new ApplicationExceptions instance.
     * If ApplicationExceptions is found, it'll be thrown as is.
     * If FrameworkException is found, it'll be thrown as is.
     * Else the input exception will be returned.
     * @param exception The input.
     * @throws ApplicationExceptions if found in the cause stack of the input exception.
     * @throws FrameworkException if found in the cause stack of the input exception.
     * @return the input exception if neither ApplicationException nor ApplicationExceptions nor FrameworkException are found in the cause stack of the input exception.
     */
    public static Throwable throwAF(Throwable exception) throws ApplicationExceptions, FrameworkException {
        ApplicationExceptions appExps = extractApplicationExceptions(exception);
        if (appExps != null)
            throw appExps;
        
        FrameworkException fe = extractFrameworkException(exception);
        if (fe != null)
            throw fe;
        
        return exception;
    }
    
    /** This method will loop through the input exception and its cause, looking for an instance of ApplicationException or ApplicationExceptions or FrameworkException.
     * If ApplicationException is found, it'll be thrown wrapped inside a new ApplicationExceptions instance.
     * If ApplicationExceptions is found, it'll be thrown as is.
     * If FrameworkException is found, it'll be thrown as is.
     * Else the input exception will be returned wrapped inside a new RuntimeException instance.
     * @param exception The input.
     * @throws ApplicationExceptions if found in the cause stack of the input exception.
     * @throws FrameworkException if found in the cause stack of the input exception.
     * @return RuntimeException if neither ApplicationException nor ApplicationExceptions nor FrameworkException are found in the cause stack of the input exception.
     */
    public static RuntimeException throwAFR(Throwable exception) throws ApplicationExceptions, FrameworkException {
        throwAF(exception);
        return exception instanceof RuntimeException ? (RuntimeException) exception : new RuntimeException(null, exception);
    }

    /**
     * Returns the stacktrace of the input exception as a String.
     * @param exception an exception.
     * @return the stacktrace of the input exception as a String.
     */
    public static String getStackTrace(Throwable exception) {
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw));
        sw.flush();
        return sw.toString();
    }

    /** 
     * Extracts the error message from the input Exception.
     * @param exception an exception.
     * @return the message from the input Exception.
     */
    public static String extractErrorMessage(Throwable exception) {
        String errorMessage = null;
        int i = 0;
        try {
            throwAF(exception);
            while(exception instanceof RuntimeException && i++ < 5){// stop going into recursive loop
                exception = exception.getCause();
            }
            errorMessage = exception!=null ? exception.getLocalizedMessage() : null;
        } catch (ApplicationExceptions | FrameworkException e) {
            errorMessage = e.getLocalizedMessage();
        }
        if (errorMessage == null && exception!=null)
            errorMessage = exception.getClass().getName();
        return errorMessage;
    }

    /**
     * This method will loop through the input exception and its cause, looking for an instance of ApplicationException or ApplicationExceptions.
     * If ApplicationException is found, it'll be returned wrapped inside a new ApplicationExceptions instance.
     * If ApplicationExceptions is found, it'll be returned as is.
     * It will return an instance of LockedApplicationException wrapped inside a new ApplicationExceptions instance if any lockign errors had occured.
     * It will then loop through the cause of the input exception, looking for an instance of SQLException
     * If the error code for the SQLException falls in the range specified by the global-rule 'jaffa.persistence.jdbcengine.customSqlErrorCodeRange',
     * an instance of SQLApplicationException will be returned wrapped inside a new ApplicationExceptions instance.
     * Else a null will be returned.
     *
     * @param exception The input exception.
     * @return an instance of ApplicationExceptions if found in the cause stack of the input exception.
     */
    public static ApplicationExceptions extractApplicationExceptionsFromSQLException(SQLException exception) {
        ApplicationExceptions appExps = null;

        // Check for SQLException
        SQLException sqlException = (SQLException) ExceptionHelper.extractException(exception, SQLException.class);
        if (sqlException != null) {
            if ("40001".equals(sqlException.getSQLState()) || "61000".equals(sqlException.getSQLState())) {
                if (log.isDebugEnabled())
                    log.debug("Found a SQL for locking errors: " + sqlException.getSQLState());
                appExps = new ApplicationExceptions(new LockedApplicationException(exception));
            } else {
                int errorCode = sqlException.getErrorCode();
                if (c_customSqlErrorCodeStart != null) {
                    if (errorCode >= c_customSqlErrorCodeStart.intValue() && errorCode <= c_customSqlErrorCodeEnd.intValue()) {
                        //Extract the first line from the message, since we do not want to pass the SQL stacktrace
                        String reason = sqlException.getMessage();
                        if (reason != null) {
                            int i = reason.indexOf('\n');
                            if (i > -1)
                                reason = reason.substring(0, i);
                        }
                        if (log.isDebugEnabled())
                            log.debug("Convert SQLException to SQLApplicationException since it has a custom sqlErrorCode of " + errorCode);
                        appExps = new ApplicationExceptions(new SQLApplicationException(reason, exception));
                    } else {
                        if (log.isDebugEnabled())
                            log.debug("Ignoring SQLException since its ErrorCode is not custom: " + errorCode);
                    }
                }
            }
        }
        return appExps;
    }
}
