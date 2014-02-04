package org.benp.notekeeper.search;

import java.util.ArrayList;
import java.util.List;

import org.benp.notekeeper.parser.ParsedSection;
import org.benp.notekeeper.parser.SectionHeader;


public class Searcher
{
    protected List<ParsedSection> parsedSections;
    protected List<String> searchWords; 
    protected boolean caseSensitive;

    public Searcher(List<ParsedSection> inParsedSections, List<String> inSearchWords) {
        this.parsedSections = inParsedSections;
        this.searchWords = inSearchWords;
        caseSensitive = false;
    }
  
    
    public List<SearchSection> search()
    {
        List<SearchSection> result =  new ArrayList<SearchSection>();
        
        if (searchWords == null)
        {
            return result;
        }
        for (ParsedSection currentSection : parsedSections)
        {
        	int sectionScore = 0;
        	for (String currentWord : searchWords) {
        		sectionScore += calculateSectionMatchScore(currentSection, currentWord);
            }
            if (sectionScore > 0) {
                SearchSection searchSection = new SearchSection();
                searchSection.setMatchScore(sectionScore);
                searchSection.setParsedSection(currentSection);
                result.add(searchSection);
            }        	
        }
        return result;
    }

    
    
    /**
     * Will see if the word passed in is "contained" in either the headers values or the body.
     * @param currentSection
     * @param word
     * @return 0 - not found, non-zero is a match
     */
    protected int calculateSectionMatchScore(ParsedSection currentSection, String word)
    {
        if (currentSection == null || currentSection.getHeaders() == null 
                || currentSection.getBody() == null || word == null)
        {
            return 0;
        }
    	int matchScore = 0;

    	matchScore += calculateAllHeadersMatchScore(currentSection.getHeaders(), word);
    	matchScore += calculateBodyMatchScore(currentSection.getBody(), word);
    	
    	return matchScore;
    }


    protected int calculateAllHeadersMatchScore(List<SectionHeader> headers, String word)
	{
		int resultHeadersMatchScore = 0;
		for (SectionHeader currentSectionHeader : headers)
		{
			resultHeadersMatchScore += calculateHeaderMatchScore(currentSectionHeader, word);
		}
		return resultHeadersMatchScore;
	}


	protected int calculateHeaderMatchScore(SectionHeader sectionHeader, String word)
	{
		int resultMatchScore = 0;
		for (String currentHeaderValue : sectionHeader.getValues())
		{
			resultMatchScore += countOccurences(currentHeaderValue, word);
		}
		return resultMatchScore;
	}


	protected int calculateBodyMatchScore(List<String> text, String word)
    {
    	int resultTextScore = 0;
    	for (String currentLine : text)
    	{
    		resultTextScore += countOccurences(currentLine, word);
    	}
    	return resultTextScore;
    }


	protected int countOccurences(String stringToSearch, String wordToSearchFor)
	{
		String tempStringToSearch;
		String tempWordToSearchFor;
		if (caseSensitive)
		{
			tempStringToSearch = stringToSearch;
			tempWordToSearchFor = wordToSearchFor;
		} else
		{
			tempStringToSearch = stringToSearch.toLowerCase();
			tempWordToSearchFor = wordToSearchFor.toLowerCase();
		}
		
		int lastIndex = 0;
		int resultCount = 0;

		while(lastIndex != -1)
		{
			lastIndex = tempStringToSearch.indexOf(tempWordToSearchFor,lastIndex);
			if( lastIndex != -1)
			{
				resultCount++;
				lastIndex+=tempWordToSearchFor.length();
			}
		}
		return resultCount;
	}	
}
