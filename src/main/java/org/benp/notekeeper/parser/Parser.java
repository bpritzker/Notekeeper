package org.benp.notekeeper.parser;

import java.util.List;

import org.benp.notekeeper.utils.NotekeeperException;

public interface Parser
{
	public List<ParsedSection> getParsedSections() throws NotekeeperException;
}
