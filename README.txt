
DESCRIPTION:





EXAMPLE of simple section block format that should be in the Notekeeper.txt file:
##########################################
GROUPS: group
TAGS: tag1

body here
##########################################



TECHNOLOGIES DEMONSTRATED:
Maven Project - With Assembly plugin
*Java Class and project design/style: might want to expand on this to fully explain how and why it's broken into it's parts
simple use of regex
OOP
Version control with git/eclipse
Used Checkstyle (had to configure my own version)
Test Driven Development
Eclipse



Mocking with mockito 
Use reflection to access private field for unit testing - NotekeeperTest(FakeNotekeeper.getNotekeeperContext())
Use of Singleton class (Context)
synchronized block - See Context object
Refactor - Was created with just a Section object.  Refactored into ParsedSection and NoralizedSection.  Did this in small steps

DESIGN CONSIDERATIONS:
1)  Added file name from command line because if deployed I don't want to REQUIRE a properties file.




#############################################################################

MUST DO:
when a parameter is passed in that it does not know it throws an exception to the user.  NO GOOD

SHOULD CORRECT:
Notekeeper.java needs some work... see below.
in Notekeeper.java the getFileParser should not throw exception
in Notekeeper.java move "printUsage" to command line display
When parsing the CommandLineParser.parseArguments, test for null
when testing testRun it should check for something that throws an exception
in CommandLinePrinter: Have init just getInstance; the display method should just take in section list
in CommandLineParser: might want to break into 2 methods??
in FileParser.java should get the section separator from the context
move "data" directory and "nk.bat" file to a "files" directory?????  not sure about the directory name
IS "TEST NUMBER" a valid header????????  I think it should be

ENHANCEMENTS:
Do I ever want to normalize the BODY?????
Allow to search ONLY in headers
Allow to exclude "Description" when searching headers
when looking for groups use word "GROUP" or "GROUPS" same for "TAG" and "TAGS"
SEE "ENHANCEMENTS.txt" file for additional features.
add case insensitive search
NotekeeperException should take in "Classname.methodName" as a parameter.
add field to Section to indicate if it was the first "file header" section and then have the search exclude it
FileParser should use context setting
Enable the file parser to correctly handle the enhanced file header description section  see "ENHANCED.txt" of this
check for extra line feeds before the tags.  That should be ok. See "EXAMPLE-EXTRA-LINE-HEADER" at end of this file
tag or group only search from flag
Change Open file to check for file before calling parse.  That will allow the program to fail without needing to throw an exception.
Display "No matches found" if nothing was found
move "printUsage" to commandlineParser class
include a manafist file in the JAR
combine sections by group
print sections by matching groups
exclude if contains: if "group2" is found then don't display that section
add default file (if no file is passed in the args then use file name "Notekeeper-NOTES.txt" and look for it in the same directory as the jar file.
use EMMA for code coverage
parse command line 
		- exclude
		- tag only
do something with first section
Add tag weight to header description (default to 1)
normalize flag in properties file
mapping file, ie database is the same as SQL (name value pair property file)
web UI interface
test for change in sectionSeparator and headerSeparator
merge files
allow regular expression searches (with regular expression helper)
reorg all the notes by groups
remove duplicate values in headers ie: database databases or "TAGS: These are key words to search for when looking to find this section" work "to" should only be used once
load from multiple files to search from
search using regex
search should have a "score" with GROUP=10,TAG=5,OTHER=1
might want to have it NOT include sections with just headers.
On file not found error: add suggestions on what to do to fix the problem.  add to class path or suggest absolute path
some headers like "DESCRIPTION" should be a single string instead of broken up
The section seperator should be generated from a single character
might want to break up the TestingUtils class into multiple classes: one for creating data and another for asserts

REVIEW:
should i split my FileParser into helper a helper class?

This program is intended to be a very simple way to keep track and search notes that someone would like to keep track of.

I have found that there are always a vary large number of little things that are specific a job and how that company does things.  
This give me a simple way to keep and search those things.  It will allow me to label and group and serach based on those tags.


Example of why this is useful:
1)  Tracking issue command.  I used it about once every 6 months.  Just didn't memorize it
2)  Table Compare Neutering - I 3 times I needed to write code to neuter a file.  I'm not going to write a wiki, it only takes about 20 minutes to track down but much quicker to look it up in my notes.





Technologies demonstrated:
see read me


