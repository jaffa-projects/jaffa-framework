package org.jaffa.soa.services;

import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.messaging.services.HeaderParam;
import org.jaffa.persistence.UOW;

import java.util.List;

/**
 * Interface for RaiseEventServiceHandler which can be used for
 * injecting the custom logic into the core routine.
 */
public interface IRaiseEventServiceHandler {

    /**
     * Injected into raiseSoaEvent and assign the sequence number parameter in soa event based on category.
     *
     * @param uow
     * @param eventName
     * @param description
     * @param category
     * @param headerParams
     * @throws FrameworkException
     * @throws ApplicationExceptions
     */
    public void raiseSoaEvent(UOW uow, String eventName, String description, String category, List<HeaderParam> headerParams) throws FrameworkException, ApplicationExceptions;

}
