package org.jaffa.modules.messaging.components.queuelist.dto;

/** The output for the QueueList contains an array of instances of this class.
 */
public class QueueListOutRowDto {
    
    /** Holds value of property queue. */
    private String queue;
    
    /** Holds value of property pending. */
    private Long pending;
    
    /** Holds value of property error. */
    private Long error;
    
    /** Holds value of property inProcess. */
    private Long inProcess;
    
    /** Holds value of property activeConsumer. */
    private Long activeConsumer;
    
    /** Holds value of property started. */
    private Boolean started;
    
    /** Holds value of property stopped. */
    private Boolean stopped;
    
    
    /** Getter for property queue.
     * @return Value of property queue.
     */
    public String getQueue() {
        return queue;
    }
    
    /** Setter for property queue.
     * @param queue New value of property queue.
     */
    public void setQueue(String queue) {
        if (queue == null || queue.length() == 0)
            this.queue = null;
        else
            this.queue = queue;
    }
    
    /** Getter for property pending.
     * @return Value of property pending.
     */
    public Long getPending() {
        return pending;
    }
    
    /** Setter for property pending.
     * @param pending New value of property pending.
     */
    public void setPending(Long pending) {
        this.pending = pending;
    }
    
    /** Getter for property error.
     * @return Value of property error.
     */
    public Long getError() {
        return error;
    }
    
    /** Setter for property error.
     * @param error New value of property error.
     */
    public void setError(Long error) {
        this.error = error;
    }
    
    /** Getter for property inProcess.
     * @return Value of property inProcess.
     */
    public Long getInProcess() {
        return inProcess;
    }
    
    /** Setter for property inProcess.
     * @param inProcess New value of property inProcess.
     */
    public void setInProcess(Long inProcess) {
        this.inProcess = inProcess;
    }
    
    /** Getter for property activeConsumer.
     * @return Value of property activeConsumer.
     */
    public Long getActiveConsumer() {
        return activeConsumer;
    }
    
    /** Setter for property activeConsumer.
     * @param activeConsumer New value of property activeConsumer.
     */
    public void setActiveConsumer(Long activeConsumer) {
        this.activeConsumer = activeConsumer;
    }
    
    /** Getter for property started.
     * @return Value of property started.
     */
    public Boolean getStarted() {
        return this.started;
    }
    
    /** Setter for property started.
     * @param started New value of property started.
     */
    public void setStarted(Boolean started) {
        this.started = started;
    }
    
    /** Getter for property stopped.
     * @return Value of property stopped.
     */
    public Boolean getStopped() {
        return this.stopped;
    }
    
    /** Setter for property stopped.
     * @param stopped New value of property stopped.
     */
    public void setStopped(Boolean stopped) {
        this.stopped = stopped;
    }
    
    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<QueueListOutRowDto>");
        buf.append("<queue>"); if (queue != null) buf.append(queue); buf.append("</queue>");
        buf.append("<pending>"); if (pending != null) buf.append(pending); buf.append("</pending>");
        buf.append("<error>"); if (error != null) buf.append(error); buf.append("</error>");
        buf.append("<inProcess>"); if (inProcess != null) buf.append(inProcess); buf.append("</inProcess>");
        buf.append("<activeConsumer>"); if (activeConsumer != null) buf.append(activeConsumer); buf.append("</activeConsumer>");
        buf.append("<started>"); if (started != null) buf.append(started); buf.append("</started>");
        buf.append("<stopped>"); if (stopped != null) buf.append(stopped); buf.append("</stopped>");
        buf.append("</QueueListOutRowDto>");
        return buf.toString();
    }
    
}
