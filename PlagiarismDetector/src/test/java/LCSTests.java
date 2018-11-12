import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class LCSTests {

	// read file content into a string
	public static String readFileToString(String filePath) throws IOException {
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));

		char[] buf = new char[10];
		int numRead;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		reader.close();
		return fileData.toString();
	}

	@Test
	/**
	 * This test case is used for testing the code-relocation aspect of 2 files
	 * where the entire code is same but the structure is modified by relocation
	 * the code blocks and changing the variable names.
	 * 
	 * @throws IOException
	 */
	public void identical_multinode() throws IOException {
		ASTLongestCommonSubSequence test = new ASTLongestCommonSubSequence();
		File file1 = new File(getClass().getResource("/testFiles/file_1.java").getFile());
		ASTParser parser1 = ASTParser.newParser(AST.JLS8);
		parser1.setSource(readFileToString(file1.getAbsolutePath()).toCharArray());
		File file2 = new File(getClass().getResource("/testFiles/file_2.java").getFile());
		ASTParser parser2 = ASTParser.newParser(AST.JLS8);
		parser2.setSource(readFileToString(file2.getAbsolutePath()).toCharArray());
		parser2.setKind(ASTParser.K_COMPILATION_UNIT);
		ASTVisitor28 astVA = new ASTVisitor28();
		List<ASTNode> subtree1 = astVA.getAstNodes();
		ASTNode r1 = parser1.createAST(null);
		CompilationUnit cuA = (CompilationUnit) r1;
		cuA.accept(astVA);
		List<ASTNode> a = astVA.getAstNodes();
		ASTVisitor28 astVB = new ASTVisitor28();
		List<ASTNode> subtree2 = astVB.getAstNodes();
		ASTNode r2 = parser2.createAST(null);
		CompilationUnit cuB = (CompilationUnit) r2;
		cuB.accept(astVB);
		List<ASTNode> b = astVB.getAstNodes();

		int i = 1, j;
		for (ASTNode nodeA : a) {
			j = 1;
			for (ASTNode nodeB : b) {
				if (nodeA.getNodeType() == ASTNode.METHOD_DECLARATION
						&& nodeB.getNodeType() == ASTNode.METHOD_DECLARATION) {
					int output = test.getLCSSimilarityForASTTree(nodeA, nodeB);
					assertEquals(96, output);
					++i;
					++j;

				}
			}
		}

	}

	@Test
	/**
	 * This test case is used for testing 2 non-plag files
	 * 
	 * @throws IOException
	 */
	public void nonidentical() throws IOException {
		ASTLongestCommonSubSequence test = new ASTLongestCommonSubSequence();
		File file1 = new File(getClass().getResource("/testFiles/file_1.java").getFile());
		ASTParser parser1 = ASTParser.newParser(AST.JLS8);
		parser1.setSource(readFileToString(file1.getAbsolutePath()).toCharArray());
		File file2 = new File(getClass().getResource("/testFiles/sorter1.java").getFile());
		ASTParser parser2 = ASTParser.newParser(AST.JLS8);
		parser2.setSource(readFileToString(file2.getAbsolutePath()).toCharArray());
		parser2.setKind(ASTParser.K_COMPILATION_UNIT);
		ASTVisitor28 astVA = new ASTVisitor28();
		List<ASTNode> subtree1 = astVA.getAstNodes();
		ASTNode r1 = parser1.createAST(null);
		CompilationUnit cuA = (CompilationUnit) r1;
		cuA.accept(astVA);
		List<ASTNode> a = astVA.getAstNodes();
		ASTVisitor28 astVB = new ASTVisitor28();
		List<ASTNode> subtree2 = astVB.getAstNodes();
		ASTNode r2 = parser2.createAST(null);
		CompilationUnit cuB = (CompilationUnit) r2;
		cuB.accept(astVB);
		List<ASTNode> b = astVB.getAstNodes();

		int i = 1, j;
		for (ASTNode nodeA : a) {
			j = 1;
			for (ASTNode nodeB : b) {
				if (nodeA.getNodeType() == ASTNode.METHOD_DECLARATION
						&& nodeB.getNodeType() == ASTNode.METHOD_DECLARATION) {
					int output = test.getLCSSimilarityForASTTree(nodeA, nodeB);
					assertEquals(0, output);
					++i;
					++j;

				}
			}
		}

	}

	@Test

	/**
	 * 
	 * This test case is used for testing the buildCostMatrix method
	 * 
	 * which builds a costMatrix to find the lengths of longest common
	 * 
	 * subtree.
	 *
	 * 
	 * 
	 * @throws IOException
	 * 
	 */

	public void test_buildCostMatrix_identical_trees() throws IOException {

		ASTLongestCommonSubSequence test = new ASTLongestCommonSubSequence();

		// lcsTests.initialize("/testFiles/file_1.java","/testFiles/file_2.java");
		List<ASTNode> tree1List;
		List<ASTNode> tree2List;
		int costMatrix[][];

		File file1 = new File(getClass().getResource("/testFiles/SimpleFile1.java").getFile());

		ASTParser parser1 = ASTParser.newParser(AST.JLS8);

		parser1.setSource(readFileToString(file1.getAbsolutePath()).toCharArray());

		File file2 = new File(getClass().getResource("/testFiles/SimpleFile2.java").getFile());

		ASTParser parser2 = ASTParser.newParser(AST.JLS8);

		parser2.setSource(readFileToString(file2.getAbsolutePath()).toCharArray());

		parser2.setKind(ASTParser.K_COMPILATION_UNIT);

		ASTVisitor28 astVA = new ASTVisitor28();

		ASTNode r1 = parser1.createAST(null);

		CompilationUnit cuA = (CompilationUnit) r1;

		cuA.accept(astVA);

		tree1List = astVA.getAstNodes();

		ASTVisitor28 astVB = new ASTVisitor28();

		ASTNode r2 = parser2.createAST(null);

		CompilationUnit cuB = (CompilationUnit) r2;

		cuB.accept(astVB);

		tree2List = astVB.getAstNodes();

		int resultMatrix[][] = {

				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },

				{ 0, 1, 1, 1, 1, 1, 1, 1, 1 },

				{ 0, 1, 2, 2, 2, 2, 2, 2, 2 },

				{ 0, 1, 2, 3, 3, 3, 3, 3, 3 },

				{ 0, 1, 2, 3, 4, 4, 4, 4, 4 },

				{ 0, 1, 2, 3, 4, 5, 5, 5, 5 },

				{ 0, 1, 2, 3, 4, 5, 6, 6, 6 },

				{ 0, 1, 2, 3, 4, 5, 6, 7, 7 },

				{ 0, 1, 2, 3, 4, 5, 6, 7, 8 }

		};

		Boolean similarFlag = true;

		int i = 1, j;

		for (ASTNode nodeA : tree1List) {

			j = 1;

			for (ASTNode nodeB : tree2List) {

				if (nodeA.getNodeType() == ASTNode.METHOD_DECLARATION

						&& nodeB.getNodeType() == ASTNode.METHOD_DECLARATION) {

					ASTVisitor28 astVisitor1 = new ASTVisitor28();

					nodeA.accept(astVisitor1);

					ASTVisitor28 astVisitor2 = new ASTVisitor28();

					nodeB.accept(astVisitor2);

					// get the preorder traversal of trees

					List<ASTNode> subtree1 = astVisitor1.getAstNodes();

					List<ASTNode> subtree2 = astVisitor2.getAstNodes();

					int subtree1Size = subtree1.size();

					int subtree2Size = subtree2.size();

					costMatrix = new int[subtree1Size][subtree2Size];

					costMatrix = test.buildCostMatrix(subtree1Size, subtree2Size, costMatrix, subtree1, subtree2);
					;

					// assertEquals(96,output);

					for (int m = 0; m < subtree1Size; m++)

					{

						System.out.println();

						for (int n = 0; n < subtree2Size; n++)

						{

							if (costMatrix[m][n] != resultMatrix[m][n])

								similarFlag = false;

						}

					}

					++i;

					++j;

				}

			}

		}

		assertEquals(similarFlag, true);

	}

}
