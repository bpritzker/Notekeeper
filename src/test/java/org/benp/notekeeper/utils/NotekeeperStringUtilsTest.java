package org.benp.notekeeper.utils;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;


public class NotekeeperStringUtilsTest {

    @Test
    public void testSplitBySpacesOrQuoted() {
        // positive test - null is passed in; expect empty list back
        List<String> actualWordList = NotekeeperStringUtils.splitBySpacesOrQuoted(null);
        assertEquals(0, actualWordList.size());

        // positive test - simple string with 2 words separated by spaces; get list of both words
        actualWordList = NotekeeperStringUtils.splitBySpacesOrQuoted("word1 word2");
        assertEquals(2, actualWordList.size());

        // positive test - "complex" test, it contains with multiple spaces and both single and double quotes;
        //     should split it as defined by the regex.  See StringUtils class for definition
        actualWordList = NotekeeperStringUtils.splitBySpacesOrQuoted(
                "   word1 word2    \"word3 with double quotes\"  'word4 with single quotes'   ");
        assertEquals(4, actualWordList.size());
        assertEquals("word1", actualWordList.get(0));
        assertEquals("word2", actualWordList.get(1));
        assertEquals("word3 with double quotes", actualWordList.get(2));
        assertEquals("word4 with single quotes", actualWordList.get(3));
    }

//    @Test
//    public void testContains() {
//        // positive test - null list and null word is passed in; should return false
//        boolean actual = StringUtils.contains(null, null);
//        assertFalse(actual);
//
//        // positive test - list is null but word is valid string; should get back false
//        actual = StringUtils.contains(null, "word");
//        assertFalse(actual);
//
//        // positive test - list is valid but word is null; should get false back
//        actual = StringUtils.contains(new ArrayList<String>(), null);
//        assertFalse(actual);
//
//        // positive test - list does NOT contain word; should get back false
//        List<String> bodyToSearch = TestingUtils.createParsedSectionList().get(1).getBody();
//        actual = StringUtils.contains(bodyToSearch, "SHOULD_NOT_FIND");
//        assertFalse(actual);
//
//        // positive test - list contains exact match; should get back true
//        actual = StringUtils.contains(bodyToSearch, "oracle");
//        assertTrue(actual);
//
//        // positive test - list contains partial match; should get back true
//        actual = StringUtils.contains(bodyToSearch, "db");
//        assertTrue(actual);
//    }
}


