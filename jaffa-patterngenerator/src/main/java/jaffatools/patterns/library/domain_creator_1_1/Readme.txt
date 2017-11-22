The domain_creator_1_1 pattern has the following features
========================================================
- The mapping files will now specify the optional 'use-member' attribute for each field so that the Persistence Engine uses the member variables for accessing/setting data on the domain object
- The setXyz() method on the domain class will be similar to the older version of the updateXyz() method.
- The updateXyz() method is now available for backwards compatibility only. It'll merely invoke the setXyz() method.
- Maintains a list of modified fields and their initial values
- Has helper methods for existence checks, findByPK
- Has getters for ForeignObjects. These will be lazily instantiated
- Has validators for the ForeignObjects. This will be invoked by the preAdd() and preUpdate() methods
- Has getters for related objects
- Has helper methods to create instances of related objects with the initialized related fields
- Has an implementation for the preAdd() method
    - It ensures the uniqueness of the primary key
    - Validates all foreign keys
- Has an implementation for the preUpdate() method
    - Validates all the modified foreign keys
- Has an implementation for the preDelete() method
    - Throws an exception if objects in association/aggregation relationship exist
    - Performs cascading deletes for objects in composition relationship
- It will ignore a relationship if any of its fields have the 'ignore' flag set to true
- Uses the domain-creator-meta_1_1.dtd, which has the new optional attribute 'Name' for a relationship

Migrating from domain_creator_1_0 to domain_creator_1_1
===============================================================
- You'll need to refactor existing domain classes if they have any implemenation for either the preAdd(), the preUpdate() or the preDelete() methods