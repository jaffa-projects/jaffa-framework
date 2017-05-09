/*
 * SavedQuery.java
 *
 * Created on January 15, 2007, 4:40 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jaffa.components.finder;

/**
 *
 * @author SeanZ
 */
public class SavedQuery {
  private String id;
  private String name;
  private String url;
  
  /** Creates a new instance of SavedQuery */
  public SavedQuery() {
  }
  
  SavedQuery(String id, String name, String url) {
    this.id = id;
    this.name = name;
    this.url = url;
  }
  
  public String getId() {
    return id;
  }
  public String getName() {
    return name;
  }
  public String getUrl() {
    return url;
  }

}
