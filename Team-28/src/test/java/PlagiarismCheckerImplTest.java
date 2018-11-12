import static org.junit.Assert.*;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test class for validating the code of PlagiarismCheckerImpl.java
 * 
 * @author Dipanjan
 *
 */
public class PlagiarismCheckerImplTest {

	@Test
	/**
	 * Test case to test a 2 files with identical content This will return a 100
	 * score
	 */
	public void Plag() {

		File file1 = new File(getClass().getResource("/testFiles/file_1.java").getFile());
		File file2 = new File(getClass().getResource("/testFiles/file_2.java").getFile());
		PlagiarismCheckerImpl pglc = new PlagiarismCheckerImpl();
		Double score = pglc.analyze(file1, file2).getPlagiarizedScore();
		Double res = 100.0;
		assertEquals(res, score);

	}

	@Test
	/**
	 * Test case to test a 2 files with same owner but different content so
	 * score will be calculated as 33*MULTIPLIER_FOR_METADATA+
	 * 0.00*MULTIPLIER_FOR_LCS_COMPARE 
	 * score=(33*0.3)+(1*0.7) ==> between 9.9 and 11
	 *  for same owner we are adding +33, and considering a score of 1 out of 100 from LCS as 2 files can have same main method and variable 
	 */
	public void nonPlag() {

		File file1 = new File(getClass().getResource("/testFiles/file_1.java").getFile());
		File file2 = new File(getClass().getResource("/testFiles/sorter1.java").getFile());
		PlagiarismCheckerImpl pglc = new PlagiarismCheckerImpl();
		Double score = pglc.analyze(file1, file2).getPlagiarizedScore();
		Double min = 9.5;
		Double max = 11.5;
		assertTrue("in range", min <= score && score <= max);
	}

	@Test
	/**
	 * Test case to test a 2 files with almost identical content This should return
	 * true as we have set the plagiarised min and max between 80 and 90. These
	 * test files are the files provided by course staff
	 */
	public void testsimilarity() {

		File file1 = new File(getClass().getResource("/testFiles/MyIntToBin.java").getFile());
		File file2 = new File(getClass().getResource("/testFiles/IntToBinCopy.java").getFile());
		CompareUtils utils = new CompareUtils();
		int min = 80;
		int max = 90;
		int score = utils.compareLCS(file1, file2);
		assertTrue("in range", min <= score && score <= max);
	}

	@Test
	/**
	 * Test case to test a 2 non-identical files with different content This
	 * should return close to a true as we have set the plagiarised min and max
	 * between 0 and 10. These test files are the files provided by course
	 * staff
	 */
	public void testnonsimilarity() {

		File file1 = new File(getClass().getResource("/testFiles/sorter1.java").getFile());
		File file2 = new File(getClass().getResource("/testFiles/Hello.java").getFile());
		CompareUtils utils = new CompareUtils();
		int min = 0;
		int max = 10;
		int score = utils.compareLCS(file1, file2);
		assertTrue("outofrange", min <= score && score <= max);
	}

}
