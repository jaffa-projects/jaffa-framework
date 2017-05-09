package org.jaffa.ria.metadata;


public class ForeignKeyLookup {

	private String title;
	private String foreignKey;
	private String foreignGraphKey;
	
	private int foreignKeySize;
	
	public int getForeignKeySize() {
		return foreignKeySize;
	}

	public void setForeignKeySize(int foreignKeySize) {
		this.foreignKeySize = foreignKeySize;
	}

	public String toString() {
		 return "[title=" + title + "foreignKey="+foreignKey +",foreignKey="+foreignKey+",foreignKeySize=" + foreignKeySize+"]";
	 }

	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getForeignKey() {
		return foreignKey;
	}

	public void setForeignKey(String foreignKey) {
		this.foreignKey = foreignKey;
	}

	public String getForeignGraphKey() {
		return foreignGraphKey;
	}

	public void setForeignGraphKey(String foreignGraphKey) {
		this.foreignGraphKey = foreignGraphKey;
	}
	
	
	
}
