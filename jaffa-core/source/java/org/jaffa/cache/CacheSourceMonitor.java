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

package org.jaffa.cache;

import org.apache.log4j.Logger;
import java.util.*;
import java.io.File;

/** This is a helper class to monitor file(s), which provide data to a cache. The cache will be cleared, whenever any of the source files are modified. <B>Note that any open iterators on the cache will have unpredictable results, whenever the cache is flushed.</B>
 * Create an instance of this class, providing the cache and the file(s) to be monitored. Then invoke the startMonitoring method. This will create a daemon Thread, which will monitor the files for any changes. Invoke the stopMonitoring method to stop the thread.
 * @author  GautamJ
 */
public class CacheSourceMonitor {
    
    private static final Logger log = Logger.getLogger(CacheSourceMonitor.class);
    
    // Default monitor frequency (5 minutes) in milliseconds.
    private static final long DEFAULT_FREQUENCY = 5 * 60 * 1000;
    
    private ICache m_cache = null;
    private Collection m_files = new LinkedList();
    private Thread m_thread = null;
    private boolean m_executeThread = false;
    private long m_millis = 0;
    
    /** Creates a new instance of CacheSourceMonitor.
     * @param cache The cache to be cleared, if any of the source files are modified.
     */
    public CacheSourceMonitor(ICache cache) {
        this(cache, null);
    }
    
    /** Creates a new instance of CacheSourceMonitor.
     * @param cache The cache to be cleared, if any of the source files are modified.
     * @param file The file to be monitored. In case, the input represents a directory, then all the files (one level deep) under that directory will be monitored for any changes.
     */
    public CacheSourceMonitor(ICache cache, String file) {
        m_cache = cache;
        if (file != null)
            m_files.add(file);
    }
    
    /** Add a file to be monitored for changes.  In case, the input represents a directory, then all the files (one level deep) under that directory will be monitored for any changes.
     * @param file The file to be monitored. In case, the input represents a directory, then all the files (one level deep) under that directory will be monitored for any changes.
     */
    public void addFile(String file) {
        m_files.add(file);
    }
    
    /** This will create a daemon Thread for monitoring the files. This thread will be run at the lowest priority, every 5 minutes by default.
     */
    public void startMonitoring() {
        startMonitoring(0);
    }
    
    /** This will create a daemon Thread for monitoring the files. This thread will be run at the lowest priority at the input frequency.
     * @param minutes The frequency at which the files will be monitored.
     */
    public void startMonitoring(int minutes) {
        m_millis = minutes > 0 ? minutes * 60 * 1000 : DEFAULT_FREQUENCY;
        if (m_thread == null) {
            m_thread = new CacheSourceMonitorThread();
            m_thread.setDaemon(true);
        }
        m_executeThread = true;
        m_thread.start();
    }
    
    /** This will stop the Thread, that was created by the call to the startMonitoring method. */
    public void stopMonitoring() {
        m_executeThread = false;
        if (m_thread != null)
            m_thread.interrupt();
    }
    
    /** This will monitor the files.
     */
    private void monitor() {
        long time = System.currentTimeMillis() - m_millis;
        for (Iterator itr = m_files.iterator(); itr.hasNext(); ) {
            String fileName = (String) itr.next();
            File file = new File(fileName);
            if (!file.exists())
                continue;
            
            if (file.isFile()) {
                if (file.lastModified() > time) {
                    if (log.isDebugEnabled())
                        log.debug(fileName + " has been modified. The Cache will be flushed.");
                    m_cache.clear();
                    break;
                }
            } else if (file.isDirectory()) {
                // Just monitor the files in the directory. We'll not be recursing thru the directory.
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    file = files[i];
                    if (file.isFile()) {
                        if (file.lastModified() > time) {
                            if (log.isDebugEnabled())
                                log.debug(file.getAbsolutePath() + " has been modified. The Cache will be flushed.");
                            m_cache.clear();
                            break;
                        }
                    }
                }
            }
        }
    }
    
    /** This thread class will monitor the files for any changes. It will flush the associated cache, in case any changes are detected in the files.
     */
    public class CacheSourceMonitorThread extends Thread {
        /** This method will be invoked by the JVM. It monitors the files for changes at the specified frequency, until the thread is stopped.
         */
        public void run() {
            while (m_executeThread) {
                monitor();
                try {
                    sleep(m_millis);
                } catch (InterruptedException e) {
                    // do nothing
                }
            }
        }
    }
    
}
