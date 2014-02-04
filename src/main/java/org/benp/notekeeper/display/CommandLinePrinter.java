package org.benp.notekeeper.display;

import java.util.List;

import org.benp.notekeeper.search.SearchSection;

public class CommandLinePrinter
{
    protected final static String DISPLAY_SECTION_SEPERATOR = "#######################################################################";
    protected List<SearchSection> searchSections;

    public CommandLinePrinter(List<SearchSection> sectionList)
    {
        this.searchSections = sectionList;
    }


    public void printAllSectionsToStandardOut()
    {
    	if (searchSections == null || searchSections.size() == 0)
    	{
    		System.out.println("No matches found. :(");
    	}
    	
    	for (SearchSection currentSearchSection : searchSections)
    	{  
    		printParsedSectionToStandardOut(currentSearchSection);
    	}
    }


	protected void printParsedSectionToStandardOut(SearchSection section)
	{
		printSectionSeperatorToStandardOut();
		for (String currentLine : section.getParsedSection().getRawSection().getText())
		{
			System.out.println(currentLine);
		}
	}


	protected void printSectionSeperatorToStandardOut()
	{
		System.out.println(CommandLinePrinter.DISPLAY_SECTION_SEPERATOR);
	}


}
