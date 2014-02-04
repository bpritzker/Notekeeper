package org.benp.notekeeper.test;

import java.util.ArrayList;
import java.util.List;

import org.benp.notekeeper.parser.ParsedSection;
import org.benp.notekeeper.parser.SectionHeader;
import org.benp.notekeeper.parser.file.RawSection;
import org.benp.notekeeper.search.SearchSection;
import org.junit.Assert;


/**
 * I wanted to be able to easily see what makes up each section and do my best to keep them in sync. 
 * I found defining them all in the same method was the best way to do this. 
 * It also helped to create the Header definition as a string right above where I define the 
 * Header object and then use it later to build the raw text.
 * 
 * @author Ben
 *
 */
public final class NotekeeperTestingUtils
{

    public static void testGettersAndSetters(Object object) throws Exception
    {
        // This will eventually use reflection to test the getter and setter methods on an object
    }


    
    /****************************************************************************************************************/
    /****************************************************************************************************************/
    /*******************************      Begin create sections          ********************************************/
    /****************************************************************************************************************/
    /****************************************************************************************************************/
    
    public static List<SearchSection> createSearchSectionList()
    {
    	List<SearchSection> resultList = new ArrayList<SearchSection>();
    	resultList.add(createSearchSection1());
    	resultList.add(createSearchSection2());
    	return resultList;
    }
    
    public static RawSection createRawSection1()
    {
    	RawSection resultRawSection = new RawSection();
    	resultRawSection.setStartingLineNumber(5);
    	resultRawSection.setText(createRawSectionTextSection1());
    	return resultRawSection;
    	
    	
    }
    
    public static List<String> createRawSectionTextSection1()
    {
    	return createSearchSection1().getParsedSection().getRawSection().getText();
    }
    
    

    
    public static List<ParsedSection> createParsedSectionList()
    {
    	List<ParsedSection> resultParsedSections = new ArrayList<ParsedSection>();
    	resultParsedSections.add(createSearchSection1().getParsedSection());
    	resultParsedSections.add(createSearchSection2().getParsedSection());
    	return resultParsedSections;
    }

    public static SectionHeader createSection1Header1()
    {
    	return createSearchSection1().getParsedSection().getHeaders().get(0);
    }

    public static SectionHeader createSection1Header2()
    {
    	return createSearchSection1().getParsedSection().getHeaders().get(1);    
    }
    
    
    public static SectionHeader createSection1Header3()
    {
    	return createSearchSection1().getParsedSection().getHeaders().get(2);
    }
    
    
    
    public static SearchSection createSearchSection1()
    {
    	SearchSection resultSearchSection = new SearchSection();
    	ParsedSection parsedSection1 = new ParsedSection();
    	
        List<SectionHeader> section1Headers = new ArrayList<SectionHeader>();
        
        // Header 1
        String header1RawText = "GROUPS: linux find search";
        SectionHeader tempSectionHeader1 = new SectionHeader();
        List<String> headerValues1 = new ArrayList<String>();    	
        tempSectionHeader1 = new SectionHeader();
        tempSectionHeader1.setName("GROUPS");
        headerValues1 = new ArrayList<String>();
        headerValues1.add("linux");
        headerValues1.add("find");
        headerValues1.add("search");
        tempSectionHeader1.setValues(headerValues1);
        section1Headers.add(tempSectionHeader1);
        
        // Header 2
        String header2RawText = "TAGS: grep search";
        SectionHeader tempSectionHeader2 = new SectionHeader();
        tempSectionHeader2.setName("TAGS");
        List<String> headerValues2 = new ArrayList<String>();
        headerValues2.add("grep");
        headerValues2.add("search");
        tempSectionHeader2.setValues(headerValues2);
        section1Headers.add(tempSectionHeader2);
        
        // Header 3
        String header3RawText = "DESCRIPTION: How to recursively grep";
        SectionHeader tempSectionHeader3 = new SectionHeader();
        List<String> headerValues3 = new ArrayList<String>();        	
        tempSectionHeader3 = new SectionHeader();
        tempSectionHeader3.setName("DESCRIPTION");
        headerValues3 = new ArrayList<String>();
        headerValues3.add("How");
        headerValues3.add("to");
        headerValues3.add("recursively");
        headerValues3.add("grep");
        tempSectionHeader3.setValues(headerValues3);
        section1Headers.add(tempSectionHeader3);
        
        parsedSection1.setHeaders(section1Headers);
        
        List<String> tempSectionBody = new ArrayList<String>();
        tempSectionBody.add("");
        tempSectionBody.add("find . -exec grep -H -n 'text' {} \\;");
        tempSectionBody.add("");
        tempSectionBody.add("");
        
        parsedSection1.setBody(tempSectionBody);

        
        RawSection tempRawSection = new RawSection();
    	List<String> rawSectionText = new ArrayList<String>();
    	rawSectionText.add(header1RawText);
    	rawSectionText.add(header2RawText);
    	rawSectionText.add(header3RawText);
    	rawSectionText.addAll(tempSectionBody);
    	tempRawSection.setStartingLineNumber(10);
    	tempRawSection.setText(rawSectionText);
    	
    	parsedSection1.setRawSection(tempRawSection);
    	
    	resultSearchSection.setMatchScore(20);
    	resultSearchSection.setParsedSection(parsedSection1);
        return resultSearchSection;
    }
    
    
    /******************************************************************************************/
        
