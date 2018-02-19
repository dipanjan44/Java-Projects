package tiapd;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.lang.ref.WeakReference;

/**
 * Thread Identification.  This represents a single thread.  Technically, the
 * Java thread identifier is only valid within a single dump.  A thread could
 * terminate and be replaced by another one with the same thread identifier.
 * The name of the thread may also not be unique.  While the combination of
 * thread name and thread identifier might not be unique, the likelihood that
 * there might be a lack of uniqueness is very small.
 *
 * @author Ken Baclawski
 * @version 1.0 05/20/2017
 */
public class ThreadIdentification {
    /** The thread itself, but only a weak reference to it. */
    private WeakReference<Thread> thread = null;

    /** The name of the thread. */
    private String name;

    /** The identifier of the thread. */
    private long id;
    

    
    

    /**
     * Construct a thread identification object.
     * @param thread The thread being identified.
     * @throws Exception if the parameter is null.
     */
    public ThreadIdentification(Thread thread) throws Exception {

        // Check that the parameter is not null.

        if (thread == null) {
            throw new Exception("Attempt to construct a ThreadIdentification object with an invalid parameter");
        }

        // Initialize the fields.

        this.thread = new WeakReference<Thread>(thread);
        this.name = thread.getName();
        this.id = thread.getId();

        // A thread must have a name.

        if (this.name == null) {
            throw new Exception("Attempt to construct a ThreadIdentification object with an unnamed thread");
        }
    }

    /**
     * Construct a thread identification object.
     * @param name The name of the thread.
     * @param id The identifier of the thread.
     * @throws Exception if the name is null or the id is 0.
     */
    public ThreadIdentification(String name, long id) throws Exception {

        // Check that all parameters are valid.

        if (name == null || id == 0) {
            throw new Exception("Attempt to construct a ThreadIdentification object with an invalid parameter");
        }

        // Initialize the fields.

        this.name = name;
        this.id = id;
    }
    
   

    /**
     * Find a thread identification object in a set of them, creating the object if needed.
     * @param name The name of the thread.
     * @param id The identifier of the thread.
     * @param threads The set of thread identification objects.
     * @return The matching thread identification object.
     * @throws Exception if the name or threads parameter is null or the id is 0.
     */
    public static ThreadIdentification getThreadIdentification(String name, long id, Map<ThreadIdentification, ThreadIdentification> threads) throws Exception {

        // Check that all parameters are valid.

        if (name == null || id == 0 || threads == null) {
            throw new Exception("Attempt to get a ThreadIdentification object with an invalid parameter");
        }

        // Construct a new thread identification object and use it to look up an
        // existing object if there is one.

        ThreadIdentification newThreadIdentification = new ThreadIdentification(name, id);
        ThreadIdentification threadIdentification = threads.get(newThreadIdentification);
        if (threadIdentification == null) {
            threadIdentification = newThreadIdentification;
            threads.put(threadIdentification, threadIdentification);
        }
        return threadIdentification;
    }
    

    /**
     * Equality of two thread identification objects.
     * @param object The other object with which to compare.
     * @return True if the two thread identification objects are the same.
     */
    public boolean equals(Object object) {
        if (!(object instanceof ThreadIdentification)) {
            return false;
        }
        ThreadIdentification otherThread = (ThreadIdentification) object;
        return name.equals(otherThread.name) && id == otherThread.id;
    }

    /**
     * Hash code for a thread identification object.
     * @return The hash code for this thread.
     */
    public int hashCode() {
        return Objects.hash(name, id);
    }

    /**
     * Show the thread name and identifier.
     * @return The thread identification.
     */
    public String toString() {
        return "Thread \"" + name + "\" id " + id;
    }
}
