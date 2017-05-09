Welcome to JAFFA - Java Application Framework For All
=====================================================

For more information visit us at http://jaffa.sourceforge.net

To get started you need to do the following

1) a. download ojdbc6.jar from oracle and include it in your local maven repository by executing following command and adding it as maven dependency

mvn install:install-file -DgroupId=com.oracle -DartifactId=ojdbc6 \
     -Dversion=11.2.0.4.0 -Dpackaging=jar -Dfile=ojdbc6.jar -DgeneratePom=true

            <dependency>
                <groupId>com.oracle</groupId>
                <artifactId>ojdbc6</artifactId>
                <version>11.2.0.4.0</version>
            </dependency>

   b. download drools-compiler-4.0.7.jar and drools-core-4.0.7.jar and include it in your local maven repository  by executing following command and adding it as maven dependency
   
mvn install:install-file -DgroupId=org.drools -DartifactId=drools-compiler \
     -Dversion=4.0.7 -Dpackaging=jar -Dfile=drools-compiler.jar -DgeneratePom=true

mvn install:install-file -DgroupId=org.drools -DartifactId=drools-core \
     -Dversion=4.0.7 -Dpackaging=jar -Dfile=drools-core.jar -DgeneratePom=true

            <dependency>
                <groupId>org.drools</groupId>
                <artifactId>drools-compiler</artifactId>
                <version>4.0.7</version>
            </dependency>
            <dependency>
                <groupId>org.drools</groupId>
                <artifactId>drools-core</artifactId>
                <version>4.0.7</version>
            </dependency>



2) Install the following products
	- Java JDK v1.7.x (http://java.sun.com)
	- Tomcat 8.x (http://jakarta.apache.org)
	
3) Have a look at build/Readme.txt for an overview of the build process, and how to configure it.

4) From Jaffa v6.0.0, jaffa projects can be built completely using maven and a parent pom is included to build jaffa

To start using of developing code you can

1) Read the "How To" guides at http://jaffa.sourceforge.net

	Try - http://jaffa.sourceforge.net/howto/getstarted/getStartedPart1.htm

2) Run the unit tests or http unit tests in Jaffa

	You'll need to edit build/sandbox.ant.properties, and make sure your pointing
	to a working database too...
	
	For the httpunittest you must have configured tomcat correctly...
	See http://jaffa.sourceforge.net/howto/getstarted/getStartedPart1.htm#section3
	

3) Check out our the sample app from CVS

	cvs -q checkout -P SampleApp CVSROOT=:pserver:anonymous@cvs.sourceforge.net:/cvsroot/jaffa
	
4) If you want to contact us for help see http://jaffa.sourceforge.net/resources/contactUs.htm	

5) Jaffa v6.0 is on GitHub under https://github.com/jaffa-projects/jaffa-framework

	
Legal
=====

JAFFA - Java Application Framework For All

Copyright © 2002 JAFFA Development Group


This library is free software; you can redistribute it and/or modify it under the terms
of the GNU Lesser General Public License as published by the Free Software Foundation;
either version 2.1 of the License, or (at your option) any later version. 

This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
See the GNU Lesser General Public License for more details.

For More Details Also See - http://jaffa.sourceforge.net/resources/legal.htm

For Acknowledgement of other open source and commercial artifacts used or referenced by this 
project go to the '\licenses' folder

Also see - http://jaffa.sourceforge.net/resources/acknowledgements.htm