DECLARE
  v_count NUMBER default 0;

BEGIN
  DBMS_OUTPUT.enable(null);
  Dbms_Output.put_line('In create table script for J_SAVED_QUERIES');

  SELECT  count(*)
    INTO  v_count
    FROM  USER_TABLES
   WHERE  TABLE_NAME = 'J_SAVED_QUERIES'
  ;

  IF (v_count > 0) then
     Dbms_Output.put_line('Table already exists, therefore not creating table: J_SAVED_QUERIES');
  ELSE 
     Dbms_Output.put_line('Table does not exist, therefore creating table: J_SAVED_QUERIES');
     EXECUTE IMMEDIATE '
        CREATE TABLE "J_SAVED_QUERIES"(
           "QUERY_ID"          VARCHAR2(100) NOT NULL,
           "USER_ID"          VARCHAR2(20) NOT NULL,
           "COMPONENT_REF"          VARCHAR2(100) NOT NULL,
           "CONTEXT_REF"          VARCHAR2(100),
           "QUERY_NAME"          VARCHAR2(100) NOT NULL,
           "IS_DEFAULT"          CHAR(1),
           "CRITERIA"          CLOB
        )
     ';
  END IF;
END;
/
