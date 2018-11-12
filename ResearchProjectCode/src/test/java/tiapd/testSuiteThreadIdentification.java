package tiapd;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class testSuiteThreadIdentification {

	/**
	 * Test suite to test the functionality of threadIdentification.java
	 * 
	 * @author Dipanjan
	 * @throws IOException
	 *
	 */

	/**
	 * Test the thread identification constructor where a thread object is
	 * passed
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testThreadIdentification() throws Exception {
		Thread thread = null;
		ThreadIdentification tidf = new ThreadIdentification(thread);
		System.out.println("The thread being passed is null");

	}

	/**
	 * Test the thread identification object behavior
	 * 
	 * @throws Exception
	 */
	@Test
	public void testuniqueThread_name_ID() throws Exception {
		String name = "Image Fetcher 0";
		long id = 0x000000001ab21000;
		String name1 = "DestroyJavaVM";
		long id1 = 0x00000000044f0800;
		String name2 = "Image Fetcher 0";
		long id2 = 0x0000000019202800;
		ThreadIdentification newThreadIdentification = new ThreadIdentification(name, id);
		ThreadIdentification newThreadIdentification1 = new ThreadIdentification(name1, id1);
		ThreadIdentification newThreadIdentification2 = new ThreadIdentification(name1, id1);
		// System.out.println(newThreadIdentification.toString());
		// System.out.println(newThreadIdentification1.toString());
		// System.out.println(newThreadIdentification2.toString());
		Map<ThreadIdentification, ThreadIdentification> threads = new HashMap<ThreadIdentification, ThreadIdentification>();
		threads.put(newThreadIdentification, newThreadIdentification);
		threads.put(newThreadIdentification1, newThreadIdentification1);
		threads.put(newThreadIdentification2, newThreadIdentification2);
		// This is the case where the thread was already present in the existing
		// map
		System.out.println(ThreadIdentification.getThreadIdentification(name1, id1, threads));
		// This the case where the thread was not present in the existing map
		System.out.println(ThreadIdentification.getThreadIdentification(name2, id2, threads));
		// This the case where the thread name was null
	}
    
	@Test(expected = Exception.class)
	public void testnullcases() throws Exception {
		String name3 = "null";
		long id3 = 0x0000000019202800;
		String name4 = "main";
		long id4 = 0;
		Map<ThreadIdentification, ThreadIdentification> nullthreads = null;
		ThreadIdentification newThreadIdentification = new ThreadIdentification(name3, id3);
		ThreadIdentification newThreadIdentification1 = new ThreadIdentification(name4, id4);
		Map<ThreadIdentification, ThreadIdentification> threads = new HashMap<ThreadIdentification, ThreadIdentification>();
		threads.put(newThreadIdentification, newThreadIdentification);
		threads.put(newThreadIdentification1, newThreadIdentification1);
		// This the case where the thread name was null
		System.out.println(ThreadIdentification.getThreadIdentification(name3, id3, threads));
		// This the case where the thread id was null
		System.out.println(ThreadIdentification.getThreadIdentification(name4, id4, threads));
		// This the case where the thread map passed was null
		System.out.println(ThreadIdentification.getThreadIdentification(name3, id3, nullthreads));

	}

}
