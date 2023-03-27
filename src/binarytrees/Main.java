package binarytrees;

public class Main {
    public static void main(String[] args) {
        int[] treeData = {1,2,3,4,5,6,7,8,9,11,12,13,14,15};
        BinarySearchTree tree = new BinarySearchTree(treeData);
        tree.traverse(tree.root, 0);
        System.out.println(tree.exists(0));
        tree.insert(16);
        tree.traverse(tree.root, 0);
        tree.remove(5);
        tree.traverse(tree.root, 0);
    }
}