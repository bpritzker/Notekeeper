package org.benp.notekeeper.parser;

import java.util.List;

import org.benp.notekeeper.parser.file.RawSection;

/**
 * This is a section that has been parsed and is ready to be searched.
 * @author Ben Pritzker
 *
 */
public class ParsedSection
{
	private RawSection rawSection;
	private List<SectionHeader> headers;
	private List<String> body;
	
	
	public RawSection getRawSection()
	{
		return rawSection;
	}
	public void setRawSection(RawSection rawSection)
	{
		this.rawSection = rawSection;
	}

	public List<String> getBody()
	{
		return body;
	}
	public void setBody(List<String> body)
	{
		this.body = body;
	}
	public List<SectionHeader> getHeaders() {
		return headers;
	}
	public void setHeaders(List<SectionHeader> headers) {
		this.headers = headers;
	}

}
