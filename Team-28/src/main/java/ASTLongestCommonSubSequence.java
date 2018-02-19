import org.eclipse.jdt.core.dom.ASTNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Monisha Karise
 **/

/** ASTSIMLCS contains algorithms which find similarity measures between ASTs **/

public class ASTLongestCommonSubSequence {

    private Map<ASTNode, ArrayList<ASTNode>> nodeArrayListHashMap;

    private static final int THRESHOLD = 40;

    public ASTLongestCommonSubSequence() {
        nodeArrayListHashMap = new HashMap<>();
    }

    /**
     * This method finds out the similarity score between two code submissions
     * represented as ASTs
     *
     * @param rootTreeOne is the subtree representing one code submission
     * @param rootTreeTwo is the subtree representing another submission
     * @return integer which gives the similarity score between r1 and r2
     *
     */

    public int getLCSSimilarityForASTTree(ASTNode rootTreeOne, ASTNode rootTreeTwo) {
        ASTVisitor28 astVA = new ASTVisitor28();
        rootTreeOne.accept(astVA);
        List<ASTNode> astNodesA = astVA.getAstNodes();
        ASTVisitor28 astVB = new ASTVisitor28();
        rootTreeTwo.accept(astVB);
        List<ASTNode> astNodesB = astVB.getAstNodes();

        int totalScore = 0;
        int i = 0;
        for (ASTNode nodeA : astNodesA) {
            for (ASTNode nodeB : astNodesB) {
                if (nodeA.getNodeType() == ASTNode.METHOD_DECLARATION
                        && nodeB.getNodeType() == ASTNode.METHOD_DECLARATION) {
                    int score = getLCSSimilarityForMethod(nodeA, nodeB);
                    if (score > THRESHOLD) {
                        i++;
                        totalScore += score;
                    }
                }
            }
        }
        
        if(i==0)
        	return 0;
        else
        return totalScore / i;
    }

    /**
     * This is the algorithm for finding the similarity percentage between
     * two ASTs using  longest common subsequence algorithm
     *
     * @param r1 is the root node of the AST representing one method
     * @param r2 is the root node of the AST representing another method
     * @return similarity percentage between two ASTs using
     *          longest common subsequence algorithm
     *
     */
    public int getLCSSimilarityForMethod(ASTNode r1, ASTNode r2) {
        ASTVisitor28 astVisitor1 = new ASTVisitor28();
        r1.accept(astVisitor1);
        ASTVisitor28 astVisitor2 = new ASTVisitor28();
        r2.accept(astVisitor2);

        //get the preorder traversal of trees
        List<ASTNode> subtree1 = astVisitor1.getAstNodes();
        List<ASTNode> subtree2 = astVisitor2.getAstNodes();
        //Map stores alignment between two sequences of nodes
        Map<ASTNode, ASTNode> tmp = new HashMap<>();

        //sizes of lists subtree1 and subtree2
        int subtree1Size = subtree1.size();
        int subtree2Size = subtree2.size();

        //declaring cost matrix
        int c[][] = new int[subtree1Size][subtree2Size];

        int i, j;
        //initializing first row and column of matrix to 0
        for (i = 0; i < subtree1Size; i++) {
            c[i][0] = 0;
        }

        for (j = 0; j < subtree2Size; j++) {
            c[0][j] = 0;
        }

        //build cost matrix
        c = buildCostMatrix(subtree1Size, subtree2Size, c, subtree1, subtree2);

        //build a map storing pairs of similar nodes
        tmp = buildMapForSimilarNodes(subtree1Size, subtree2Size, subtree1, subtree2, tmp, c);

        int result = 0;
        //alignment stores final alignment between two trees
        Map<ASTNode, ASTNode> alignment = new HashMap<>();

        for (ASTNode v : subtree1) {

            //stores final alignment between two trees
            result = storeAlignmentBetweenNodes(tmp, v, alignment, r1, result);

        }
        //calculate percentage of score
        result = (result * 100) / Math.max(subtree1Size, subtree2Size);
        return result;
    }

