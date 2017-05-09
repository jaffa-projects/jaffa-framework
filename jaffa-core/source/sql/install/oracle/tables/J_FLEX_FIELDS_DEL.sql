DECLARE
  v_count NUMBER default 0;

BEGIN
  DBMS_OUTPUT.enable(null);
  Dbms_Output.put_line('In create table script for J_FLEX_FIELDS_DEL');

  SELECT  count(*)
    INTO  v_count
    FROM  USER_TABLES
   WHERE  TABLE_NAME = 'J_FLEX_FIELDS_DEL'
  ;

  IF (v_count > 0) then
     Dbms_Output.put_line('Table already exists, therefore not creating table: J_FLEX_FIELDS_DEL');
  ELSE 
     Dbms_Output.put_line('Table does not exist, therefore creating table: J_FLEX_FIELDS_DEL');
     EXECUTE IMMEDIATE '
        CREATE TABLE "J_FLEX_FIELDS_DEL"(
           "FLEX_FIELD_ID"          VARCHAR2(80) NOT NULL,
           "OBJECT_NAME"          VARCHAR2(80) NOT NULL,
           "KEY1"          VARCHAR2(80) NOT NULL,
           "VERSION_NUMBER"          NUMBER(10),
           "DELETION_CREATED_ON"          DATE
        )
     ';
  END IF;
END;
/
