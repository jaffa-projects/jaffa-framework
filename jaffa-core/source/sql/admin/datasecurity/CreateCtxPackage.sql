CREATE OR REPLACE TYPE role IS VARRAY(20) OF VARCHAR2(50);
/

CREATE OR REPLACE PACKAGE jaffa_sec IS 

PROCEDURE Set_UserID (p_User_ID IN use1.userid%TYPE, roles  IN role) ;
PROCEDURE ClearCtx;

END;
/

CREATE OR REPLACE PACKAGE BODY jaffa_sec IS 

PROCEDURE Set_UserID (p_User_ID IN use1.userid%TYPE, roles IN role) IS
        
        BEGIN
		DBMS_SESSION.CLEAR_CONTEXT('<<SQL.PREFIX>>_sec_ctx',null);
		
		DBMS_SESSION.SET_CONTEXT('<<SQL.PREFIX>>_sec_ctx', 'userid', p_User_ID);


		FOR i IN 1..roles.COUNT LOOP

			DBMS_SESSION.SET_CONTEXT('<<SQL.PREFIX>>_sec_ctx', 'is_' || roles(i), 'TRUE');

		END LOOP;


	END;

PROCEDURE ClearCtx IS
        
        BEGIN
		DBMS_SESSION.CLEAR_CONTEXT('<<SQL.PREFIX>>_sec_ctx',null);
		
	END;

END;

/

