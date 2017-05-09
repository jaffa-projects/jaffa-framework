SET QUOTED_IDENTIFIER ON
GO

-- Note: Depending on how we choose to implement Schemas, we may need to make this more complex by
-- including such as: select schema_name()

IF EXISTS (SELECT 1 
             FROM sysobjects 
            WHERE xtype='u' AND name='J_VALID_FIELD_VALUES'
           ) 
   BEGIN
        SELECT 'Table already exists, therefore not creating table: J_VALID_FIELD_VALUES.' 
   END
ELSE 
   BEGIN
        SELECT 'Table does not exist, therefore creating table: J_VALID_FIELD_VALUES.'
        CREATE TABLE "J_VALID_FIELD_VALUES"(
                "TABLE_NAME"          VARCHAR(20) NOT NULL,
                "FIELD_NAME"          VARCHAR(30) NOT NULL,
                "LEGAL_VALUE"          VARCHAR(200) NOT NULL,
                "DESCRIPTION"          VARCHAR(80),
                "REMARKS"          VARCHAR(250),
                "VERSION_NUMBER"          NUMERIC(10)
        )
   END
GO
