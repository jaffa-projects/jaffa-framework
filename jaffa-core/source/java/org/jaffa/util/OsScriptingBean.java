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

/*
 * OsScriptingBean.java
 *
 * Created on August 1, 2005, 7:12 AM
 */

package org.jaffa.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.jaffa.exceptions.ScriptingException;

/** OsScriptingBean is used for invoking OS commands and capturing their
 * output. In more complex cases that require environment variables to be set
 * and other setup and teardown code, it will write a temporary script that
 * will be executed. This script will be different based on OS being Windows
 * or Unix-based, and if Unix, whether it is CSH or SH based.
 *
 * @version 1.0
 * @author  PaulE
 *
 * @todo - Should delete the generated script file once its been executed.
 */
public class OsScriptingBean {
    private static Logger log = Logger.getLogger(OsScriptingBean.class);
    
    
    //---------------------------------------------------------
    // How much should we wait for the ouput streams to complete before we give up
    // How much to wait in each test cycle
    protected long incrementalOutputWaitTime = 100L; // 0.1 seconds
    // How much to wait in total
    protected long maxOutputWaitTime = 10000L; // 10 seconds
    // Can be used to destroy the process created by the exec() method.
    protected Process m_process;
    
    /**
     * Holds value of property command.
     */
    private String[] command;
    
    /**
     * Holds value of property environment.
     */
    private Properties environment;
    
    /**
     * Holds value of property setupScripts.
     */
    private List setupScripts;
    
    /**
     * Holds value of property teardownScripts.
     */
    private List teardownScripts;
    
    /**
     * Holds value of property output.
     */
    private String output;
    
    /**
     * Holds value of property error.
     */
    private String error;
    
    private StreamFilter errorFilter = null;
    private StreamFilter outputFilter = null;
    private File scriptFile = null;
    
    /**
     * Holds value of property unixShell.
     */
    private String unixShell = "/bin/sh";
    
    /**
     * Getter for property commandArgs.
     * @return Value of property commandArgs.
     */
    public String[] getCommand() {
        return this.command;
    }
    
    /**
     * Setter for property command.
     * @param command New value of property command.
     */
    public void setCommand(String[] command) {
        this.command = command;
    }
    
    /**
     * Getter for property environment.
     * @return Value of property environment.
     */
    public Properties getEnvironment() {
        return this.environment;
    }
    
    /**
     * Setter for property environment.
     * @param environment New value of property environment.
     */
    public void setEnvironment(Properties environment) {
        this.environment = environment;
    }
    
    /**
     * Getter for property setupScripts.
     * @return Value of property setupScripts.
     */
    public List getSetupScripts() {
        return this.setupScripts;
    }
    
    /**
     * Setter for property setupScripts.
     * @param setupScripts New value of property setupScripts.
     */
    public void setSetupScripts(List setupScripts) {
        this.setupScripts = setupScripts;
    }
    
    /**
     * Getter for property teardownScripts.
     * @return Value of property teardownScripts.
     */
    public List getTeardownScripts() {
        return this.teardownScripts;
    }
    
    /**
     * Setter for property teardownScripts.
     * @param teardownScripts New value of property teardownScripts.
     */
    public void setTeardownScripts(List teardownScripts) {
        this.teardownScripts = teardownScripts;
    }
    
    /**
     * Getter for property unixShell.
     * @return Value of property unixShell.
     */
    public String getUnixShell() {
        return this.unixShell;
    }
    
    /**
     * Setter for property unixShell.
     * @param unixShell New value of property unixShell.
     */
    public void setUnixShell(String unixShell) {
        this.unixShell = unixShell;
    }
    
    /**
     * Getter for property output.
     * @return Value of property output.
     */
    public String getOutput() {
        if(outputFilter==null)
            return null;
        else
            return outputFilter.getData();
    }
    
    /**
     * Getter for property error.
     * @return Value of property error.
     */
    public String getError() {
        if(errorFilter==null)
            return null;
        else
            return errorFilter.getData();
    }
    
    
    public File getScriptFile() throws ScriptingException {
        if(scriptFile==null)
            scriptFile = writeScript();
        return scriptFile;
    }
    
    public void reset() {
        scriptFile=null;
        outputFilter = null;
        errorFilter = null;
    }
    
