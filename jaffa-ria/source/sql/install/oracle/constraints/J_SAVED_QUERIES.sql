DECLARE
  v_count NUMBER default 0;

BEGIN
  DBMS_OUTPUT.enable(null);
  Dbms_Output.put_line('In create constraint script for J_SAVED_QUERIES');

  SELECT  count(*)
    INTO  v_count
    FROM  USER_CONSTRAINTS
   WHERE  upper(INDEX_NAME) = 'J_SAVED_QUERIESP1'
      or  lower(INDEX_NAME) = 'j_saved_queriesp1'
  ;

  IF (v_count > 0) then
     Dbms_Output.put_line('Constraint already exists, therefore not creating constraint: J_SAVED_QUERIESP1');
  ELSE 
     Dbms_Output.put_line('Constraint does not exist, therefore creating constraint: J_SAVED_QUERIESP1');
     EXECUTE IMMEDIATE '
        ALTER TABLE "J_SAVED_QUERIES"
           ADD CONSTRAINT "J_SAVED_QUERIESP1" PRIMARY KEY ("QUERY_ID")
     ';
  END IF;
END;
/

DECLARE
  v_count NUMBER default 0;

BEGIN
  DBMS_OUTPUT.enable(null);
  Dbms_Output.put_line('In create constraint script for J_SAVED_QUERIES');

  SELECT  count(*)
    INTO  v_count
    FROM  USER_INDEXES
   WHERE  upper(INDEX_NAME) = 'J_SAVED_QUERIESC1'
      or  lower(INDEX_NAME) = 'j_saved_queriesc1'
  ;

  IF (v_count > 0) then
     Dbms_Output.put_line('Constraint already exists, therefore not creating constraint: J_SAVED_QUERIESC1');
  ELSE 
     Dbms_Output.put_line('Constraint does not exist, therefore creating constraint: J_SAVED_QUERIESC1');
     EXECUTE IMMEDIATE '
        CREATE UNIQUE INDEX "J_SAVED_QUERIESC1" ON"J_SAVED_QUERIES" ("USER_ID","COMPONENT_REF","CONTEXT_REF","QUERY_NAME")
     ';
  END IF;
END;
/

