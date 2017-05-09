SET QUOTED_IDENTIFIER ON
GO

-- Note: Depending on how we choose to implement Schemas, we may need to make this more complex by
-- including such as: select schema_name()

IF EXISTS (SELECT 1 
             FROM sysobjects 
            WHERE xtype='u' AND name='J_AUDIT_TRANSACTION_FIELDS'
           ) 
   BEGIN
        SELECT 'Table already exists, therefore not creating table: J_AUDIT_TRANSACTION_FIELDS.' 
   END
ELSE 
   BEGIN
        SELECT 'Table does not exist, therefore creating table: J_AUDIT_TRANSACTION_FIELDS.'
        CREATE TABLE "J_AUDIT_TRANSACTION_FIELDS"(
                "FIELD_ID"          VARCHAR(80) NOT NULL,
                "OBJECT_ID"          VARCHAR(80) NOT NULL,
                "FIELD_NAME"          VARCHAR(80) NOT NULL,
                "FROM_VALUE"          VARCHAR(2000),
                "TO_VALUE"          VARCHAR(2000),
                "CHANGED"          BIT,
                "FLEX"          BIT,
                "OVERFLOW_FLAG"          BIT,
                "MULTILINE_HTML_FLAG"          VARCHAR(1)
        )
   END
GO
