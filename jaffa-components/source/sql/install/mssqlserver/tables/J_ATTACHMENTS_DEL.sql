SET QUOTED_IDENTIFIER ON
GO

-- Note: Depending on how we choose to implement Schemas, we may need to make this more complex by
-- including such as: select schema_name()

IF EXISTS (SELECT 1 
             FROM sysobjects 
            WHERE xtype='u' AND name='J_ATTACHMENTS_DEL'
           ) 
   BEGIN
        SELECT 'Table already exists, therefore not creating table: J_ATTACHMENTS_DEL.' 
   END
ELSE 
   BEGIN
        SELECT 'Table does not exist, therefore creating table: J_ATTACHMENTS_DEL.'
        CREATE TABLE "J_ATTACHMENTS_DEL"(
                "ATTACHMENT_ID"          VARCHAR(80) NOT NULL,
                "SERIALIZED_KEY"          VARCHAR(500) NOT NULL,
                "VERSION_NUMBER"          NUMERIC(10),
                "DELETION_CREATED_ON"          DATETIME
        )
   END
GO
