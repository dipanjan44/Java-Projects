package tiapd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

/**
 * Virtual Machine Information.
 *
 * @author Ken Baclawski
 * @version 1.0 05/20/2017
 */
public class VirtualMachineInfo {
    /** The list of dumps. */
    private List<DumpInfo> dumps = new ArrayList<DumpInfo>();

    /** The set of all threads. */
    private Map<ThreadIdentification, ThreadIdentification> threads = new HashMap<ThreadIdentification, ThreadIdentification>();

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
     * Construct a virtual machine information object.
     */
    public VirtualMachineInfo() {}

    /**
     * Parse a set of dumps from a file.
     * @param dumpFilename The name of the file with the dumps.
     */
    public void parse(String dumpFilename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(dumpFilename));

	// Loop over the dumps in the file.

        for (;;) {
            DumpInfo dump = null;
            try {
                dump = DumpInfo.parse(reader, threads, frames);
            } catch (IOException ioe) {
                System.err.println("The dump information may be incorrect due to an I/O problem: " + ioe);
            } catch (Exception e) {
                System.err.println("The dump information may be incorrect due to: " + e);
	    }

	    // The last call to DumpInfo.parse returns null to indicate that
	    // the end of the input stream has been reached.

            if (dump == null) {
                break;
            } else {
                dumps.add(dump);
            }
        }
    }

    /**
     * Main program for testing parsing of a dump file.
     * @param args The command-line arguments.  Only the first is used.  It is
     * the name of the file with the dumps.
     * @throws IOException if an I/O error occurs.
     */
    public static void main(String... args) throws IOException {
        if (args.length < 1) {
            System.err.println("Usage: java tiapd.VirtualMachineInfo dump-file");
            return;
        }
        VirtualMachineInfo vmInfo = new VirtualMachineInfo();
        vmInfo.parse(args[0]);
	System.out.println("There were " + vmInfo.dumps.size() + " dumps and " + vmInfo.threads.size() + " threads in the dump.");
    }
}
