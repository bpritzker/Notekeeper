package org.benp.notekeeper.utils;

import java.util.Scanner;

/**
 * This class are just simple utils I use in the Notekeeper application.
 * @author Ben
 *
 */
public class NotekeeperUtils
{
	/**
	 * Just used to close a scanner. If the close fails then swallow exception.
	 * @param scanner
	 */
    public static void closeQuietlyScanner(Scanner scanner)
    {
        if (scanner != null) {
            scanner.close();
        }
    }	

}
