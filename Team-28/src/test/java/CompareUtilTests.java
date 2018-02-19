import static org.junit.Assert.assertEquals;
import java.io.File;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test class to test the functionality of CompareUtils
 * 
 * @author Dipanjan
 *
 */
public class CompareUtilTests {

	@Test
	/**
	 * Test case to test a non-plagiarised file
	 */
	public void nonPlag() {

		File directory = new File("");
		String fileOnePath = directory.getAbsolutePath() + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources" + File.separator + "testFiles" + File.separator + "Bye.java";
		String fileTwoPath = directory.getAbsolutePath() + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources" + File.separator + "testFiles" + File.separator + "Hello.java";

		Assert.assertFalse(CompareUtils.compareContent(fileOnePath, fileTwoPath));

	}

	@Test
	/**
	 * Test case to test identical files contents
	 */
	public void copiedPlag() {

		File directory = new File("");

		String fileTwoPath = directory.getAbsolutePath() + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources" + File.separator + "testFiles" + File.separator + "Hello.java";
		String fileThreePath = directory.getAbsolutePath() + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources" + File.separator + "testFiles" + File.separator + "Hello.java";

		Assert.assertTrue(CompareUtils.compareContent(fileThreePath, fileTwoPath));

	}

	@Test
	/**
	 * Test case to test plagiarised file- same owner of file
	 */
	public void filecreatedsameOwner() {

		File directory = new File("");
		String fileOnePath = directory.getAbsolutePath() + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources" + File.separator + "testFiles" + File.separator + "Bye.java";
		String fileTwoPath = directory.getAbsolutePath() + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources" + File.separator + "testFiles" + File.separator + "Hello.java";

		Assert.assertEquals(34, CompareUtils.compareMetaData(fileOnePath, fileTwoPath));

	}

	@Test
	/**
	 * Test case to test plagiarised files with same creation-time-stamp,owner
	 * and size
	 */
	public void identicalmetadata() {

		File directory = new File("");

		String fileTwoPath = directory.getAbsolutePath() + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources" + File.separator + "testFiles" + File.separator + "Hello.java";
		String fileThreePath = directory.getAbsolutePath() + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources" + File.separator + "testFiles" + File.separator + "Hello.java";

		Assert.assertEquals(100, CompareUtils.compareMetaData(fileTwoPath, fileThreePath));

	}

	@Test
	/**
	 * Test case to check the LCS score for identical files and size for exact
	 * identical files it returns 96
	 */
	public void LCSscore() {

		File file1 = new File(getClass().getResource("/testFiles/file_1.java").getFile());
		File file2 = new File(getClass().getResource("/testFiles/file_2.java").getFile());
		CompareUtils cmp = new CompareUtils();
		int score = cmp.compareLCS(file1, file2);
		assertEquals(96, score);

	}

	@Test
	/**
	 * Test case to check the LCS score for non-plag completely different files
	 * It should return 0
	 */
	public void LCSscoreNonPlag() {

		File file1 = new File(getClass().getResource("/testFiles/file_1.java").getFile());
		File file2 = new File(getClass().getResource("/testFiles/Sorter2.java").getFile());
		CompareUtils cmp = new CompareUtils();
		int score = cmp.compareLCS(file1, file2);
		assertEquals(0, score);
	}

	//@Test
	/**
	 * Test case to check the different owners of a non-plag file It should
	 * return 0. 
	 * Note: This test case doesnt work on windows laptop, as any file on windows gets tagged to the owner of the laptop
	 * it works on a mac/linux os
	 */
	/*public void NonPlagdiffowner() {

		File directory = new File("");

		String fileTwoPath = directory.getAbsolutePath() + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources" + File.separator + "testFiles" + File.separator + "Hello.java";
		String fileThreePath = "C:\\NE_MSCS\\Fall-2017\\MSD-5500\\Project\\resources\\JavaQuizFall2017\\src\\q3\\Example.java";

		Assert.assertEquals(0, CompareUtils.compareMetaData(fileTwoPath, fileThreePath));

	}*/

	@Test
	/**
	 * Test case to check the no plagiarised score for a null file return 0
	 */
	public void onefileNullexception() {

		File file1 = new File(getClass().getResource("/testFiles/file_1.java").getFile());
		File file2 = new File("");
		CompareUtils cmp = new CompareUtils();
		int score = cmp.compareLCS(file1, file2);
		assertEquals(0, score);

	}

}
