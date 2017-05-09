DECLARE
  v_count NUMBER default 0;

BEGIN
  DBMS_OUTPUT.enable(null);
  Dbms_Output.put_line('In create constraint script for J_VALID_FIELD_VALUES');

  SELECT  count(*)
    INTO  v_count
    FROM  USER_CONSTRAINTS
   WHERE  upper(INDEX_NAME) = 'J_VALID_FIELD_VALUESP1'
      or  lower(INDEX_NAME) = 'j_valid_field_valuesp1'
  ;

  IF (v_count > 0) then
     Dbms_Output.put_line('Constraint already exists, therefore not creating constraint: J_VALID_FIELD_VALUESP1');
  ELSE 
     Dbms_Output.put_line('Constraint does not exist, therefore creating constraint: J_VALID_FIELD_VALUESP1');
     EXECUTE IMMEDIATE '
        ALTER TABLE "J_VALID_FIELD_VALUES"
           ADD CONSTRAINT "J_VALID_FIELD_VALUESP1" PRIMARY KEY ("TABLE_NAME","FIELD_NAME","LEGAL_VALUE")
     ';
  END IF;
END;
/
