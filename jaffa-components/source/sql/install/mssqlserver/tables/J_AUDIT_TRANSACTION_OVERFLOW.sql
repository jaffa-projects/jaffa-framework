SET QUOTED_IDENTIFIER ON
GO

-- Note: Depending on how we choose to implement Schemas, we may need to make this more complex by
-- including such as: select schema_name()

IF EXISTS (SELECT 1 
             FROM sysobjects 
            WHERE xtype='u' AND name='J_AUDIT_TRANSACTION_OVERFLOW'
           ) 
   BEGIN
        SELECT 'Table already exists, therefore not creating table: J_AUDIT_TRANSACTION_OVERFLOW.' 
   END
ELSE 
   BEGIN
        SELECT 'Table does not exist, therefore creating table: J_AUDIT_TRANSACTION_OVERFLOW.'
        CREATE TABLE "J_AUDIT_TRANSACTION_OVERFLOW"(
                "FIELD_ID"          VARCHAR(80) NOT NULL,
                "OBJECT_ID"          VARCHAR(80) NOT NULL,
                "FIELD_NAME"          VARCHAR(80) NOT NULL,
                "FROM_VALUE"          TEXT,
                "TO_VALUE"          TEXT
        )
   END
GO
