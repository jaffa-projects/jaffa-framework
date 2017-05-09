<%--
Work Order Query, based on simple static table

Will provide a few basic filters and sorts to support the demo

Parameters
  limit = number of rows to limit to
  startAt = row number to start returning data from
  sort = comma seperated list of columns names to sort by (decending ordering only)
  "column name" = if a specific column name is passed in the value is used to limit
                  results to rows where the value of the column begins with the
                  value passed in
Example invocation
  workOrderQuery.jsp?startAt=11&limit=10&serial=S&sort=serial,part                  

--%><%@page import="java.util.*,java.io.*"%><%!
  static ArrayList<String> cols = null;
  static ArrayList<ArrayList<String>> data = null;
  static int pkIdx = -1;

  void readData() throws Exception {
    data = new ArrayList<ArrayList<String>>();
    cols = new ArrayList<String>();
    
    // readFile
    InputStream is = getServletContext().getResourceAsStream("/tests/extjs/multigroup/orders.csv");
    BufferedReader bReader = new BufferedReader(new InputStreamReader(is));
    
    // Read Col Headings
    String str = bReader.readLine();
    if(str==null)
      throw new ServletException("Can't Read Data File");
    for(String s : str.split(","))  
      cols.add(s);
    System.out.println("Data has "+cols.size()+" columns");
    pkIdx = cols.indexOf("workOrderNo");

    // Read Data
    str = bReader.readLine();
    int row = 0;
    while ( (str = bReader.readLine()) != null) {
      ArrayList<String> d = new ArrayList<String>();
      for(String s : str.split(","))
        d.add(s);
      data.add(d);
      if(d.size()<cols.size()) {
        System.err.println("Data Row "+row+" has missing columns " + d.size());
        System.err.println("Data = "+str);
        for(int j=0; j<d.size();j++)
          System.err.println("   "+cols.get(j)+" = " + d.get(j));
      }
      row++;
    }
    System.out.println("Data has "+data.size()+" rows");
  }
  
  private class Comp implements Comparator<ArrayList<String>> {
     ArrayList<Integer> sortIdxs;
     ArrayList<Integer> sortDir;
     private Comp(ArrayList<Integer> sortIdxs, ArrayList<Integer> sortDir) {
         this.sortIdxs = sortIdxs;
         this.sortDir = sortDir;
     }
     
     public int compare(ArrayList<String> d1, ArrayList<String> d2) {
       int j = 0;
       for(Integer i : sortIdxs) {
         int dir = sortDir.get(j++);
         String a = d1.get(i);
         String b = d2.get(i);
         if(a==null && b!=null) return -1*dir;
         if(a!=null && b!=null) {
           int c = a.compareTo(b);
           if(c!=0) return c*dir;
         }
       }
       return 0;
     }
     
     public boolean equals(Object obj) {
        return sortIdxs.equals( ((Comp)obj).sortIdxs);
     }
  }
%><%
try {
  if(cols==null) readData();
  
  String sort = request.getParameter("sort");
  if(sort!=null && sort.length()>0) {
    System.out.println("Sort By "+sort);
    ArrayList<Integer> sortIdxs = new ArrayList<Integer>();
    ArrayList<Integer> sortDir = new ArrayList<Integer>();
    
    for(String s : sort.split(",")) {
      int dir=1;
      if(s.endsWith(" DESC")) dir=-1;
      int pos = s.indexOf(" ");
      if(pos>=0)
        s=s.substring(0,pos);
      int x = cols.indexOf(s);
      if(x>=0) {
        sortIdxs.add(x);
        sortDir.add(dir);
      } else
        System.err.println("Unknown sort column : "+s);
    }  
    Collections.sort(data, new Comp(sortIdxs,sortDir));
  }
  
  int startAt = request.getParameter("start")==null? 1
                : Integer.parseInt(request.getParameter("start"));
  System.out.println("Start At Row "+startAt);
  int limit = request.getParameter("limit")==null? 5
                : Integer.parseInt(request.getParameter("limit"));
  System.out.println("Limit to "+limit);
  
  Map<Integer,String> filters = new HashMap<Integer,String>();
  Enumeration parameterNames = request.getParameterNames();
  if(parameterNames != null) {
    while(parameterNames.hasMoreElements() ) {
      String n = (String) parameterNames.nextElement();
      String v = (String) request.getParameter(n);
      int x = cols.indexOf(n);
      if(x>=0) filters.put(x,v);
    }
  }
  
  StringBuffer sb = new StringBuffer();
  int i = -1;
  int total = 0;
  for(ArrayList<String> d : data) {
    i++;
    // see if we need this object
    // apply filter
    boolean ok = true;
    if(!filters.isEmpty()) {
      for(Map.Entry<Integer,String> e : filters.entrySet() ) {
        if(!d.get(e.getKey()).startsWith(e.getValue()) ) {
          ok=false;
          break;
        }
      }
    }
    if(ok) {
      if(total>=startAt-1 && (limit==0 || total < limit+startAt-1)) {
        // write object
        if(total>=startAt) sb.append(",");
        sb.append("{\n");
        for(int j = 0;j<cols.size() && j<d.size();j++) {
          sb.append("      ").append(j==0?" ":",").append(cols.get(j)).append(" : '").append(d.get(j)).append("'\n");
        }
        sb.append("    }");
        System.out.println("   WO: " + d.get(pkIdx));
      }
      total++;
    }
  }
  System.out.println("Total Matches "+total);
  %>
 {rows:[
    <%=sb.toString()%>
 ]}
<% } catch (Exception e) {
      e.printStackTrace(System.err);
%>
ERROR - See Logs
<%}%>
