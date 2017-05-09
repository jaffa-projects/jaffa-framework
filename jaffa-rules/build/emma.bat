REM ===================================
REM This batch file will check the code coverage of the Jaffa Rules Engine by the unit tests.
REM It uses EMMA (http://emma.sourceforge.net), which has a single distribution 'emma.jar'
REM ===================================



REM ===================================
REM Generate the JAR to be instrumented, but first clean the distribution
REM ===================================
call ant -buildfile ./build.xml clean buildJar



REM ===================================
REM Instrument the classes in the JAR. Specify a filter to focus on specific classes
REM ===================================
java -cp ../lib/emma.jar emma instr -outmode overwrite -filter org.jaffa.rules.aop.* -instrpath ../dist/jar/jaffa-rules_1_0_0.jar -outfile ../dist/emma/coverage.em



REM ===================================
REM Execute the unit tests
REM Move the coverage file to the dist folder. Delete the redundant coverage file from the parent folder
REM ===================================
call ant -buildfile ./unit-test.xml executeUnitTest
move coverage.ec ../dist/emma
del ..\coverage.ec



REM ===================================
REM Generate the code coverage report: 'dist/emma/coverage/index.html'
REM ===================================
java -cp ../lib/emma.jar emma report -report html -sourcepath ../source/java -input ../dist/emma/coverage.em -in ../dist/emma/coverage.ec


REM ===================================
REM Move the report to the dist folder and then view it
REM ===================================
move coverage ../dist/emma
echo The report has been moved to %cd%/../dist/emma/coverage/index.html
"C:\Program Files\Internet Explorer\iexplore.exe" %cd%/../dist/emma/coverage/index.html



pause
