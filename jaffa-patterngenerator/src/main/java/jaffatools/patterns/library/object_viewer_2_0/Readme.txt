The object_viewer_2_0 pattern has the following features
========================================================
- Uses Tiles to render the JSP
- Uses the object-viewer-meta_2_0.dtd, which has the new attributes - MainLayout, ViewerLayout, Updator
- The structure of the element 'RelatedObjectJoinBetween' has been modified to specify both logical and actual names of the join fields.
- Can invoke the Update component, if specified
- The FormBean no longer uses the initForm() method for initializing the fields. Instead, the getters get the values directly from the component.


Migrating from object_viewer_1_0 to object_viewer_2_0
======================================================
- The XML file used for the old pattern can still be used. Simply change the 'object_viewer_1_0' to 'object_viewer_2_0'
- However, the element 'RelatedObjectJoinBetween' will have to be suitably modified to pass the DTD checks
- The initForm() method has been removed from the FormBean. Handle the existing customizations in that method appropriately.
- Enable the support for Tiles by adding the TilePlugin in struts-config.xml and using the tiles-defs.xml that comes bundled in jaffa-html.zip
- Check out the build script HttpUnitTestApp.xml on how to merge the tiles definition fragments at build time
