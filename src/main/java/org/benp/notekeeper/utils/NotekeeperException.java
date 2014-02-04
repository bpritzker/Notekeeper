package org.benp.notekeeper.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * This exception will be used to normalize any exception thrown in the program.
 *
 */
public class NotekeeperException extends Exception
{
    private static final long serialVersionUID = -369530845041444222L;
    List<Pair<String, String>> nameValuePairs = new ArrayList<Pair<String, String>>();

    public NotekeeperException(String additionalMessage)
    {
        super(additionalMessage, null);
    }
    
    public NotekeeperException(String additionalMessage, Exception e)
    {
        super(additionalMessage, e);
    }

    public NotekeeperException(String additionalMessage, Exception e, String name, String value)
    {
    	super(additionalMessage, e);
    	Pair<String, String> tempNameValuePair = new ImmutablePair<String, String>(name, value);
    	nameValuePairs.add(tempNameValuePair);
    }
    
    public void addNameValuePair(String name, String value)
    {
    	Pair<String, String> tempNameValuePair = new ImmutablePair<String, String>(name, value);
    	nameValuePairs.add(tempNameValuePair);    	
    }
    
    public List<Pair<String, String>> getNameValuePairs()
    {
    	return nameValuePairs;
    }
    
    public void printStackTraceWithPairs()
    {
    	for (Pair<String, String> currentPair : nameValuePairs)
    	{
    		System.out.println("Name: <" + currentPair.getKey() + ">  --  Value: <" + currentPair.getValue() + ">" );
    	}
    	
    	this.printStackTrace();
    }
}