    private File writeScript() throws ScriptingException {
        Writer writer = null;
        StringBuffer sb = new StringBuffer();
        File sh = null;
        boolean windows = System.getProperty("os.name").toLowerCase().startsWith("windows");
        boolean csh = unixShell!=null && unixShell.endsWith("csh");
        try {
            sh = File.createTempFile("osb-",windows?".bat":".sh");
            if(windows)
                sb.append("@ECHO OFF\n");
            else
                sb.append("#!").append(unixShell).append("\n\n");
            
            // Set the environment
            if(environment!=null)
                for (Enumeration enumeration = environment.propertyNames(); enumeration.hasMoreElements(); ) {
                String propertyName = (String) enumeration.nextElement();
                if(windows)
                    sb.append("SET ")
                    .append(propertyName)
                    .append("=")
                    .append(environment.getProperty(propertyName))
                    .append("\n");
                else if(csh)
                    sb.append("setenv ")
                    .append(propertyName)
                    .append(" \"")
                    .append(environment.getProperty(propertyName))
                    .append("\"\n");
                else
                    sb.append(propertyName)
                    .append("=\"")
                    .append(environment.getProperty(propertyName))
                    .append("\"\nexport ")
                    .append(propertyName)
                    .append("\n");
                
                
                }
            
            // Run any setup scripts
            if(setupScripts!=null)
                for (Iterator it = setupScripts.iterator(); it.hasNext(); ) {
                sb.append((String) it.next()).append("\n");
                }
            
            // Insert the real command to run
            for(int j=0; j<command.length; j++) {
                if(j>0) sb.append(' ');
                sb.append( command[j] );
            }
            sb.append("\n");

            if (log.isDebugEnabled()) {
                if (windows) {
                    sb.append("echo End of print command batch file.\n");
                } else {
                    sb.append("echo \"End of print command batch file.\"\n");
                }
            }

            if(windows)
                sb.append("set _EXITCODE=%ERRORLEVEL%\n");
            else if(csh)
                sb.append("setenv _EXITCODE $status\n");
            else
                sb.append("_EXITCODE=$?\n");
            
            // Run any setup scripts
            if(teardownScripts != null)
                for (Iterator it = teardownScripts.iterator(); it.hasNext(); ) {
                sb.append((String) it.next()).append("\n");
                }
            
            if (windows)
                sb.append("exit %_EXITCODE%\n");
            else
                sb.append("exit $_EXITCODE\n");
            
            // Write out the script
            writer = new BufferedWriter( new FileWriter(sh, false) );
            writer.write( sb.toString() );

            if(log.isDebugEnabled())
                log.debug("Created Script : " + sh.getAbsolutePath() +
                        "\n******************************************\n"+
                        sb.toString()+
                        "\n******************************************");
            return sh;
        } catch (IOException e) {
            log.error("Error Creating Script",e);
            throw new ScriptingException(sh==null?null:sh.getAbsolutePath(), e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    throw new ScriptingException("Failed to close Writer.", e);
                }
                writer = null;
            }
        }
    }
    
    public int exec() throws ScriptingException {
        boolean windows = System.getProperty("os.name").toLowerCase().startsWith("windows");
        
        //Get the scrit file
        File script = getScriptFile();
        
        if(!windows) {
            // need to set the script to be executable
            OsScriptingBean chmod = new OsScriptingBean();
            if( chmod.exec( new String[] {"chmod","a+x", script.getAbsolutePath()} ) != 0) {
                // Error making file executable
                if(log.isDebugEnabled())
                    log.debug("Error making file executable : " + script.getAbsolutePath() +
                            "\n****Output*************************************\n"+
                            chmod.getOutput()+
                            "\n****Error**************************************\n" +
                            chmod.getError());
                throw new ScriptingException("chmod a+x " + script.getAbsolutePath());
            }
        }
        
        // Execute the script
        return exec( new String[] {script.getAbsolutePath()} );
    }
    
    /** This version of exec is the simple non-scripted version. It executes the
     * passed in command and parameters, are returns the result directly.
     * It is still possible to get values from getOutput() and getError() once this
     * method has been invoked
     */
    public int exec(String[] command) throws ScriptingException {
        Runtime rt = Runtime.getRuntime();
        ArrayList cmd = new ArrayList();
        String osName = System.getProperty("os.name" );
        if(osName.equals("Windows 95")) {
            cmd.add("command.com");
            cmd.add("/C");
        } else if(osName.startsWith("Windows")) {
            cmd.add("cmd.exe");
            cmd.add("/C");
        }
        for(int i =0; i<command.length;i++)
            cmd.add(command[i]);
        
        String[] c = (String[]) cmd.toArray(new String[] {});
        if(log.isDebugEnabled())
            log.debug("Executing Command : " + StringHelper.printArray(c));
        try {
            m_process = rt.exec( c );
            outputFilter = new StreamFilter(m_process.getInputStream(), "OUTPUT");
            outputFilter.start();
            errorFilter = new StreamFilter(m_process.getErrorStream(), "ERROR");
            errorFilter.start();           
            if (log.isDebugEnabled()) {
                log.debug("Wait for executed command... ");
            }
            int i = m_process.waitFor();
            if (log.isDebugEnabled()) {
                log.debug("Returned from waitFor process with return value = " + i);
            }
            long waitTime = 0;
            while (outputFilter.isAlive() || errorFilter.isAlive()) {
                if (log.isDebugEnabled())
                    log.debug("Waiting for the OututStream and/or ErrorStream to stop");
                Thread.sleep(incrementalOutputWaitTime);
                waitTime+=incrementalOutputWaitTime;
                if(waitTime>=maxOutputWaitTime) {
                    if(log.isInfoEnabled())
                        log.info("Time Out. Stopped Waiting for the OututStream and/or ErrorStream to stop");
                    // Stop the waiting threads
                    if(outputFilter.isAlive())
                        outputFilter.interrupt();
                    if(errorFilter.isAlive())
                        errorFilter.interrupt();
                    break;
                }
            }
            if (log.isDebugEnabled()) {
                log.debug("Finished executing command.  Return value = " + i);
            }
            return i;
        } catch(java.io.IOException e) {
            throw new ScriptingException(command[0], e);
        } catch(InterruptedException e) {
            throw new ScriptingException(command[0], e);
        } finally {
            m_process = null;
        }
    }
    
    /** This method will be invoked to terminate a long running OS process.
     */
    public void destroy() {
        if (m_process != null) {
            if (log.isDebugEnabled())
                log.debug("Destroying the process: " + StringHelper.printArray(this.getCommand()));
            m_process.destroy();
            m_process = null;
            
            if (outputFilter != null && outputFilter.isAlive()) {
                if (log.isDebugEnabled())
                    log.debug("Interrupting the OutputFilter");
                outputFilter.interrupt();
            }
            
            if (errorFilter != null && errorFilter.isAlive()) {
                if (log.isDebugEnabled())
                    log.debug("Interrupting the ErrorFilter");
                errorFilter.interrupt();
            }
        } else {
            if (log.isDebugEnabled())
                log.debug("No process found for destroying. It may have completed normally, or may already have been destroyed");
        }
    }
    
    static class StreamFilter extends Thread {
        InputStream is;
        String name;
        StringBuffer sb = new StringBuffer();
        
        StreamFilter(InputStream is, String name) {
            this.is = is;
            this.name = name;
        }
        
        public String getData() {
            return sb==null?null:sb.toString();
        }
        
        public void run() {
            if (log.isDebugEnabled()) {
                log.debug("Start StreamFilter thread: " + name);
            }
          InputStreamReader isr = null;
          BufferedReader br = null;
            try {
                isr = new InputStreamReader(is);
                br = new BufferedReader(isr);
                // Old version
                String line=null;
                int count=0;
                while ( (line = br.readLine()) != null) {
                    if (isInterrupted()) {
                        if(log.isDebugEnabled())
                            log.debug("StreamFilter has been interrupted.");
                        break;
                    }
                    for(int i=0;i<line.length();i++)
                        if(!Character.isIdentifierIgnorable(line.charAt(i)))
                            sb.append(line.charAt(i));
                    sb.append('\n');
                    if(log.isDebugEnabled()) {
                        log.debug(name+":"+(++count)+">"+line);
                        //log.debug(name+":"+(++count)+">"+URLEncoder.encode(line,"UTF-8"));
                    }
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        log.error(e);
                    }
                }
                if (isr != null) {
                    try {
                        isr.close();
                    } catch (IOException e) {
                        log.error(e);
                    }
                }
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        log.error(e);
                    }
                }
                if (log.isDebugEnabled()) {
                    log.debug("End StreamFilter thread: " + name);
                }
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //LoggerHelper.init();
        try {
            OsScriptingBean osb=new OsScriptingBean();
            // Environment
            ListProperties env = new ListProperties();
            env.setProperty("myFile","c:\\badfilename");
            osb.setEnvironment(env);
            
            // Setup
            List setup = new ArrayList();
            setup.add("echo ** Before Command **");
            osb.setSetupScripts(setup);
            
            // Command Line
            ArrayList cmd = new ArrayList();
            cmd.add("type");
            cmd.add("%myFile%");
            osb.setCommand( (String[]) cmd.toArray(new String[0]) );
            
            // Teardown
            List teardown = new ArrayList();
            teardown.add("echo ** After Command **");
            osb.setTeardownScripts(teardown);
            
            // Execute
            int returnCode = osb.exec();
            
            System.out.println("Script = " + osb.getScriptFile() );
            System.out.println("Return Code=" + returnCode);
            System.out.println("Standard Output...");
            System.out.println(osb.getOutput());
            System.out.println("Standard Error...");
            System.out.println(osb.getError());
            
            // Test the return value- it should fail
            //assert returnCode==1;
            
            
            // Reset Environment
            osb.reset();
            env.setProperty("myFile","c:\\boot.ini");
            osb.setEnvironment(env);
            
            // Execute
            returnCode = osb.exec();
            
            System.out.println("Script = " + osb.getScriptFile() );
            System.out.println("Return Code=" + returnCode);
            System.out.println("Standard Output...");
            System.out.println(osb.getOutput());
            System.out.println("Standard Error...");
            System.out.println(osb.getError());
            
            // Test the return value- it should not fail
            //assert returnCode==0;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}

