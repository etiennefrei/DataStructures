package binarytrees;

public class BinarySearchTree {
    final Node root = new Node(Integer.MIN_VALUE, null, null);
    public BinarySearchTree() {

    }

    public BinarySearchTree(int[] list) {
        root.right = buildTree(list, 0, list.length-1);
    }

    boolean exists(int key){
        Node r = root.right;
        while (r != null) {
            if (r.key == key) return true;
            r = key > r.key ? r.right : r.left;
        }
        return false;
    }

    void show(){
        traverse(root, 0);
    }

    private Node buildTree(int[] a, int start, int end){
        if (start > end) return null;
        int mid = (end + start)/2;
        Node parent = new Node(a[mid], null, null);
        parent.left = buildTree(a, start, mid-1);
        parent.right = buildTree(a, mid+1, end);
        return parent;
    }

    void traverse(Node root, int level){
        if (root != null){
            traverse(root.right, level + 1);
            for (int i = 0; i < level; ++i)
                System.out.print("    ");
            System.out.println(root.key);
            traverse(root.left, level + 1);
        }
    }

    private SearchResult find(int key){
        SearchResult searchResult = new SearchResult(root, root.right, false);
        while (searchResult.node != null) {
            if (searchResult.node.key == key) return searchResult;
            searchResult.parent = searchResult.node;
            if (key > searchResult.node.key){
                searchResult.node = searchResult.node.right;
                searchResult.isLeftChild=false;
            } else {
                searchResult.node = searchResult.node.left;
                searchResult.isLeftChild=true;
            }
        }
        return searchResult;
    }

    public boolean insert(int key){
        SearchResult searchResult = find(key);
        if (searchResult.node != null) return false;
        if (searchResult.isLeftChild){
            searchResult.parent.left = new Node(key, null, null);
        } else {
            searchResult.parent.right = new Node(key, null, null);
        }
        return true;
    }

    public boolean remove(int key){
        SearchResult searchResult = find(key);
        if (searchResult.node == null) return false;
        if (searchResult.node.left == null && searchResult.node.right == null) {
            if (searchResult.isLeftChild){
                searchResult.parent.left = null;
            } else {
                searchResult.parent.right = null;
            }
        } else if (searchResult.node.left == null ^ searchResult.node.right == null) {
            if (searchResult.isLeftChild){
                searchResult.parent.left = (searchResult.node.left == null) ? searchResult.node.right : searchResult.node.left;
            } else {
                searchResult.parent.right = (searchResult.node.right == null) ? searchResult.node.left : searchResult.node.right;
            }
        } else {
            Node r = searchResult.node.left;
            while(r.right != null)
                r = r.right;
            remove(r.key);
            searchResult.node.key = r.key;
        }

        if (searchResult.isLeftChild){
            searchResult.parent.left = searchResult.node.left;
        } else {
            searchResult.parent.right = searchResult.node.right;
        }
        return true;
    }

    private static class Node {
        int key;
        Node left;
        Node right;

        public Node(int key, Node left, Node right) {
            this.key = key;
            this.left = left;
            this.right = right;
        }
    }

    private static class SearchResult {
        BinarySearchTree.Node parent;
        BinarySearchTree.Node node;
        boolean isLeftChild;

        public SearchResult(BinarySearchTree.Node parent, BinarySearchTree.Node node, boolean isLeftChild) {
            this.parent = parent;
            this.node = node;
            this.isLeftChild = isLeftChild;
        }
    }
}
