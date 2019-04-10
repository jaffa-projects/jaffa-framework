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
package org.jaffa.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jaffa.api.cluster.Link;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * FileContents - A custom object for storing the contents and context-salience of a
 * custom configuration archive file.
 *
 * @author Matthew Wayles
 * @version 1.0
 */

public class FileContents {
    @Expose
    private String name = null;
    @Expose
    private String contextSalience = null;
    @Expose
    private String variationSalience = null;
    @Expose
    @SerializedName("import")
    private String importProperty = null;
    @Expose
    private List<String> contents = null;
    @Expose
    private List<Link> links;

    private String url = null;

    /**
     * Retrieve the value from the contextSalience variable
     *
     * @return contextSalience  The contextSalience retrieved
     **/
    public String getContextSalience() {
        return this.contextSalience;
    }

    /**
     * Set the context-salience value
     *
     * @param contextSalience The contextSalience to set
     */
    void setContextSalience(String contextSalience) {

        this.contextSalience = contextSalience;
    }

    /**
     * Retrieve the value from the variationSalience variable
     *
     * @return variationSalience  The variationSalience retrieved
     */
    public String getVariationSalience() {
        return variationSalience;
    }

    /**
     * Set the variation-salience value
     *
     * @param variationSalience The variationSalience to set
     */
    public void setVariationSalience(String variationSalience) {
        this.variationSalience = variationSalience;
    }

    /**
     * Retrieve the value from the import variable
     *
     * @return importProperty  The importProperty retrieved
     */
    public String getImportProperty() {
        return importProperty;
    }

    /**
     * Set the import property value
     *
     * @param importProperty The importProperty to set
     */
    public void setImportProperty(String importProperty) {
        this.importProperty = importProperty;
    }

    /**
     * Retrieve the value from the URL variable
     *
     * @return url  The url retrieved
     **/
    public String getUrl() {
        return url;
    }

    /**
     * Set the URL value
     *
     * @param url The URL to set
     */
    public void setUrl(String url) {
        this.url = url;
    }


    /**
     * Retrieve the value from the name variable
     *
     * @return name The name retrieved
     **/
    public String getName() {
        return this.name;
    }

    /**
     * Set the name value
     *
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieve the contents of the custom compressed file
     *
     * @return contents   The contents of the custom compressed file
     **/
    public List<String> getContents() {
        return this.contents;
    }

    /**
     * Create a String list to hold the custom compressed contents
     *
     * @param contents A List<String> object
     */
    public void setContents(List<String> contents) {
        this.contents = contents;
    }

    /**
     * Add a configuration file found in compressed file to contents list
     *
     * @param contentsItem The configuration file found in the custom compressed file
     * @return This instance with an updated contents list
     */
    FileContents addContentsItem(String contentsItem) {
        if (this.contents == null) {
            setContents(new ArrayList<>());
        }
        this.contents.add(contentsItem);
        return this;
    }

    /**
     * Retrieve the links associated with the custom compressed file
     *
     * @return links  The links associated with the custom compressed file
     **/
    public List<Link> getLinks() {
        return this.links;
    }

    /**
     * Create a Link object list to hold the compressed file links
     *
     * @param links A List<Link> object
     */
    public void setLinks(List<Link> links) {
        this.links = links;
    }

  /**
   * Add a link to a compressed file object
   *
   * @param link The link to add to the custom compressed file
   */
  public void addLink(Link link) {
    if (this.links == null) {
      setLinks(new ArrayList<>());
    }
    links.add(link);
  }


  // HELPER METHODS
  /**
   * Custom swagger-generated equals comparator
   * @param o The operand to compare
   * @return  Boolean value indicated equality
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FileContents fileContents = (FileContents) o;
    return Objects.equals(this.name, fileContents.name) &&
        Objects.equals(this.contents, fileContents.contents);
  }

  /**
   * Custom swagger-generated hash translator
   * @return  The hashcode value of the name, contents objects
   */
  @Override
  public int hashCode() {
    return Objects.hash(name, contents);
  }

  /**
   * Custom swagger-generated toString translator
   * @return  A string representation of the object
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FileContents {\n");

    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    context-salience: ").append(toIndentedString(contextSalience)).append("\n");
    sb.append("    contents: ").append(toIndentedString(contents)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

