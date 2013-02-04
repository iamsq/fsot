#Fifty Shades of Testing (FSOT)
### UCL COMP3092 Group Project (Group 4)

# What is FSOT?
This project aims to extend current code coverage and testing tools to reduce the amount of time required to regression test large project. A large project will have many tests suites and test cases. However, when only a few lines of the source code are changed, not all the test suites and test cases need to be run again; only the ones that are affected by the changed lines. Fifty Shades of Testing produces a CSV report containing detailed information of which lines of code are affected by which test methods, allowing an automated script/algorithm to only run the tests that are affected and save a considerable amount of time.

# Getting Started
## Quick Setup
### 1 - Download JAR
Download [Fifty Shades of Teting JAR file] (http://www.google.com)

### 2 - Run from the console
``` console
>> cd directory/of/downloaded/fsot/jar/file   # Change directory to the directory of the downloaded FSOT JAR file.
>> java -jar FSOT-quickie.jar
```

## How to prepare/setup FSOT for use?
### 1 - Clone repository or download as zip

### 2 - Compile
# Run ant -f build-jar.xml to compile.
# Dependency libraries are downloaded on the fly via Ivy.
# A distributable folder "fsot-relase" will be created.

### 3 - Run fsot
# At the moment FSOT is only capable of running from the console. In order to run FSOT on a project, you need to go the `fsot-release` folder.
``` console
>> cd fsot-release   # Change directory to the directory of the downloaded FSOT JAR file.
```
Then, you can use FSOT on your project as follows:
```
usage: fsot -f <path> -c <compile-target-name> -j <test-target-name> -v3|-v4 [options]
                                
Compulsory flags:  
  -f,--build-file <path>                    - ant buildfile of a project  
  -c,--ant-compile <compile-target-name>    - specify ant compile target name
  -j,--ant-test <test-target-name>          - specify ant test target name   
  -v3, --JUnit3 OR -v4, --JUnit4            - specify JUnit version used in the project
  
Optional flags:
  -l,--list-tests                           - list all JUnit tests found within the project
  
  -t                                        - run an individual test class or a list of test classes seperated by commas
  -a                                        - execute all identified tests except those specified in exclusion list
  -x,--exclusion-list <exclude>             - exclusion list - an individual test class or a list of test classes seperated by commas
  -h,--html                                 - generates CodeCover HTML report`)
``` 

## What is popped out? (Output)

To understand the output produced by FSOT, it is necessary to understand (what kind of coverage items does CodeCover provide) [http://codecover.org/features/coverage.html].
The output produced by FSOT is a [CSV] (http://en.wikipedia.org/wiki/Comma-separated_values) file that will be under ________________ directory.
The format of the CSV is as follows:
### Header
```
Test Name, Pass/Fail/Error, sourceFile1, S1, S2, ..., Sn, sourceFile2, S1, S2, ..., Sn, ...
```
Therefore, each row will commence with a test name followed by its pass/fail/error status. Then, under each statement (e.g. S1), it will have the number of times that that particular test ran that particular statement.

### Body
Below is a single example taken from running FSOT on the [JGAP] (http://jgap.sourceforge.net/) project.
```
Test Name,Pass/Fail/Error,org.jgap.BaseGene.java,B2,B4,S1,S2,S3,S4,S5,S6,org.jgap.BaseGeneticOperator.java,B2,S1 ...
org.jgap.supergenes.SupergeneInternalParserTest,Pass,-1,1,1,1,1,1,1,1,1,-1,2,1 ...
```
For instance, we can see that the test `org.jgap.supergenes.SupergeneInternalParserTest` runs `B2` in the `org.jgap.BaseGene.java` file once (`1`).

Note that the values under the columns with a source file name as their header (e.g. `org.jgap.BaseGene.java` or `org.jgap.BaseGeneticOperator.java` in the example above) are always `-1` to avoid any confusion.

### How to find out what `S1`, `B14` etc. are?
FSOT would be useless if one did not know what the statements in each file actually were. Therefore, another output produced by FSOT is a XML formatted mapping. This file is called `FSOTReportMapping.xml`. This mapping contains a list of all source files, their statements and the start and end offset of those statements in the file.

Below is a partial example of a mapping XML file.

``` xml
<?xml version="1.0" encoding="UTF-8"?>
<FSOTReportMapping Date="1359388927043">
   <SourceFiles Count="1">
      <SourceFile Id="id1" Name="filename1.java">
         <CoverableItem EndOffset="98765" Id="S1" StartOffset="12345" />
      </SourceFile>
   </SourceFiles>
</FSOTReportMapping>
```

As we can see, for instance, we now know that `S1` is a statement in the file `filename1.java` and it has a start offset of `12345` and an end offset of `98765`. 

##License
Copyright (c) 2013, Liam McArdle, Shahriar Tajbakhsh, Shi Qi Ng, Waseem Ilyas, Alexandra L Browne, Safwat Al-Shokairy

You may use Fifty Shades of Testing wherever you want without having to mention it; we are that kind! We are not proud of the code we have written as we were under a lot of time pressure to complete this project as part of our BSc Computer Science at University College London; but, it does the job! :-(

Licensed under [The MIT License] (http://opensource.org/licenses/MIT)
