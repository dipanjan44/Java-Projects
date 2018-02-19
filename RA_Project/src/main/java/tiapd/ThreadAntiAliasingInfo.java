package tiapd;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Thread Anti-Aliasing Information.
 *
 * @author Ken Baclawski
 * @version 1.0 05/20/2017
 */
public class ThreadAntiAliasingInfo {
	/** The identifier. */
	private long id;

	/** The number of dumps that use the thread. */
	private short numberOfDumps;

	/** Whether the thread is in the current dump. */
	private boolean foundInCurrentThreadDump;

	/** The name of the thread. */
	private String threadName;

	/*
	 * Constructor for the ThreadAntiAliasingInfo
	 */
	public ThreadAntiAliasingInfo(boolean foundInCurrentThreadDump, String threadName) {
		this.foundInCurrentThreadDump = foundInCurrentThreadDump;
		this.threadName = threadName;
	}

	/**
	 * 
	 * @param foundInCurrentThreadDump     -
	 * @param threadName:  The fully qualifies name of the thread
	 * @param threadAntiAlias : The set of anti-alias object
	 * @return the matching AntiAliasing object
	 */
	public static ThreadAntiAliasingInfo getAntiAliasingInfo(boolean foundInCurrentThreadDump, String threadName,
			Map<ThreadAntiAliasingInfo, ThreadAntiAliasingInfo> threadAntiAlias) {
		ThreadAntiAliasingInfo newThreadAntiAliasingInfo = new ThreadAntiAliasingInfo(foundInCurrentThreadDump,
				threadName);
		ThreadAntiAliasingInfo AntiAliasingInfo = threadAntiAlias.get(newThreadAntiAliasingInfo);
		if (AntiAliasingInfo == null) {
			AntiAliasingInfo = newThreadAntiAliasingInfo;
			AntiAliasingInfo.foundInCurrentThreadDump = true;
			AntiAliasingInfo.threadName = threadName;
			threadAntiAlias.put(AntiAliasingInfo, AntiAliasingInfo);
		}
		return AntiAliasingInfo;

	}

	// Do we need this method? why we need to again calculate the number of dumps
	
	 public void incrementNumOfDumps(ThreadAntiAliasingInfo threadAntiAlias)
	 {
	 
	 }

}