    public static SearchSection createSearchSection2()
    {
    	SearchSection resultSearchSection2 = new SearchSection();
        ParsedSection parsedSection2 = new ParsedSection();

        List<SectionHeader> tempSection2Headers = new ArrayList<SectionHeader>();

        String header1RawText = "GROUPS: eclipse";
        SectionHeader tempHeader1 = new SectionHeader();
        tempHeader1.setName("GROUPS");
        List<String>header1Values = new ArrayList<String>();
        header1Values.add("eclipse");
        tempHeader1.setValues(header1Values);
        tempSection2Headers.add(tempHeader1);
        
        String header2RawText = "TAGS: eclipse shortcut trick and tip";
        SectionHeader tempHeader2 = new SectionHeader();
        tempHeader2.setName("TAGS");
        List<String> header2Values = new ArrayList<String>();
        header2Values.add("eclipse");
        header2Values.add("shortcut");
        header2Values.add("trick");
        header2Values.add("and");
        header2Values.add("tip");
        tempHeader2.setValues(header2Values);
        tempSection2Headers.add(tempHeader2);


        String header3RawText = "DESCRIPTION: Find tables with name";
        SectionHeader tempHeader3 = new SectionHeader();
        List<String> header3Values = new ArrayList<String>();
        tempHeader3.setName("DESCRIPTION");
        header3Values = new ArrayList<String>();
        header3Values.add("Find");
        header3Values.add("tables");
        header3Values.add("with");
        header3Values.add("name");
        tempHeader3.setValues(header3Values);
        tempSection2Headers.add(tempHeader3);
        
        parsedSection2.setHeaders(tempSection2Headers);

        
        List<String> tempBody = new ArrayList<String>();
        tempBody.add("");
        tempBody.add("CTRL+F11");
        tempBody.add("tables");
        tempBody.add("CTRL+O - re-org packages");
        tempBody.add("");
        parsedSection2.setBody(tempBody);
        
        
        RawSection tempRawSection = new RawSection();
        tempRawSection.setStartingLineNumber(53);
        List<String> rawSectionText = new ArrayList<String>();
        rawSectionText.add(header1RawText);
        rawSectionText.add(header2RawText);
        rawSectionText.add(header3RawText);
        rawSectionText.addAll(tempBody);
        parsedSection2.setRawSection(tempRawSection);
        
        resultSearchSection2.setMatchScore(9);
        resultSearchSection2.setParsedSection(parsedSection2);
        return resultSearchSection2;
   }
    
    /******************************************************************************************/
    
    
    
