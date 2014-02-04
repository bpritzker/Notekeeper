package org.benp.notekeeper.parser.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.benp.notekeeper.parser.ParsedSection;
import org.benp.notekeeper.parser.SectionHeader;
import org.benp.notekeeper.test.NotekeeperTestingUtils;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileParserTest extends FileParser
{
    public static FileParserFake fileParser;
    private File EMPTY_TEST_FILE = new File(FileParserFake.pathToTestingFiles + "/emptyTestFile.txt");
    private File THREE_SECTIONS_TEST_FILE = new File(FileParserFake.pathToTestingFiles + "/threeSectionsTestFile.txt");
    private File NEGATIVE_SECTIONS_TEST_FILE = new File(FileParserFake.pathToTestingFiles + "/negativeSectionsTestFile.txt");

    @BeforeClass
    public static void beforeClass()
    {
        fileParser = new FileParserFake();
    }
    
    @Test
    public void testConstructorEmpty()
    {
    	// positive test - call the default constructor and verify the file parser file is set to default "Notekeeper.txt"
    	FileParser actualFileParser = new FileParser();
    	assertEquals("Notekeeper.txt", actualFileParser.getFile().getPath());
    }
    
    @Test
    public void testConstructorWithFile()
    {
    	// positive test - call the default constructor and verify the file parser file is set to default "Notekeeper.txt"
    	FileParser actualFileParser = new FileParser(new File("MyDirectory/Notekeeper.txt"));
    	assertEquals("MyDirectory\\Notekeeper.txt", actualFileParser.getFile().getPath());
    }
    
    
    @Test
    public void testGetParsedSections() throws Exception
    {
    	FileParserFake tempFileParser = new FileParserFake();
    	FileParserFake spyFileParser = spy(tempFileParser);
    	List<RawSection> tempRawSectionList = new ArrayList<RawSection>();
    	spyFileParser.parseRawSectionsResult = tempRawSectionList;
    	spyFileParser.getParsedSections();
    	verify(spyFileParser, times(1)).getParsedSections();
    	verify(spyFileParser, times(1)).parseRawSections(tempRawSectionList);
    	// TODO: At some point might want to try and use "Mockito.Answer" instead of getting tricky with the Fake object.
    }
    
    
    @Test
    public void testLoadRawSections() throws Exception
    {

    	FileParserFake tempFileParser = new FileParserFake();
    	FileParserFake spyFileParser = spy(tempFileParser);
    	spyFileParser.fakeGetScanner = true;
    	spyFileParser.fakeLoadRawSectionsFromFile = true;
    	spyFileParser.loadRawSections();
    	verify(spyFileParser, times(1)).getFile();
    	verify(spyFileParser, times(1)).getScanner(spyFileParser.getFile());
    	verify(spyFileParser, times(1)).loadRawSectionsFromFile(null);
    }
    
    
    @Test
    public void testParseRawSections() throws Exception
    {
    	// positive test - Test a normal single raw section
    	List<RawSection> rawSections = fileParser.loadRawSections();
    	List<ParsedSection> actualParsedSections = fileParser.parseRawSections(rawSections);
    	assertEquals(6, actualParsedSections.size());
    	
    	// positive test - Test with an empty raw section
    	RawSection emptyRawSection = new RawSection();
    	List<RawSection> emptyRawSectionList = new ArrayList<RawSection>();
    	emptyRawSectionList.add(emptyRawSection);
    	actualParsedSections = fileParser.parseRawSections(emptyRawSectionList);
    	assertEquals(0, actualParsedSections.size());
    	
    } 
    
    
    

    
    
    @Test
	public void testParseRawSection()
	{
    	// positive test - if null passed in get null back out.
    	ParsedSection actualParsedSection =  fileParser.parseRawSection(null);
		assertNull(actualParsedSection);
		
		// positive test - parse raw section
		RawSection testingRawSection = NotekeeperTestingUtils.createRawSection1();
		actualParsedSection = fileParser.parseRawSection(testingRawSection);
		NotekeeperTestingUtils.assertEquals(testingRawSection, actualParsedSection.getRawSection());
		assertEquals(3, actualParsedSection.getHeaders().size());
		assertEquals(4, actualParsedSection.getBody().size());
	}    
    

	
	@Test
	public void testGetFile()
	{
		File actualFile = fileParser.getFile();
		assertNotNull(actualFile);
	}
	
    @Test
    public void testBuildFileNotFoundExceptionMessage() throws Exception
    {
        // positive test - null was passed in; we don't want an exception but should get nice message
        String actualMessage = fileParser.buildFileNotFoundExceptionMessage(null);
        assertEquals(
                "\n\n\n***** FILE NOT FOUND *****\n"
                + "\t The file passed in was <null>"
                , actualMessage);

        // positive test - pass in valid File object; expect a good message back
        File testFile = new File("home/myDir/FileNotFoundForExceptionMessage.txt");
        actualMessage = fileParser.buildFileNotFoundExceptionMessage(testFile);
        assertEquals(
                "\n\n\n***** FILE NOT FOUND *****\n"
                + "\tLooking for (Path)                         <" + testFile.getPath() + ">\n"
                + "\tLooking for (FileName)                     <" + testFile.getName() + ">\n"
                + "\tLooking for (Absolute Path)                <" + testFile.getAbsolutePath() + ">\n"
                + "\tLooking for (Canonical File Absolute Path) <"
                + testFile.getCanonicalFile().getAbsolutePath()
                + ">\n\n\n",
                actualMessage);

        // positive test - could not get canonical file absolute path; expect nice message back
        testFile = new File("home/myDir/FileNotFoundForExceptionMessage.txt", "*badchild");
        actualMessage = fileParser.buildFileNotFoundExceptionMessage(testFile);
        assertEquals("\n\n\n***** FILE NOT FOUND *****\n"
                + "\tLooking for (Path)                         <" + testFile.getPath() + ">\n"
                + "\tLooking for (FileName)                     <" + testFile.getName() + ">\n"
                + "\tLooking for (Absolute Path)                <" + testFile.getAbsolutePath() + ">\n"
                + "\tLooking for (Canonical File Absolute Path) <" + "INVALID_CANONICAL_FILE" + ">\n\n\n",
                actualMessage);
    }
    
    @Test
    public void testIsEmptyParsedSecton()
    {
    	// positive test - empty section, should return true
    	ParsedSection tempParsedSection = NotekeeperTestingUtils.createEmptyParsedSection();
    	assertTrue(fileParser.isEmptyParsedSecton(tempParsedSection));
    }
    
    @Test
    public void testLoadRawSectionsFromFile() throws Exception
    {
        // positive test - if file is empty; get empty list back
        Scanner fileScanner = new Scanner(EMPTY_TEST_FILE);
        List<RawSection> actualSections = fileParser.loadRawSectionsFromFile(fileScanner);
        assertEquals(0, actualSections.size());

        // positive test - has multiple sections; should get all 3 sections back
        fileScanner = new Scanner(THREE_SECTIONS_TEST_FILE);

        actualSections = fileParser.loadRawSectionsFromFile(fileScanner);
        assertEquals(3, actualSections.size());
        assertEquals(0, actualSections.get(0).getStartingLineNumber());
        assertEquals(7, actualSections.get(0).getText().size());
        assertEquals(9, actualSections.get(1).getStartingLineNumber());
        assertEquals(7, actualSections.get(1).getText().size());
        assertEquals(17, actualSections.get(2).getStartingLineNumber());
        assertEquals(11, actualSections.get(2).getText().size());

        // negative test - one of the sections have NO headers; expect all sections back
        fileScanner = new Scanner(NEGATIVE_SECTIONS_TEST_FILE);

        actualSections = fileParser.loadRawSectionsFromFile(fileScanner);
        assertEquals(3, actualSections.size());
    }
    
    
    @Test
    public void testBuildHeaderList() {
        // positive test - pass in a section with text and headers;
        //     get parsed SectionHeader back
        RawSection sectionToParse = NotekeeperTestingUtils.createRawSection1();
        List<SectionHeader> actualHeaderList = fileParser.buildHeaderList(sectionToParse);
        assertEquals(3, actualHeaderList.size());
        
        assertEquals("GROUPS", actualHeaderList.get(0).getName());
        assertEquals(5, actualHeaderList.get(0).getMatchWeight());
        assertEquals(3, actualHeaderList.get(0).getValues().size());
        assertEquals("linux", actualHeaderList.get(0).getValues().get(0));
        assertEquals("find", actualHeaderList.get(0).getValues().get(1));
        assertEquals("search", actualHeaderList.get(0).getValues().get(2));
        
        assertEquals("TAGS", actualHeaderList.get(1).getName());
        assertEquals(3, actualHeaderList.get(1).getMatchWeight());
        assertEquals(2, actualHeaderList.get(1).getValues().size());
        assertEquals("grep", actualHeaderList.get(1).getValues().get(0));
        assertEquals("search", actualHeaderList.get(1).getValues().get(1));

        assertEquals("DESCRIPTION", actualHeaderList.get(2).getName());
        assertEquals(1, actualHeaderList.get(2).getMatchWeight());
        assertEquals(4, actualHeaderList.get(2).getValues().size());
        assertEquals("How", actualHeaderList.get(2).getValues().get(0));
        assertEquals("to", actualHeaderList.get(2).getValues().get(1));
        assertEquals("recursively", actualHeaderList.get(2).getValues().get(2));
        assertEquals("grep", actualHeaderList.get(2).getValues().get(3));



        // positive test - section has NO headers; it should still parse with no exceptions
        sectionToParse.getText().clear();
        sectionToParse.getText().add("Non header row");
        actualHeaderList = fileParser.buildHeaderList(sectionToParse);
        assertEquals(0, actualHeaderList.size());
    }
    
    @Test
    public void testGetMatchWeight()
    {
    	// positive test - when empty string is passed in we get 1 back
    	int actualMatchWeight = fileParser.getMatchWeight("");
    	assertEquals(1, actualMatchWeight);

    	// positive test - when non GROUPS or TAGS string is passed in we get 1 back
    	actualMatchWeight = fileParser.getMatchWeight("DESCRIPTION");
    	assertEquals(1, actualMatchWeight);

    	// positive test - when "GROUPS" string is passed in we get it's wight back
    	actualMatchWeight = fileParser.getMatchWeight("GROUPS");
    	assertEquals(5, actualMatchWeight);

    	// positive test - when "TAGS" string is passed in we get it's weight back
    	actualMatchWeight = fileParser.getMatchWeight("TAGS");
    	assertEquals(3, actualMatchWeight);

    
    }
    

    @Test
    public void testIsEmptyTextSection()
    {
        // positive test - null passed in; returns true because a null section is considered emptyTestFile
        assertTrue(fileParser.isEmptyTextSection(null));

        // positive test - is empty non null list; expect true since the section is empty
        List<String> sectionTextList = new ArrayList<String>();
        assertTrue(fileParser.isEmptyTextSection(sectionTextList));

        // positive test - section text list contains multiple all empty or blank spaces Strings
        sectionTextList.add("");
        sectionTextList.add("      ");
        assertTrue("This should be an empty section", fileParser.isEmptyTextSection(sectionTextList));

        // positive test - contains a valid tag; this is not considered empty so return false
        sectionTextList.clear();
        sectionTextList.add("TAG: aTag");
        assertFalse(fileParser.isEmptyTextSection(sectionTextList));

        // positive test - contains a line that is qualifies as non empty and is not a tag;
        //     since it contains data it's not empty
        sectionTextList.clear();
        sectionTextList.add("TAG: this");
        sectionTextList.add("Non empty String");
        assertFalse(fileParser.isEmptyTextSection(sectionTextList));
    }
    
    
 
    
    @Test
    public void testGetHeaderBodyText() {
        List<String> textList = NotekeeperTestingUtils.createRawSectionTextSection1();

        // positive test - text with body and headers is passed in; get headers list with all headers
        List<String> actualList = fileParser.getHeaderBodyText(GET_HEADERS, textList);
        assertEquals(3, actualList.size());

        // positive test - text with body and headers is passed in; expect body text is parsed
        actualList = fileParser.getHeaderBodyText(GET_BODY, textList);
        assertEquals(4, actualList.size());

        // positive test - text body with NO headers is passed in; get the body text back
        textList.clear();
        textList.add(" non tag line ");
        textList.add("TAGLIKE: this is a tag like line in the body");
        actualList = fileParser.getHeaderBodyText(GET_BODY, textList);
        assertEquals(2, actualList.size());

        // positive test - text with no body but has headers; get headers back
        textList.clear();
        textList.add(" HEADER: a header");
        textList.add("TAGLIKE: this is a tag like line in the body");
        actualList = fileParser.getHeaderBodyText(GET_HEADERS, textList);
        assertEquals(2, actualList.size());
    }    
    
    
    @Test
    public void testContainsNonHeaderText() {
        // positive test - text with headers is passed in;
        //     returns false because it does not contain any body text
        boolean actual = fileParser.containsNonHeaderText("TAGS: oracle tag2");
        assertFalse(actual);

        // positive test - line contains body text; true because it has body text
        actual = fileParser.containsNonHeaderText("no tags here.");
        assertTrue(actual);

        // positive test - line contains header text with underscores and spaces;
        // expect false because it's a header
        actual = fileParser.containsNonHeaderText("UNDESCORE_TAG DESC: oracle tag2");
        assertFalse(actual);

        // positive test - body text with a colon; true because line is NOT a tag event though it contains a colon
        actual = fileParser.containsNonHeaderText("This should NOT be a tag : more stuff here");
        assertTrue(actual);

        // positive test - line is empty; true, it's considered a non header
        actual = fileParser.containsNonHeaderText("");
        assertTrue(actual);

        // positive test - line contains only white space characters; true since it's not a header
        actual = fileParser.containsNonHeaderText("  \t  \n  ");
        assertTrue(actual);
    }
}
