package org.jaffa.api.cluster;

import java.util.Map;

/**
 * Interface to access node metadata storage from jaffa components
 *
 * @author Terry Sikes, Matthew Wayles
 * @version 1.0
 */
public interface IClusterMetadataDAO {

    /**
     * Retrieve all cluster node information metadata
     *
     * @return The metadata for every node registered in metadata storage
     */
    Map<String, NodeInformation> getClusterMetadata();

    /**
     * Replace a node in the metadata storage with a new NodeInformation object
     *
     * @param key  The key of the node to be replaced
     * @param node The new node to replace the old node value for the provided key
     * @return Boolean value indicating success or failure of operation
     */
    void put(String key, NodeInformation node);
}
