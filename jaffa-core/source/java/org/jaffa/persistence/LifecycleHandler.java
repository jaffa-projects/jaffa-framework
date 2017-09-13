package org.jaffa.persistence;

import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.exceptions.PostAddFailedException;
import org.jaffa.persistence.exceptions.PostDeleteFailedException;
import org.jaffa.persistence.exceptions.PostLoadFailedException;
import org.jaffa.persistence.exceptions.PostUpdateFailedException;
import org.jaffa.persistence.exceptions.PreAddFailedException;
import org.jaffa.persistence.exceptions.PreDeleteFailedException;
import org.jaffa.persistence.exceptions.PreUpdateFailedException;

import java.util.ArrayList;
import java.util.List;

public class LifecycleHandler implements ILifecycleHandler {

    protected static Logger log = Logger.getLogger(LifecycleHandler.class);

    private List<ILifecycleHandler> lifeCycleHandlers = new ArrayList<>();
    private Class<?> persistentClass;
    private ILifecycleHandler targetBean;

    public void setTargetBean(ILifecycleHandler lifecycleHandler) {
        this.targetBean = lifecycleHandler;
    }

    /**
     * Gets all life cycle handlers set on a concrete life cycle handler.  This allows custom code to be
     * injected before or after lifecycle methods.
     */
    public List<ILifecycleHandler> getLifeCycleHandlers() {
        return lifeCycleHandlers;
    }

    /**
     * This method is triggered by the UOW, before adding this object to the Add-Store, but after a UOW has been associated to the object.
     * A concrete persistent object should provide the implementation, if its necessary.
     *
     * @throws PreAddFailedException if any error occurs during the process.
     */
    public void preAdd() throws PreAddFailedException {
        if (log.isDebugEnabled())
            log.debug("Handle Event : preAdd");
    }

    /**
     * This method is triggered by the UOW, after adding this object to the Add-Store.
     * A concrete persistent object should provide the implementation, if its necessary.
     *
     * @throws PostAddFailedException if any error occurs during the process.
     */
    public void postAdd() throws PostAddFailedException {
        if (log.isDebugEnabled())
            log.debug("Handle Event : postAdd");
    }

    /**
     * This method is triggered by the UOW, before adding this object to the Update-Store.
     * A concrete persistent object should provide the implementation, if its necessary.
     *
     * @throws PreUpdateFailedException if any error occurs during the process.
     */
    public void preUpdate() throws PreUpdateFailedException {
        if (log.isDebugEnabled())
            log.debug("Handle Event : preupdate");
    }

    /**
     * This method is triggered by the UOW, after adding this object to the Update-Store.
     * A concrete persistent object should provide the implementation, if its necessary.
     *
     * @throws PostUpdateFailedException if any error occurs during the process.
     */
    public void postUpdate() throws PostUpdateFailedException {
        if (log.isDebugEnabled())
            log.debug("Handle Event : postupdate");
    }

    /**
     * This method is triggered by the UOW, before adding this object to the Delete-Store.
     * A concrete persistent object should provide the implementation, if its necessary.
     *
     * @throws PreDeleteFailedException if any error occurs during the process.
     */
    public void preDelete() throws PreDeleteFailedException {
        if (log.isDebugEnabled())
            log.debug("Handle Event : predelete");
    }

    /**
     * This method is triggered by the UOW, after adding this object to the Delete-Store.
     * A concrete persistent object should provide the implementation, if its necessary.
     *
     * @throws PostDeleteFailedException if any error occurs during the process.
     */
    public void postDelete() throws PostDeleteFailedException {
        if (log.isDebugEnabled())
            log.debug("Handle Event : postdelete");
    }

    /**
     * This method is triggered by the UOW, after adding this object to the Delete-Store.
     * A concrete persistent object should provide the implementation, if its necessary.
     *
     * @throws PostDeleteFailedException if any error occurs during the process.
     */
    public void postLoad() throws PostLoadFailedException {
        if (log.isDebugEnabled())
            log.debug("Handle Event : postload");
    }

    /**
     * This method is invoked before adding/updating a domain object.
     * This will perform the following tasks:
     * Will invoke the performForeignKeyValidations() method to ensure no invalid foreign-keys are set.
     * Will invoke PersistentHelper.checkMandatoryFields() to perform mandatory field checks.
     * Will invoke the Rules Engine to perform mandatory field checks.
     *
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException    Indicates some system error
     */
    public void validate() throws ApplicationExceptions, FrameworkException {
        if (log.isDebugEnabled())
            log.debug("Handle Event : validate");
    }
}
