DECLARE
  v_count NUMBER default 0;

BEGIN
  DBMS_OUTPUT.enable(null);
  Dbms_Output.put_line('In create constraint script for J_ATTACHMENTS');

  SELECT  count(*)
    INTO  v_count
    FROM  USER_CONSTRAINTS
   WHERE  upper(INDEX_NAME) = 'J_ATTACHMENTSP1'
      or  lower(INDEX_NAME) = 'j_attachmentsp1'
  ;

  IF (v_count > 0) then
     Dbms_Output.put_line('Constraint already exists, therefore not creating constraint: J_ATTACHMENTSP1');
  ELSE 
     Dbms_Output.put_line('Constraint does not exist, therefore creating constraint: J_ATTACHMENTSP1');
     EXECUTE IMMEDIATE '
        ALTER TABLE "J_ATTACHMENTS"
           ADD CONSTRAINT "J_ATTACHMENTSP1" PRIMARY KEY ("ATTACHMENT_ID")
     ';
  END IF;
END;
/
