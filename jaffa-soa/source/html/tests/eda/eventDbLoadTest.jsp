<%--
This test will create events, persist them with a UOW, then directly consume them.
--%>
<%@page import="
org.jaffa.presentation.portlet.session.UserSession,
java.io.IOException,
java.util.ArrayList,
java.util.List,
java.util.concurrent.ExecutorService,
java.util.concurrent.Executors,
javax.servlet.http.HttpServletRequest,
org.apache.log4j.Logger,
org.jaffa.exceptions.ApplicationException,
org.jaffa.exceptions.ApplicationExceptions,
org.jaffa.exceptions.FrameworkException,
org.jaffa.modules.messaging.services.HeaderParam,
org.jaffa.modules.user.services.UserContextWrapper,
org.jaffa.modules.user.services.UserContextWrapperFactory,
org.jaffa.persistence.UOW,
org.jaffa.persistence.exceptions.*,
org.jaffa.presentation.portlet.session.UserSession,
org.jaffa.security.SecurityTag,
org.jaffa.session.ContextManagerFactory,
org.jaffa.soa.services.SOAEventQueueMessage,
org.jaffa.soa.services.SOAEventHandler,
org.jaffa.soa.domain.SOAEvent,
org.jaffa.soa.domain.SOAEventParam
" %>
<%!

        private static final Logger log = Logger.getLogger(TestThread.class);
        
	class TestThread implements Runnable {

	    private String soaEventParamName, soaEventParamValue, eventName, eventDesc, userId;
	    

	    TestThread(String userId, String soaEventParamName, String soaEventParamValue, String eventName, String eventDesc){
		this.userId = userId;
		this.soaEventParamName = soaEventParamName;
		this.soaEventParamValue = soaEventParamValue;
		this.eventName = eventName;
		this.eventDesc = eventDesc;
	    }

	    public void run() {
		UOW uow = null;
		
		//Persist the Event (that would be done by the user) ....
		String id=null;
		try {
		    uow = new UOW();
		    SOAEvent soaEvent = new SOAEvent();
		    soaEvent.setEventName(eventName);
		    soaEvent.setDescription(eventDesc);
    		    uow.add(soaEvent);
    		    
		    SOAEventParam soaEventParam = new SOAEventParam();
		    soaEventParam.setEventId(soaEvent.getEventId());
		    soaEventParam.setName(soaEventParamName);
		    soaEventParam.setValue(soaEventParamValue);
		    uow.add(soaEventParam);
		    
		    uow.commit();
		    id = soaEvent.getEventId();
		    log.info("Wrote Event: "+id);
		    uow = null;
		} catch (FrameworkException fe) {
		    fe.printStackTrace();
		} catch (ApplicationException ae) {
		    ae.printStackTrace();
		} finally {
		    if(uow!=null) {
			try {
			    uow.rollback();
			} catch(Exception e) {
			    e.printStackTrace();
			    //the uow cannot be rolled back
			}
		    }
		}
		if(id==null) {
			log.error("Failed to Write Event to DB");
			return;
		}
		
		// Now pretend we can execute it straight way .....
		
		UserContextWrapper ucw = null;
		try {
		    ucw = UserContextWrapperFactory.instance(userId);
		    uow = new UOW();
		    HeaderParam headerParam = new HeaderParam(soaEventParamName, soaEventParamValue);
		    List<HeaderParam> headerParamList = new ArrayList<HeaderParam>();
		    headerParamList.add(headerParam);
		    
		    // This would submit the event to run
		    //RaiseEventService raiseEventService = new RaiseEventService();
		    //raiseEventService.raiseSoaEvent(uow, eventName, eventDesc,null,headerParamList);
		    
		    // This will execute the event directly
		    SOAEventQueueMessage event = new SOAEventQueueMessage();
		    event.setEventId(java.util.UUID.randomUUID().toString());
		    event.setEventName(this.eventName);
		    event.setDescription(this.eventDesc);
		    event.setCategory("Test");
		    event.setCreatedOn(new org.jaffa.datatypes.DateTime());
		    event.setCreatedBy(this.userId);
		    event.setHeaderParams((HeaderParam[])headerParamList.toArray(new HeaderParam[0]));
		    SOAEventHandler seh = new SOAEventHandler();
		    seh.invoke(uow, event);
		
		
		    // Now delete the event...
		    SOAEvent ev = SOAEvent.findByPK(uow, id);
		    if(ev!=null) {
		    	uow.delete(ev);
		    	//log.info("Deleted Event: "+id);
		    } else
		    	log.warn("Event was lost: "+id);
		    uow.commit();
		    log.info("Processed Event: "+id);
		} catch (ApplicationExceptions applicationExceptions) {
		    applicationExceptions.printStackTrace();
		} catch (FrameworkException fe) {
		    fe.printStackTrace();
		} catch (ApplicationException ae) {
		    ae.printStackTrace();
		} finally {
		    if(ucw!=null)
		        ucw.unsetContext();
		    if(uow!=null) {
			try {
			    uow.rollback();
			} catch(Exception e) {
			    e.printStackTrace();
			    //the uow cannot be rolled back
			}
		    }
		}
	    }
	}


    private void execute(HttpServletRequest request, JspWriter out) throws Exception {
        Integer noOfEvents = Integer.parseInt(request.getParameter("numberOfEvents"));
        Integer poolSize = Integer.parseInt(request.getParameter("poolSize"));
        if(poolSize<1 || poolSize>1000) {
        	out.write("<h1>Pool size must be 1..1,000</h1>");
        	return;
        }
        
        if(noOfEvents<1 || noOfEvents>1000000) {
        	out.write("<h1>No of Events must be 1..1,000,000</h1>");
        	return;
        }
        
        long startTime = System.currentTimeMillis();
        String userId = UserSession.getUserSession(request).getUserId();
        String soaEventParamName = request.getParameter("name");
        String soaEventParamValue = request.getParameter("value");
        String eventName = request.getParameter("eventName");
        
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        for(int i = 0; i < noOfEvents ; i++){
	    TestThread tt = new TestThread(userId, soaEventParamName, soaEventParamValue, eventName, eventName+":"+i);
	    executor.execute(tt);
	}
	executor.shutdown(); 
        
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime) / 1000;
   
        out.write("No of Events Created " + noOfEvents + "<br>");
        out.write("Total Duration (sec)  " + duration);
    }


