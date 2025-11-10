import java.util.Comparator;

import LinkedListStack.LinkedStack;
import queues.*;
import java.util.Iterator;

import org.w3c.dom.Node;

// 1. T extends Comparable<T> means:
//    - T is a generic type parameter that represents the type of data stored in the tree
//    - "extends Comparable<T>" is a constraint that requires T to implement the Comparable interface
//    - This ensures that objects of type T can be compared to each other using compareTo()
//    - This is necessary for a BST because we need to order elements (left < parent < right)
public class BinarySearchTree<T extends Comparable<T>> implements BSTInterface<T>{
    protected BSTNode<T> root;
    // 2. A Comparator is an interface from java.util that defines how to compare two objects
    //    - It has a compare(T o1, T o2) method that returns:
    //      * negative number if o1 < o2
    //      * zero if o1 == o2
    //      * positive number if o1 > o2
    //    - Comparators allow us to define custom ordering logic separate from the object's natural ordering
    protected Comparator<T> comp;

    protected boolean found;

    public BinarySearchTree()
    {
        root=null;
        
        // 3. When we set comp = new Comparator<T>() {...}, we are:
        //    - Creating an anonymous inner class that implements the Comparator interface
        //    - Defining the compare() method inline to use the object's natural ordering
        //    - This implementation simply delegates to the compareTo() method from Comparable
        //    - This provides a default comparison strategy when no custom Comparator is provided
        comp = new Comparator<T>(){
            public int compare(T element1, T element2){
                return ((Comparable<T>)element1).compareTo(element2);
            }
        };
    }
    
    // 4. Passing a Comparator into the constructor allows:
    //    - Custom ordering of elements in the tree (e.g., reverse order, case-insensitive strings)
    //    - Flexibility to sort objects without modifying their class or natural ordering
    //    - The ability to create multiple trees with different orderings for the same type
    //    - Example: You could sort Strings alphabetically in one tree and by length in another
    public BinarySearchTree(Comparator<T> comp){
        root = null;
        this.comp = comp;
    }

    public boolean isFull()
    {
        return false;
    }
    public boolean isEmpty(){
        return root==null;
    }

    //The minimum element in a BST is always going to be the leftmost element in the tree. 
    // This is a result of the binary search property
    // elements to the left are less than or equal to their ancestors
    public T min(){
        if (isEmpty()){
            return null;
        }
        BSTNode<T> node = root;
        while(node.getLeft()!=null){
            node=node.getLeft();
        }
        return node.getInfo();
    }

    // Maximum element is the rightmost element
    // result of binary search property
    // elements to the right are always greater than their ancestors
    public T max(){
        if (isEmpty()){
            return null;
        }
        BSTNode<T> node = root;
        while(node.getRight()!=null){
            node=node.getRight();
        }
        return node.getInfo();
    }

    public Integer size(){
        return recSize(root);
    }

    private int recSize(BSTNode<T> node) {
        if (node == null){
            return 0;
        }
        else {
            return 1 + recSize(node.getLeft()) + recSize(node.getRight());
        }
    }

    private int iterSize(BSTNode<T> node){
        int count=0;
        if(node!=null){
            LinkedStack<BSTNode<T>> nodeStack = new LinkedStack<BSTNode<T>>();
            BSTNode<T> currNode;
            nodeStack.push(node);
            while(!nodeStack.isEmpty()){
                currNode = nodeStack.pop();
                count++;
                if(currNode.getLeft() != null){
                    nodeStack.push(currNode.getLeft());
                }
                if(currNode.getRight() != null){
                    nodeStack.push(currNode.getRight());
                }
            }
        }
        return count;
    }
    
    public boolean contains (T Target){
        return recContains(Target, root);
    }

    private boolean recContains(T target, BSTNode<T> node){
        if(node==null){
            return false;
        }
        if(comp.compare(target, node.getInfo())<0){
            return recContains(target, node.getLeft());
        }
        if(comp.compare(target, node.getInfo())>0){
            return recContains(target, node.getRight());
        }
        return true;
    }

    public T get(T target){
        return recGet(target, root);
    }
    private T recGet(T target, BSTNode<T> node){
        if(node==null){
            return null;
        }
        if(comp.compare(target, node.getInfo())<0){
            return recGet(target, node.getLeft());
        }
        if(comp.compare(target, node.getInfo())>0){
            return recGet(target, node.getRight());
        }
        return node.getInfo();

    }

    private void preOrder(BSTNode<T> node, LinkedQueue<T> queue){
        if(node==null){
            return;
        }
        queue.enqueue(node.getInfo());
        preOrder(node.getLeft(), queue);
        preOrder(node.getRight(), queue);
    }

    private void postOrder(BSTNode<T> node, LinkedQueue<T> queue){
        if(node==null){
            return;
        }
        postOrder(node.getLeft(), queue);
        postOrder(node.getRight(), queue);
        queue.enqueue(node.getInfo());
    }

    private void inOrder(BSTNode<T> node, LinkedQueue<T> queue){
        if(node==null){
            return;
        }
        inOrder(node.getLeft(), queue);
        queue.enqueue(node.getInfo());
        inOrder(node.getRight(), queue);
    }
    
