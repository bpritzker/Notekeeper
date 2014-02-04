package org.benp.notekeeper.search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.benp.notekeeper.parser.ParsedSection;
import org.benp.notekeeper.parser.SectionHeader;
import org.benp.notekeeper.test.NotekeeperTestingUtils;
import org.benp.notekeeper.utils.NotekeeperException;
import org.junit.Before;
import org.junit.Test;

public class SearcherTest 
{
	// this searcher is just used to test methods
	Searcher searcher;
	
	@Before
	// This will make sure we start off with a clean searcher for each test
	public void before()
	{
		searcher = new Searcher(null, null);
	}

	@Test
    public void testSearcherConstructor()
    {
    	List<ParsedSection> actualParsedSection = new ArrayList<ParsedSection>();
    	List<String> actualSearchWords = new ArrayList<String>();
    	
    	
    	// positive test - Verify the variables are set accordingly in the constructor
    	Searcher searcher = new Searcher(actualParsedSection, actualSearchWords);
    	assertEquals(searcher.parsedSections, actualParsedSection);
    	assertEquals(searcher.searchWords, actualSearchWords);
    	assertFalse(searcher.caseSensitive);
    }
	
    @Test
    public void testSearch() throws NotekeeperException 
    {
        // positive test - search for null; get back 0 sections
    	List<ParsedSection> expectedParsedSections = NotekeeperTestingUtils.createParsedSectionList();
        Searcher searcher = new Searcher(expectedParsedSections, null);
        List<SearchSection> actualSearchSectionList = searcher.search();
        assertEquals(0, actualSearchSectionList.size());
        
        // positive test - simple search for the word "search"; return 1 section match
        List<String> searchWords = new ArrayList<String>();
        searchWords.add("search");
        searcher = new Searcher(expectedParsedSections, searchWords);
        actualSearchSectionList = searcher.search();
        assertEquals(1, actualSearchSectionList.size());
        assertEquals(expectedParsedSections.get(0), actualSearchSectionList.get(0).getParsedSection());
        assertEquals(2,actualSearchSectionList.get(0).getMatchScore());
        
        // positive test - only one of the sections contain both words;
        //     get back just the one section that matched
        searchWords.add("grep");
        searcher = new Searcher(expectedParsedSections, searchWords);
        actualSearchSectionList = searcher.search();
        assertEquals(1, actualSearchSectionList.size());
        assertEquals(5, actualSearchSectionList.get(0).getMatchScore());
        
        // positive test - find multiple words in single section
        searchWords.add("eclipse");
        searchWords.add("tables");
        searcher = new Searcher(expectedParsedSections, searchWords);
        actualSearchSectionList = searcher.search();
        assertEquals(2, actualSearchSectionList.size());
        assertEquals(5, actualSearchSectionList.get(0).getMatchScore());        
        assertEquals(4, actualSearchSectionList.get(1).getMatchScore());        
        
    }
    
    @Test
    public void testCalculateSectionMatchScore() {
        Searcher searcher = new Searcher(NotekeeperTestingUtils.createParsedSectionList(), null);
        // positive test - null section and null word is passed in; get back 0
        int actual = searcher.calculateSectionMatchScore(null, null);
        assertEquals(0,actual);

        // positive test - section is null but word is valid string; get back 0
        actual = searcher.calculateSectionMatchScore(null, "word");
        assertEquals(0,actual);

        // positive test - section is valid but has null body and headers word is null; get back 0
        ParsedSection tempSection = new ParsedSection();
        actual = searcher.calculateSectionMatchScore(tempSection, "word");
        assertEquals(0,actual);

        // positive test - section is valid but has null headers; get back 0
        List<String> tempBody = new ArrayList<String>();
        tempSection.setBody(tempBody);
        actual = searcher.calculateSectionMatchScore(tempSection, "word");
        assertEquals(0,actual);

        // positive test - section is valid but has null body; get back 0
        tempSection.setBody(null);
        List<SectionHeader> sectionHeaders = new ArrayList<SectionHeader>();
        tempSection.setHeaders(sectionHeaders);
        actual = searcher.calculateSectionMatchScore(tempSection, "word");
        assertEquals(0,actual);

        // positive test - section is valid but word is null; get back 0
        tempSection.setBody(tempBody);
        actual = searcher.calculateSectionMatchScore(tempSection, null);
        assertEquals(0,actual);

        // positive test - section does NOT contain word; get back 0
        ParsedSection sectionToSearch = NotekeeperTestingUtils.createSearchSection1().getParsedSection();
        actual = searcher.calculateSectionMatchScore(sectionToSearch, "NOT_FOUND");
        assertEquals(0,actual);

        // positive test - section contains 4 matches in just the headers match; get back 2
        actual = searcher.calculateSectionMatchScore(sectionToSearch, "search");
        assertEquals(2,actual);
        
        // positive test - section contains multiple matches in both headers and text
        actual = searcher.calculateSectionMatchScore(NotekeeperTestingUtils.createSearchSection1().getParsedSection(), "grep");
        assertEquals(3, actual);
        
        // positive test - section contains multiple matches in both headers and body
        actual = searcher.calculateSectionMatchScore(NotekeeperTestingUtils.createSearchSection3().getParsedSection(), "sql");
        assertEquals(6, actual);
        
    }
    
    

    
    @Test
    public void testCalculateAllHeadersMatchScore()
    {
    	List<SectionHeader> tempSectionHeader = new ArrayList<SectionHeader>();
    	
    	// positive test - search for word that is in headers once, get 1 match back 
    	tempSectionHeader.add(NotekeeperTestingUtils.createSection1Header1());
    	tempSectionHeader.add(NotekeeperTestingUtils.createSection1Header2());
    	int actualScore = searcher.calculateAllHeadersMatchScore(tempSectionHeader, "linux");
    	assertEquals(1, actualScore);
    	
    	// positive test - word that is in 2 different headers, get match score back of 2
    	tempSectionHeader.add(NotekeeperTestingUtils.createSection1Header3());
    	actualScore = searcher.calculateAllHeadersMatchScore(tempSectionHeader, "grep");
    	assertEquals(2, actualScore);
    	
    	
    	
    }
    
