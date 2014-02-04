package org.benp.notekeeper.parser.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.benp.notekeeper.parser.ParsedSection;
import org.benp.notekeeper.parser.Parser;
import org.benp.notekeeper.parser.SectionHeader;
import org.benp.notekeeper.utils.NotekeeperException;
import org.benp.notekeeper.utils.NotekeeperStringUtils;
import org.benp.notekeeper.utils.NotekeeperUtils;

public class FileParser implements Parser
{
	
	private String sectionSeparator = "##############################";
	protected final String GET_HEADERS = "HEADERS";
	protected final String GET_BODY = "BODY";
	private String headerIndicator = ":";
	private File fileToParse;

	
	public FileParser()
	{
		fileToParse = new File("Notekeeper.txt");
	}
	
	
	public FileParser(File inFileToParse)
	{
		fileToParse = inFileToParse;
	}
	

	public List<ParsedSection> getParsedSections() throws NotekeeperException
	{
		List<RawSection> rawSections = loadRawSections();
		List<ParsedSection> resultParsedSectoins = parseRawSections(rawSections);
		return resultParsedSectoins;
	}
	

	protected List<RawSection> loadRawSections() throws NotekeeperException
	{
		File file = getFile();
		Scanner scanner = null;
		List<RawSection> resultRawSections = null;
		try 
		{
			scanner = getScanner(file);
			resultRawSections = loadRawSectionsFromFile(scanner);
		} finally 
		{
			NotekeeperUtils.closeQuietlyScanner(scanner);
		}
		return resultRawSections;
	}	
	
	
	/**
	 * Returns all non empty sections
	 * @param rawSections
	 * @return
	 */
	protected List<ParsedSection> parseRawSections(List<RawSection> rawSections)
	{
		List<ParsedSection> resultParsedSections = new ArrayList<ParsedSection>();
		for (RawSection currentRawSection : rawSections)
		{
			ParsedSection tempParsedSection = parseRawSection(currentRawSection);
			if (!isEmptyParsedSecton(tempParsedSection))
			{
				resultParsedSections.add(tempParsedSection);
			}
		}
		return resultParsedSections;
	}



	/**
	 * An empty raw section is defined as having no values for the headers and no NON white space in any of the 
	 * body text.
	 * 
	 * It can have multiple headers and multiple lines in the body text but they are all empty.
	 * 
	 * @param tempParsedSection
	 * @return
	 */
	protected boolean isEmptyParsedSecton(ParsedSection parsedSection)
	{
		for (SectionHeader currentHeader : parsedSection.getHeaders())
		{
			if (currentHeader.getValues().size() > 0)
			{
				return false;
			}
		}
		
		for (String currentBodyTextLine : parsedSection.getBody())
		{
            if (currentBodyTextLine.matches("\\S")) {
                return false;
            }			
		}
		
		return true;
	}


	protected ParsedSection parseRawSection(RawSection inRawSection)
	{
		if (inRawSection == null)
		{
			return null;
		}
		
		ParsedSection resultParsedSection = new ParsedSection();
		resultParsedSection.setRawSection(inRawSection);
		
		resultParsedSection.setHeaders(buildHeaderList(inRawSection));
		resultParsedSection.setBody(getHeaderBodyText(GET_BODY, inRawSection.getText()));
		
		return resultParsedSection;
	}


    protected List<SectionHeader> buildHeaderList(RawSection parsedSection) {
        List<SectionHeader> resultHeaders = new ArrayList<SectionHeader>();
        List<String> unparsedSectionHeaders = getHeaderBodyText(GET_HEADERS, parsedSection.getText());

        SectionHeader sectionHeader;
        for (String currentHeader : unparsedSectionHeaders) {
            String[] splitString = currentHeader.split(headerIndicator);
            sectionHeader = new SectionHeader();
            sectionHeader.setName(splitString[0]);
            sectionHeader.setValues(NotekeeperStringUtils.splitBySpacesOrQuoted(splitString[1]));
            sectionHeader.setMatchWeight(getMatchWeight(splitString[0]));
            resultHeaders.add(sectionHeader);
        }
        return resultHeaders;
    }