    public Iterator<T> getIterator(BSTInterface.Traversal orderType){
        //Goal: Create and returns an Iterator providing a traversal of a "snapshot" of the current tree in the order indicated by the argument
        //support preorder, postorder,inorder traversal
        final LinkedQueue<T> infoQueue = new LinkedQueue<T>();
        if(orderType==BSTInterface.Traversal.PreOrder){
            preOrder(root,infoQueue);
        }
        else if(orderType==BSTInterface.Traversal.InOrder){
            inOrder(root, infoQueue);
        }
        else if(orderType==BSTInterface.Traversal.PostOrder){
            postOrder(root, infoQueue);
        }
        return new Iterator<T>() {
            public boolean hasNext(){
                return !infoQueue.isEmpty();
            }
            public T next(){
                if (!hasNext()){
                    throw new IndexOutOfBoundsException("No more elements in iterator");
                }
                return infoQueue.dequeue();
            }
            public void remove(){
                throw new UnsupportedOperationException("Remove not supported");
            }
        };
    }

    public Iterator<T> iterator(){
        return getIterator(BSTInterface.Traversal.InOrder);
    }

    private BSTNode<T> recAdd(T element, BSTNode<T> node){
        if(node==null){
            return new BSTNode<T>(element);
        }
        int comparison = comp.compare(element, node.getInfo());
        if(comparison <= 0){
            node.setLeft(recAdd(element, node.getLeft()));
        }
        else {
            node.setRight(recAdd(element, node.getRight()));
        }
        return node;
    }

    public boolean add (T element){
        root = recAdd(element, root);
        return true;
    }

    private BSTNode<T> recRemove(T target, BSTNode<T> node){
        if(node == null){
            found = false;
        }
        else {
            int comparison = comp.compare(target, node.getInfo());
            if(comparison < 0){
                node.setLeft(recRemove(target, node.getLeft()));
            }
            else if(comparison > 0){
                node.setRight(recRemove(target, node.getRight()));
            }
            else {
                // Found the node to remove
                node = removeNode(node);
                found = true;
            }
        }
        return node;
    }

    private BSTNode<T> removeNode(BSTNode<T> node){
        T data;
        // Case 1: Node has no children (leaf node)
        if(node.getLeft() == null && node.getRight() == null){
            return null;
        }
        // Case 2: Node has only right child
        else if(node.getLeft() == null){
            return node.getRight();
        }
        // Case 3: Node has only left child
        else if(node.getRight() == null){
            return node.getLeft();
        }
        // Case 4: Node has two children
        else {
            // Replace with predecessor (max value in left subtree)
            data = getPredecessor(node.getLeft());
            node.setInfo(data);
            node.setLeft(recRemove(data, node.getLeft()));
            return node;
        }
    }

    private T getPredecessor(BSTNode<T> subtree){
        
        while(subtree.getRight() != null){
            subtree = subtree.getRight();
        }
        return subtree.getInfo();
    }

    public boolean remove (T target){
        
        root = recRemove(target, root);
        return found;
    }

    
    public int balance(){
        return recBalance(root);
    }

    private int recBalance(BSTNode<T> node){
        if(node == null){
            return 0;
        }
        int leftHeight = height(node.getLeft());
        int rightHeight = height(node.getRight());
        return leftHeight - rightHeight;
    }

    private int height(BSTNode<T> node){
        if(node == null){
            return 0;
        }
        return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
    }

    public boolean isBalanced(){
        return Math.abs(balance()) <= 1;
    }

    // TODO: implement maxDepth. 
    /**
    Returns the max root-to-leaf depth of the tree.
    Uses a recursive helper that recurs down to find
    the max depth.
    */
    public int maxDepth() 
    {
        return(maxDepth(root));    
    }

    private int maxDepth(BSTNode<T> node) 
    {
        if (node == null)
        {
            return 0;
        }

        int left = maxDepth(node.getLeft());
        int right = maxDepth(node.getRight());

        if (left > right)
        {
            return left + 1;
        }
        else
        {
            return right + 1;
        }
    }

    //TODO: 
    /**
     Returns the min value in a non-empty binary search tree.
    Uses a helper method that iterates to the left to find
    the min value.
    */
    public int minValue() 
    {
        if (root == null)
        {
            throw new IllegalStateException("Tree is empty");
        }

        return(minValue(root));
    }
   
    private int minValue(BSTNode<T> node)
    {
        if (node == null) 
        {
            throw new IllegalStateException("Tree is empty");
        }

        if (node.getLeft() == null)
        {
            return (Integer) node.getInfo();
        }
        else 
        {
            return minValue(node.getLeft());
        }
    }

    //TODO:

        /**
     Changes the tree by inserting a duplicate node
    on each nodes's .left.
    
    
    So the tree...
        2
    / \
    1   3

    Is changed to...
        2
        / \
        2   3
        /   /
    1   3
    /
    1

    Uses a recursive helper to recur over the tree
    and insert the duplicates.
    */
    public void doubleTree() 
    {
        doubleTree(root);       
    }

    private void doubleTree(BSTNode<T> node) 
    {
        if (node == null)
        {
            return;
        }

        doubleTree(node.getLeft());
        doubleTree(node.getRight());

        BSTNode<T> previousLeft = node.getLeft();
        BSTNode<T> duplicate = new BSTNode<>(node.getInfo());
        node.setLeft(duplicate);
        duplicate.setLeft(previousLeft);    
    }

    //TODO:
    /*
    Compares the receiver to another tree to
    see if they are structurally identical.
    */
    public boolean sameTree(BinarySearchTree<T> other) 
    {
        return( sameTree(root, other.root));
    }

    private boolean sameTree(BSTNode<T> a, BSTNode<T> b) 
    {
        if (a == null && b == null)
        {
            return true;
        }

        if (a == null || b == null)
        {
            return false;
        }

        if (!a.getInfo().equals(b.getInfo()))
        {
            return false;
        }

        return sameTree(a.getLeft(), b.getLeft()) && 
               sameTree(a.getRight(), b.getRight());                       
    }
}