    /**
     * This section will contain all of the little tricky stuff
     * @return
     */
  public static SearchSection createSearchSection3()
  {
  	SearchSection resultSearchSection3 = new SearchSection();
      ParsedSection parsedSection3 = new ParsedSection();

      List<SectionHeader> tempSection3Headers = new ArrayList<SectionHeader>();

      String header1RawText = "GROUPS: sql oracle";
      SectionHeader tempHeader1 = new SectionHeader();
      tempHeader1.setName("GROUPS");
      List<String>header1Values = new ArrayList<String>();
      header1Values.add("sql");
      header1Values.add("oracle");
      tempHeader1.setValues(header1Values);
      tempSection3Headers.add(tempHeader1);
      
      String header2RawText = "TAGS: tables sql \"sql table list\"";
      SectionHeader tempHeader2 = new SectionHeader();
      tempHeader2.setName("TAGS");
      List<String> header2Values = new ArrayList<String>();
      header2Values.add("tables");
      header2Values.add("sql");
      header2Values.add("sql table list");
      tempHeader2.setValues(header2Values);
      tempSection3Headers.add(tempHeader2);


      String header3RawText = "DESCRIPTION: Find all tables that contain string";
      SectionHeader tempHeader3 = new SectionHeader();
      List<String> header3Values = new ArrayList<String>();
      tempHeader3.setName("DESCRIPTION");
      header3Values = new ArrayList<String>();
      header3Values.add("Find");
      header3Values.add("all");
      header3Values.add("tables");
      header3Values.add("that");
      header3Values.add("contain");
      header3Values.add("string");
      tempHeader3.setValues(header3Values);
      tempSection3Headers.add(tempHeader3);
      
      parsedSection3.setHeaders(tempSection3Headers);

      
      List<String> tempBody = new ArrayList<String>();
      tempBody.add("");
      tempBody.add("");
      tempBody.add("This is the SQL that will find all the tables");
      tempBody.add("That start with the specific string.");
      tempBody.add("Just copy the sql below into your sql editor and ");
      tempBody.add("run it.");
      tempBody.add("\"select tablename from sys.tables where tablename like '%<NAME>%\"");
      tempBody.add("");
      tempBody.add("");
      tempBody.add("");
      parsedSection3.setBody(tempBody);
      
      
      RawSection tempRawSection = new RawSection();
      tempRawSection.setStartingLineNumber(53);
      List<String> rawSectionText = new ArrayList<String>();
      rawSectionText.add(header1RawText);
      rawSectionText.add(header2RawText);
      rawSectionText.add(header3RawText);
      rawSectionText.add("");
      rawSectionText.add("");
      rawSectionText.addAll(tempBody);
      rawSectionText.add("");
      rawSectionText.add("");
      rawSectionText.add("");
      parsedSection3.setRawSection(tempRawSection);
      
      resultSearchSection3.setMatchScore(9);
      resultSearchSection3.setParsedSection(parsedSection3);
      return resultSearchSection3;
 }    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * returns an empty section header, NO nulls
     * @return
     */
    public static ParsedSection createEmptyParsedSection()
    {
    	ParsedSection resultParsedSection = new ParsedSection();
    	RawSection tempRawSection = new RawSection();
    	tempRawSection.setStartingLineNumber(0);
    	tempRawSection.setText(new ArrayList<String>());
    	resultParsedSection.setRawSection(tempRawSection);

    	SectionHeader tempSectionHeader = new SectionHeader();
    	tempSectionHeader.setName("");
    	tempSectionHeader.setValues(new ArrayList<String>());
    	List<SectionHeader> tempSectionHeaderList = new ArrayList<SectionHeader>();
    	tempSectionHeaderList.add(tempSectionHeader);
    	resultParsedSection.setHeaders(tempSectionHeaderList);
    	
    	resultParsedSection.setBody(new ArrayList<String>());
    	return resultParsedSection;
    }
    
    
    public static String createParsedSectionListTextString()
    {
    	return "";
    }
    

    /****************************************************************************************************************/
    /****************************************************************************************************************/
    /*******************************      Begin asserts                  ********************************************/
    /****************************************************************************************************************/
    /****************************************************************************************************************/

	public static void assertEquals(RawSection expectedRawSection, RawSection actualRawSection)
    {
    	Assert.assertEquals("Raw Section starting line numbers are not equal.",
    			expectedRawSection.getStartingLineNumber(), actualRawSection.getStartingLineNumber());
    	assertEquals(expectedRawSection.getText(), actualRawSection.getText());
    }
    
    

    public static void assertEquals(List<String> expectedStringList, List<String> actualStringList)
    {
        if (expectedStringList == null && actualStringList == null)
        {
            return;
        }

        if (expectedStringList == null || actualStringList == null)
        {
            Assert.fail("One of the result sets were null; "
                    + "expectedStringList <" + expectedStringList + ">"
                    + ", actualStringList <" + actualStringList + ">");
        }

        if (expectedStringList.size() != actualStringList.size())
        {
            Assert.fail("The Lists were different sizes; "
                    + "expectedStringList <" + expectedStringList.size() + ">"
                    + ", actualStringList <" + actualStringList.size() + ">");
        }

        for (int index = 0; index < expectedStringList.size(); index++)
        {
            Assert.assertEquals("assert fail for index <" + index + ">"
                    , expectedStringList.get(index), actualStringList.get(index));
        }
    }

    
    /****************************************************************************************************************/
    /****************************************************************************************************************/
    /*******************************      Begin simple debug tools       ********************************************/
    /****************************************************************************************************************/
    /****************************************************************************************************************/


}
