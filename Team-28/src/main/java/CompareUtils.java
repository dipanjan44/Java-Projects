import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.ASTNode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileOwnerAttributeView;

/**
 * This class implements the logic for comparing identical file content, similar
 * metadata and calling the LCS algorithm for the 2 input files
 *
 * @author Dipanjan
 */
public class CompareUtils {

	/**
	 * Function to compare the contents of 2 java files // if the files are
	 * passed as String arguments
	 *
	 * @param fileOne
	 *            - the first java file
	 * @param fileTwo
	 *            - the second java file
	 * @return - true if the contents are identical else false
	 */
	public static Boolean compareContent(String fileOne, String fileTwo) {
		return compareContent(new File(fileOne), new File(fileTwo));
	}

	/**
	 * Function to compare the contents of 2 java files // if the files are
	 * passed as File arguments
	 *
	 * @param fileOne
	 *            - the first java file
	 * @param fileTwo
	 *            - the second java file
	 * @return - true if the contents are identical else false
	 */
	public static Boolean compareContent(File fileOne, File fileTwo) {
		boolean isContentEqual = false;
		try {
			isContentEqual = FileUtils.contentEquals(fileOne, fileTwo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isContentEqual;
	}

	/**
	 * Function to compare the contents and metadata of 2 given java files
	 *
	 * @param fileOne
	 *            - first java file
	 * @param fileTwo
	 *            - second java file
	 * @return
	 */
	public static int compareMetaData(String fileOne, String fileTwo) {
		Path fileOnePath = Paths.get(fileOne);
		Path fileTwoPath = Paths.get(fileTwo);
		Integer count = 0;
		try {
			BasicFileAttributes fileAttributesOne = Files.readAttributes(fileOnePath, BasicFileAttributes.class);
			BasicFileAttributes fileAttributesTwo = Files.readAttributes(fileTwoPath, BasicFileAttributes.class);
			FileOwnerAttributeView ownerAttributesOne = Files.getFileAttributeView(fileOnePath,
					FileOwnerAttributeView.class);
			FileOwnerAttributeView ownerAttributesTwo = Files.getFileAttributeView(fileTwoPath,
					FileOwnerAttributeView.class);
			if (fileAttributesOne.creationTime().equals(fileAttributesTwo.creationTime())) {
				count += 33;
			}
			if (fileAttributesOne.size() == fileAttributesTwo.size()) {
				count += 33;
			}
			if (ownerAttributesOne.getOwner().equals(ownerAttributesTwo.getOwner())) {
				count += 34;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return count;
	}

	/**
	 * Function to get the similarity of 2 files based on the LCS matching
	 * algorithm
	 * 
	 * @param fileOne
	 * @param fileTwo
	 * @return
	 */

	public static int compareLCS(File fileOne, File fileTwo) {
		ASTNode rootNodeOne = ASTUtils.getASTNode(fileOne);
		ASTNode rootNodeTwo = ASTUtils.getASTNode(fileTwo);
		ASTLongestCommonSubSequence astLCS = new ASTLongestCommonSubSequence();
		return astLCS.getLCSSimilarityForASTTree(rootNodeOne, rootNodeTwo);
	}
}
