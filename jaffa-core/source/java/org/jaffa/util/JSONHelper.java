/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jaffa.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import javax.servlet.ServletRequest;

/**
 *
 * @author SeanZ
 */
public class JSONHelper {
    
    private static final Logger log = Logger.getLogger(JSONHelper.class);
    
    /**
     * Converts a Map object into JSON string. 
     * Iteratorily removes all the null values in the Map before converting to JSON string.
     * @param m
     * @return
     */
    public static String map2json(Map m) {
        // remove the null values
        m = removeNullEntries(m);
        // construct the json string
        return map2jsonConverter(m);
    }

    /**
     * Converts a Map Object into JSON string.
     * @param hm
     * @return
     */
    public static String map2jsonConverter(Map hm) {
        if (hm==null || hm.size()==0) {
            return "{}";
        }
        JSONObject jo = new JSONObject();
        for (Iterator it = hm.keySet().iterator(); it.hasNext();) {
            Object k = it.next();
            if (hm.get(k)==null || hm.get(k) instanceof String) {
                jo.accumulate(k.toString(), hm.get(k));
            } else if (hm.get(k) instanceof Collection || hm.get(k).getClass().isArray()) {
                jo.accumulate(k.toString(), JSONArray.fromObject(hm.get(k)).toString());
            } else {
                log.debug("map2jsonConverter: "+hm.get(k).getClass().getName());
                jo.accumulate(k.toString(), JSONObject.fromObject(hm.get(k)).toString());
            }
        }
        return jo.toString();
    }

    /**
     * Convert the request parameter map to a json string.
     *
     * @param request
     * @return
     */
    public static String requestParams2json(ServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        JSONObject jo = new JSONObject();
        for (Iterator<String> it=params.keySet().iterator(); it.hasNext();) {
            String k = it.next();
            String[] v = params.get(k);
            // TODO this builds up the params objects and should probably escape with the StringHelper
            // TODO like we do when reading params in jsp pages.
            if (v==null || v.length==0) {
                continue;
            } else if (v.length==1) {
                jo.accumulate(k, v[0]);
            } else {
                jo.accumulate(k, JSONArray.fromObject(v));
            }
        }
        return jo.toString();
    }
    
    private static Map removeNullEntries(Map hm) {
        Map oo = new HashMap();
        for (Iterator it = hm.keySet().iterator(); it.hasNext();) {
            Object k = it.next();
            if (hm.get(k) == null) {
                continue;
            } else if (hm.get(k) instanceof Map) {
                if (((Map) hm.get(k)).size() > 0) {
                    Map mp = removeNullEntries((Map) hm.get(k));
                    if (mp.size() > 0) {
                        oo.put(k, mp);
                    }
                }
            } else {
                oo.put(k, hm.get(k));
            }
        }
        return oo;
    }
}
