package org.benp.notekeeper.parser.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.benp.notekeeper.test.NotekeeperTestingUtils;
import org.junit.Test;

public class RawSectionTest
{
	@Test
	public void testConstructor()
	{
		// positive test - Test when it is constructed that the text is non-null
		RawSection actualRawSection = new RawSection();
		assertNotNull(actualRawSection.getText());
		assertEquals(0, actualRawSection.getText().size());
	}
	
    @Test
    public void testGettersAndSetters() throws Exception {
        NotekeeperTestingUtils.testGettersAndSetters(new RawSection());
    }	
}
