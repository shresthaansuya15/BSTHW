import java.util.Iterator;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        // Test 1: Create a BST with integers using natural ordering
        System.out.println("=== Test 1: Basic Integer BST ===");
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        
        // Add elements
        System.out.println("Adding elements: 50, 30, 70, 20, 40, 60, 80");
        bst.add(50);
        bst.add(30);
        bst.add(70);
        bst.add(20);
        bst.add(40);
        bst.add(60);
        bst.add(80);
        
        // Test basic properties
        System.out.println("Size: " + bst.size());
        System.out.println("Is Empty: " + bst.isEmpty());
        System.out.println("Min: " + bst.min());
        System.out.println("Max: " + bst.max());
        System.out.println("Is Balanced: " + bst.isBalanced());
        System.out.println();
        
        // Test contains
        System.out.println("=== Test 2: Contains ===");
        System.out.println("Contains 40: " + bst.contains(40));
        System.out.println("Contains 100: " + bst.contains(100));
        System.out.println();
        
        // Test get
        System.out.println("=== Test 3: Get ===");
        System.out.println("Get 60: " + bst.get(60));
        System.out.println("Get 99: " + bst.get(99));
        System.out.println();
        
        // Test traversals
        System.out.println("=== Test 4: Traversals ===");
        
        System.out.print("Inorder (should be sorted): ");
    Iterator<Integer> inorder = bst.getIterator(BSTInterface.Traversal.InOrder);
        while(inorder.hasNext()){
            System.out.print(inorder.next() + " ");
        }
        System.out.println();
        
        System.out.print("Preorder: ");
    Iterator<Integer> preorder = bst.getIterator(BSTInterface.Traversal.PreOrder);
        while(preorder.hasNext()){
            System.out.print(preorder.next() + " ");
        }
        System.out.println();
        
        System.out.print("Postorder: ");
    Iterator<Integer> postorder = bst.getIterator(BSTInterface.Traversal.PostOrder);
        while(postorder.hasNext()){
            System.out.print(postorder.next() + " ");
        }
        System.out.println("\n");
        
        // Test remove
        System.out.println("=== Test 5: Remove ===");
        System.out.println("Removing 20 (leaf node): " + bst.remove(20));
        System.out.println("Size after removal: " + bst.size());
        
        System.out.println("Removing 30 (node with two children): " + bst.remove(30));
        System.out.println("Size after removal: " + bst.size());
        
        System.out.println("Removing 100 (non-existent): " + bst.remove(100));
        System.out.println("Size after removal: " + bst.size());
        
        System.out.print("Inorder after removals: ");
        Iterator<Integer> afterRemove = bst.iterator();
        while(afterRemove.hasNext()){
            System.out.print(afterRemove.next() + " ");
        }
        System.out.println("\n");
    }
}