    @Test
    public void testCalculateHeaderMatchScore()
    {
    	// positive test - search for word that is not in header, should get no hits back
    	int actualScore = searcher.calculateHeaderMatchScore(NotekeeperTestingUtils.createSection1Header2(), "notfound");
    	assertEquals(0, actualScore);

    	// positive test - search for word that is in header once, get one hit back
    	actualScore = searcher.calculateHeaderMatchScore(NotekeeperTestingUtils.createSection1Header2(), "grep");
    	assertEquals(1, actualScore);
    }
    
    @Test
    public void testCalculateBodyMatchScore()
    {
    	// positive test - search for a word that is not in the text, get 0 hits back
    	int actualScore = searcher.calculateBodyMatchScore(
    			NotekeeperTestingUtils.createSearchSection2().getParsedSection().getBody(), "Notfound");
    	assertEquals(0, actualScore);

    	// positive test - search for a word that is in the text, 1 hit
    	actualScore = searcher.calculateBodyMatchScore(
    			NotekeeperTestingUtils.createSearchSection2().getParsedSection().getBody(), "CTRL+F11");
    	assertEquals(1, actualScore);
    	
    	// positive test - search for a word that is in the text two times with different cases, 2 hit
    	actualScore = searcher.calculateBodyMatchScore(
    			NotekeeperTestingUtils.createSearchSection3().getParsedSection().getBody(), "sql");
    	assertEquals(3, actualScore);
    	
    	
    }
    
    
    @Test
    public void testCountOccurences()
    {
    	// positive test - case insensitive found with differnt case
    	int actual = searcher.countOccurences("SQL", "sql");
    	assertEquals(1, actual);
    	
    	// positive test - case insensitive both same case 
    	actual = searcher.countOccurences("sql", "sql");
    	assertEquals(1, actual);
    	
    	// positive test - case insensitive test, multiple words
    	actual = searcher.countOccurences("another string sql", "sql");
    	assertEquals(1, actual);
    	
    	// positive test - case insensitive, not found
    	actual = searcher.countOccurences("not found", "sql");
    	assertEquals(0, actual);
    	
    	// positive test - case insensitive, not found because whole word is not contained in search string
    	actual = searcher.countOccurences("sql", "not found sql");
    	assertEquals(0, actual);
    	
    	// positive test - case insensitive, found twice
    	actual = searcher.countOccurences("sql found sql", "sql");
    	assertEquals(2, actual);
    	
    	// positive test - special characters
    	actual = searcher.countOccurences("CTRL+F11", "CTRL+F11");
    	assertEquals(1, actual);
    	
    	
    	
    	// Begin case sensitive tests....
    	// positive test - case sensitive, not found because of different case
    	searcher.caseSensitive = true;
    	actual = searcher.countOccurences("SQL", "sql");
    	assertEquals(0, actual);
    	
    	// positive test - case sensitive, found same case
    	actual = searcher.countOccurences("sql", "sql");
    	assertEquals(1, actual);
    	
    	// positive test - same case contained in string, found
    	actual = searcher.countOccurences("sql statement", "sql");
    	assertEquals(1, actual);
    }

}
