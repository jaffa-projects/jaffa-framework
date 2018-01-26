package org.jaffa.modules.printing.apis.data;

import org.jaffa.beans.factory.config.StaticContext;
import org.jaffa.datatypes.DateTime;
import org.jaffa.soa.graph.SystemCodeGraph;


public class PrinterDefinitionGraph extends SystemCodeGraph {

    /**
     * Holds value of property printerClass.
     */
    private String printerClass;
    /**
     * Holds value of property description.
     */
    private String description;
    /**
     * Holds value of property siteCode.
     */
    private String siteCode;
    /**
     * Holds value of property locationCode.
     */
    private String locationCode;
    /**
     * Holds value of property remote.
     */
    private Boolean remote;
    /**
     * Holds value of property realPrinterName.
     */
    private String realPrinterName;
    /**
     * Holds value of property printerOption1.
     */
    private String printerOption1;
    /**
     * Holds value of property printerOption2.
     */
    private String printerOption2;
    /**
     * Holds value of property outputType.
     */
    private String outputType;
    /**
     * Holds value of property scaleToPageSize.
     */
    private String scaleToPageSize;

    /**
     * Holds value of property lastChangedBy.
     */
    private String lastChangedBy;
    /**
     * Holds value of property lastChangedOn.
     */
    private DateTime lastChangedOn;
    /**
     * Holds value of property createdOn.
     */
    private DateTime createdOn;
    /**
     * Holds value of property createdBy.
     */
    private String createdBy;

    /**
     * Default constructor.
     */
    public PrinterDefinitionGraph() {
        StaticContext.configure(this);
    }

    /**
     * Getter for property printerClass.
     *
     * @return Value of property printerClass.
     */
    public String getPrinterClass() {
        return printerClass;
    }

    /**
     * Setter for property printerClass.
     *
     * @param printerClass Value of property printerClass.
     */
    public void setPrinterClass(String printerClass) {
        String oldPrinterClass = this.printerClass;
        this.printerClass = printerClass;
        propertyChangeSupport.firePropertyChange("printerClass", oldPrinterClass, printerClass);
    }

    /**
     * Getter for property description.
     *
     * @return Value of property description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Setter for property description.
     *
     * @param description New value of property description.
     */
    public void setDescription(String description) {
        String oldDescription = this.description;
        this.description = description;
        propertyChangeSupport.firePropertyChange("description", oldDescription, description);
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        String oldSiteCode = this.siteCode;
        this.siteCode = siteCode;
        propertyChangeSupport.firePropertyChange("siteCode", oldSiteCode, siteCode);
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        String oldLocationCode = this.locationCode;
        this.locationCode = locationCode;
        propertyChangeSupport.firePropertyChange("locationCode", oldLocationCode, locationCode);
    }

    public Boolean getRemote() {
        return remote;
    }

    public void setRemote(Boolean remote) {
        Boolean oldRemote = this.remote;
        this.remote = remote;
        propertyChangeSupport.firePropertyChange("remote", oldRemote, remote);
    }

    public String getRealPrinterName() {
        return realPrinterName;
    }

    public void setRealPrinterName(String realPrinterName) {
        String oldRealPrinterName = this.realPrinterName;
        this.realPrinterName = realPrinterName;
        propertyChangeSupport.firePropertyChange("realPrinterName", oldRealPrinterName, realPrinterName);
    }

    public String getPrinterOption1() {
        return printerOption1;
    }

    public void setPrinterOption1(String printerOption1) {
        String oldPrinterOption1 = this.printerOption1;
        this.printerOption1 = printerOption1;
        propertyChangeSupport.firePropertyChange("printerOption1", oldPrinterOption1, printerOption1);
    }

    public String getPrinterOption2() {
        return printerOption2;
    }

    public void setPrinterOption2(String printerOption2) {
        String oldPrinterOption2 = this.printerOption2;
        this.printerOption2 = printerOption2;
        propertyChangeSupport.firePropertyChange("printerOption2", oldPrinterOption2, printerOption2);
    }

    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        String oldOutputType = this.outputType;
        this.outputType = outputType;
        propertyChangeSupport.firePropertyChange("outputType", oldOutputType, outputType);
    }

    public String getScaleToPageSize() {
        return scaleToPageSize;
    }

    public void setScaleToPageSize(String scaleToPageSize) {
         String oldScaleToPageSize = this.scaleToPageSize;
         this.scaleToPageSize = scaleToPageSize;
         propertyChangeSupport.firePropertyChange("scaleToPageSize", oldScaleToPageSize, scaleToPageSize);
    }

    /**
     * @return the lastChangedBy
     */
    public String getLastChangedBy() {
        return lastChangedBy;
    }

    /**
     * @param lastChangedBy the lastChangedBy to set
     */
    public void setLastChangedBy(String lastChangedBy) {
        String oldLastChangedBy = this.lastChangedBy;
        this.lastChangedBy = lastChangedBy;
        propertyChangeSupport.firePropertyChange("lastChangedBy", oldLastChangedBy, lastChangedBy);

    }

    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        String oldCreatedBy = this.createdBy;
        this.createdBy = createdBy;
        propertyChangeSupport.firePropertyChange("createdBy", oldCreatedBy, createdBy);

    }

    /**
     * @return the lastChangedOn
     */
    public DateTime getLastChangedOn() {
        return this.lastChangedOn;
    }

    /**
     * @param lastChangedOn the lastChangedOn to set
     */
    public void setLastChangedOn(DateTime lastChangedOn) {
        DateTime oldLastChangedOn = this.lastChangedOn;
        this.lastChangedOn = lastChangedOn;
        propertyChangeSupport.firePropertyChange("lastChangedOn", oldLastChangedOn, lastChangedOn);
    }

    /**
     * @return the createdOn
     */
    public DateTime getCreatedOn() {
        return this.createdOn;
    }

    /**
     * @param createdOn the createdOn to set
     */
    public void setCreatedOn(DateTime createdOn) {
        DateTime oldCreatedOn = this.createdOn;
        this.createdOn = createdOn;
        propertyChangeSupport.firePropertyChange("createdOn", oldCreatedOn, createdOn);
    }

}