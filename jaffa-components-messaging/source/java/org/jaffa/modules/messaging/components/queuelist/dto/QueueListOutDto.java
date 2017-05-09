package org.jaffa.modules.messaging.components.queuelist.dto;

/** The output for the QueueList.
 */
public class QueueListOutDto {
    
    private QueueListOutRowDto[] m_rows;
    
    /** Returns an array of rows.
     * @return An array of rows.
     */
    public QueueListOutRowDto[] getRows() {
        return m_rows;
    }
    
    /** Add an array of rows. This will overwrite existing rows.
     * @param rows An array of rows.
     */
    public void setRows(QueueListOutRowDto[] rows) {
        m_rows = rows;
    }
    
    
    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<QueueListOutDto>");
        buf.append("<rows>");
        if (m_rows != null) {
            for (QueueListOutRowDto row : m_rows)
                buf.append(row);
        }
        buf.append("</rows>");
        buf.append("</QueueListOutDto>");
        return buf.toString();
    }
}
