package tiapd;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Frame Information.  The stack consists of a linear sequence of frames.  Each
 * frame is specified by the method being executed and the line number of the
 * method that was being executed when the stack trace was constructed.
 *
 * @author Ken Baclawski
 * @version 1.0 05/20/2017
 */
public class StackFrameInfo {
    /** The method being executed during this stack frame. */
    private String methodName = null;

    /** The line number being executed in the method. */
    private String lineNumber = null;

    /** The total number of occurrences in all dumps. */
    private int totalNumOfOccur = 0;

    /** The predecessors of the stack frame. */
    Set<StackFrameInfo> predecessors = new HashSet<StackFrameInfo>();

    /** The successors of the stack frame. */
    Set<StackFrameInfo> successors = new HashSet<StackFrameInfo>();

    /** The coalescing stack segment. */
    StackSegmentInfo coalescingSegment = null;
    
    /** The lowest stack frame. */
    private static StackFrameInfo FRAME_FLOOR = new StackFrameInfo();

    /** The highest stack frame. */
    private static StackFrameInfo FRAME_CEILING = new StackFrameInfo();
    
    /** The thread that is being observed. */
    private ThreadIdentification threadIdentification = null;

    /**
     * Construct a frame with no method or line number.  This is used only for
     * the floor and ceiling frames.
     */
    private StackFrameInfo() {}

    /**
     * Construct frame information.
     * @param methodName The fully qualified name of the method being executed.
     * @param lineNumber The line number of the method being executed.  This
     * includes the name of the file.
     */
    public StackFrameInfo(String methodName, String lineNumber) {
        this.methodName = methodName;
        this.lineNumber = lineNumber;
    }

    /**
     * Increment the total number of occurrences.
     */
    public void incrementTotalNumOfOccur() {
        ++totalNumOfOccur;
    }

    /**
     * Find a stack frame object in a set of them, creating the object if needed.
     * @param methodName The fully qualified name of the method being executed.
     * @param lineNumber The line number of the method being executed.  This
     * includes the name of the file.
     * @param frames The set of stack frame objects.
     * @return The matching stack frame object.
     */
    public static StackFrameInfo getFrameInfo(String methodName, String lineNumber, Map<StackFrameInfo, StackFrameInfo> frames) {
        StackFrameInfo newFrameInfo = new StackFrameInfo(methodName, lineNumber);
        StackFrameInfo frameInfo = frames.get(newFrameInfo);
        if (frameInfo == null) {
            frameInfo = newFrameInfo;
            frames.put(frameInfo, frameInfo);
        }
        return frameInfo;
    }

    /**
     * Add a predecessor.
     * @param predecessor The predecessor to add.
     */
    public void addPredecessor(StackFrameInfo predecessor) {
        predecessors.add(predecessor);
    }

    /**
     * Add a successor.
     * @param successor The successor to add.
     */
    public void addSuccessor(StackFrameInfo successor) {
        successors.add(successor);
    }

    /**
     * Set the coalescing segment.
     * @param coalescingSegment The coalescing segment to be used.
     */
    public void setCoalescingSegment(StackSegmentInfo coalescingSegment) {
        this.coalescingSegment = coalescingSegment;
    }

    /**
     * Show the frame information.
     * @return The frame information.
     */
    public String toString() {
        return methodName + "(" + lineNumber + ")[" + totalNumOfOccur + "]";
    }
    
    
    /**
     * Set the predecessors and successors for one stack frame.
     * @param stackFrame The stack frame.
     */
    public static void setPredecessorsAndSuccessors(List<StackFrameInfo> stackFrame) {

        // Check for degenerate cases.

        if (stackFrame == null || stackFrame.size() == 0) {
            return;
        }

        // Set all of the successors and the predecessors except for the bottom.

        StackFrameInfo successor = FRAME_CEILING;
        for (StackFrameInfo frame : stackFrame) {
            frame.addSuccessor(successor);
            successor.addPredecessor(frame);
            successor = frame;
        }

        // Take care of the bottom.

        StackFrameInfo bottom = stackFrame.get(stackFrame.size() - 1);
        bottom.addPredecessor(FRAME_FLOOR);
        FRAME_FLOOR.addSuccessor(bottom);
    }    
}
