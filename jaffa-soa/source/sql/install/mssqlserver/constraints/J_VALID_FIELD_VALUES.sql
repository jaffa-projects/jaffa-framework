SET QUOTED_IDENTIFIER ON
GO

-- Note: Depending on how we choose to implement Schemas, we may need to make this more complex by
-- including such as: select schema_name()
IF EXISTS (SELECT 1 
             FROM sysobjects 
            WHERE xtype='PK' AND name='J_VALID_FIELD_VALUESP1'
           ) 
   BEGIN
        SELECT 'Constraint already exists, therefore not creating constraint: J_VALID_FIELD_VALUESP1.' 
   END
ELSE 
   BEGIN
        SELECT 'Constraint does not exist, therefore creating constraint: J_VALID_FIELD_VALUESP1.'
        ALTER TABLE "J_VALID_FIELD_VALUES"
           ADD CONSTRAINT "J_VALID_FIELD_VALUESP1" PRIMARY KEY ("TABLE_NAME","FIELD_NAME","LEGAL_VALUE")
   END
GO
