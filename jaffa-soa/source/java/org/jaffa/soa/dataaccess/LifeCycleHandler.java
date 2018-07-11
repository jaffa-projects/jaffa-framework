package org.jaffa.soa.dataaccess;

import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.ILifecycleHandler;
import org.jaffa.persistence.exceptions.*;

import java.util.ArrayList;
import java.util.List;

public class LifeCycleHandler implements ILifecycleHandler {

    private List<ILifecycleHandler> lifecycleHandlers= new ArrayList<>();
    private ILifecycleHandler targetBean;


    @Override
    public void preAdd() throws PreAddFailedException {

    }

    @Override
    public void postAdd() throws PostAddFailedException {

    }

    @Override
    public void preUpdate() throws PreUpdateFailedException {

    }

    @Override
    public void postUpdate() throws PostUpdateFailedException {

    }

    @Override
    public void preDelete() throws PreDeleteFailedException {

    }

    @Override
    public void postDelete() throws PostDeleteFailedException {

    }

    @Override
    public void postLoad() throws PostLoadFailedException {

    }

    @Override
    public void validate() throws ApplicationExceptions, FrameworkException {

    }

    /**
     * Adds a new handler to the beginning of the list of all handlers.
     */
    public void prependTransformationHandlers(List<ILifecycleHandler> handlers) {
        for (ILifecycleHandler handler : handlers) {
            handler.setTargetBean(this);
        }

        // maintain order of the input list
        lifecycleHandlers.addAll(0, handlers);
    }

    /**
     * Adds a new handler to the end of the list of all handlers.
     */
    public void appendTransformationHandlers(List<ILifecycleHandler> handlers) {
        for (ILifecycleHandler handler : handlers) {
            handler.setTargetBean(this);
            lifecycleHandlers.add(handler);
        }
    }

    /**
     * Gets the ordered list of transformation handlers to fire when invoking lifecycle events on this handler.
     */
    public List<ILifecycleHandler> getTransformationHandlers() {
        return lifecycleHandlers;
    }

    /**
     * Sets this handler's target handler instance - that would be the instance of the handler that this instance
     * is operating on.
     */
    public void setTargetBean(ILifecycleHandler targetBean) {
        this.targetBean = targetBean;
    }

    /**
     * Gets this handler's target handler instance - that would be the instance of the handler that this instance
     * is operating on.
     */
    public ILifecycleHandler getTargetBean() {
        return targetBean;
    }

}
