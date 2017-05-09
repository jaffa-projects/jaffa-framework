/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jaffa.ria.finder.apis.data;
import org.jaffa.ria.finder.apis.data.SavedQueryGraph;
import org.jaffa.soa.graph.GraphUpdateResponse;
/**
 *
 * @author BobbyC
 */
public class SavedQueryUpdateResponse extends GraphUpdateResponse<SavedQueryGraph> {
    public SavedQueryGraph getSource()
    {
        return super.source;
    }

    public void setSource(SavedQueryGraph source) {
        super.source = source;
    }
}
