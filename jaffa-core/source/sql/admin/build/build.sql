/* $Id: build.sql,v 1.1 2003-03-27 01:05:39 ut_paule Exp $ */
connect <<SQL.ADMIN.USER>>/<<SQL.ADMIN.PASS>>@<<SQL.DATABASE>>

PROMPT datasecurity/DropCtxPackage.sql
@@../datasecurity/DropCtxPackage.sql

PROMPT datasecurity/CreateCtxPackage.sql
@@../datasecurity/CreateCtxPackage.sql

exit;
