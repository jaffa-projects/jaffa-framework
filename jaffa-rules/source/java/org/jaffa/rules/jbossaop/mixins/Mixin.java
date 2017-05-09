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
package org.jaffa.rules.jbossaop.mixins;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;

/** This class is used to model mixin definition.
 */
public class Mixin {

    private String[] interfaces;
    private String mixinClass;
    private String construction;

    /**
     * Creates a new instance.
     */
    public Mixin() {
    }

    /**
     * Creates a new instance.
     */
    public Mixin(String[] interfaces, String mixinClass, String construction) {
        this.interfaces = interfaces;
        this.mixinClass = mixinClass;
        this.construction = construction;
    }

    /**
     * Getter for property interfaces.
     * @return the interfaces
     */
    public String[] getInterfaces() {
        return interfaces;
    }

    /**
     * Setter for property interfaces.
     * @param interfaces the interfaces to set
     */
    public void setInterfaces(String[] interfaces) {
        this.interfaces = interfaces;
    }

    /**
     * Getter for property mixinClass.
     * @return the mixinClass
     */
    public String getMixinClass() {
        return mixinClass;
    }

    /**
     * Setter for property mixinClass.
     * @param mixinClass the mixinClass to set
     */
    public void setMixinClass(String mixinClass) {
        this.mixinClass = mixinClass;
    }

    /**
     * Getter for property construction.
     * @return the construction
     */
    public String getConstruction() {
        return construction;
    }

    /**
     * Setter for property construction.
     * @param construction the construction to set
     */
    public void setConstruction(String construction) {
        this.construction = construction;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder("<Mixin>");
        if (interfaces != null)
            buf.append("<interfaces>").append(Arrays.toString(interfaces)).append("</interfaces>");
        if (mixinClass != null)
            buf.append("<mixinClass>").append(mixinClass).append("</mixinClass>");
        if (construction != null)
            buf.append("<construction>").append(construction).append("</construction>");
        buf.append("</Mixin>");
        return buf.toString();
    }

    /**
     * Returns true if the fields of the input match the fields of this object.
     * @param obj the object to match.
     * @return true if the fields of the input match the fields of this object.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Mixin other = (Mixin) obj;
        if (!Arrays.deepEquals(this.interfaces, other.interfaces))
            return false;
        if ((this.mixinClass == null) ? (other.mixinClass != null) : !this.mixinClass.equals(other.mixinClass))
            return false;
        if ((this.construction == null) ? (other.construction != null) : !this.construction.equals(other.construction))
            return false;
        return true;
    }

    /**
     * Returns the sum of hashCodes of all the fields in this object.
     * @return the sum of hashCodes of all the fields in this object.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + (this.interfaces != null ? Arrays.deepHashCode(this.interfaces) : 0);
        hash = 19 * hash + (this.mixinClass != null ? this.mixinClass.hashCode() : 0);
        hash = 19 * hash + (this.construction != null ? this.construction.hashCode() : 0);
        return hash;
    }

    /**
     * Generates an introduction fragment for insertion into an AOP file.
     * @param writer the writer into which the fragment is written.
     * @param className the class for which the introduction is being generated.
     * @throws IOException if any I/O error occurs.
     */
    public void generateIntroduction(BufferedWriter writer, String className) throws IOException {
        if (interfaces != null && interfaces.length > 0 && mixinClass != null && construction != null) {
            writer.write("  <introduction class='");
            writer.write(className);
            writer.write("'>");
            writer.newLine();
            writer.write("    <mixin>");
            writer.newLine();
            writer.write("      <interfaces>");
            for (int i = 0; i < interfaces.length; i++) {
                if (i > 0)
                    writer.write(',');
                writer.write(interfaces[i]);
            }
            writer.write("</interfaces>");
            writer.newLine();
            writer.write("      <class>");
            writer.write(mixinClass);
            writer.write("</class>");
            writer.newLine();
            writer.write("      <construction>");
            writer.write(construction);
            writer.write("</construction>");
            writer.newLine();
            writer.write("    </mixin>");
            writer.newLine();
            writer.write("  </introduction>");
            writer.newLine();
        }
    }
}
