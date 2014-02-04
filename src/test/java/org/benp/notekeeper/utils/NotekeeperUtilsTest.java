package org.benp.notekeeper.utils;

import java.io.File;
import java.util.Scanner;

import org.junit.Test;

public class NotekeeperUtilsTest
{
    @Test
    public void testCloseQuietlyScanner() throws Exception
    {
        // positive test - call close when object is null; no exception is thrown
        NotekeeperUtils.closeQuietlyScanner(null);

        // positive test - called with open scanner; hope it closes it but verify no exception is thrown
        //     can't mock to spy on it because Scanner if a final class
        File file = new File("src/test/resources/files/emptyTestFile.txt");
        NotekeeperUtils.closeQuietlyScanner(new Scanner(file));
    }
	
	
	
}
