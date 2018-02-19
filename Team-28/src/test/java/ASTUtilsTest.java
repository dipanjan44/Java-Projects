import static org.junit.Assert.*;

import java.io.File;

import org.eclipse.jdt.core.dom.ASTNode;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test class to test few of the key features of the AST Parser. Though the
 * parser is the in-built one provided by eclipse still its better to validate
 * few of the key functionalities we are using
 * 
 * @author Dipanjan
 *
 */
public class ASTUtilsTest {

	@Test
	/**
	 * Test case to see that the AST nodes generated for identical files with
	 * different variables are having same nodetypes
	 */
	public void validateASTNodeType() {

		File file3 = new File(getClass().getResource("/testFiles/file_3.java").getFile());
		File file4 = new File(getClass().getResource("/testFiles/file_4.java").getFile());
		ASTUtils ast = new ASTUtils();
		assertEquals(ast.getASTNode(file3).getNodeType(), ast.getASTNode(file4).getNodeType());

	}

	/**
	 * check the children nodes of similar files are identical
	 */
	@Test
	public void checkChildren() {

		File file3 = new File(getClass().getResource("/testFiles/MyIntToBin.java").getFile());
		File file4 = new File(getClass().getResource("/testFiles/IntToBinCopy.java").getFile());
		ASTUtils ast = new ASTUtils();
		assertEquals(ast.getChildren(ast.getASTNode(file3)).size(), ast.getChildren(ast.getASTNode(file4)).size());
	}
	
	/**
	 * check the children nodes of dissimilar files produce are different
	 */
	@Test
	public void checkChildrennocontent() {

		File file3 = new File(getClass().getResource("/testFiles/nullFile.java").getFile());
		File file4 = new File(getClass().getResource("/testFiles/IntToBinCopy.java").getFile());
		ASTUtils ast = new ASTUtils();
		assertNotEquals(ast.getChildren(ast.getASTNode(file3)), ast.getChildren(ast.getASTNode(file4)));
	}
	
	/**
	 * check the children nodes of dissimilar files produce are different
	 */
	@Test
	public void childdrenfornullNodes() {
		
		ASTUtils ast = new ASTUtils();
		ASTNode astnode=null;
		int size=ast.getChildren(astnode).size();	
		assertEquals(0, size);
	}

	@Test
	/**
	 * Test case to see that the length AST nodes generated for identical files
	 * return the same value
	 * 
	 */
	public void validateASTNodeLength() {

		File file3 = new File(getClass().getResource("/testFiles/file_3.java").getFile());
		File file4 = new File(getClass().getResource("/testFiles/file_4.java").getFile());
		ASTUtils ast = new ASTUtils();
		assertEquals(ast.getASTNode(file3).getLength(), ast.getASTNode(file4).getLength());
	}

}
