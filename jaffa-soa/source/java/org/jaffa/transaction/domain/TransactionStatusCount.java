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

package org.jaffa.transaction.domain;

/**
 * Holds the count per Transaction type
 */
public class TransactionStatusCount {
    private long openCount;
    private long inProcessCount;
    private long successCount;
    private long errorCount;
    private long holdCount;
    private long interruptedCount;

    /**
     * Gets the count of interrupted Transactions
     *
     * @return the count of interrupted Transactions
     */
    public long getInterruptedCount() {
        return interruptedCount;
    }

    /**
     * Sets the count of interrupted Transactions
     *
     * @param interruptedCount the count of interrupted Transactions
     */
    public void setInterruptedCount(long interruptedCount) {
        this.interruptedCount = interruptedCount;
    }

    /**
     * Gets the count of on hold Transactions
     *
     * @return the count of on hold Transactions
     */
    public long getHoldCount() {
        return holdCount;
    }

    /**
     * Sets the count of on hold Transactions
     *
     * @param holdCount the count of on hold Transactions
     */
    public void setHoldCount(long holdCount) {
        this.holdCount = holdCount;
    }

    /**
     * Gets the count of error Transactions
     *
     * @return the count of error Transactions
     */
    public long getErrorCount() {
        return errorCount;
    }

    /**
     * Sets the count of error Transactions
     *
     * @param errorCount the count of error Transactions
     */
    public void setErrorCount(long errorCount) {
        this.errorCount = errorCount;
    }

    /**
     * Gets the count of success Transactions
     *
     * @return the count of success Transactions
     */
    public long getSuccessCount() {
        return successCount;
    }

    /**
     * Sets the count of success Transactions
     *
     * @param successCount the count of success Transactions
     */
    public void setSuccessCount(long successCount) {
        this.successCount = successCount;
    }

    /**
     * Gets the count of in process Transactions
     *
     * @return the count of in process Transactions
     */
    public long getInProcessCount() {
        return inProcessCount;
    }

    /**
     * Sets the count of in process Transactions
     *
     * @param inProcessCount the count of in process Transactions
     */
    public void setInProcessCount(long inProcessCount) {
        this.inProcessCount = inProcessCount;
    }

    /**
     * Gets the count of open Transactions
     *
     * @return the count of open Transactions
     */
    public long getOpenCount() {
        return openCount;
    }

    /**
     * Sets the count of open Transactions
     *
     * @param openCount the count of open Transactions
     */
    public void setOpenCount(long openCount) {
        this.openCount = openCount;
    }

    /**
     * Gets the total count
     *
     * @return the total count
     */
    public long getTotalCount() {
        return openCount + inProcessCount + successCount + errorCount + holdCount + interruptedCount;
    }
}
