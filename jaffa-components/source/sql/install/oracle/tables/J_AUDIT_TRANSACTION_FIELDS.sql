DECLARE
  v_count NUMBER default 0;

BEGIN
  DBMS_OUTPUT.enable(null);
  Dbms_Output.put_line('In create table script for J_AUDIT_TRANSACTION_FIELDS');

  SELECT  count(*)
    INTO  v_count
    FROM  USER_TABLES
   WHERE  TABLE_NAME = 'J_AUDIT_TRANSACTION_FIELDS'
  ;

  IF (v_count > 0) then
     Dbms_Output.put_line('Table already exists, therefore not creating table: J_AUDIT_TRANSACTION_FIELDS');
  ELSE 
     Dbms_Output.put_line('Table does not exist, therefore creating table: J_AUDIT_TRANSACTION_FIELDS');
     EXECUTE IMMEDIATE '
        CREATE TABLE "J_AUDIT_TRANSACTION_FIELDS"(
           "FIELD_ID"          VARCHAR2(80) NOT NULL,
           "OBJECT_ID"          VARCHAR2(80) NOT NULL,
           "FIELD_NAME"          VARCHAR2(80) NOT NULL,
           "FROM_VALUE"          VARCHAR2(2000),
           "TO_VALUE"          VARCHAR2(2000),
           "CHANGED"          CHAR(1),
           "FLEX"          CHAR(1),
           "OVERFLOW_FLAG"          CHAR(1),
           "MULTILINE_HTML_FLAG"          VARCHAR2(1)
        )
     ';
  END IF;
END;
/
