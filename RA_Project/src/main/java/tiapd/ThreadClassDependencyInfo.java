package tiapd;

import java.util.Set;

/**
 * Thread Class Dependency Information.  These are client-server relationships.
 *
 * @author Ken Baclawski
 * @version 1.0 05/20/2017
 */
public class ThreadClassDependencyInfo {
    /** The server thread. */
    private ThreadClassInfo server;

    /** The client threads. */
    private Set<ThreadClassInfo> clients;
}
