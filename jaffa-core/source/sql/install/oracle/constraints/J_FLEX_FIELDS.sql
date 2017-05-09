DECLARE
  v_count NUMBER default 0;

BEGIN
  DBMS_OUTPUT.enable(null);
  Dbms_Output.put_line('In create constraint script for J_FLEX_FIELDS');

  SELECT  count(*)
    INTO  v_count
    FROM  USER_CONSTRAINTS
   WHERE  upper(INDEX_NAME) = 'J_FLEX_FIELDSP1'
      or  lower(INDEX_NAME) = 'j_flex_fieldsp1'
  ;

  IF (v_count > 0) then
     Dbms_Output.put_line('Constraint already exists, therefore not creating constraint: J_FLEX_FIELDSP1');
  ELSE 
     Dbms_Output.put_line('Constraint does not exist, therefore creating constraint: J_FLEX_FIELDSP1');
     EXECUTE IMMEDIATE '
        ALTER TABLE "J_FLEX_FIELDS"
           ADD CONSTRAINT "J_FLEX_FIELDSP1" PRIMARY KEY ("FLEX_FIELD_ID")
     ';
  END IF;
END;
/
