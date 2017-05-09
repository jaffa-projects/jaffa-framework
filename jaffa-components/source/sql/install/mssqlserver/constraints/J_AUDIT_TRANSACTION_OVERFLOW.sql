SET QUOTED_IDENTIFIER ON
GO

-- Note: Depending on how we choose to implement Schemas, we may need to make this more complex by
-- including such as: select schema_name()
IF EXISTS (SELECT 1 
             FROM sysobjects 
            WHERE xtype='PK' AND name='J_AUDIT_TRANSACTION_OVERFLOWP1'
           ) 
   BEGIN
        SELECT 'Constraint already exists, therefore not creating constraint: J_AUDIT_TRANSACTION_OVERFLOWP1.' 
   END
ELSE 
   BEGIN
        SELECT 'Constraint does not exist, therefore creating constraint: J_AUDIT_TRANSACTION_OVERFLOWP1.'
        ALTER TABLE "J_AUDIT_TRANSACTION_OVERFLOW"
           ADD CONSTRAINT "J_AUDIT_TRANSACTION_OVERFLOWP1" PRIMARY KEY ("FIELD_ID")
   END
GO
