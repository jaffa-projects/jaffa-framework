DECLARE
  v_count NUMBER default 0;

BEGIN
  DBMS_OUTPUT.enable(null);
  Dbms_Output.put_line('In create table script for J_FLEX_FIELDS');

  SELECT  count(*)
    INTO  v_count
    FROM  USER_TABLES
   WHERE  TABLE_NAME = 'J_FLEX_FIELDS'
  ;

  IF (v_count > 0) then
     Dbms_Output.put_line('Table already exists, therefore not creating table: J_FLEX_FIELDS');
  ELSE 
     Dbms_Output.put_line('Table does not exist, therefore creating table: J_FLEX_FIELDS');
     EXECUTE IMMEDIATE '
        CREATE TABLE "J_FLEX_FIELDS"(
           "FLEX_FIELD_ID"          VARCHAR2(80) NOT NULL,
           "OBJECT_NAME"          VARCHAR2(80) NOT NULL,
           "KEY1"          VARCHAR2(80) NOT NULL,
           "KEY2"          VARCHAR2(80),
           "KEY3"          VARCHAR2(80),
           "FIELD_NAME"          VARCHAR2(80) NOT NULL,
           "VALUE"          VARCHAR2(2000),
           "VERSION_NUMBER"          NUMBER(10)
        )
     ';
  END IF;
END;
/
