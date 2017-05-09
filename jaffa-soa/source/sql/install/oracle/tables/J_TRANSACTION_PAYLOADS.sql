DECLARE
  v_count NUMBER default 0;

BEGIN
  DBMS_OUTPUT.enable(null);
  Dbms_Output.put_line('In create table script for J_TRANSACTION_PAYLOADS');

  SELECT  count(*)
    INTO  v_count
    FROM  USER_TABLES
   WHERE  TABLE_NAME = 'J_TRANSACTION_PAYLOADS'
  ;

  IF (v_count > 0) then
     Dbms_Output.put_line('Table already exists, therefore not creating table: J_TRANSACTION_PAYLOADS');
  ELSE 
     Dbms_Output.put_line('Table does not exist, therefore creating table: J_TRANSACTION_PAYLOADS');
     EXECUTE IMMEDIATE '
        CREATE TABLE "J_TRANSACTION_PAYLOADS"(
           "ID"          VARCHAR2(80) NOT NULL,
           "EXTERNAL_MESSAGE"          BLOB,
           "INTERNAL_MESSAGE"          BLOB,
           "INTERNAL_MESSAGE_CLASS"          VARCHAR2(2000)
        )
     ';
  END IF;
END;
/
