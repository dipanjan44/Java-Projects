import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.StructuralPropertyDescriptor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains few of the supporting functions for the generated AST's
 *
 * @author Dipanjan
 */
public class ASTUtils {
	/**
	 * This function extracts the child nodes for a given node
	 * 
	 * @param astNode
	 *            - a node from the generated AST
	 * @return - list of children of the given node
	 */
	public static List<ASTNode> getChildren(ASTNode astNode) {
		List<ASTNode> children = new ArrayList<>();
		if (astNode != null) {
			List list = astNode.structuralPropertiesForType();
			for (int i = 0; i < list.size(); i++) {
				StructuralPropertyDescriptor curr = (StructuralPropertyDescriptor) list.get(i);
				Object child = astNode.getStructuralProperty(curr);
				if (child instanceof List) {
					for (Object n : ((List) child).toArray()) {
						children.add((ASTNode) n);
					}
				} 
			}
		}
		return children;
	}

	/**
	 * This function generates the AST nodes from a given code block
	 * 
	 * @param codeBlock
	 *            - a block of code from a file
	 * @return - the AST nodes from the codeblock
	 */
	private static ASTNode getASTNode(String codeBlock) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(codeBlock.toCharArray());
		return parser.createAST(null);
	}

	/**
	 * This function generates the AST nodes from a given file
	 * 
	 * @param file
	 * @return
	 */
	public static ASTNode getASTNode(File file) {
		StringBuilder fileContent = new StringBuilder();
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader br = new BufferedReader(fileReader);
			String nextLine = br.readLine();
			while (nextLine != null) {
				fileContent.append(nextLine);
				nextLine = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getASTNode(fileContent.toString());
	}
}
