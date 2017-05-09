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

package org.jaffa.components.finder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

public class QueryIdList {

    ArrayList list;
    /*
     * 
     */
    public QueryIdList() {
        list = new ArrayList();
    }
    
    public QueryIdList(String idList) {
        this();
        if(idList != null && idList.length()>0)
        {
            // 
            StringTokenizer st = new StringTokenizer(idList,",");
            while (st.hasMoreElements()) {
                String element = (String) st.nextElement();
                Integer i = new Integer(element);
                
                // In order to sort the list, work out where to put it,
                // either in the position of its value -1 (indexes start at 0)
                // or the last place in the list (list.size()-1)
                
                int position = 0;
                for (int loop = 0; loop < list.size(); loop ++)
                {
                    if(i.intValue() < ((Integer)list.get(loop)).intValue())
                    {
                        position = loop;
                        break;
                    }
                    else
                    {
                        position = loop + 1;
                    }
                }
                
//                if (list.size() > 0 && position == 0)
//                {
//                    position = list.size();
//                }
                list.add(position, i);
            }
        }
    }

    public String nextAvailable() {
        String retVal = null;
        if ( list.size() > 0 )
        {
            for (int loop = 0; loop < list.size(); loop++)
            {
                Integer i = (Integer) list.get(loop);
                if( i.intValue() > (loop + 1) )
                {
                    Integer newValue = new Integer(loop+1);
                    list.add(loop,newValue);
                    retVal = newValue.toString();
                    break;
                }
            }
            if (retVal == null)
            {
                Integer newValue = new Integer(list.size()+1);
                list.add(list.size(),newValue);
                retVal = newValue.toString();
            }
        }
        else
        {
            Integer newValue = new Integer(1);
            list.add(0,newValue);
            retVal = newValue.toString();
        }
        return retVal;
    }

    public void remove(String toRemove)
    {
        Iterator removeIter = list.iterator();
        Integer toRemoveInteger = new Integer(toRemove);
        while(removeIter.hasNext())
        {
            Integer next = (Integer) removeIter.next();
            if(next.equals(toRemoveInteger))
            {
                removeIter.remove();
            }
        }
        
    }
    
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        Iterator listIterator = list.iterator();
        while (listIterator.hasNext()) 
        {
            Integer element = (Integer) listIterator.next();
            sb.append(element.intValue());
            
            if (listIterator.hasNext())
            {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public String[] toArray()
    {
        String[] retVal = new String[list.size()];
        for (int loop = 0; loop < list.size(); loop++)
        {
            Integer element = (Integer) list.get(loop);
            retVal[loop] = String.valueOf(element.intValue());
        }
        
        return retVal;
    }
}
