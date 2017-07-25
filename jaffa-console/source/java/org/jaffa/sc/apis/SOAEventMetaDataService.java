/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jaffa.sc.apis;

 import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.jaffa.soa.services.ConfigurationService;
import org.jaffa.soa.services.SOAEventEnabledConfigurationFactory;
import org.jaffa.soa.services.configdomain.Param;
import org.jaffa.soa.services.configdomain.SoaEventInfo;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

/**
 * @author Saravanan
 */
public class SOAEventMetaDataService {

    /**
     * enable logging for WebServiceDocService
     */
    private static Logger log = Logger.getLogger(SOAEventMetaDataService.class);

    /**
     * @return JSONArray. The JSON array of deployed web service in application.
     */
    public JSONArray loadTree() throws JAXBException, MalformedURLException {

        if (log.isDebugEnabled()) {
            log.debug("Loading tree started");
        }
        JSONArray jsonArray = createTree();
        String jsonResult = jsonArray.toString();
        if (log.isDebugEnabled()) {
            log.debug("result: " + jsonResult); // just for debug purposes-
        }

        return jsonArray;
    }

    /**
     * Set an event type (by event name) as enabled/disabled.  THis calls the service through the factory so
     * the singular instance of the service implementation can be used.
     *
     * @param eventName Name of the event to enable/disable
     * @param isEnabled True to enable or false to disable
     */
    public void setEnabled(String eventName, boolean isEnabled) {
        SOAEventEnabledConfigurationFactory.instance().setEnabled(eventName, isEnabled);
    }

    /**
     * @return JSONArray.
     */
    private JSONArray createTree() throws JAXBException, MalformedURLException {

        JSONArray array = new JSONArray();

        // get all of the SOA events
        List<SoaEventInfo> soaEvents = getSoaEventInfo();

        // create a root node for the tree
        TreeNode globalRoot = new TreeNode("SOA Events");
        globalRoot.setLeaf(false);
        globalRoot.setUniqueName("SOA_EVENTS");

        // get the default event state and the list of all events that are not in the default state
        final boolean areEventsEnabledByDefault = SOAEventEnabledConfigurationFactory.instance().areEventsEnabledByDefault();
        final List<String> eventsInNonDefaultState = SOAEventEnabledConfigurationFactory.instance().getEventsInNonDefaultState();

        // create a tree node for each SOA event
        for (SoaEventInfo soaEvent : soaEvents) {
            String[] chain = null;
            if (soaEvent.getName().indexOf("_") > 0) {
                chain = soaEvent.getName().split("_");
            } else {
                chain = soaEvent.getName().split("\\.");
            }
            TreeNode currentNode = null;

            // create a tree node for each level of chaining in the event's name
            for (int j = 0; j < chain.length; j++) {
                TreeNode wantedNode = new TreeNode(chain[j]);
                wantedNode.setSoaEventName(soaEvent.getName());
                wantedNode.setLabel(soaEvent.getLabel());
                wantedNode.setDescription(soaEvent.getDescription());
                wantedNode.setSoaEventParams(getParams(soaEvent));

                // set the unique name of this tree node, this is the chain of parent node names and this node's name
                StringBuilder sb = new StringBuilder();
                for (int k = 0; k <= j; k++) {

                    // add an underscore after the first node name in the chain
                    if (k > 0) {
                        sb.append("_");
                    }

                    // append the node name from the chain of node names
                    sb.append(chain[k]);
                }
                wantedNode.setUniqueName(sb.toString());

                // set the isEnabled value on the tree node so we can use it to filter visibility
                if (areEventsEnabledByDefault && eventsInNonDefaultState.contains(soaEvent.getName())) {
                    wantedNode.setIsEnabled(false);
                } else if (!areEventsEnabledByDefault && !eventsInNonDefaultState.contains(soaEvent.getName())) {
                    wantedNode.setIsEnabled(false);
                } else {
                    wantedNode.setIsEnabled(true);
                }

                // if this is not the bottom node in the branch, give it the normal icon
                // else, set the icon based on the isEnabled flag of the event
                if (j < (chain.length - 1)) {
                    wantedNode.setIconCls("copy");
                    wantedNode.setIsSelectable(false);
                } else if (wantedNode.getIsEnabled()) {
                    wantedNode.setIconCls("icon-filter-enabled");
                    wantedNode.setIsSelectable(true);
                } else {
                    wantedNode.setIconCls("icon-filter-disabled");
                    wantedNode.setIsSelectable(true);
                }

                if (j == 0) {
                    for (int k = 0; k < globalRoot.getChild().size(); k++) {
                        if (wantedNode.getText().equals(globalRoot.getChild().get(k).getText())) {
                            currentNode = globalRoot.getChild().get(k);
                            break;
                        } else {
                            if (k == globalRoot.getChild().size() - 1) {
                                globalRoot.addChild(wantedNode);
                                currentNode = wantedNode;
                            }
                        }
                    }
                    if (globalRoot.getChild().isEmpty()) {
                        globalRoot.addChild(wantedNode);
                        currentNode = wantedNode;
                    }
                } else {
                    if (currentNode.getChild() == null || currentNode.getChild().isEmpty()) {
                        currentNode.addChild(wantedNode);
                        currentNode = wantedNode;
                    }
                    for (int k = 0; k < currentNode.getChild().size(); k++) {
                        if (wantedNode.getText().equals(currentNode.getChild().get(k).getText())) {
                            currentNode = currentNode.getChild().get(k);
                            break;
                        } else {
                            if (k == currentNode.getChild().size() - 1) {
                                currentNode.addChild(wantedNode);
                                currentNode = wantedNode;
                            }
                        }
                    }
                }
            }
        }

        array.add(globalRoot.toJson());

        return array;
    }

    private List<SoaEventInfo> getSoaEventInfo() throws JAXBException, MalformedURLException {

    	SoaEventInfo[] soaEventInfo = ConfigurationService.getInstance().getAllSoaEventInfo();

        return soaEventInfo!=null ? Arrays.asList(soaEventInfo) : new ArrayList<SoaEventInfo>();
    }

    private String getParams(SoaEventInfo soaEvent) {

        List<Param> params = soaEvent.getParam();

        if (log.isDebugEnabled()) {
            log.debug("params size:" + params);
        }

        JSON json = JSONSerializer.toJSON(params);

        if (log.isDebugEnabled()) {
            log.debug("json:" + json.toString());
        }

        return json.toString();
    }
}