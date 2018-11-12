import java.io.File;
import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test class to validate the input files passed for comparison
 * 
 * @author Dipanjan
 *
 */
public class FileUtilsTest {
	
	/**
	 * Test for valid java files
	 */
	@Test
	public void isJavaFile() {
		File directory = new File("");
		String file1 = directory.getAbsolutePath() + File.separator + "src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "testFiles" + File.separator + "Bye.java";
		String file2 = directory.getAbsolutePath() + File.separator + "src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "testFiles" + File.separator + "Hello.java";
		String file3 = directory.getAbsolutePath() + File.separator + "src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "testFiles" + File.separator + "test.txt";
		Assert.assertTrue(FileUtils.isValidJavaFile(new File(file1)));
		Assert.assertTrue(FileUtils.isValidJavaFile(new File(file2)));
		Assert.assertFalse(FileUtils.isValidJavaFile(new File(file3)));
	}
	
	/**
	 * Test for valid files and exists 
	 */
	@Test
	public void isFile() {
		File directory = new File("");
		String file1 = directory.getAbsolutePath() + File.separator + "src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "testFiles" + File.separator + "Bye.java";
		String file2 = directory.getAbsolutePath() + File.separator + "src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "testFiles" + File.separator + "Hello.java";
		String file3 = directory.getAbsolutePath() + File.separator + "src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "testFiles" + File.separator + "test.txt";
		Assert.assertTrue(FileUtils.isValidFile(new File(file1)));
		Assert.assertTrue(FileUtils.isValidFile(new File(file2)));
		Assert.assertTrue(FileUtils.isValidFile(new File(file3)));

	}

	/**
	 * Test for validfile but not exists 
	 */
	@Test
	public void filenotexists() {
		File directory = new File("");
		String file1 = directory.getAbsolutePath() + File.separator + "src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "testFiles" + File.separator + "Fileisnotthere.java";
		
		Assert.assertFalse(FileUtils.isValidFile(new File(file1)));
	}
	
		
	/**
	 * Test for not-valid file and not exists 
	 */
	@Test
	public void validnonjava() {
		File directory = new File("");
		String file1 = directory.getAbsolutePath() + File.separator + "src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "testFiles" + File.separator + "testPGfolder1";
		Assert.assertFalse(FileUtils.isValidFile(new File(file1)));
	}
	
	
}
