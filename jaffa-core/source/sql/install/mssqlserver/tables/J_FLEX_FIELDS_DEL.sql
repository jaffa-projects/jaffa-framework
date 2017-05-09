SET QUOTED_IDENTIFIER ON
GO

-- Note: Depending on how we choose to implement Schemas, we may need to make this more complex by
-- including such as: select schema_name()

IF EXISTS (SELECT 1 
             FROM sysobjects 
            WHERE xtype='u' AND name='J_FLEX_FIELDS_DEL'
           ) 
   BEGIN
        SELECT 'Table already exists, therefore not creating table: J_FLEX_FIELDS_DEL.' 
   END
ELSE 
   BEGIN
        SELECT 'Table does not exist, therefore creating table: J_FLEX_FIELDS_DEL.'
        CREATE TABLE "J_FLEX_FIELDS_DEL"(
                "FLEX_FIELD_ID"          VARCHAR(80) NOT NULL,
                "OBJECT_NAME"          VARCHAR(80) NOT NULL,
                "KEY1"          VARCHAR(80) NOT NULL,
                "VERSION_NUMBER"          NUMERIC(10),
                "DELETION_CREATED_ON"          DATETIME
        )
   END
GO
