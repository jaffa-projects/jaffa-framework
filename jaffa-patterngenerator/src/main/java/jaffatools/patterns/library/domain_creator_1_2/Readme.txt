The domain_creator_1_2 pattern has the following features
========================================================
- The LastUpdatedUserId and LastUpdatedDateTime will now be stamped, in addition to the preUpdate() method, in the preAdd() method as well
- The performPreDeleteReferentialIntegrity() method has been optimized to retrieve merely the object count for an aggregate/associated Domain class, rather than complete object data

Migrating from domain_creator_1_0 to domain_creator_1_1
===============================================================
- You'll need to refactor existing domain classes if they have any implemenation for the preAdd() method
