Welcome to JAFFA - Java Application Framework For All
=====================================================

The Build Process
=================

The scripts in this folder are part of Jaffa's build process,
and are responsible for many things. This short document
will give you a brief overview of what each ones does. It
will also give a quick start guide to how to configure the 
build process to your environment.


Overview of Scripts
===================

build.xml
---------

This is the main build for the Jaffa Framework. By default it will
- Compile All Code
- Generate jaffa.jar, jaffa-tools.jar and jaffa-html.zip

In addition it can do the following...
- Create a source code zip of the project
- Generate Java Doc
- Generate the jaffa-tomcat.jar


UnitTest.xml
------------
This script is used to test the 'jaffa framework'. It can be
used as a model for how a nightly build process could be written to do
automated testing of a core application logic.

This ant script by default will do the following...
- Compile the Unit Tests
- Build a Unit Test JAR
- Execute all Unit Tests and generate a JUnit Report


HttpUnitTestApp.xml
-------------------
This script is used to build and test the 'test application'. It can be
used as a model for how a nightly build process could be written to do
automated testing of a web application.

This ant script by default will do the following...
- Compile The HttpUnitTest Application
- Configure the Properties/XML files for the WAR file
- Build a HttpUnitTest WAR File from the compiled classes, HTML, and config files
- Stop Tomcat
- Deploy the WAR file
- Start Tomcat
- Compile all HttpUnitTests
- Build a HttpUnitTests JAR
- Run all HttpUnitTest against the deployed application and generate a JUnit Report


Main.xml
--------
This is the master script for Jaffa. It is used to call all the other scripts to do a
start to finish test. It builds Jaffa, and all of its artifacts (using build.xml), it
then does all the unit testing (using UnitTest.xml), and then continues on to do the
Http Unit Testing using (HttpUnitTestApp.xml).



Generate.xml
------------
This is a convenience and script for calling JAXB to code generate various domain objects
for XML marshalling. This is typically used in an ad-hoc fashion by the development team. 



Other Files
===========

header.xml
    Called by other scripts to do initialization
    
searchandreplace.xml
    Called by some configuration tasks that need to 
    substitute token values of real deployment values. Tokens typically take
    the form of <<token.name>> in a given file. For each unique 'token.name'
    there is a replacefilter line in this file, and there should be a 
    corresponding 'token.name=' line in the (sandbox.ant.)properties file.


Getting Started
===============

First do the following
1. Read the 'Readme.txt' in the root folder, you'll see how goo pointers there

2. Read the 'lib/Readme.txt' it talks about some additional required libraries

	a. you may provide these extra libraries in the jaffa 'lib' folder or
	
	b. set up your own project with its lib folder, and point the jaffa 
	   build to that folder. To do this edit the property 'additional.libraries.folder'
	   at the top of the 'build/Jaffa.ant.properties' file
	   
3. If you intend to use the 'Generate.xml' you'll need to read the comments in
   that file about setting up the web services developers pack.
   
4. By default all the build scripts will get there properties from 'sandbox.ant.properties'.
   Typically you will want to make a copy of this file and personalize it. To get
   the build scripts to use your file instead of the default you must update the 
   property 'config.file' at the top of the 'build/Jaffa.ant.properties' file.
   
   Typically a modified 'build/Jaffa.ant.properties' file will look like...
   
   --cut--
   # Modify this setting to specify a different application-specific properties file
   config.file=C:/Sandbox/MyJaffa/build/sandbox.ant.properties
   #config.file=./build/sandbox.ant.properties

   # Modify this setting to specify a folder having additional JAR/ZIP library files
   additional.libraries.folder=C:/Sandbox/MyJaffa/lib
   #additional.libraries.folder=
   --cut--

5. If you are using oracle, we assume that you have ojdbc14.jar, if you have ojdbc14.jar,
   then you should re-package that classes as a jar. If you want to use a different name, then
   you'll have to manually change the deploy scripts. As we have a few classes that rely on
   Oracle (for CLOB/BLOB/ARRAY support) we by default only compile these classes in Jaffa,
   if this JAR is available!
