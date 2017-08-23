package org.jaffa.loader;


/**
 * ContextKey class is the unique key in the Repository Map.
 * ContextKey definition consist of unique ID of the repositoryKey, fully qualified file source of the key, variation and precedence of the key
 */
public class ContextKey implements Comparable<ContextKey>{

    /**
     * Unique Key to identify the repository element
     */
    private String id;

    /**
     * Variation of repository element
     */
    private String variation;

    /**
     * Source file fully qualified path that is used to build the repository element
     */
    private String fileName;

    /**
     * Precedence defines the order in which the Repository element is retrieved
     */
    private String precedence;


    /**
     * Constructor
     * @param id
     * @param variation
     * @param fileName
     * @param precedence
     */
    public ContextKey(String id, String fileName, String variation,  String precedence) {
        this.id = id;
        this.variation = variation;
        this.fileName = fileName;
        this.precedence = precedence;
    }

    /**
     * Getter for Id
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Setter for Id
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter for variation
     * @return
     */
    public String getVariation() {
        return variation;
    }

    /**
     * Setter for Id
     * @param variation
     */
    public void setVariation(String variation) {
        this.variation = variation;
    }

    /**
     * Getter for fileName
     * @return
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Setter for Id
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Getter for precedence
     * @return
     */
    public String getPrecedence() {
        return precedence;
    }

    /**
     * Setter for Id
     * @param precedence
     */
    public void setPrecedence(String precedence) {
        this.precedence = precedence;
    }

    @Override
    public String toString() {
        return "ContextKey{" +
                "id='" + id + '\'' +
                ", variation='" + variation + '\'' +
                ", fileName='" + fileName + '\'' +
                ", precedence='" + precedence + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContextKey)) return false;

        ContextKey that = (ContextKey) o;

        if (!getId().equals(that.getId())) return false;
        if (getVariation() != null ? !getVariation().equals(that.getVariation()) : that.getVariation() != null)
            return false;
        return getPrecedence() != null ? getPrecedence().equals(that.getPrecedence()) : that.getPrecedence() == null;
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + (getVariation() != null ? getVariation().hashCode() : 0);
        result = 31 * result + (getPrecedence() != null ? getPrecedence().hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(ContextKey o) {
        int i = getId().compareTo(o.getId());
        if(i == 0) {
            i = this.getPrecedence().compareTo(o.getPrecedence());
        }
        if(i == 0) {
            i = this.getVariation().compareTo(o.getVariation());
        }
        return i;
    }
}
