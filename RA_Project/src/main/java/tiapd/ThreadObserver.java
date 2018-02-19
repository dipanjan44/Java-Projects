package tiapd;

import java.lang.Runnable;
import java.lang.Thread;
import java.lang.StackTraceElement;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

/**
 * Virtual Machine Thread Observer.  This is a runnable object that is
 * intended to be run as a separate observer thread within a Java Virtual
 * Machine.  This is not yet complete.
 *
 * @author Ken Baclawski
 * @version 1.0 05/25/2017
 */
public class ThreadObserver implements Runnable {
    /** The list of dumps. */
    private List<DumpInfo> dumps = new ArrayList<DumpInfo>();

    /** The set of all threads. */
    private Map<ThreadInfo, ThreadInfo> threads = new HashMap<ThreadInfo, ThreadInfo>();

    /** The set of all stack frames. */
    private Map<StackFrameInfo, StackFrameInfo> frames = new HashMap<StackFrameInfo, StackFrameInfo>();

    /** The set of stack segments. */
    private Set<StackSegmentInfo> stackSegmentInfoSet;

    /** The set of classified threads. */
    private Set<ThreadClassInfo> threadClassificationInfoSet;

    /** The set of anti-aliased threads. */
    private Set<ThreadAntiAliasingInfo> threadAntiAliasingInfoSet;

    /** The set of dependencies among classified threads. */
    private Set<ThreadClassDependencyInfo> threadClassDependencyInfoSet;

    /** The lowest stack frame. */
    private StackFrameInfo FRAME_FLOOR = null;

    /** The highest stack frame. */
    private StackFrameInfo FRAME_CEILING = null;

    /** The lowest stack segment. */
    private StackSegmentInfo SEGMENT_FLOOR = null;

    /** The highest stack segment. */
    private StackSegmentInfo SEGMENT_CEILING = null;

    /**
     * Construct a virtual machine thread observer.
     */
    public ThreadObserver() {}

    /**
     * Observe the virtual machine.
     */
    public void run() {
	for (;;) {

	    // This gives all live threads and their stack traces.
	    
	    Map<Thread, StackTraceElement[]> threadToStackTrace = Thread.getAllStackTraces();
	    
	    // This gives information about locking and waiting.
	    
	    ThreadMXBean mxBean = ManagementFactory.getPlatformMXBean(ThreadMXBean.class);
	    ThreadInfo[] threadInfos = mxBean.dumpAllThreads(true, true);

	    // Analysis here...

	    try {
		Thread.sleep(30000);
	    } catch (Exception e) {
	    }
	}

    }

    /**
     * Main program for testing thread observing.
     */
    public static void main(String... args) {
	ThreadObserver observer = new ThreadObserver();
	Thread observerThread = new Thread(observer);
	observerThread.start();
	System.out.println("There were " + observer.dumps.size() + " dumps and " + observer.threads.size() + " threads in the dump.");
    }
}