%>
<html>
<head>
    <title>Event Load Test</title>
    <style>
        body {
            color: black;
            font-size: 10pt;
            font-family: Tahoma;
        }

        .rulename {
            font-weight: bold;
            color: blue;
        }

        .fieldname {
            font-weight: bold;
            color: green;
        }

        table {
            font-size: 8pt;
            color: black;
            font-family: Tahoma;
        }

        thead td {
            font-size: 8pt;
            color: black;
            background: #CBE0F5;
            padding: 3px;
            text-align: left;
            border-right: 1px groove #B1CAEA;
        }

        tr {
            font-size: 8pt;
            color: black;
            background: #f1f1f1;
            padding: 3px;
        }

        tr.row0 {
            background: #f1f1f1;
        }

        tr.row1 {
            background: #e2e2e2;
        }

        td {
            font-size: 8pt;
            border-bottom: 1px solid #C8C8C8;
            border-left: 1px solid #C8C8C8;
            padding: 2px;
            color: black;
        }

        .true {
            font-weight: bold;
            color: red;
        }

        .false {
            color: gray;
        }
    </style>
</head>
<body>
<form action="eventDbLoadTest.jsp" method="post">
    <h2>SoaEvent DB Queue Test</h2>
    <%
        Long numberOfEvents = (request != null && request.getParameter("numberOfEvents") != null ? Long.parseLong(request.getParameter("numberOfEvents")) : 0);
    %>
    <table>
        <thead>
        <tr class='row0'>
            <td></td>
            <td></td>
            <td></td>
        </tr>
        </thead>
        <tr>
            <td>Soa Event Name:</td>
            <td colspan="2"><input type="text" name="eventName" size="100" value="Interfaces.Servigistics.ReplenishmentProcess.RecommendationNotReceived"></td>
        </tr>
        <tr>
            <td>Soa Event Parameters (name/value):</td>
            <td><input type="text" name="name" size="20" value="createdOn"/></td>
            <td><input type="text" name="value" size="20" value="01/01/1970 12:00:00"/></td>
        </tr>
        <tr>
            <td>No of Events:</td>
            <td><input type="text" name="numberOfEvents" value="0" size="5"/></td>
            <td></td>
        </tr>
        <tr>
            <td>Execution Threadpool Size:</td>
            <td><input type="text" name="poolSize" value="1" size="5"/></td>
            <td></td>
        </tr>

        <tr>
            <td colspan="4" align="right"><input type="submit" value="Go"/></td>
        </tr>    
    </table>
</form>
<%
    if ((numberOfEvents != null && numberOfEvents > 0)) {
        execute(request, out);
    }
%>

</form>
</body>
</html>
