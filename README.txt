It seems that I am learning new things all the time. The problem is that there are just too many things to keep track of. At one of my previous jobs there was a maintenance procedure we had to perform every three months. It’s was just a single sql command but I could never remember the exact syntax. So once every three months I would need to dig through all my files/emails/google/notes looking for the exact syntax. I knew it was there somewhere but where? I did have a files of “notes” but there must have been over 200 things in that file. How do I find what I was looking for? 

How do you save over 200 items in a text file but be able to easily find what you are looking for? Most notes fit into multiple categories so a simple folder like structure to categorize them would not work. For example, if you had a sql maintenance command that you ran to clean up a history table (yea, it should be automated but let’s assume for now it’s not ;) the sql command you need to run could be classified as either “sql” or as “maintenance” or even “cleanup” (and don’t get me started on “clean-up”) since it really does fit all those categories. 
What to do, what to do…. What would I do if I forgot the word I used to mean “maintenance” (I could have called it “clean up”) but I knew it was a sql statement. I could just search for “sql” but think of how many hits that would produce?

At first I figured someone else had already solved this problem so I looked at what was available. There were so many note taking/organization programs out there that one of them would do what I wanted. Well, I could not find it. Most were concerned with allowing a user to store everything and make them accessible everywhere they wanted instead of just text with a focus on searching. 
Here is what I was looking for: 

1) KISS (Keep It Simple and Stupid): I really wanted to just be able to write something down very quickly in a text editor. I was ok sacrificing simplicity for features. I also didn’t want to spend time figuring out all the complicated custom configuration. Just start typing.

2) Don’t make me install software: I worked in Finance for many years and they frown on installing software.. oh, I also don’t want to clutter my computer or get a virus! Do I really need to install this only to find out it didn’t fit my needs? 

3) Runs on both windows and Linux: I sometimes do development on Linux so it would be nice if my solution works on both systems.

4) Fast: If I was going to be using this all the time I didn’t want to wait for it to start up. 

5) Must have “tags”*: I figured out the best way to organize my notes was with a hierarchy of tags.  A directory structure just would not work. A large number of tasks could fit into multiple categories and I wanted a note to be in all of them. 

6) I wanted more than just a simple search for an exact word. I wanted something like a translation table. For example, if I search for “db” it should also match on the word database. This was not a requirement right away but eventually it would be nice. If nothing else, it would catch misspellings.

Since I could not find what I was looking for I decided I would write my own solution. I came up with the idea that for each note I would have a standard header that listed all the groups and tags for each item. Then, later I could go back and write a program to search the file based on those groups and tags. This was just a simple copy and paste into a text file. Very simple to create but gave me all the search capability I could build. Here is what I came up with…..

It would be a simple text file and the header would look like what you see below. You just put whatever overall groups you think the note fits into and and additional tags you think would make it easy to find. The program would then parse each section and based on the words you search for it will figure out which notes best match and print them. Simple and easy.



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


