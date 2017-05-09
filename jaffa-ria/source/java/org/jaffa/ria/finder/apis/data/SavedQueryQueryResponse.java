/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jaffa.ria.finder.apis.data;
import org.jaffa.ria.finder.apis.data.SavedQueryGraph;
import org.jaffa.soa.graph.GraphQueryResponse;

/**
 *
 * @author BobbyC
 */
public class SavedQueryQueryResponse extends GraphQueryResponse<SavedQueryGraph> {

    /*** Getter for property graphs.* @return the graphs*/
    public SavedQueryGraph[] getGraphs() {
        return super.graphs;
    }

    /*** Setter for property graphs.* @param graphs the graphs to set */
    public void setGraphs(SavedQueryGraph[] graphs) {
        super.graphs = graphs;
    }
}
