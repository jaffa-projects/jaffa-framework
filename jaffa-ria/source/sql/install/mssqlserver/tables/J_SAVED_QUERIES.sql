SET QUOTED_IDENTIFIER ON
GO

-- Note: Depending on how we choose to implement Schemas, we may need to make this more complex by
-- including such as: select schema_name()

IF EXISTS (SELECT 1 
             FROM sysobjects 
            WHERE xtype='u' AND name='J_SAVED_QUERIES'
           ) 
   BEGIN
        SELECT 'Table already exists, therefore not creating table: J_SAVED_QUERIES.' 
   END
ELSE 
   BEGIN
        SELECT 'Table does not exist, therefore creating table: J_SAVED_QUERIES.'
        CREATE TABLE "J_SAVED_QUERIES"(
                "QUERY_ID"          VARCHAR(100) NOT NULL,
                "USER_ID"          VARCHAR(20) NOT NULL,
                "COMPONENT_REF"          VARCHAR(100) NOT NULL,
                "CONTEXT_REF"          VARCHAR(100),
                "QUERY_NAME"          VARCHAR(100) NOT NULL,
                "IS_DEFAULT"          BIT,
                "CRITERIA"          TEXT
        )
   END
GO
