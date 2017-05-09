package org.jaffa.soa.services;

/**
 * Factory fro creating instances of the SOAEventEnabledConfiguration
 */
public class SOAEventEnabledConfigurationFactory {

    // Singleton  instance of this configuration object
    private static SOAEventEnabledConfiguration instance;

    // Load the initial instance
    static{
        reload();
    }

    /**
     * Provides access to the an instance of the SOAEventEnabledConfiguration
     *
     * @return An instance of the SOAEventEnabledConfiguration
     */
    public synchronized static SOAEventEnabledConfiguration instance() {
        return instance;
    }

    /**
     * Clears all loaded configuration and reloads on demand
     */
    public synchronized static void reload(){
        instance = new SOAEventEnabledConfigurationImpl();
    }

}
