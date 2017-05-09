DECLARE
  v_count NUMBER default 0;

BEGIN
  DBMS_OUTPUT.enable(null);
  Dbms_Output.put_line('In create constraint script for J_TRANSACTION_PAYLOADS');

  SELECT  count(*)
    INTO  v_count
    FROM  USER_CONSTRAINTS
   WHERE  upper(INDEX_NAME) = 'J_TRANSACTION_PAYLOADSP1'
      or  lower(INDEX_NAME) = 'j_transaction_payloadsp1'
  ;

  IF (v_count > 0) then
     Dbms_Output.put_line('Constraint already exists, therefore not creating constraint: J_TRANSACTION_PAYLOADSP1');
  ELSE 
     Dbms_Output.put_line('Constraint does not exist, therefore creating constraint: J_TRANSACTION_PAYLOADSP1');
     EXECUTE IMMEDIATE '
        ALTER TABLE "J_TRANSACTION_PAYLOADS"
           ADD CONSTRAINT "J_TRANSACTION_PAYLOADSP1" PRIMARY KEY ("ID")
     ';
  END IF;
END;
/
