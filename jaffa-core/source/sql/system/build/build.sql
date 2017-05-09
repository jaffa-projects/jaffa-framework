/* $Id: build.sql,v 1.1 2003-03-27 01:05:39 ut_paule Exp $ */
connect <<SQL.SYSTEM.USER>>/<<SQL.SYSTEM.PASS>>@<<SQL.DATABASE>>

PROMPT datasecurity/DropContext.sql
@@../datasecurity/DropContext.sql
PROMPT datasecurity/CreateContext.sql
@@../datasecurity/CreateContext.sql

exit;
