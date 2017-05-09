/*
 * FileExplorerBean.java
 *
 * Created on October 17, 2005, 12:30 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jaffa.applications.jaffa.modules.admin.components.fileexplorer.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jaffa.datatypes.DateTime;
import org.jaffa.datatypes.Formatter;

/**
 *
 * @author PaulE
 */
public class FileExplorerBean {
    List folders = new ArrayList();
    List files = new ArrayList();

    /** Creates a new instance of FileExplorerBean */
    public FileExplorerBean() {
    }

    public void addFile(File file,boolean canAccess,boolean canRename,boolean canDelete) {
        files.add( new FileBean(file,canAccess,canRename,canDelete));
    }

    public void addFolder(File folder,boolean canAccess,boolean canRename,boolean canDelete) {
        folders.add( new FolderBean(folder,canAccess,canRename,canDelete));
    }
    
    public FolderBean[] getFolders() {
        FolderBean[] objects = (FolderBean[]) folders.toArray(new FolderBean[] {});
        Arrays.sort(objects);
        return objects;
    }

    public FileBean[] getFiles() {
        FileBean[] objects = (FileBean[]) files.toArray(new FileBean[] {});
        Arrays.sort(objects);
        return objects;
    }

    private static String formatSize(long value) {
	String valueLength = "" + value;
	Double divider = new Double(1024);
    	if(valueLength.length() <= 3) {
    		return "" + value + " bytes";
    	} else if(valueLength.length() <= 6)  {
    		Double fileSize = new Double(value);
    		Double fileSizeResult = new Double(fileSize.doubleValue() / divider.doubleValue());
		return "" + Formatter.format(fileSizeResult , "#,##0.0") + " Kb";
	} else {
		Double fileSize = new Double(value);
		Double fileSizeResult = new Double(fileSize.doubleValue() / divider.doubleValue() / divider.doubleValue());
		return "" + Formatter.format(fileSizeResult , "#,##0.0") + " Mb";
	}

    }
    public static final class FileBean implements Comparable {
        File file;
        String name;
        String lowerCaseName; //used by the comparator
        DateTime lastModified;
        long fileSize;
        boolean canAccess;
        boolean canRename;
        boolean canDelete;
        FileBean(File file,boolean canAccess,boolean canRename,boolean canDelete) {
            this.file = file;
            this.name = file.getName();
            this.lowerCaseName = this.name != null ? this.name.toLowerCase() : null;
            this.lastModified = new DateTime(file.lastModified());
            this.fileSize = file.length();
            this.canAccess = canAccess;
            this.canRename = canRename;
            this.canDelete = canDelete;
        }
        public File getFile() { return file; }
        public String getName() { return name; }
        public boolean isAccessable() { return canAccess; }
        public boolean isRenameAllowed() { return canRename; }
        public boolean isDeleteAllowed() { return canDelete; }
        public DateTime getLastModified() { return lastModified; }
        public long getSize() { return fileSize; }
        public String getFormattedSize() { return formatSize(fileSize); }
        
        /** Compares this object with another FileBean object.
         * Note: this class has a natural ordering that is inconsistent with equals
         * It merely compares the name of a file, and not the whole path.
         * Hence, it is quite possible that this method might indicate an equality, while the 'equals' method may return a false.
         * @param obj the other FileBean object.
         * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
         */
        public int compareTo(Object obj) {
            FileBean target = (FileBean) obj;
            return lowerCaseName != null ? (target.lowerCaseName != null ? lowerCaseName.compareTo(target.lowerCaseName) : 1) : (target.lowerCaseName != null ? -1 : 0);
        }
    }

    public static final class FolderBean implements Comparable {
        File file;
        String name;
        String lowerCaseName; //used by the comparator
        DateTime lastModified;
        boolean canAccess;
        boolean canRename;
        boolean canDelete;
        FolderBean(File file,boolean canAccess,boolean canRename,boolean canDelete) {
            this.file = file;
            this.name = file.getName();
            this.lowerCaseName = this.name != null ? this.name.toLowerCase() : null;
            this.lastModified = new DateTime(file.lastModified());
            this.canRename = canRename;
            this.canDelete = canDelete;
        }
        public File getFile() { return file; }
        public String getName() { return name; }
        public boolean isAccessable() { return canAccess; }
        public boolean isRenameAllowed() { return canRename; }
        public boolean isDeleteAllowed() { return canDelete; }
        public DateTime getLastModified() { return lastModified; }
        
        /** Compares this object with another FolderBean object.
         * Note: this class has a natural ordering that is inconsistent with equals
         * It merely compares the name of a folder, and not the whole path.
         * Hence, it is quite possible that this method might indicate an equality, while the 'equals' method may return a false.
         * @param obj the other FolderBean object.
         * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
         */
        public int compareTo(Object obj) {
            FolderBean target = (FolderBean) obj;
            return lowerCaseName != null ? (target.lowerCaseName != null ? lowerCaseName.compareTo(target.lowerCaseName) : 1) : (target.lowerCaseName != null ? -1 : 0);
        }
    }

    /**
     * Holds value of property parentAvailable.
     */
    private boolean parentAvailable;

    /**
     * Getter for property parentAvailable.
     * @return Value of property parentAvailable.
     */
    public boolean isParentAvailable() {
        return this.parentAvailable;
    }

    /**
     * Setter for property parentAvailable.
     * @param parentAvailable New value of property parentAvailable.
     */
    public void setParentAvailable(boolean parentAvailable) {
        this.parentAvailable = parentAvailable;
    }

    /**
     * Holds value of property folderName.
     */
    private String folderName;

    /**
     * Getter for property folderName.
     * @return Value of property folderName.
     */
    public String getFolderName() {
        return this.folderName;
    }

    /**
     * Setter for property folderName.
     * @param folderName New value of property folderName.
     */
    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    /**
     * Holds value of property uploadAllowed.
     */
    private boolean uploadAllowed;

    /**
     * Getter for property uploadAllowed.
     * @return Value of property uploadAllowed.
     */
    public boolean isUploadAllowed() {
        return this.uploadAllowed;
    }

    /**
     * Setter for property uploadAllowed.
     * @param uploadAllowed New value of property uploadAllowed.
     */
    public void setUploadAllowed(boolean uploadAllowed) {
        this.uploadAllowed = uploadAllowed;
    }

}
