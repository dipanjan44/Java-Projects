package tiapd;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Test suite to check the VirtualMachineInfo.java
 * 
 * @author Dipanjan
 *
 */
public class testSuiteVirtualMachineInfo {

	@Test(expected = Exception.class)
	/** Test case to test the output of a file after parsing **/

	public void testOutput() throws IOException {

		File directory = new File("");
		String fileOne = directory.getAbsolutePath() + File.separator + "src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "testFiles" + File.separator + "stacktrace_1.txt";
		/*
		 * String fileTwoPath = directory.getAbsolutePath() + File.separator +
		 * "src" + File.separator + "test" + File.separator + "resources" +
		 * File.separator + "testFiles" + File.separator + "Hello.java";
		 */

		VirtualMachineInfo vmi = new VirtualMachineInfo();
		DumpInfo dumpStats = new DumpInfo();
		vmi.parse(fileOne);
		System.out.println(dumpStats.toString());

	}
}
