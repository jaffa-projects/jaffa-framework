Overview

GOLDesp generates certain domain code using patterns that specify how
source files inter-relate, for example, the object-relational mapping
between a Java domain class and the corresponding SQL table.
Important files in this process include:

 * Metadata files - These XML files contain high level mapping
  information, which may include the domain object, the database
  table, and the pattern template used to create the various need
  files.  Example metadata file -
  DomainModel/build/patterns/domain_creator_1_1/requisitioning/purchase/ScheduledQuantity.xml
  
 * Pattern Template files - These XML files contain information about
  specific files to be created and the WebMacro (.wm) files used to
  create them.  Example -
  DomainModel/source/java/patterns/library/domain_creator_1_1/DomainCreatorPattern.xml
  
 * WebMacro files - These WM files contain the details about the
  content to be created.  Example -
  DomainModel/source/java/patterns/library/domain_creator_1_1/DomainObjectWithJpa.wm
  
Code generation will not occur for every maven build; it will occur on
an as-needed basis.

Generating Files with Maven from the Command Line

One can generate files from maven in several different ways, using the
exec-maven-plugin.  While it is also possible to call the
PatternGenerator's main method from the command line, using maven
is preferred, because the pom file gathers together the dependencies,
options, and default values.  One can select an individual domain
pattern metadata file or an entire folder as source and generate code
into target folders.  If a source folder is specified, only the
metadata files in that exact directory are used; metadata files in
subfolders are not used.

Destination folders can be specified via optional command line
arguments.  Users can use the destination arguments to generate Java
in a different folder than SQL.
    * arg0 (mandatory) = source file or directory. 
    * arg1 (optional) = destination directory for java
    * arg2 (optional) = destination directory for SQL

In many cases, the source directory and destination directories should
correspond, i.e., the code generator should overwrite existing files.
The code generator knows to retain custom code (that is between
certain markers) from the source location when it is rewritten.  If
the destination is in a different location than the source, the custom
code is not retained.

Examples

Single metadata file, default destinations specified in pom file:

mvn exec:java -Dexec.mainClass="org.jaffa.tools.PatternGenerator" -Dexec.args="C:/GOLDesp/Jaffa/trunk/DomainModel/build/patterns/domain_creator_1_1/requisitioning/purchase/ScheduledQuantity.xml"

Metadata directory, default destinations specified in pom file:

mvn exec:java -Dexec.mainClass="org.jaffa.tools.PatternGenerator" -Dexec.args="C:/GOLDesp/Jaffa/trunk/DomainModel/build/patterns/domain_creator_1_1/requisitioning/purchase"

Metadata directory specified in pom file, destinations specified on
command line via exec.args:

mvn exec:java -Dexec.mainClass="org.jaffa.tools.PatternGenerator" -Dexec.args="C:/GOLDesp/Jaffa/trunk/DomainModel/build/patterns/domain_creator_1_1/requisitioning/purchase C:/Temp/GenFiles/TestMade C:/Temp/SQL"

Configuration

The pom.xml file for the pattern generator has several properties for
configuring the code generation that should probably be updated to be
consistent with the local environment when generating code.  These
properties start with "gen.".

There are two properties files, PatternGenerator.properties and
WebMacro.properties, that can also be used to configure parts of the
code generation process.  In the case where arguments provided on the
command line conflict with those specified in the properties files,
the ones from the command line take precedence.

Properties files

PatternGenerator.properties

These are the properties, with example values.  If you will be
generating classes, you will probably want to change these.

    #The root directory for the generated files
    ProjectRootDirectory=C:/GOLDesp/Jaffa/trunk/DomainModel/source

    #Directory for the temporary file created when the pattern template XML has the template metadata applied to it
    TempMetaDataDirectory=C:/Temp/TempMetaData

    #Directory to hold the generated logs
    LogDirectory=C:/Temp/logs

WebMacro.properties

The only property you are likely to want to change is the LogFile
property.

Written Files

The ProjectRootDirectory property within PatternGenerator.properties
specifies the root directory for the generated files.  In the new
PatternGenerator, command line arguments can be used to specify where
Java and SQL files are written, which will override the
ProjectRootDirectory property.  In addition to the generated files,
there are log files and temporary files written during the code
generation process.

Audit log

The audit log records significant events that occurred during pattern
generation.  Its location is specified by the LogDirectory property
from PatternGenerator.properties.  Example contents:

    11/03/2017 15:00:17 - Processing PatternTemplate:
    patterns/library/domain_creator_1_1/DomainCreatorPattern.xml using
    PatternMetaData: ScheduledQuantity.xml

    Created temporary file: C:\Temp\TempMetaData\ScheduledQuantity.xml
    Recreated with existing customizations:
    C:\GOLDesp\Jaffa\trunk\DomainModel\source\java\com\mirotechnologies\requisitioning\purchase\domain\ScheduledQuantity.java

WebMacro (WM) log

The wm log records WebMacro events that occurred during pattern
generation.  Its location is specified by the LogFile property from
WebMacro.properties.

Temp files

The directory specified by the TempMetaDataDirectory property contains
the temporary XML file created when the pattern template XML has the
template metadata applied to it.  It references some WebMacro (.wm)
template files, Java files, XML files and SQL files.

