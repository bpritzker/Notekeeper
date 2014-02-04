package org.benp.notekeeper.parser.file;

import java.util.ArrayList;
import java.util.List;

public class RawSection
{
	private List<String> text = new ArrayList<String>();
	private int startingLineNumber;
	
	
	public List<String> getText() {
		return text;
	}
	public void setText(List<String> text) {
		this.text = text;
	}
	public int getStartingLineNumber() {
		return startingLineNumber;
	}
	public void setStartingLineNumber(int startingLineNumber) {
		this.startingLineNumber = startingLineNumber;
	}
}
