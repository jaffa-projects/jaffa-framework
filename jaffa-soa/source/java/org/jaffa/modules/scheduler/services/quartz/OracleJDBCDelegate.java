/*
 *
 *  **************************************************TAPESTRY SOLUTIONS PROPRIETARY VER 2.0
 *  *
 *  *  Copyright © 2016 Tapestry Solutions, Inc.
 *  *  THIS PROGRAM IS PROPRIETARY TO TAPESTRY SOLUTIONS, INC.
 *  *  REPRODUCTION, DISCLOSURE, OR USE, IN WHOLE OR IN PART,
 *  *  UNDERTAKEN EXCEPT WITH PRIOR WRITTEN AUTHORIZATION OF
 *  *  TAPESTRY SOLUTIONS IS PROHIBITED.
 *  *
 *  **************************************************TAPESTRY SOLUTIONS PROPRIETARY VER 2.0
 *
 */

package org.jaffa.modules.scheduler.services.quartz;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.jaffa.persistence.engines.jdbcengine.querygenerator.TypeDefs;
import org.jaffa.util.ExceptionHelper;
import org.quartz.JobDetail;
import org.quartz.impl.jdbcjobstore.NoSuchDelegateException;
import org.quartz.impl.jdbcjobstore.StdJDBCDelegate;
import org.slf4j.LoggerFactory;

/** This is an extension of Quartz's StdJDBCDelegate. Use this under the following scenarios:
 * 1- You are using an Oracle database for the Quartz tables
 * 2- The Oracle database is pre-9.2 version
 * 3- The Oracle JDBC driver is outdated
 * <p>
 * JaffaScheduler writes a serialized object into the JobDataMap of a Job. Quartz's StdJDBCDelegate fails
 * if the above conditions are true, and if the size of the object exceeds 4000 bytes.
 * <p>
 * This extension to Quartz's StdJDBCDelegate utilizes JaffaCore's TypeDefs class, which in turn
 * uses oracle-specific code for inserting BLOBs.
 * <p>
 * This class overrides the methods which deal with Job data only. It does not override the methods
 * that deal with Trigger data, since JaffaScheduler does not add anything into a Trigger's JobDataMap.
 */
public class OracleJDBCDelegate extends StdJDBCDelegate {

    private static final Logger log = Logger.getLogger(OracleJDBCDelegate.class);

    // Accessor the 'private static Blob createBlob(Connection connection, byte[] value)' method of the TypeDefs class,
    // which utilizes oracle-specific code for inserting BLOBs
    private static Method c_createBlob = null;
    static {
        try {
            c_createBlob = TypeDefs.class.getDeclaredMethod("createBlob", Connection.class, new byte[0].getClass());
            c_createBlob.setAccessible(true);
        } catch (Exception e) {
            String errorMsg = "Reflection of 'private static Blob createBlob(Connection connection, byte[] value)' method of the TypeDefs class has failed";
            log.fatal(errorMsg, e);
            throw new RuntimeException(errorMsg, e);
        }
    }

    /** Creates an instance. The arguments are simply passed to the base class
     * @param logger the logger to use during execution
     * @param tablePrefix the prefix of all table names
     * @param instanceId the instance id
     */
    public OracleJDBCDelegate(Log logger, String tablePrefix, String instanceId) {
        this(LoggerFactory.getLogger(logger.getClass()), tablePrefix, instanceId, Boolean.FALSE);
    }

    /** Creates an instance. The arguments are simply passed to the base class
     * @param logger the logger to use during execution
     * @param tablePrefix the prefix of all table names
     * @param instanceId the instance id
     * @param useProperties indicates if only String data is being added to the JobDataMap
     */
    public OracleJDBCDelegate(org.slf4j.Logger logger, String tablePrefix, String instanceId, Boolean useProperties) {
        super();
        try {
            initialize(LoggerFactory.getLogger(logger.getClass()), tablePrefix, null, instanceId, null, useProperties, null);
        } catch (NoSuchDelegateException e) {
            log.fatal(e);
        }
    }


