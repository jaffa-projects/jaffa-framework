The object_lookup_2_0 pattern has the following features
========================================================
- Uses Tiles to render the criteria and results screens
- Has the property 'consolidatedCriteriaAndResults' which will display the criteria and results JSPs in one page
- New base classes LookupComponent2, LookupAction and LookupForm
- This uses just one Form class to support both the criteria and results JSPs
- This uses just one Action class to handle the events raised by the criteria and results JSPs
- Displays 'NumberOfRecords' below the UserGrid on the results jsp. Added the message 'label.Jaffa.Inquiry.numberOfRecords={0} records were retrieved'
- Uses the object-lookup-meta_2_0.dtd, which has the new attributes - ConsolidatedCriteriaAndResultsTitle, MainLayout, LookupLayout
- Supports multi-selects. For this to work, the client component will need to register IMultiSelectLookupListener with the Lookup component and render it in the same screen


Migrating from object_lookup_1_0 to object_lookup_2_0
======================================================
- One of the biggest change is that there is just one Form class and one Action class for the criteria and results screens.
- The ComponentController has also been revamped
- The criteria and results JSPs have been simplified to just contain the required fields. The rest of the html trimmings have been moved to the layout JSPs
- The rest of the files are unchanged
- The XML file used for the old pattern can still be used. Simply change the 'object_lookup_1_0' to 'object_lookup_2_0'
- Before regenerating the lookup components, get a list of customizations using SourceDecomposerUtils
- You'll have to manually add the customizations for the XxxLookupForm, XxxLookupAction and XxxLookupComponent classes. Code merging will work for all other files, where applicable
- Enable the support for Tiles by adding the TilePlugin in struts-config.xml and using the tiles-defs.xml that comes bundled in jaffa-html.zip
- Check out the build script HttpUnitTestApp.xml on how to merge the tiles definition fragments at build time
