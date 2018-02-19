package tiapd;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Collections;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Thread Dump Information.
 *
 * A thread dump consists of information about a single thread observed at one
 * point in time.  Each thread dump specifies a stack trace and lock
 * information.
 *
 * @author Ken Baclawski
 * @version 1.0 05/20/2017
 */
public class ThreadDumpInfo {
    /** The dump during which the thread was observed. */
    private DumpInfo dump = null;

    /** The thread that is being observed. */
    private ThreadIdentification threadIdentification = null;

    /** The stack trace that was observed. */
    private List<StackFrameInfo> stackTrace = new ArrayList<StackFrameInfo>();

    /** Optional lock information. */
    private LockInfo lockInfo = null;

    /** The stack frame format. */
    private static final Pattern stackFramePattern = Pattern.compile("\\s*at\\s*(([a-zA-Z_$][\\w$]*/)*[a-zA-Z_$][\\w$]*[.][a-zA-Z_$<][\\w$<>]*)[(]([^)]*)[)]");

    /** The notification format. */
    private static final Pattern notificationPattern = Pattern.compile("\\s*-- Waiting for notification on: [^@]*@0x([0-9a-fA-F]+)");

    /** The parking format. */
    private static final Pattern parkingPattern = Pattern.compile("\\s*-- Parking to wait for: [^@]*@0x([0-9a-fA-F]+)");

    /** The lock wait format. */
    private static final Pattern lockWaitPattern = Pattern.compile("\\s*-- Blocked trying to get lock: [^@]*@0x([0-9a-fA-F]+)");

    /** The lock hold format. */
    private static final Pattern lockHoldPattern = Pattern.compile("\\s*\\^-- Holding lock: [^@]*@0x([0-9a-fA-F]+)");

    /** The other lock information format. */
    private static final Pattern lockOtherPattern = Pattern.compile("\\s*\\^--");

    /** The end of the stack trace. */
    public static final String TRACE_END = "    -- end of trace";

    
    /** The list of finegrained segments */
    private List<StackSegmentInfo> fineGrainedSegments=new ArrayList();
    
    /**  */
    private Map<List<StackFrameInfo>,StackSegmentInfo> segments=new HashMap<List<StackFrameInfo>,StackSegmentInfo>();
    
