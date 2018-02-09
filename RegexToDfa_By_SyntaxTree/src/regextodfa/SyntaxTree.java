package regextodfa;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author @ALIREZA_KAY
 */
public class SyntaxTree {

    private String regex;
    private BinaryTree bt;
    private Node root; //the head of raw syntax tree
    private int numOfLeafs;
    private Set<Integer> followPos[];

    public SyntaxTree(String regex) {
        this.regex = regex;
        bt = new BinaryTree();
        
        /**
         * generates the binary tree of the syntax tree
         */
        root = bt.generateTree(regex);
        numOfLeafs = bt.getNumberOfLeafs();
        followPos = new Set[numOfLeafs];
        for (int i = 0; i < numOfLeafs; i++) {
            followPos[i] = new HashSet<>();
        }
        //bt.printInorder(root);
        generateNullable(root);
        generateFirstposLastPos(root);
        generateFollowPos(root);
    }

    private void generateNullable(Node node) {
        if (node == null) {
            return;
        }
        if (!(node instanceof LeafNode)) {
            Node left = node.getLeft();
            Node right = node.getRight();
            generateNullable(left);
            generateNullable(right);
            switch (node.getSymbol()) {
                case "|":
                    node.setNullable(left.isNullable() | right.isNullable());
                    break;
                case "&":
                    node.setNullable(left.isNullable() & right.isNullable());
                    break;
                case "*":
                    node.setNullable(true);
                    break;
            }
        }
    }

    private void generateFirstposLastPos(Node node) {
        if (node == null) {
            return;
        }
        if (node instanceof LeafNode) {
            LeafNode lnode = (LeafNode) node;
            node.addToFirstPos(lnode.getNum());
            node.addToLastPos(lnode.getNum());
        } else {
            Node left = node.getLeft();
            Node right = node.getRight();
            generateFirstposLastPos(left);
            generateFirstposLastPos(right);
            switch (node.getSymbol()) {
                case "|":
                    node.addAllToFirstPos(left.getFirstPos());
                    node.addAllToFirstPos(right.getFirstPos());
                    //
                    node.addAllToLastPos(left.getLastPos());
                    node.addAllToLastPos(right.getLastPos());
                    break;
                case "&":
                    if (left.isNullable()) {
                        node.addAllToFirstPos(left.getFirstPos());
                        node.addAllToFirstPos(right.getFirstPos());
                    } else {
                        node.addAllToFirstPos(left.getFirstPos());
                    }
                    //
                    if (right.isNullable()) {
                        node.addAllToLastPos(left.getLastPos());
                        node.addAllToLastPos(right.getLastPos());
                    } else {
                        node.addAllToLastPos(right.getLastPos());
                    }
                    break;
                case "*":
                    node.addAllToFirstPos(left.getFirstPos());
                    node.addAllToLastPos(left.getLastPos());
                    break;
            }
        }
    }

    private void generateFollowPos(Node node) {
        if (node == null) {
            return;
        }
        Node left = node.getLeft();
        Node right = node.getRight();
        switch (node.getSymbol()) {
            case "&":
                Object lastpos_c1[] = left.getLastPos().toArray();
                Set<Integer> firstpos_c2 = right.getFirstPos();
                for (int i = 0; i < lastpos_c1.length; i++) {
                    followPos[(Integer) lastpos_c1[i] - 1].addAll(firstpos_c2);
                }
                break;
            case "*":
                Object lastpos_n[] = node.getLastPos().toArray();
                Set<Integer> firstpos_n = node.getFirstPos();
                for (int i = 0; i < lastpos_n.length; i++) {
                    followPos[(Integer) lastpos_n[i] - 1].addAll(firstpos_n);
                }
                break;
        }
        generateFollowPos(node.getLeft());
        generateFollowPos(node.getRight());

    }

    public void show(Node node) {
        if (node == null) {
            return;
        }
        show(node.getLeft());
        Object s[] = node.getLastPos().toArray();

        show(node.getRight());
    }

    public void showFollowPos() {
        for (int i = 0; i < followPos.length; i++) {
            Object s[] = followPos[i].toArray();
        }
    }

    public Set<Integer>[] getFollowPos() {
        return followPos;
    }

    public Node getRoot() {
        return this.root;
    }
}
