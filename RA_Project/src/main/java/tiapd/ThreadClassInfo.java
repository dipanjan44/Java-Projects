package tiapd;

import java.lang.management.ThreadInfo;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Thread Classification Information.
 *
 * @author Ken Baclawski
 * @version 1.0 05/20/2017
 */
public class ThreadClassInfo {
    /** The segments that determine this thread classification. */
    private List<StackSegmentInfo> segments;
    
    //comment
    private Set<ThreadClassInfo> threadClasses=new HashSet<ThreadClassInfo>();

    


}
