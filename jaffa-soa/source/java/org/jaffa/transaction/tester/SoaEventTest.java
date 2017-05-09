package org.jaffa.transaction.tester;


import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.exceptions.*;
import org.jaffa.presentation.portlet.session.UserSession;
import org.jaffa.security.SecurityTag;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.soa.services.RaiseEventService;
import org.jaffa.modules.messaging.services.HeaderParam;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jaffa.modules.user.services.UserContextWrapperFactory;
import org.jaffa.modules.user.services.UserContextWrapper;

public class SoaEventTest {
    private static final Logger log = Logger.getLogger(SoaEventTest.class);

    public void testSoaEvent(String userId, String soaEventParamName, String soaEventParamValue, String eventName, String eventDesc, long count, int poolSize) {
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);

        for (int i = 0; i < count; i++) {
            TestThread tt = new TestThread(userId, soaEventParamName, soaEventParamValue, eventName + i, eventDesc);
            //Thread t = new Thread(tg, tt);
            //t.start();
            executor.execute(tt);
        }
        executor.shutdown();
    }


}

class TestThread implements Runnable {

    private String soaEventParamName, soaEventParamValue, eventName, eventDesc, userId;

    TestThread(String userId, String soaEventParamName, String soaEventParamValue, String eventName, String eventDesc) {
        this.userId = userId;
        this.soaEventParamName = soaEventParamName;
        this.soaEventParamValue = soaEventParamValue;
        this.eventName = eventName;
        this.eventDesc = eventDesc;
    }

    @Override
    public void run() {
        UOW uow = null;
        UserContextWrapper ucw = null;
        try {
            synchronized (this) {
                ucw = UserContextWrapperFactory.instance(userId);
            }
            uow = new UOW();
            HeaderParam headerParam = new HeaderParam(soaEventParamName, soaEventParamValue);
            RaiseEventService raiseEventService = new RaiseEventService();
            List<HeaderParam> headerParamList = new ArrayList<HeaderParam>();
            headerParamList.add(headerParam);
            raiseEventService.raiseSoaEvent(uow, eventName, eventDesc, null, headerParamList);
            uow.commit();
        } catch (ApplicationExceptions applicationExceptions) {
            applicationExceptions.printStackTrace();
        } catch (FrameworkException fe) {
            fe.printStackTrace();
        } catch (ApplicationException ae) {
            ae.printStackTrace();
        } finally {
            synchronized (this) {
                if (ucw != null)
                    ucw.unsetContext();
            }
            if (uow != null) {
                try {
                    uow.rollback();
                } catch (Exception e) {
                    e.printStackTrace();
                    //the uow cannot be rolled back
                }
            }
        }
    }
}
