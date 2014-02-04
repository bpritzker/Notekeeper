package org.benp.notekeeper.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * These are String utilities and should all be static so do not allow the
 * class to be instantiated.
 *
 */

public final class NotekeeperStringUtils
{

    private NotekeeperStringUtils()
    {
        // private constructor to prevent someone from instantiating
    }

    public static List<String> splitBySpacesOrQuoted(String stringToSplit)
    {
        List<String> matchList = new ArrayList<String>();
        if (stringToSplit == null)
        {
            return matchList;
        }

        Pattern regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
        Matcher regexMatcher = regex.matcher(stringToSplit);
        while (regexMatcher.find())
        {
            if (regexMatcher.group(1) != null)
            {
                // Add double-quoted string without the quotes
                matchList.add(regexMatcher.group(1));
            } else if (regexMatcher.group(2) != null)
            {
                // Add single-quoted string without the quotes
                matchList.add(regexMatcher.group(2));
            } else
            {
                // Add unquoted word
                matchList.add(regexMatcher.group());
            }
        }
        return matchList;
    }

}
