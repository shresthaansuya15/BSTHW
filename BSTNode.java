public class BSTNode<T> {
    private T info;
    private BSTNode<T> left;
    private BSTNode<T> right;

    public BSTNode(T info){
        this.info=info;  // The node info
        left=null; // A link to the left child node
        right=null; // A link to the right child node
    }
    public void setInfo(T info){this.info = info;}
    public T getInfo(){return info;}
    public void setLeft(BSTNode<T> link){left = link;}
    public void setRight(BSTNode<T> link){right = link;}
    public BSTNode<T> getLeft(){return left;}
    public BSTNode<T> getRight(){return right;}

}