    /**
     * Construct an observation of a thread at one point in time by reading a
     * jRockit thread dump.
     * 
     * @param dump The dump that observed the thread.
     * @param thread The thread being observed.
     * @param allFrames The set of all frames currently known.
     * @param reader The stream with the dump information.
     * @throws IOException if an I/O error occurs
     * @throws Exception if a parameter is null or the input is not properly
     * formatted.
     */
    public ThreadDumpInfo(DumpInfo dump, ThreadIdentification threadIdentification, BufferedReader reader, 
                          Map<StackFrameInfo, StackFrameInfo> allFrames) throws IOException, Exception {

        // Check that all parameters are non-null.

        if (dump == null || threadIdentification == null || reader == null || allFrames == null) {
            throw new Exception("Attempt to construct a ThreadDumpInfo object with a null parameter");
        }

        // This class implements a many-to-many relationship between dumps and
        // threads.

        this.dump = dump;
        this.threadIdentification = threadIdentification;

        // Read and parse the thread dump information for one thread.

        for (;;) {
            String line = reader.readLine();

                        // The information must end with an empty line or a line specifying
            // the end of the trace.
            
            if (line == null || line.equals(DumpInfo.DUMP_END)) {
                throw new Exception("Premature end of a dump for " + threadIdentification);
            }
            if (line.isEmpty() || line.equals(TRACE_END)) {

                // Set the precessors and successors.

                StackFrameInfo.setPredecessorsAndSuccessors(stackTrace);
                return;
                
                //maybe add segment predecessor
            }
            
            
            
            // First check for a stack frame.  This is the most common case.

            Matcher stackFrameMatcher = stackFramePattern.matcher(line);
            if (stackFrameMatcher.find()) {
                StackFrameInfo frameInfo = StackFrameInfo.getFrameInfo(stackFrameMatcher.group(1), stackFrameMatcher.group(3), allFrames);
                stackTrace.add(frameInfo);
                dump.incrementNumOfOccur(frameInfo);
                frameInfo.incrementTotalNumOfOccur();
                continue;
            }
            
               // Check the segmentInfo and it in the map
                StackSegmentInfo segmentInfo = StackSegmentInfo.getSegmentInfo(stackTrace,segments);
                fineGrainedSegments.add(segmentInfo);
            
                

            // Check whether the thread is waiting to be notified.

            Matcher notificationMatcher = notificationPattern.matcher(line);
            if (notificationMatcher.find()) {
                long objectAddress = Long.parseLong(notificationMatcher.group(1), 16);
                if (lockInfo == null) {
                    lockInfo = new LockInfo();
                }
                try {
                    lockInfo.addWait(LockInfo.LockWaitType.NOTIFICATION, objectAddress);
                } catch (Exception e) {
                    throw new Exception("A stack trace specifies that a thread is waiting more than once: " + threadIdentification);
                }
                continue;
            }

            // Check whether the thread is parked.

            Matcher parkingMatcher = parkingPattern.matcher(line);
            if (parkingMatcher.find()) {
                long objectAddress = Long.parseLong(parkingMatcher.group(1), 16);
                if (lockInfo == null) {
                    lockInfo = new LockInfo();
                }
                try {
                    lockInfo.addWait(LockInfo.LockWaitType.PARKED, objectAddress);
                } catch (Exception e) {
                    throw new Exception("A stack trace specifies that a thread is waiting more than once: " + threadIdentification);
                }
                continue;
            }

            // Check whether the thread is waiting to acquire a lock.

            Matcher lockWaitMatcher = lockWaitPattern.matcher(line);
            if (lockWaitMatcher.find()) {
                long objectAddress = Long.parseLong(lockWaitMatcher.group(1), 16);
                if (lockInfo == null) {
                    lockInfo = new LockInfo();
                }
                try {
                    lockInfo.addWait(LockInfo.LockWaitType.LOCK, objectAddress);
                } catch (Exception e) {
                    throw new Exception("A stack trace specifies that a thread is waiting more than once: " + threadIdentification);
                }
                continue;
            }

            // Check whether the thread is locking an object.

            Matcher lockHoldMatcher = lockHoldPattern.matcher(line);
            if (lockHoldMatcher.find()) {
                long objectAddress = Long.parseLong(lockHoldMatcher.group(1), 16);
                if (lockInfo == null) {
                    lockInfo = new LockInfo();
                }
                lockInfo.addLock(objectAddress);
                continue;
            }

            // Check for some other information in the stack trace.

            Matcher lockOtherMatcher = lockOtherPattern.matcher(line);
            if (lockOtherMatcher.find()) {

                // This informtion is not being used.

                continue;
            }

            // Throw an exception if any other line occurs.

            throw new Exception("Invalid stack trace line: " + line + " for " + threadIdentification);
        }

        // This is unreachable.
    }

    /**
     * Get the stack trace as a immutable collection.
     * @return The stack trace.
     */
    public List<StackFrameInfo> getStackTrace() {
        return Collections.unmodifiableList(stackTrace);
    }

    /**
     * Get the size of the stack trace.
     * @return The stack trace size.
     */
    public int getStackTraceSize() {
        return stackTrace.size();
    }

    /**
     * Equality of two thread dumps.  The ThreadDump class implements a
     * many-to-many relationship between dumps and threads, so an object is
     * uniquely determined by a dump and a thread.
     * @param object The other object with which to compare.
     * @return True if the two thread dumps are for the same dump and thread.
     */
    public boolean equals(Object object) {
        if (!(object instanceof ThreadDumpInfo)) {
            return false;
        }
        ThreadDumpInfo otherThreadDumpInfo = (ThreadDumpInfo) object;
        return dump.equals(otherThreadDumpInfo.dump) && threadIdentification.equals(otherThreadDumpInfo.threadIdentification);
    }

    /**
     * Hash code for a thread dump.  It depends only on the dump and the thread.
     * @return The hash code for this thread dump.
     */
    public int hashCode() {
        return Objects.hash(dump, threadIdentification);
    }

    /**
     * Show some of the dump and thread information.
     * @return Some of the dump and thread information as a string.
     */
    public String toString() {
        return dump + " one of which is " + threadIdentification + " with " + stackTrace.size() + " frames in its stack";
    }
}
