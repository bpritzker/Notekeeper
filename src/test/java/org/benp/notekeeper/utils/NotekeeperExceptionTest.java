package org.benp.notekeeper.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;


public class NotekeeperExceptionTest
{
    @Test
    public void testNotekeeperExceptionConstructorWithJustMessage()
    {
        // positive test - call constructor with just a message; expect NotekeeperException with message but null event
        String expectedMessage = "My Error Message";
        NotekeeperException actualException = new NotekeeperException(expectedMessage);
        assertEquals(expectedMessage, actualException.getMessage());
        assertNotNull(actualException.getNameValuePairs());
        assertNull(actualException.getCause());
    }

    @Test
    public void testNotekeeperExceptionConstructorWithMessageAndException()
    {
        // positive test - pass in null message and null exception; expect nulls for messages
        NotekeeperException actualException = new NotekeeperException(null, null);
        assertNull(actualException.getLocalizedMessage());
        assertNull(actualException.getMessage());
        assertNotNull(actualException.getNameValuePairs());
        assertEquals("org.benp.notekeeper.utils.NotekeeperExceptionTest", actualException.getStackTrace()[0].getClassName());

        // positive test - pass in valid message and null exception; verify the mesage is what we expect
        actualException = new NotekeeperException("Custom Message 1", null);
        assertEquals("Custom Message 1", actualException.getMessage());

        // positive test - pass in valid message and exception;
        //     expect to get back the message and exception we passed in
        Exception testException = new Exception();
        actualException = new NotekeeperException("Custom Message 2", testException);
        assertEquals("Custom Message 2", actualException.getMessage());
        assertEquals(NotekeeperExceptionTest.class.getName(),
                actualException.getStackTrace()[0].getClassName().toString());
        assertEquals("testNotekeeperExceptionConstructorWithMessageAndException",
                actualException.getStackTrace()[0].getMethodName());
        assertEquals("NotekeeperExceptionTest.java", actualException.getStackTrace()[0].getFileName());
        // NOTE: If you change the test the line number value below could also change
        assertEquals(43, actualException.getStackTrace()[0].getLineNumber());
    }
    
    @Test
    public void testAddNameValuePair()
    {
    	NotekeeperException nke = new NotekeeperException(null, null);
    	
    	// positive test - add a name value pair and make sure we get it back.
    	nke.addNameValuePair("name1", "Value1");
    	assertEquals(1, nke.getNameValuePairs().size());
    	assertEquals("name1", nke.getNameValuePairs().get(0).getKey());
    	assertEquals("Value1", nke.getNameValuePairs().get(0).getValue());

    	// positive test - add second value, should get it back.
    	nke.addNameValuePair("name2", "Value2");
    	assertEquals(2, nke.getNameValuePairs().size());
    	assertEquals("name2", nke.getNameValuePairs().get(1).getKey());
    	assertEquals("Value2", nke.getNameValuePairs().get(1).getValue());

    }
    
    @Test
    public void testNotekeeperExceptionWithStringNameValues()
    {
    	// positive test - pass in null for message and exception and values for pairs
    	NotekeeperException actualException = new NotekeeperException(null, null, "TestName", "TestValue");
    	assertEquals("TestName", actualException.getNameValuePairs().get(0).getKey());
    	assertEquals("TestValue", actualException.getNameValuePairs().get(0).getValue());
    
    	// positive test - pass in null for message and exception and null for pairs. Make sure no problems
    	actualException = new NotekeeperException(null, null, null, null);
    	assertNull(actualException.getNameValuePairs().get(0).getKey());
    	assertNull(actualException.getNameValuePairs().get(0).getValue());
    	
     }
    
    
    @Test
    public void testPrintStackTraceWithPairs()
    {
        // positive test - verify when 2 pairs are set we print them both along with stack trace.
    	NotekeeperException nke = new NotekeeperException(null, null);
    	nke.addNameValuePair("name1", "Value1");
    	nke.addNameValuePair("name2", "Value2");
    	ByteArrayOutputStream myOut = new ByteArrayOutputStream();
    	ByteArrayOutputStream myErr = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));
        System.setErr(new PrintStream(myErr));
        nke.printStackTraceWithPairs();
        String actualStandardOutput = myOut.toString();
        String actualStandardError = myErr.toString();
        assertEquals(createExpectedPrintStackTraceWithPairsOutput(), actualStandardOutput);
        // We check for correctness of the exception elsewhere, don't need to it here, just make sure it prints
        assertNotNull(actualStandardError);
        
        
        
    }

	private Object createExpectedPrintStackTraceWithPairsOutput() {
		StringBuilder sb = new StringBuilder();
		sb.append("Name: <name1>  --  Value: <Value1>");
		sb.append(System.getProperty("line.separator"));
		sb.append("Name: <name2>  --  Value: <Value2>");
		sb.append(System.getProperty("line.separator"));
		return sb.toString();
	}
	
}