    /** Insert the job detail record.
     * <p>
     * THIS IS A COPY OF THE METHOD IN THE BASE CLASS.
     * THE ONLY DIFFERENCE IS THAT IT USES THE TypeDefs CLASS TO CREATE THE BLOB.
     * <p>
     * @param conn the DB Connection
     * @param job the job to insert
     * @return number of rows inserted
     * @throws IOException if there were problems serializing the JobDataMap
     * @throws SQLException if a database access error occurs.
     */
    public int insertJobDetail(Connection conn, JobDetail job) throws IOException, SQLException {
        ByteArrayOutputStream baos = serializeJobData(job.getJobDataMap());
        PreparedStatement ps = null;
        int insertResult = 0;
        try {
            if (log.isDebugEnabled())
                log.debug("Inserting a JobDetail record");
            ps = conn.prepareStatement(rtp(INSERT_JOB_DETAIL));
            ps.setString(1, job.getKey().getName());
            ps.setString(2, job.getKey().getGroup());
            ps.setString(3, job.getDescription());
            ps.setString(4, job.getJobClass().getName());
            setBoolean(ps, 5, job.isDurable());
            setBoolean(ps, 6, job.isConcurrentExectionDisallowed());
            setBoolean(ps, 7, job.isPersistJobDataAfterExecution());
            setBoolean(ps, 8, job.requestsRecovery());
            ps.setBlob(9, (Blob) c_createBlob.invoke(null, extractWrappedConnection(conn), baos.toByteArray())); //ps.setBytes(9, baos.toByteArray())


            insertResult = ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            SQLException se = (SQLException) ExceptionHelper.extractException(e, SQLException.class);
            if (se != null)
                throw se;
            else {
                String s = "Error in BLOB creation";
                log.error(s, e);
                throw new RuntimeException(s, e);
            }
        } finally {
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException ignore) {
                }
            }
        }
        // TODO: Validate
        // - JobDetails and Triggers are no longer configured with a list of names of listeners to notify, instead listeners identify which jobs/triggers they're interested in.
        // - Listeners are now assigned a set of Matcher instances - which provide matching rules for jobs/triggers they wish to receive events for.
        // - Listeners are now managed through a ListenerManager API, rather than directly with the Scheduler API.
        // if (insertResult > 0) {
        //    String[] jobListeners = job.getJobListenerNames();
        //    for (int i = 0; jobListeners != null && i < jobListeners.length; i++)
        //        insertJobListener(conn, job, jobListeners[i]);
        // }
        return insertResult;
    }

    /** Update the job detail record.
     * <p>
     * THIS IS A COPY OF THE METHOD IN THE BASE CLASS.
     * THE ONLY DIFFERENCE IS THAT IT USES THE TypeDefs CLASS TO CREATE THE BLOB.
     * <p>
     * @param conn the DB Connection
     * @param job the job to insert
     * @return number of rows inserted
     * @throws IOException if there were problems serializing the JobDataMap
     * @throws SQLException if a database access error occurs.
     */
    public int updateJobDetail(Connection conn, JobDetail job) throws IOException, SQLException {
        ByteArrayOutputStream baos = serializeJobData(job.getJobDataMap());
        PreparedStatement ps = null;
        int insertResult = 0;
        try {
            if (log.isDebugEnabled())
                log.debug("Updating a JobDetail record");
            ps = conn.prepareStatement(rtp(UPDATE_JOB_DETAIL));
            ps.setString(1, job.getDescription());
            ps.setString(2, job.getJobClass().getName());
            setBoolean(ps, 3, job.isDurable());
            setBoolean(ps, 4, job.isConcurrentExectionDisallowed());
            setBoolean(ps, 5, job.isPersistJobDataAfterExecution());
            setBoolean(ps, 6, job.requestsRecovery());
            ps.setBlob(7, (Blob) c_createBlob.invoke(null, extractWrappedConnection(conn), baos.toByteArray())); //ps.setBytes(7, baos.toByteArray());
            ps.setString(8, job.getKey().getName());
            ps.setString(9, job.getKey().getGroup());

            insertResult = ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            SQLException se = (SQLException) ExceptionHelper.extractException(e, SQLException.class);
            if (se != null)
                throw se;
            else {
                String s = "Error in BLOB creation";
                log.error(s, e);
                throw new RuntimeException(s, e);
            }
        } finally {
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException ignore) {
                }
            }
        }
        // TODO: Validate
        // - JobDetails and Triggers are no longer configured with a list of names of listeners to notify, instead listeners identify which jobs/triggers they're interested in.
        // - Listeners are now assigned a set of Matcher instances - which provide matching rules for jobs/triggers they wish to receive events for.
        // - Listeners are now managed through a ListenerManager API, rather than directly with the Scheduler API.
        // if (insertResult > 0) {
        //    deleteJobListeners(conn, job.getKey().getName(), job.getKey().getGroup());
        //    
        //    String[] jobListeners = job.getJobListenerNames();
        //    for (int i = 0; jobListeners != null && i < jobListeners.length; i++)
        //       insertJobListener(conn, job, jobListeners[i]);
        // }
        return insertResult;
    }

    /** Update the job data map for the given job.
     * <p>
     * THIS IS A COPY OF THE METHOD IN THE BASE CLASS.
     * THE ONLY DIFFERENCE IS THAT IT USES THE TypeDefs CLASS TO CREATE THE BLOB.
     * <p>
     * @param conn the DB Connection
     * @param job the job to insert
     * @return number of rows inserted
     * @throws IOException if there were problems serializing the JobDataMap
     * @throws SQLException if a database access error occurs.
     */
    public int updateJobData(Connection conn, JobDetail job) throws IOException, SQLException {
        ByteArrayOutputStream baos = serializeJobData(job.getJobDataMap());
        PreparedStatement ps = null;
        try {
            if (log.isDebugEnabled())
                log.debug("Updating the job data map");
            ps = conn.prepareStatement(rtp(UPDATE_JOB_DATA));
            ps.setBlob(1, (Blob) c_createBlob.invoke(null, extractWrappedConnection(conn), baos.toByteArray()));  //ps.setBytes(1, baos.toByteArray())
            ps.setString(2, job.getKey().getName());
            ps.setString(3, job.getKey().getGroup());

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            SQLException se = (SQLException) ExceptionHelper.extractException(e, SQLException.class);
            if (se != null)
                throw se;
            else {
                String s = "Error in BLOB creation";
                log.error(s, e);
                throw new RuntimeException(s, e);
            }
        } finally {
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException ignore) {
                }
            }
        }
    }

    /** Quartz-1.6.x wraps the original Connection inside a Proxy.
     * This method will extract the original Connection, allowing access to vendor specific operations
     */
    private Connection extractWrappedConnection(Connection conn) {
        if (conn instanceof Proxy) {
            try {
                if (log.isDebugEnabled())
                    log.debug("Extracting a WrappedConnection from the Proxy: " + conn);
                Proxy connProxy = (Proxy) conn;
                InvocationHandler invocationHandler = Proxy.getInvocationHandler(connProxy);
                Method m = invocationHandler.getClass().getMethod("getWrappedConnection");
                if (Connection.class.isAssignableFrom(m.getReturnType())) {
                    Connection wrappedConnection = (Connection) m.invoke(invocationHandler);
                    if (log.isDebugEnabled())
                        log.debug("Extracted WrappedConnection: " + wrappedConnection);
                    return wrappedConnection;
                } else {
                    if (log.isDebugEnabled())
                        log.debug("WrappedConnection cannot be extracted since the method 'public Connection getWrappedConnection()' is missing from the InvocationHandler: " + invocationHandler);
                }
            } catch (Exception e) {
                // do nothing
                if (log.isDebugEnabled())
                    log.debug("Exception in obtaining the WrappedConnection from the Proxy: " + conn, e);
            }
        }
        return conn;
    }
}