    protected int getMatchWeight(String headerName) 
    {
    	int resultMatchWeight = 1;
    	if (headerName.equals("GROUPS"))
    	{
    		resultMatchWeight = 5;
    	} else if (headerName.equals("TAGS"))
    	{
    		resultMatchWeight = 3;
    	}
    	return resultMatchWeight;
	}


	/**
     * This method takes in raw section text and pulls out either the header OR the body text from the raw section.
     * This should be done in the same method so that a change to the definitions will remain in-sync.  We do not want
     * text to be considered as both header AND body text.
     * 
     * NOTE: If speed becomes a problem this section can be refactored to run once but right now we are just 
     * parsing small text files so it's not a big deal to call twice.
     * @param type
     * @param text
     * @return
     */
    protected List<String> getHeaderBodyText(String type, List<String> text)
    {
        List<String> resultTextList = new ArrayList<String>();
        boolean foundNonHeaderLine = false;
        int currentIndex = 0;
        String currentLine;
        while (currentIndex < text.size()
                && ((type.equals(GET_HEADERS) && !foundNonHeaderLine) || type.equals(GET_BODY)))
        {
            currentLine = text.get(currentIndex);
            currentIndex++;
            // once we start the body part of the text everything else is considered part of the body
            if (!foundNonHeaderLine) {
                foundNonHeaderLine = containsNonHeaderText(currentLine);
            }

            if (type.equals(GET_HEADERS) && !foundNonHeaderLine)
            {
                resultTextList.add(currentLine);
            } else if (type.equals(GET_BODY) && foundNonHeaderLine)
            {
                resultTextList.add(currentLine);
            }
        }
        return resultTextList;
    }
    
    /**
     * This method defines what is considered a header.
     * The definition of header (subject to change):
     * ALL CAPS followed by any number of white space then colon
     * @param currentTextLine
     * @return
     */
    protected boolean containsNonHeaderText(String currentTextLine)
    {
        // if it doesn't contain the headerIndicator then it's NOT a header
        if (!currentTextLine.contains(headerIndicator)) {
            return true;
        }

        // matches any number of characters, any lower case character, any number of characters.
        // Headers names are all caps, so if we find a lowercase in the header name it's not a header.
        // This will prevent "This: is not a header" returning true
        String headerName = currentTextLine.split(headerIndicator)[0];
        return (headerName.matches(".*[a-z].*"));
    }    

    
	/**
	 * This will return ALL sections from a file. Including the header and empty ones.
	 * It's not this methods job to remove the empty sections since it just gets all sections.
	 * 
	 * @param fileScanner
	 * @return
	 */
    protected List<RawSection> loadRawSectionsFromFile(Scanner fileScanner)
    {
        List<RawSection> resultSectionList = new ArrayList<RawSection>();
        RawSection currentSection;
        String currentLine;
        int currentSectionStartingLineNumber = -1;
        int currentLineNumber = 0;

        List<String> currentSectionText = new ArrayList<String>();
        while (fileScanner.hasNext()) {
            currentLine = fileScanner.nextLine();
            currentLineNumber++;
            if (!currentLine.contains(sectionSeparator))
            {
                currentSectionText.add(currentLine);
            } else
            {
                // if the section is "empty" then clear it and start looking for the next section.
                //  clear it because it could have been just tags
                //  or just empty string that are not part of the next section
                if (isEmptyTextSection(currentSectionText))
                {
                    currentSectionText.clear();
                } else
                {
                    // found new section.  Take what we have and add it
                    currentSection = new RawSection();
                    currentSection.setText(currentSectionText);
                    currentSection.setStartingLineNumber(currentSectionStartingLineNumber + 1);
                    currentSectionStartingLineNumber = currentLineNumber;
                    resultSectionList.add(currentSection);
                    currentSectionText = new ArrayList<String>();
                }
            }
        }
        if (!isEmptyTextSection(currentSectionText))
        {
            currentSection = new RawSection();
            currentSection.setText(currentSectionText);
            currentSection.setStartingLineNumber(currentSectionStartingLineNumber + 1);
            resultSectionList.add(currentSection);
        }
        return resultSectionList;
    }
    
    
	
	
	
