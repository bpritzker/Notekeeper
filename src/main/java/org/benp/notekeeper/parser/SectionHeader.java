package org.benp.notekeeper.parser;

import java.util.List;

public class SectionHeader
{

    private String name;
    private List<String> values;
    private int matchWeight;

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public List<String> getValues()
    {
        return values;
    }
    public void setValues(List<String> values)
    {
        this.values = values;
    }
	public int getMatchWeight() {
		return matchWeight;
	}
	public void setMatchWeight(int matchWeight) {
		this.matchWeight = matchWeight;
	}
}
