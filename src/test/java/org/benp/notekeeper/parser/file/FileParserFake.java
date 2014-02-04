package org.benp.notekeeper.parser.file;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import org.benp.notekeeper.parser.ParsedSection;
import org.benp.notekeeper.utils.NotekeeperException;


/**
 * This is a fake object because the code we are overriding the getFile method when testing. This will allow us to redirect our testing 
 * to find the location of the test file.
 * 
 * Definition: 
 * A fake is an object in use during a test that the code you are testing can call in place of production code. 
 * A fake is used to isolate the code you are trying to test from other parts of your application.
 * 
 * @author Ben
 *
 */
public class FileParserFake extends FileParser
{
	 public static String pathToTestingFiles = "src/test/resources/files";
	 public List<RawSection> parseRawSectionsResult = null;
	 public boolean fakeGetScanner = false;
	 public boolean fakeLoadRawSectionsFromFile = false;
	 public boolean throwExceptionGettingParsedSections = false;
	 
	@Override
	public File getFile()
	{
		return new File(pathToTestingFiles + "/Notekeeper.txt");
	}
	
	
	
	@Override
	protected List<RawSection> loadRawSections() throws NotekeeperException
	{
		if (parseRawSectionsResult == null)
		{
			return super.loadRawSections();
		} else
		{
			return parseRawSectionsResult;
		}
	}
	
	@Override
	protected Scanner getScanner(File file) throws NotekeeperException
	{
		if (fakeGetScanner)
		{
			return null;
		} else
		{
			return super.getScanner(file);
		}
	}
	
	
	@Override
	protected List<RawSection> loadRawSectionsFromFile(Scanner fileScanner)
	{
		if (fakeLoadRawSectionsFromFile)
		{
			return null;
		} else
		{
			return super.loadRawSectionsFromFile(fileScanner);
		}
	}
	
    @Override
    public List<ParsedSection> getParsedSections() throws NotekeeperException
    {
    	if (throwExceptionGettingParsedSections)
    	{
    		throw new NotekeeperException("Parse Section Exception", null, "FileName", "MyFile.txt");
    	} else
    	{
    		return super.getParsedSections();
    	}
    }
	
}