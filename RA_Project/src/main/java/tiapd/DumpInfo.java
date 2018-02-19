package tiapd;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.DateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Dump Information.
 *
 * A dump consists of a timestamp and information about a collection of threads
 * and their relationships to one another. There is a many-to-many relationship
 * between Dump and Thread. The ThreadDumpInfo specifies this relationship.
 *
 * @author Ken Baclawski
 * @version 1.0 05/20/2017
 */
public class DumpInfo {
    /**
     * Class for storing stack frame information for this dump.
     */
    private class StackFrameDumpInfo {
        /** The number of occurrences of the stack frame in this dump. */
        public int numOfOccur = 0;
    }

    /** Map from stack frames to stack frame dump info. */
    private Map<StackFrameInfo, StackFrameDumpInfo> frameToDumpInfo =
        new HashMap<StackFrameInfo, StackFrameDumpInfo>();

    /** The timestamp of this dump. */
    private Date timestamp = null;
    
    /** The list of thread dump objects. */
    private List<ThreadDumpInfo> threadDumps = new ArrayList<ThreadDumpInfo>();
    
    /** The stackFrane information statistics */
    private static Map<String, StackFrameInfo> stackFramestats;
    
    /** A formatter for dates. */
    private static DateFormat dateFormat = null;
    
    /** Beginning of a dump. */
    public final static String DUMP_START = "===== FULL THREAD DUMP ===============";
    
    /** End of a dump. */
    public final static String DUMP_END = "===== END OF THREAD DUMP ===============";
    
    /** Thread information pattern. */
    private final static Pattern threadIdentificationPattern = Pattern
        .compile("\"([^\"]*)\" id=([?]|[0-9]+) idx=(0x)?[0-9a-fA-F]+ tid=([0-9]+) prio=[0-9]+");
    
    /** Lock chain pattern. */
    private final static Pattern lockChainPattern = Pattern.compile("(Circular|Blocked|Open).*lock chain");
    
    /** States in the parsing loop. */
    private enum ParsingState {
        INITIAL, TIMESTAMP, THREAD, CHAIN,
            }
    
    /**
     * Construct an empty dump.
     */
    public  DumpInfo() {
    }
    
    /**
     * Parse a dump produced by jRockit using a reader.
     * 
     * @param reader
     *            the reader with the dump information.
     * @param threads
     *            The set of all threads currently known.
     * @param frames
     *            The set of all frames currently known.
     * @return The dump object or null if it reached the end of the input stream
     *         while still in the initial state.
     * @throws IOException
     *             if an I/O error occurs.
     * @throws Exception
     *             if a parameter is invalid or the input is not properly
     *             formatted.
     */
    public static DumpInfo parse(BufferedReader reader, Map<ThreadIdentification, ThreadIdentification> threads,
                                 Map<StackFrameInfo, StackFrameInfo> frames) throws IOException, Exception {
        
        // Start in the initial state with an empty dump object.
        
        DumpInfo dump = new DumpInfo();
        ParsingState parsingState = ParsingState.INITIAL;
        
        // Parse all of the stack traces of threads in this dump.
        
        for (;;) {
            String line = reader.readLine();
            
            // The end of the dump is indicated by reaching the end of the
            // input stream while still in the initial state.
            
            if (line == null) {
                if (parsingState == ParsingState.INITIAL) {
                    return null;
                }
                throw new Exception("Premature end of a dump");
            }
            
            // The state machine starts in the initial state, then transitions
            // to the timestamp state, then transitions to the thread state to
            // read the stack traces of the threads, and finally either ends or
            // transitions to the lock chain information which is ignored.
            
            switch (parsingState) {
            case INITIAL:
                
                // In the initial state, ignore all lines until the one
                // indicating the start of a dump.
                
                if (line.equals(DUMP_START)) {
                    parsingState = ParsingState.TIMESTAMP;
                } else if (line.equals(DUMP_END)) {
                    throw new Exception("Premature end of a dump");
                }
                break;
            case TIMESTAMP:
                
                // The line immediately after the start of the dump has a
                // timestamp.
                
                if (line.equals(DUMP_END)) {
                    throw new Exception("Premature end of a dump");
                }
                dump.timestamp = TimeParser.jRockitParse(line);
                if (dump.timestamp == null) {
                    throw new Exception("Invalid dump timestamp \"" + line + "\"");
                }
                parsingState = ParsingState.THREAD;
                break;
            case THREAD:
                
                // The main part of a dump is the set of stack traces.
                
                if (!line.isEmpty() && line.charAt(0) == '"') {
                    
                    // This is the first line of a stack trace. It specifies
                    // which thread is being traced.
                    
                    Matcher threadIdentificationMatcher = threadIdentificationPattern.matcher(line);
                    if (threadIdentificationMatcher.find()) {
                        
                        // Extract the thread name and thread id and convert
                        // them to a thread information object.
                        
                        ThreadIdentification threadIdentification = ThreadIdentification.getThreadIdentification
                            (threadIdentificationMatcher.group(1), Long.parseLong(threadIdentificationMatcher.group(4)), threads);
                        
                        /**
                         * Update the stackframe statistics for the current
                         * Thread
                         */
                        //stackFramestats = StackFrameInfo.updateStackFrameStatistics(threadIdentification);
                        
                        //System.out.println("Dipanjan is testing this ->" +stackFramestats);
                        
                        // Parse the stack trace for this thread and add it to
                        // the set of thread dumps in this dump.
                        
                        ThreadDumpInfo threadDumpInfo = new ThreadDumpInfo(dump, threadIdentification, reader, frames);
                        
                        int stackTraceSize = threadDumpInfo.getStackTraceSize();
                        List<StackFrameInfo> StackTrace = threadDumpInfo.getStackTrace();
                        dump.threadDumps.add(threadDumpInfo);
                        continue;
                    } else {
                        // This should never happen.
                        throw new Exception("Incorrectly formatted thread dump");
                    }
                } else if (line.equals(DUMP_END)) {
                    
                    // The end of a dump is specified with a dump end line.
                    
                    return dump;
                }
                
                // If there are any lock chains, then they are after the stack
                // traces.
                
                Matcher lockChainMatcher = lockChainPattern.matcher(line);
                if (lockChainMatcher.find()) {
                    parsingState = ParsingState.CHAIN;
                    continue;
                }
                
                // This only happens if the line precedes the stack trace.
                // Such a line is ignored.
                
                break;
            case CHAIN:
                
                // This information is not used because all waiting and locking
                // information is already in the stack trace.
                
                if (line.equals(DUMP_END)) {
                    return dump;
                }
                break;
            default:
                
                // This should never happen.
                
                throw new Exception("Incorrectly formatted thread dump");
            }
        }
    }
    
    /**
     * Get the timestamp of the dump.
     * 
     * @return The time when the dump was taken.
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Increment the number of occurrences of a stack frame.
     * @param stackFrame The stack frame to be updated if null or not in the
     * map, then the operation is ignored.
     */
    public void incrementNumOfOccur(StackFrameInfo stackFrame) {
        if (stackFrame != null) {
            StackFrameDumpInfo stackFrameDumpInfo = frameToDumpInfo.get(stackFrame);
            if (stackFrameDumpInfo != null) {
                ++stackFrameDumpInfo.numOfOccur;
            }
        }
    }

    /**
     * Show the main dump information.
     * 
     * @return Some of the dump information as a string.
     */
    public String toString() {
        if (dateFormat == null) {
            dateFormat = DateFormat.getDateInstance();
        }
        return "Dump at " + dateFormat.format(timestamp) + " has " + threadDumps.size() + " threads";
    }
}
