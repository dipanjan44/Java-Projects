package tiapd;

import java.util.Date;
import java.util.Calendar;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Timestamp Parser.
 *
 * This is used for parsing timestamps in dumps.
 *
 * @author Ken Baclawski
 * @version 1.0 05/20/2017
 */
public class TimeParser {
    /** The jRockit timestamp pattern. */
    public final static Pattern jRockitTimestampPattern = Pattern.compile
        ("^\\s*(Sun|Mon|Tue|Wed|Thu|Fri|Sat)" +
         "\\s+((Jan)|(Feb)|(Mar)|(Apr)|(May)|(Jun)|(Jul)|(Aug)|(Sep)|(Oct)|(Nov)|(Dec))"+
         "\\s+([1-9]\\d*)\\s+(\\d{2}):(\\d{2}):(\\d{2})\\s+(\\d{4})");

    /**
     * Parse a jRockit timestamp.  This method is thread-safe.
     * @param text The timestamp in jRockit format.  There can be additional
     * characters after the timestamp, but only whitespace can precede the
     * timestamp.
     * @return The timestamp as a Java Date object or null if the parameter
     * does not have the proper format.
     */
    public static Date jRockitParse(String text) {
        if (text == null) {

            // There is no text so it does not have the proper format.

            return null;
        }

        // Use a pattern matcher to check that the format is correct.

        Matcher timestampMatcher = jRockitTimestampPattern.matcher(text);
        if (!timestampMatcher.find()) {
            
            // The format is wrong, so return null.

            return null;
        }

        // The pattern is correct, so extract the numbers.

        int year = Integer.parseInt(timestampMatcher.group(19));
        int day = Integer.parseInt(timestampMatcher.group(15));
        int hour = Integer.parseInt(timestampMatcher.group(16));
        int minute = Integer.parseInt(timestampMatcher.group(17));
        int second = Integer.parseInt(timestampMatcher.group(18));
        
        // Convert the month name to a month number.
        
        int month = 0;
        for (int i = 3; i <= 14; ++i) {
            if (timestampMatcher.group(i) != null) {
                month = i - 3;
                break;
            }
        }
        
        // Convert the numbers to a Date object.  If the numbers are not
        // meaningful (for example, Feb 30), then an exception should be
        // thrown, and a null timestamp returned.
        
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setLenient(false);
            calendar.set(year, month, day, hour, minute, second);
            return calendar.getTime();
        } catch (Exception e) {
            
            // This should not happen if the timestamp was produced by
            // jRockit.
            
            return null;
        }
    }
}
