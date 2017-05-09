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
 * MouldException.java
 *
 * Created on February 26, 2004, 11:36 AM
 */

package org.jaffa.beans.moulding.mapping;

import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.FrameworkException;

/**
 *
 * @author  PaulE
 */
public class MouldException extends FrameworkException {

    public static final int METHOD_NOT_FOUND = 0; // Mould Error at {0} - Method {1} not found.
    public static final int NO_KEY_ON_OBJECT = 1; // Mould Error at {0} - Can't get key '{1}' on object '{2}'
    public static final int ACCESS_ERROR = 2; // Mould Error at {0} - Method Access Error : {1}
    public static final int INVOCATION_ERROR = 3; // Mould Error at {0} -  Method Invocation Error : {1}
    public static final int INSTANTICATION_ERROR = 4; // Mould Error at {0} - Object Constuction Error : {1}
    public static final int INTROSPECT_ERROR = 5; // Mould Error at {0} - Can't Introspect Method : {1}
    public static final int INVALID_FK_MAPPING = 6; // Mould Error at {0} - Invalid mapping, Foreign Key field '{1}' not accessable on '{2}'
    public static final int MISMATCH_FK_MAPPING = 7; // Mould Error at {0} - Mismatch in foreign key mapping from '{1}' to '{2}'
    public static final int CANT_SET_KEY_FIELD = 8; // Mould Error at {0} - Can't set key field '{1}' on object '{2}'
    public static final int NO_SETTER = 9; // Mould Error at {0} - No Setter for property '{1}' on object '{2}'
    public static final int NO_GETTER = 10; // Mould Error at {0} -  No Getter for property '{1}' on object '{2}'
    public static final int DATATYPE_MISMATCH = 11; // Data Type Mismatch method '{0}' expected '{1}', but had '{2}'

    private static final String LABEL_PREFIX = "exception." + MouldException.class.getName();

   /**
     * Constructs an instance of <code>MouldException</code> with the specified detail message.
     * @param reasonCode the detail message.
     */
    public MouldException(int reasonCode) {
        super(LABEL_PREFIX + "." + reasonCode);
    }

    /**
     * Constructs an instance of <code>MouldException</code> with the specified detail message.
     * @param reasonCode the detail message.
     */
    public MouldException(int reasonCode, String path) {
        super(LABEL_PREFIX + "." + reasonCode, new String[] {path});
    }

    /**
     * Constructs an instance of <code>MouldException</code> with the specified detail message.
     * @param reasonCode the detail message.
     */
    public MouldException(int reasonCode, String path, String param1) {
        super(LABEL_PREFIX + "." + reasonCode, new String[] {path, param1});
    }

    /**
     * Constructs an instance of <code>MouldException</code> with the specified detail message.
     * @param reasonCode the detail message.
     */
    public MouldException(int reasonCode, String path, String param1, String param2) {
        super(LABEL_PREFIX + "." + reasonCode, new String[] {path, param1, param2});
    }

    /**
     * Constructs an instance of <code>MouldException</code> with the specified detail message.
     * @param reasonCode the detail message.
     */
    public MouldException(int reasonCode, String path, Throwable cause) {
        super(LABEL_PREFIX + "." + reasonCode, new String[] {path}, cause);
    }
}
