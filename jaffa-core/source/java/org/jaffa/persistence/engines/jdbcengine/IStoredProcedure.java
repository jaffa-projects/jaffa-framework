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

package org.jaffa.persistence.engines.jdbcengine;

import org.jaffa.persistence.*;

/** This is the interface for a StoredProcedure. */
public interface IStoredProcedure extends IPersistent {

    /** A static for denoting an INPUT parameter of a StoredProcedure */
    public static final int IN   = 0x01;

    /** A static for denoting an OUTPUT parameter of a StoredProcedure */
    public static final int OUT  = 0x02;

    /** A static for denoting an INPUT/OUTPUT parameter of a StoredProcedure */
    public static final int BOTH = 0x03;

    /** A static for registering a cursor-type with a CallableStatement */
    public static final String CURSOR = "CURSOR";

    /** The implementing class should return an array of field names,
     * which correspond to the parameters of the StoredProcedure.
     * The implementing class should provide Getters/Setters for these fields.
     * @return An array of parameters to the StoredProcedure.
     */
    public String[] getParameters();

    /** The implementing class should return an array of Strings signifying the SQL type
     * for each of the parameter returned in the getParameters() method.
     * eg. VARCHAR, BOOLEAN etc. etc. These should be the same as those defined in the mapping files
     * @return An array of Strings signifying the SQL type of the parameters.
     */
    public String[] getParamSqlTypes();

    /** The implementing class should return an array of integers signifying the directions (input, output or input/output)
     * for each of the parameter returned in the getParameters() method.
     * @return An array of integers signifying the directions of the parameters.
     */
    public int[] getParamDirections();

    /** The implementing class will return a String having the call to the StoredProcedure.
     * For eg. To invoke the 'ke_vcpkg1.getvoucher' StoredProcedure, which takes 3 parameters, this method
     * will return the string "{call ke_vcpkg1.getvoucher(?,?,?)}".
     * @return a String having the call to the StoredProcedure.
     */
    public String prepareCall();

}