    /**
     * An empty section is defined as having at lest one line that NOT a
     * tag or contains at least one non space or tab character.
     * If you wanted to write a different type of file parser that
     * accepts all headers then just over ride this method
     * 
     * @param sectionTextList
     * @return
     */
    protected boolean isEmptyTextSection(List<String> sectionTextList)
    {
        if (sectionTextList == null)
        {
            return true;
        }

        if (sectionTextList.isEmpty())
        {
            return true;
        }

        int currentLineIndex = 0;
        String currentLine;
        boolean foundNonEmptyLine = false;
        while (currentLineIndex < sectionTextList.size() && !foundNonEmptyLine)
        {
            currentLine =  sectionTextList.get(currentLineIndex);
            currentLineIndex++;
            //  NOTE because the first section of a file are really just header descriptions
            if (currentLine.matches(".*\\w.*"))
            {
                foundNonEmptyLine = true;
            }
        }
        return !foundNonEmptyLine;
    }	
	
	
    protected Scanner getScanner(File file) throws NotekeeperException
    {
        Scanner resultScanner = null;
        if (file == null)
        {
            String fileNotFoundExceptionMessage = buildFileNotFoundExceptionMessage(null);
            throw new NotekeeperException(fileNotFoundExceptionMessage, null);
        }

        try {
            resultScanner = new Scanner(file);
        } catch (FileNotFoundException fnfe)
        {
            String fileNotFoundExceptionMessage = buildFileNotFoundExceptionMessage(file);
            NotekeeperUtils.closeQuietlyScanner(resultScanner);
            throw new NotekeeperException(fileNotFoundExceptionMessage, fnfe, "File", file.getAbsolutePath());
        }
        return resultScanner;
    }	
	
	
	
    /**
     * The file not found exception message should be one of the most detailed error messages because it's
     * the part of the application the user is most likely to change.
     * @param notFoundFile
     * @return String containing as much information about where it was looking for the file
     */
    protected String buildFileNotFoundExceptionMessage(File notFoundFile)
    {
        String resultMessage = "\n\n\n***** FILE NOT FOUND *****\n";
        if (notFoundFile == null)
        {
            resultMessage += "\t The file passed in was <null>";
        } else
        {
            // try to get the CanonicalFile Absolute path.
            String canonicalFileAbsolutePath = null;
            try
            {
                canonicalFileAbsolutePath = notFoundFile.getCanonicalFile().getAbsolutePath();
            } catch (IOException ioe)
            {
                // since the program is already going to halt because of file not found error we
                // don't need to throw this one, it is just an attempt to get more info
                canonicalFileAbsolutePath = "INVALID_CANONICAL_FILE";
            }
            resultMessage +=
                    "\tLooking for (Path)                         <" + notFoundFile.getPath() + ">\n"
                    + "\tLooking for (FileName)                     <" + notFoundFile.getName() + ">\n"
                    + "\tLooking for (Absolute Path)                <" + notFoundFile.getAbsolutePath() + ">\n"
                    + "\tLooking for (Canonical File Absolute Path) <" + canonicalFileAbsolutePath + ">\n\n\n";
        }
        return resultMessage;
    }
	
	
	
    /**
     * This will be reworked so it can be set from properties or parameter.
     * @return
     */
	protected File getFile()
	{
		return fileToParse;
	}

}
