This module is a packaged version of the DWR (DirectWebRemoting) module from http://gethead.org/dwr

This module contains a prebuilt JAR from DWR, source is available at their web site (http://gethead.org/dwr)

DWR is Licensed under the Apache License, Version 2.0 (http://www.apache.org/licenses/LICENSE-2.0)

Additional extentions to DWR are provided by this module and are licensed as part of the JAFFA framework under the LGPL
(See http://jaffa.cvs.sourceforge.net/*checkout*/jaffa/JaffaCore/licenses/jaffa.license)

--------------------------------
For Maven project dependency:-
--------------------------------
   Download dwr-2.0.5.jar and include it in your local maven repository  by executing following command and adding it as maven dependency

mvn install:install-file -DgroupId=org.directwebremoting -DartifactId=dwr-osgi \
     -Dversion=2.0.5 -Dpackaging=jar -Dfile=dwr-2.0.5.jar -DgeneratePom=true

            <dependency>
                <groupId>org.directwebremoting</groupId>
                <artifactId>dwr-osgi</artifactId>
                <version>2.0.5</version>
            </dependency>
