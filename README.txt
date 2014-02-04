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
Test Driven Development
Eclipse

Mocking with mockito 





