/**
 * 
 */
package org.benp.notekeeper.search;

import org.benp.notekeeper.parser.ParsedSection;


/**
 * 
 * This is the result class returned by a search.
 * It contains a score and the original section that was found.
 * It can then be sorted at a later date.
 * 
 * @author Ben
 *
 */
public class SearchSection {
    
    private ParsedSection parsedSection;
    private int matchScore;
    
    public ParsedSection getParsedSection() {
		return parsedSection;
	}

    public void setParsedSection(ParsedSection parsedSection) {
		this.parsedSection = parsedSection;
	}

	public int getMatchScore() {
		return matchScore;
	}

	public void setMatchScore(int matchScore) {
		this.matchScore = matchScore;
	}
}
