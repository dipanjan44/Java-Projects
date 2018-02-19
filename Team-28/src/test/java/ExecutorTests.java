import static org.junit.Assert.*;

import java.io.File;
import org.junit.Assert;
import org.junit.Test;

public class ExecutorTests {
		
	/**
	 * test the executor positive flow
	 */
	@Test
	public void positivepath() {
		File directory = new File("");
		String file1 = directory.getAbsolutePath() + File.separator + "src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "testFiles" + File.separator + "file_1.java";
		String file2 = directory.getAbsolutePath() + File.separator + "src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "testFiles" + File.separator + "file_2.java";
		Executor ex = new Executor();
		String response=ex.execute(file1,file2);
		String resp = "{response: { plagiarizedScore: 100.0, files: [file_1.java, file_2.java] } }";
		assertEquals(resp, response);
	}
	/**
	 * File exists but not .java
	 */
	@Test
	public void noJavaFile() {
		File directory = new File("");
		String file1 = directory.getAbsolutePath() + File.separator + "src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "testFiles" + File.separator + "test.txt";
		String file2 = directory.getAbsolutePath() + File.separator + "src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "testFiles" + File.separator + "NoValidfile2.java";
		Executor ex = new Executor();

		String resp = "{response: { errorMessage: Invalid JavaFile Provided in Argument 1 } }";
		assertEquals(resp, ex.execute(file1, file2).toString());
	}

	/**
	 * invalid javafile for file1
	 */

	@Test
	public void invalidJavaFilearg2() {
		File directory = new File("");
		String file1 = directory.getAbsolutePath() + File.separator + "src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "testFiles" + File.separator + "test.txt";
		String file2 = directory.getAbsolutePath() + File.separator + "src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "testFiles" + File.separator + "Hello.java";
		Executor ex = new Executor();

		String resp = "{response: { errorMessage: Invalid JavaFile Provided in Argument 1 } }";
		assertEquals(resp, ex.execute(file1, file2).toString());
	}

	/**
	 * invalid javafile for file2
	 */

	@Test
	public void invalidJavaFilearg1() {
		File directory = new File("");
		String file1 = directory.getAbsolutePath() + File.separator + "src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "testFiles" + File.separator + "bye.java";
		String file2 = directory.getAbsolutePath() + File.separator + "src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "testFiles" + File.separator + "test.txt";
		Executor ex = new Executor();

		String resp = "{response: { errorMessage: Invalid JavaFile Provided in Argument 2 } }";
		assertEquals(resp, ex.execute(file1, file2).toString());
	}

	/**
	 * null are passed in the filepath
	 */
	@Test
	public void nullfilePath() {
		String file1 = null;
		String file2 = null;
		Executor ex = new Executor();
		String resp = "{response: { errorMessage: Invalid Files Uploaded! } }";
		assertEquals(resp, ex.execute(file1, file2).toString());
	}
	
	/**
	 * file 2 is null
	 */
	@Test
	public void file2null() {
		File directory = new File("");
		String file1 = directory.getAbsolutePath() + File.separator + "src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "testFiles" + File.separator + "bye.java";
		String file2 = null;
		
		Executor ex = new Executor();
		String resp = "{response: { errorMessage: Invalid Files Uploaded! } }";
		assertEquals(resp, ex.execute(file1, file2).toString());
	}
	
	/**
	 * file 1 is null
	 */
	@Test
	public void file1null() {
		File directory = new File("");
		String file2 = directory.getAbsolutePath() + File.separator + "src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "testFiles" + File.separator + "bye.java";
		String file1 = null;
		
		Executor ex = new Executor();
		String resp = "{response: { errorMessage: Invalid Files Uploaded! } }";
		assertEquals(resp, ex.execute(file1, file2).toString());
	}
	
	/**
	 * test the executor negative flow with a non-exitent non-jave file for argument1
	 */
	@Test
	public void nonexixtentnonjava1() {
		File directory = new File("");
		String file1 = directory.getAbsolutePath() + File.separator + "src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "testFiles" + File.separator + "file_1.txt";
		String file2 = directory.getAbsolutePath() + File.separator + "src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "testFiles" + File.separator + "file_2.java";
		Executor.main(new String[] {"file1", "file2","file3"});
		Executor ex = new Executor();
		String response=ex.execute(file1,file2);
		String resp = "{response: { errorMessage: Invalid JavaFile Provided in Argument 1 } }";
		assertEquals(resp, response);
	}
	
	/**
	 * test the executor negative flow with a non-exitent non-jave file for argument2
	 */
	@Test
	public void nonexixtentnonjava2() {
		File directory = new File("");
		String file1 = directory.getAbsolutePath() + File.separator + "src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "testFiles" + File.separator + "file_1.java";
		String file2 = directory.getAbsolutePath() + File.separator + "src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "testFiles" + File.separator + "file_2.txt";
		Executor ex = new Executor();
		Executor.main(new String[] {"file1", "file2"});
		String response=ex.execute(file1,file2);
		String resp = "{response: { errorMessage: Invalid JavaFile Provided in Argument 2 } }";
		assertEquals(resp, response);
	}
	
	
}
