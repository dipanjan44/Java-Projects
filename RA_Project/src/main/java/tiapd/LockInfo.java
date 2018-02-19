package tiapd;

import java.util.Set;
import java.util.HashSet;

/**
 * Lock Information.
 *
 * A thread can be waiting to be notified, waiting to acquire a lock or parked.
 * A thread can own a set of locks.  Waiting, parking and owning are all on
 * objects, not on other threads.  It is possible, for example, for a thread to
 * be waiting to be notified without any thread owning the notification object.
 * Waiting to be unparked never involves any object ownership.  The object that
 * is owned or waited for is specified with an object address.  Such an address
 * is meaningful only within a single dump.  This is not because objects might
 * be moved but rather because a lot can happen between two dumps.  For
 * example, an object can be garbage collected and another one allocated at the
 * same address as the one that was just garbage collected.  So having the same
 * address in two dumps does not mean that the objects are the same.
 *
 * @author Ken Baclawski
 * @version 1.0 05/20/2017
 */
public class LockInfo {
    /** Enumeration of lock wait types. */
    public enum LockWaitType { NOTIFICATION, PARKED, LOCK, NONE, };

    /** The type of wait if the thread is waiting. */
    private LockWaitType lockWaitType = LockWaitType.NONE;

    /** Object address of object being waited for. */
    private long objectAddress = 0;

    /** The locks that are owned by this thread. */
    private Set<Long> ownedLocks = null;

    /**
     * Construct a lock information object that specifies no waiting and no
     * lock ownerships.  Waiting and lock ownership is specified separately
     * later.
     */
    public LockInfo() {}

    /**
     * Specify that a thread is waiting.  It can be waiting to be notified,
     * waiting to acquire a lock or parked.  In every case, it is waiting on an
     * object specified by an address.  This address is meaningful only within
     * the context of a single dump.  If the thread was already specified to be
     * be waiting, then an exception is thrown.
     * @param lockWaitType Optional type of wait.  If null or NONE, then the
     * thread is not waiting and the request is ignored.
     * @param objectAddress Optional address of the object being waited for.
     * If the address is 0, then the thread is not waiting and the request is
     * ignored.
     * @throws Exception if the thread is already waiting on an object.
     */
    public void addWait(LockWaitType lockWaitType, long objectAddress) throws Exception {
        if (this.lockWaitType != LockWaitType.NONE) {
            throw new Exception("Attempt to specify a second time that a thread is waiting");
        }
        if (objectAddress == 0 || lockWaitType == null || lockWaitType == LockWaitType.NONE) {
            return;
        }
        this.lockWaitType = lockWaitType;
        this.objectAddress = objectAddress;
    }

    /**
     * Add another object to the set of objects that are locked by a thread.
     * @param objectAddress Optional address of the object being waited for.
     * If the address is 0, then the request is ignored.
     */
    public void addLock(long objectAddress) {
        if (objectAddress != 0) {
            if (ownedLocks == null) {
                ownedLocks = new HashSet<Long>();
            }
            ownedLocks.add(objectAddress);
        }
    }

    /**
     * Show the lock information.
     * @return The lock information as a string.
     */
    public String toString() {
        String info = "";
        switch (lockWaitType) {
        case NOTIFICATION:
            info = info + " waiting to be notified on object " + String.format("%x", objectAddress);
            break;
        case PARKED:
            info = info + " waiting to be unparked on object " + String.format("%x", objectAddress);
            break;
        case LOCK:
            info = info + " waiting to be acquire a lock on object " + String.format("%x", objectAddress);
            break;
        case NONE: default:
            break;
        }
        if (ownedLocks != null) {
            info = info + " owning lock(s):";
        }
        for (Long objectAddress : ownedLocks) {
            info = info + " " + objectAddress;
        }
        return info;
    }
}
