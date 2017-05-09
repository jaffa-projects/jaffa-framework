DECLARE
  v_count NUMBER default 0;

BEGIN
  DBMS_OUTPUT.enable(null);
  Dbms_Output.put_line('In create table script for J_ATTACHMENTS');

  SELECT  count(*)
    INTO  v_count
    FROM  USER_TABLES
   WHERE  TABLE_NAME = 'J_ATTACHMENTS'
  ;

  IF (v_count > 0) then
     Dbms_Output.put_line('Table already exists, therefore not creating table: J_ATTACHMENTS');
  ELSE 
     Dbms_Output.put_line('Table does not exist, therefore creating table: J_ATTACHMENTS');
     EXECUTE IMMEDIATE '
        CREATE TABLE "J_ATTACHMENTS"(
           "ATTACHMENT_ID"          VARCHAR2(80) NOT NULL,
           "DOCUMENT_REFERENCE_ID"          VARCHAR2(80),
           "SERIALIZED_KEY"          VARCHAR2(500) NOT NULL,
           "ORIGINAL_FILE_NAME"          VARCHAR2(255),
           "ATTACHMENT_TYPE"          VARCHAR2(1),
           "CONTENT_TYPE"          VARCHAR2(100),
           "DESCRIPTION"          VARCHAR2(100),
           "REMARKS"          VARCHAR2(255),
           "SUPERCEDED_BY"          VARCHAR2(80),
           "CREATED_ON"          DATE,
           "CREATED_BY"          VARCHAR2(20),
           "LAST_CHANGED_ON"          DATE,
           "LAST_CHANGED_BY"          VARCHAR2(20),
           "DATA"          BLOB,         
           "VERSION_NUMBER"          NUMBER(10)
        )
     ';
  END IF;
END;
/
