CREATE OR REPLACE PACKAGE itemfilter AS
FUNCTION item_policy (D1 VARCHAR2, D2 VARCHAR2) RETURN VARCHAR2;


END;

/

CREATE OR REPLACE PACKAGE BODY itemfilter AS

FUNCTION item_policy (D1 VARCHAR2, D2 VARCHAR2) RETURN VARCHAR2
IS D_predicate VARCHAR2(2000);
	BEGIN
	IF sys_context('jaffa_sec_ctx', 'userid') = 'SYSTEM' THEN
		D_predicate := '';
	ELSIF sys_context('jaffa_sec_ctx', 'userid') = '' THEN
		D_predicate := ' ''1'' = ''2'' ';
        ELSIF sys_context('jaffa_sec_ctx', 'is_Manager') = 'TRUE' THEN

		D_predicate := 'serial < ''C''';


        ELSE
		D_predicate := '';            

	END IF; 
                RETURN D_predicate;    
        
                
END;

END itemfilter;
/

