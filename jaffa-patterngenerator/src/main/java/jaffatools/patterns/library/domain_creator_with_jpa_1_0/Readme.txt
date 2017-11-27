The domain_creator_with_jpa_1_0 pattern has the following features
==================================================================
- It is based off the domain_creator_1_2
- It has been enhanced to create domain classes that are compatible with JPA
- The mapping file has been modified to not append 'm_' to the member names. It assigns 'java.sql.Timestamp' as the type for DateTime and DateOnly fields, and 'java.lang.String' for BOOLEAN_YN, BOOLEAN_TF and BOOLEAN_10 fields
- The Create Table script for Oracle uses the Timestamp datatype for DateTime fields
