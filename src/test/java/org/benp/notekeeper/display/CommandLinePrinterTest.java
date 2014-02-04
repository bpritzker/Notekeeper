package org.benp.notekeeper.display;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.benp.notekeeper.parser.file.RawSection;
import org.benp.notekeeper.search.SearchSection;
import org.benp.notekeeper.test.NotekeeperTestingUtils;
import org.benp.notekeeper.utils.NotekeeperConstants;
import org.junit.Test;

public class CommandLinePrinterTest
{


    @Test
    public void testCommandLinePrinterConstructor()
    {
        // positive test - null was passed when initialized; expect null to be set 
        CommandLinePrinter commandLinePrinter = new CommandLinePrinter(null);
        assertNull(commandLinePrinter.searchSections);

        // positive test - non null was passed into constructor; expect same object back
        List<SearchSection> expectedSearchSectionList = NotekeeperTestingUtils.createSearchSectionList();
        commandLinePrinter = new CommandLinePrinter(expectedSearchSectionList);
        assertEquals("Should be same object.", expectedSearchSectionList, commandLinePrinter.searchSections);
    }

    @Test
    public void testPrintSectionsToStandardOut()
    {
        // positive test - the print method is called with 1 section; expect it to print the single section
        ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));
        List<SearchSection> expectedSearchSections = NotekeeperTestingUtils.createSearchSectionList();
        CommandLinePrinter commandLinePrinter = new CommandLinePrinter(expectedSearchSections);
        commandLinePrinter.printAllSectionsToStandardOut(); // This should now be directed to the byte array (magic ;-)
        String actualStandardOutput = myOut.toString();
        StringBuilder stringBuilderExpected = new StringBuilder();
        stringBuilderExpected.append(convertRawSectionToExpectedString(expectedSearchSections.get(0).getParsedSection().getRawSection()));
        stringBuilderExpected.append(convertRawSectionToExpectedString(expectedSearchSections.get(1).getParsedSection().getRawSection()));
        assertEquals(stringBuilderExpected.toString(), actualStandardOutput);
        
        // positive test - if there are no search results (empty list) then print out message saying that
        myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));
        commandLinePrinter = new CommandLinePrinter(new ArrayList<SearchSection>());
        commandLinePrinter.printAllSectionsToStandardOut();
        actualStandardOutput = myOut.toString();
        assertEquals("No matches found. :(" + NotekeeperConstants.NEW_LINE, actualStandardOutput);
    }
    
    
    @Test
	public void testPrintParsedSectionToStandardOut()
	{
        // positive test - verify the section is printed correctly
        ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));
        CommandLinePrinter commandLinePrinter = new CommandLinePrinter(NotekeeperTestingUtils.createSearchSectionList());
        commandLinePrinter.printParsedSectionToStandardOut(NotekeeperTestingUtils.createSearchSectionList().get(0));
        String actualStandardOutput = myOut.toString();
        String expectedString = convertRawSectionToExpectedString(
        		NotekeeperTestingUtils.createSearchSectionList().get(0).getParsedSection().getRawSection());
        assertEquals(expectedString, actualStandardOutput);
	}
    
    
    @Test
    public void testPrintSectionSeperatorToStandardOut()
	{
        // positive test - verify the section separator is printed correctly
        ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));
        CommandLinePrinter commandLinePrinter = new CommandLinePrinter(NotekeeperTestingUtils.createSearchSectionList());
        commandLinePrinter.printSectionSeperatorToStandardOut();
        String actualStandardOutput = myOut.toString();
        String expectedString = CommandLinePrinter.DISPLAY_SECTION_SEPERATOR + System.getProperty("line.separator");
        assertEquals(expectedString, actualStandardOutput);
	}
    
    
    
    
    private String convertRawSectionToExpectedString(RawSection rawSection)
    {
    	StringBuilder sb = new StringBuilder();
    	sb.append(CommandLinePrinter.DISPLAY_SECTION_SEPERATOR);
    	sb.append(System.getProperty("line.separator"));
    	for (String currentLine : rawSection.getText())
    	{
    		sb.append(currentLine + System.getProperty("line.separator"));
    	}
    	
    	return sb.toString();
    }
    
    
    
}
