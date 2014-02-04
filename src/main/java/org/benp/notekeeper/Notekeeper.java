/**
 *  This program is designed to provide a VERY SIMPLE way to keep track of notes and provide
 *  a powerful way to search and find the note you are looking for.
 *
    Copyright (C) 2013  Morris "Ben" Pritzker

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.benp.notekeeper;

import java.util.Arrays;
import java.util.List;

import org.benp.notekeeper.display.CommandLinePrinter;
import org.benp.notekeeper.parser.ParsedSection;
import org.benp.notekeeper.parser.Parser;
import org.benp.notekeeper.parser.file.FileParser;
import org.benp.notekeeper.search.SearchSection;
import org.benp.notekeeper.search.Searcher;
import org.benp.notekeeper.utils.NotekeeperException;


public class Notekeeper
{

	protected Parser parser;
	
	/**
     * This is the main method into the program called from the command line. 
     * It should do nothing but instantiate the Notekeeper object and pass in the args.
     */
    public static void main(String[] args)
    {
        Notekeeper noteKeeper = new Notekeeper();
        noteKeeper.run(args);
    }    

    public void run(String[] args)
    {
    	if (args == null)
    	{
    		System.out.println("Please enter a word to search for.");
    		return;
    	}
    	try
    	{
	    	init();
	    	List<ParsedSection> parsedSections = parser.getParsedSections();
	    	Searcher searcher = new Searcher(parsedSections, Arrays.asList(args));
	    	List<SearchSection> searchSections = searcher.search();
	    	CommandLinePrinter commandLinePrinter = new CommandLinePrinter(searchSections);
	    	commandLinePrinter.printAllSectionsToStandardOut();
	    	
    	} catch (NotekeeperException nke)
    	{
    		nke.printStackTraceWithPairs();
    	}
    }

    
    
	protected void init()
	{
		parser = new FileParser();
	}

}