    /**
     * This method builds a cost matrix for implementing the longest common subsequence method
     * between two AST Trees subtree1 and subtree2 using dynamic programming
     * @param subtree1 is the first subtree
     * @param subtree2 is the second subtree
     * @param c which is an empty matrix
     * @param subtree1Size is the size of subtree1
     * @param subtree2Size is the size of subtree2
     * @return c[][] which stores the lengths of the every possible sequence between subtree1 and subtree2
     *
     */
    public int[][] buildCostMatrix(int subtree1Size, int subtree2Size, int[][] c, List<ASTNode> subtree1, List<ASTNode> subtree2) {

        for (int i = 1; i < subtree1Size; i++) {
            for (int j = 1; j < subtree2Size; j++) {

                ASTNode v1 = subtree1.get(i - 1);
                ASTNode v2 = subtree2.get(j - 1);
                //if nodes are similar 
                if (v1.getNodeType() == v2.getNodeType() && (v1.getParent().getNodeType() == v2.getParent().getNodeType())
                        || v1.getNodeType() == ASTNode.BLOCK) {
                    c[i][j] = c[i - 1][j - 1] + 1;

                } else {
                    c[i][j] = Math.max(c[i][j - 1], c[i - 1][j]);
                }
            }
        }

        return c;
    }

    /**
     * This method builds a map of similar nosed
     * @param subtree1 is the first subtree
     * @param subtree2 is the second subtree
     * @param subtree1Size is the size of subtree1
     * @param subtree2Size is the size of subtree2
     * @param c which has the lengths of the every possible sequence between subtree1 and subtree2
     * @return tmp stores similar pairs of nodes
     *
     */
    public Map<ASTNode, ASTNode> buildMapForSimilarNodes(int subtree1Size, int subtree2Size, List<ASTNode> subtree1, List<ASTNode> subtree2, Map<ASTNode, ASTNode> tmp, int[][] c) {
        int i = subtree1Size - 1;
        int j = subtree2Size - 1;
        while (i > 0 && j > 0) {
            ASTNode v3 = subtree1.get(i - 1);
            ASTNode v4 = subtree2.get(j - 1);
            //if nodes are similar, store in map
            if (v3.getNodeType() == v4.getNodeType() &&
                    (v3.getParent().getNodeType() == v4.getParent().getNodeType())
                    || v3.getNodeType() == ASTNode.BLOCK) {
                tmp.put(v3, v4);
                i--;
                j--;
            } else if (c[i][j - 1] > c[i - 1][j]) {
                j = j - 1;
            } else {
                i = i - 1;
            }
        }

        return tmp;
    }

    /**
     * This method finds out the final alignment between trees r1 and r2 
     * @param tmp stores similar pairs of nodes
     * @param v is an ASTNode belonging to subtree1
     * @param r1 is the root node of the AST representing one method
     * @param alignment stores the final set of similar node pairs
     * @param result stores the length of the longest common subtree
     * @return length of the longest common subsequence 
     *
     */
    public int storeAlignmentBetweenNodes(Map<ASTNode, ASTNode> tmp, ASTNode v, Map<ASTNode, ASTNode> alignment, ASTNode r1, int result) {

        ASTNode w;
        if (tmp.containsKey(v)) {
            w = tmp.get(v);
            //If v is root node , store (v,w) in alignment
            if (v == r1) {
                alignment.put(v, w);
            } else {
                ASTNode p1 = v.getParent();
                ASTNode p2 = w.getParent();

                if (alignment.containsKey(p1)) {
                    //store (v,w) in alignment only if their parents are also aligned
                    if (alignment.get(p1).equals(p2)) {
                        alignment.put(v, w);
                    } else if (v.getNodeType() == ASTNode.BLOCK) {

                        //for each child node v1 of v , check if w is it parent and then put (v,w) in alignment
                        for (ASTNode v1 : ASTUtils.getChildren(v)) {
                            if (tmp.containsKey(v1)) {
                                ASTNode w1 = tmp.get(v1);
                                if (w1.getParent() == w) {
                                    alignment.put(v, w);
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            result = calculateResult(result, v, w, alignment);


        }

        return result;
    }


    /**
     * This method calculates the length of the longest common subtree between two AST trees
     * @param result is used to store the length
     * @param v is an ASTNode belonging to subtree1
     * @param w is an ASTNode belonging to subtree2
     * @param alignment stores the final set of similar node pairs
     * @return result length of the longest common subtree between two AST trees 
     *
     */
    public int calculateResult(int result, ASTNode v, ASTNode w, Map<ASTNode, ASTNode> alignment) {
        if (alignment.containsKey(v)) {
            ArrayList<ASTNode> listOfNodes;
            if (nodeArrayListHashMap.containsKey(v)) {
                listOfNodes = nodeArrayListHashMap.get(v);
            } else {
                listOfNodes = new ArrayList<>(100);
            }
            listOfNodes.add(w);
            nodeArrayListHashMap.put(v, listOfNodes);
            result = result + 1;
        }

        return result;

    }

}