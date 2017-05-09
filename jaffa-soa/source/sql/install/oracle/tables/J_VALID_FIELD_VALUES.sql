DECLARE
  v_count NUMBER default 0;

BEGIN
  DBMS_OUTPUT.enable(null);
  Dbms_Output.put_line('In create table script for J_VALID_FIELD_VALUES');

  SELECT  count(*)
    INTO  v_count
    FROM  USER_TABLES
   WHERE  TABLE_NAME = 'J_VALID_FIELD_VALUES'
  ;

  IF (v_count > 0) then
     Dbms_Output.put_line('Table already exists, therefore not creating table: J_VALID_FIELD_VALUES');
  ELSE 
     Dbms_Output.put_line('Table does not exist, therefore creating table: J_VALID_FIELD_VALUES');
     EXECUTE IMMEDIATE '
        CREATE TABLE "J_VALID_FIELD_VALUES"(
           "TABLE_NAME"          VARCHAR2(20) NOT NULL,
           "FIELD_NAME"          VARCHAR2(30) NOT NULL,
           "LEGAL_VALUE"          VARCHAR2(200) NOT NULL,
           "DESCRIPTION"          VARCHAR2(80),
           "REMARKS"          VARCHAR2(250),
           "VERSION_NUMBER"          NUMBER(10)
        )
     ';
  END IF;
END;
/
