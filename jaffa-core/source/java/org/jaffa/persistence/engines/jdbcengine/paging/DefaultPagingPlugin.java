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
package org.jaffa.persistence.engines.jdbcengine.paging;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/** This implementation of the IPagingPlugin adopts the most ineffient approch.
 * It fetches all records until the 'firstResult', and then fetches 'maxResults'.
 * @author  GautamJ
 */
public class DefaultPagingPlugin extends AbstractPagingPlugin {

    private static final Logger log = Logger.getLogger(DefaultPagingPlugin.class);
    private int m_currentResult = -1;

    /** Advances the ResultSet upto 'firstResult' based on the configuration settings.
     * @param resultSet the ResultSet from which the data is to be loded.
     * @return true if the new current row is valid; false if there are no more rows.
     * @throws SQLException if any database error occurs.
     */
    public boolean next(ResultSet resultSet) throws SQLException {
        // advance the ResultSet by 1
        boolean valid = resultSet.next();
        if (valid)
            ++m_currentResult;

        // advance the ResultSet until the currentResult matches the firstResult
        while (valid && m_currentResult < m_firstResult) {
            if (log.isDebugEnabled())
                log.debug("Ignoring result " + m_currentResult + " since it is less than firstResult " + m_firstResult);
            valid = resultSet.next();
            if (valid)
                ++m_currentResult;
        }

        // ensure that the currentResult is less than the maxResults, else return a false
        if (valid && m_maxResults > 0 && m_currentResult >= (m_firstResult + m_maxResults)) {
            if (log.isDebugEnabled())
                log.debug("Ignoring result " + m_currentResult + " since exceeds the maxResults " + m_maxResults);
            valid = false;
        }

        return valid;
    }
}
