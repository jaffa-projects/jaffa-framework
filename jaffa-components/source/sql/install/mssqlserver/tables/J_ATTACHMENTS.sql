SET QUOTED_IDENTIFIER ON
GO

-- Note: Depending on how we choose to implement Schemas, we may need to make this more complex by
-- including such as: select schema_name()

IF EXISTS (SELECT 1 
             FROM sysobjects 
            WHERE xtype='u' AND name='J_ATTACHMENTS'
           ) 
   BEGIN
        SELECT 'Table already exists, therefore not creating table: J_ATTACHMENTS.' 
   END
ELSE 
   BEGIN
        SELECT 'Table does not exist, therefore creating table: J_ATTACHMENTS.'
        CREATE TABLE "J_ATTACHMENTS"(
                "ATTACHMENT_ID"          VARCHAR(80) NOT NULL,
                "DOCUMENT_REFERENCE_ID"          VARCHAR(80),
                "SERIALIZED_KEY"          VARCHAR(500) NOT NULL,
                "ORIGINAL_FILE_NAME"          VARCHAR(255),
                "ATTACHMENT_TYPE"          VARCHAR(1),
                "CONTENT_TYPE"          VARCHAR(100),
                "DESCRIPTION"          VARCHAR(100),
                "REMARKS"          VARCHAR(255),
                "SUPERCEDED_BY"          VARCHAR(80),
                "CREATED_ON"          DATETIME,
                "CREATED_BY"          VARCHAR(20),
                "LAST_CHANGED_ON"          DATETIME,
                "LAST_CHANGED_BY"          VARCHAR(20),
                "DATA"          IMAGE,				
                "VERSION_NUMBER"          NUMERIC(10)
        )
   END
GO
