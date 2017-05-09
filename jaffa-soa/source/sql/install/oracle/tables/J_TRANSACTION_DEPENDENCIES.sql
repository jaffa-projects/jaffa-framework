DECLARE
  v_count NUMBER default 0;

BEGIN
  DBMS_OUTPUT.enable(null);
  Dbms_Output.put_line('In create table script for J_TRANSACTION_DEPENDENCIES');

  SELECT  count(*)
    INTO  v_count
    FROM  USER_TABLES
   WHERE  TABLE_NAME = 'J_TRANSACTION_DEPENDENCIES'
  ;

  IF (v_count > 0) then
     Dbms_Output.put_line('Table already exists, therefore not creating table: J_TRANSACTION_DEPENDENCIES');
  ELSE 
     Dbms_Output.put_line('Table does not exist, therefore creating table: J_TRANSACTION_DEPENDENCIES');
     EXECUTE IMMEDIATE '
        CREATE TABLE "J_TRANSACTION_DEPENDENCIES"(
           "TRANSACTION_ID"          VARCHAR2(80) NOT NULL,
           "DEPENDS_ON_ID"          VARCHAR2(80) NOT NULL,
           "STATUS"          VARCHAR2(20)
        )
     ';
  END IF;
END;
/
