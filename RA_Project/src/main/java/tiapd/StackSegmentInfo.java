package tiapd;

import java.lang.management.ThreadInfo;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;
import java.util.Objects;

/**
 * Stack Segment Information.  A stack segment is a contiguous sequence of
 * stack frames occurring within a stack trace.  This class is incomplete.
 *
 * @author Ken Baclawski
 * @version 1.0 05/20/2017
 */
public class StackSegmentInfo extends SegmentInfo {
    /** The identifier of the stack segment. */
    private long id;

    /** The name of the stack segment. */
    private String name;

    /** The dimension of the stack segment. */
    private String dimension;

    /** The number of occurrences of the stack segment. */
    private short numOfOccur;

    /** The total number of occurrences of the stack segment. */
    private short totalNumOfOccur;

    /** The elements of the stack segment. */
    private List<StackFrameInfo> elements = new ArrayList<StackFrameInfo>();

    /** The first stack segment. */
    private SegmentInfo firstSegment;

    /** The second stack segment. */
    private SegmentInfo secondSegment;

    /** The coalescing stack segment. */
    private StackSegmentInfo coalescingSegment;

    /** The predecessors to this stack segment. */
    private Set<StackSegmentInfo> predecessors;

    /** The successors to this stack segment. */
    private Set<StackSegmentInfo> successors;

    /** The seasonal trend of this stack segment. */
    private SeasonalTrendInfo trend;

    /** The thread classifications that use this stack segment. */
    private Set<ThreadClassInfo> partOfThreadClasses;

    //** The lowest stack segment. */
    private static StackSegmentInfo SEGMENT_FLOOR = new StackSegmentInfo();
    
    
    /**
     * Clear the number of occurrences in the current dump.  This is performed
     * prior to obtaining the dump information.
     */
    public void clearNumOfOccur() {
	numOfOccur = 0;
    }

    /**
     * Increment the number of occurrences.  Both the number of occurrences and
     * the total number of occurrences are incremented.
     */
    public void incrementNumOfOccur() {
	++numOfOccur;
	++totalNumOfOccur;
    }

    /**
     * Find a stack segment in a set of them, creating the object if needed.
     * @param frames The sequence of frames.
     * @param segments The set of stack segment objects.
     * @return The matching stack segment object.
     */
    public static StackSegmentInfo getSegmentInfo(List<StackFrameInfo> frames, Map<List<StackFrameInfo>, StackSegmentInfo> segments) {
	StackSegmentInfo segment = segments.get(frames);
	if (segment == null) {
	    segment = new StackSegmentInfo();
	    segment.elements = new ArrayList<StackFrameInfo>(frames);
	    segments.put(frames, segment);
	}
	
	return segment;
    }
    

	public StackSegmentInfo getLast() {
		return (StackSegmentInfo) predecessors.toArray()[predecessors.size()-1];
	}
    
    /**
     * Equality of two stack segments.
     * @param object The other object with which to compare.
     * @return True if the two stack segments are the same.
     */
    public boolean equals(Object object) {
	if (!(object instanceof StackSegmentInfo)) {
	    return false;
	}
	StackSegmentInfo otherStackSegment = (StackSegmentInfo) object;
	if (elements.size() != otherStackSegment.elements.size()) {
	    return false;
	}
	for (int i = 0; i < elements.size(); ++i) {
	    if (!elements.get(i).equals(otherStackSegment.elements.get(i))) {
		return false;
	    }
	}
	return true;
    }
    /**
     * add the predecessor for the stacksegment
     * @param predecessor
     */
    
    public void addPredecessor(StackSegmentInfo predecessor) {
        predecessors.add(predecessor);
    }
    /**
     * add the successor for the stacksegment
     * @param successor
     */
    public void addSuccessor(StackSegmentInfo successor) {
        successors.add(successor);
    }
    
   /**
    * Set the predecessors and successors for one stack segment.
    * @param stackSegment
    */
    public static void setPredecessorsAndSuccessors(List<StackSegmentInfo> stackSegment) {

        if (stackSegment == null || stackSegment.size() == 0) {
            return;
        }

        // Take care of the bottom.

        StackSegmentInfo bottom = stackSegment.get(stackSegment.size() - 1);
        bottom.addPredecessor(SEGMENT_FLOOR);
        SEGMENT_FLOOR.addSuccessor(bottom);
    } 

    /**
     * Hash code for a thread information object.
     * @return The hash code for this thread.
     */
    public int hashCode() {
	return Objects.hash(elements.toArray());
    }
}
