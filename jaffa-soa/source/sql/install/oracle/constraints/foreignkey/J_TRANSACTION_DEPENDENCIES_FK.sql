-- Foreign Key constraint from the [TRANSACTION_ID] column to the [ID] column in the J_TRANSACTIONS table.
-- Add an index on the [TRANSACTION_ID] column if it does not already exist.
BEGIN
    -- If the foreign key already exists, log a message.
    DECLARE
        foreign_key_exists EXCEPTION;
        PRAGMA EXCEPTION_INIT(foreign_key_exists, -2275);
    BEGIN
        EXECUTE IMMEDIATE 'ALTER TABLE J_TRANSACTION_DEPENDENCIES ADD CONSTRAINT FK_J_TRANSACTION_DEPEND_1 FOREIGN KEY (TRANSACTION_ID) REFERENCES J_TRANSACTIONS (ID) ON DELETE CASCADE ENABLE';
        Dbms_Output.put_line('Foreign key constraint added on the J_TRANSACTION_DEPENDENCIES[TRANSACTION_ID] column to the J_TRANSACTIONS[ID] column.');
    EXCEPTION
        WHEN foreign_key_exists
        THEN Dbms_Output.put_line('A foreign key constraint from the [TRANSACTION_ID] column to the J_TRANSACTIONS[ID] column already exists.');
    END;

    -- If the index already exists, log a message.
    DECLARE
        index_exists EXCEPTION;
        list_exists EXCEPTION;
        PRAGMA EXCEPTION_INIT(index_exists, -955);
        PRAGMA EXCEPTION_INIT(list_exists, -1408);
    BEGIN
        EXECUTE IMMEDIATE 'CREATE INDEX J_TRANSACTION_DEPENDENCIESFI1 ON J_TRANSACTION_DEPENDENCIES (TRANSACTION_ID)';
        Dbms_Output.put_line('Index created on the J_TRANSACTION_DEPENDENCIES[TRANSACTION_ID] column.');
    EXCEPTION
        WHEN index_exists
        THEN Dbms_Output.put_line('An index already exists on the [TRANSACTION_ID] column.');
        WHEN list_exists
        THEN Dbms_Output.put_line('An index already exists on the [TRANSACTION_ID] column.');
    END;

END;
/

-- Foreign Key constraint from the [DEPENDS_ON_ID] column to the [ID] column in the J_TRANSACTIONS table.
-- Add an index on the [DEPENDS_ON_ID] column if it does not already exist.
BEGIN
    -- If the foreign key already exists, log a message.
    DECLARE
        foreign_key_exists EXCEPTION;
        PRAGMA EXCEPTION_INIT(foreign_key_exists, -2275);
    BEGIN
        EXECUTE IMMEDIATE 'ALTER TABLE J_TRANSACTION_DEPENDENCIES ADD CONSTRAINT FK_J_TRANSACTION_DEPEND_2 FOREIGN KEY (DEPENDS_ON_ID) REFERENCES J_TRANSACTIONS (ID) ON DELETE CASCADE ENABLE';
        Dbms_Output.put_line('Foreign key constraint added on the J_TRANSACTION_DEPENDENCIES[DEPENDS_ON_ID] column to the J_TRANSACTIONS[ID] column.');
    EXCEPTION
        WHEN foreign_key_exists
        THEN Dbms_Output.put_line('A foreign key constraint from the [DEPENDS_ON_ID] column to the J_TRANSACTIONS[ID] column already exists.');
    END;

    -- If the index already exists, log a message.
    DECLARE
        index_exists EXCEPTION;
        list_exists EXCEPTION;
        PRAGMA EXCEPTION_INIT(index_exists, -955);
        PRAGMA EXCEPTION_INIT(list_exists, -1408);
    BEGIN
        EXECUTE IMMEDIATE 'CREATE INDEX J_TRANSACTION_DEPENDENCIESFI2 ON J_TRANSACTION_DEPENDENCIES (DEPENDS_ON_ID)';
        Dbms_Output.put_line('Index created on the J_TRANSACTION_DEPENDENCIES[DEPENDS_ON_ID] column.');
    EXCEPTION
        WHEN index_exists
        THEN Dbms_Output.put_line('An index already exists on the [DEPENDS_ON_ID] column.');
        WHEN list_exists
        THEN Dbms_Output.put_line('An index already exists on the [DEPENDS_ON_ID] column.');
    END;

END;
/
