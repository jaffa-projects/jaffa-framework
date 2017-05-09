DECLARE
  v_count NUMBER default 0;

BEGIN
  DBMS_OUTPUT.enable(null);
  Dbms_Output.put_line('In create constraint script for J_TRANSACTIONS');

  SELECT  count(*)
    INTO  v_count
    FROM  USER_CONSTRAINTS
   WHERE  upper(INDEX_NAME) = 'J_TRANSACTIONSP1'
      or  lower(INDEX_NAME) = 'j_transactionsp1'
  ;

  IF (v_count > 0) then
     Dbms_Output.put_line('Constraint already exists, therefore not creating constraint: J_TRANSACTIONSP1');
  ELSE 
     Dbms_Output.put_line('Constraint does not exist, therefore creating constraint: J_TRANSACTIONSP1');
     EXECUTE IMMEDIATE '
        ALTER TABLE "J_TRANSACTIONS"
           ADD CONSTRAINT "J_TRANSACTIONSP1" PRIMARY KEY ("ID")
     ';
  END IF;
END;
/
