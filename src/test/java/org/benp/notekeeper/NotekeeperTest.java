package org.benp.notekeeper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.benp.notekeeper.parser.file.FileParserFake;
import org.benp.notekeeper.utils.NotekeeperConstants;
import org.junit.Test;



public class NotekeeperTest
{

	@Test
	public void testRun()
	{
		// positive test - when run is called, so is the init method.
		MockNotekeeper notekeeper = new MockNotekeeper();
		MockNotekeeper spyNotekeeper = spy(notekeeper);
		spyNotekeeper.run(new String[0]);
		verify(spyNotekeeper, times(1)).init();
		
		// negative test - exception is thrown
        ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));
        ByteArrayOutputStream myErr = new ByteArrayOutputStream();
        System.setErr(new PrintStream(myErr));
        MockNotekeeper notekeeperException = new MockNotekeeper();
        notekeeperException.throwException = true;
        notekeeperException.run(new String[0]);
        String actualStandardOutput = myOut.toString();
        String actualStandardErr = myErr.toString();
        assertEquals("Name: <FileName>  --  Value: <MyFile.txt>" + NotekeeperConstants.NEW_LINE, actualStandardOutput);		
        assertTrue(actualStandardErr.contains("Parse Section Exception"));		

        // positive test - when args are null, we should get an message back saying so
        myOut = new ByteArrayOutputStream(); // reset 
        System.setOut(new PrintStream(myOut));
        notekeeper.run(null);
        actualStandardOutput = myOut.toString();
        assertEquals("Please enter a word to search for." + NotekeeperConstants.NEW_LINE, actualStandardOutput);

        
	}
	
    @Test
    public void testInit() throws Exception
    {
        // positive test - call init; expect the parser to be initialized
    	Notekeeper notekeeper = new Notekeeper();
    	notekeeper.init();
    	assertNotNull(notekeeper.getClass().getDeclaredField("parser"));
    }
    
    
    class MockNotekeeper extends Notekeeper
    {
    	boolean throwException = false;
    	
    	@Override 
    	protected void init()
    	{
			FileParserFake tempFileParser = new FileParserFake();
    		if (throwException)
    		{
    			tempFileParser.throwExceptionGettingParsedSections = true;
    		} 
    		super.parser = tempFileParser;
    	}
    }
    

    
	
}
