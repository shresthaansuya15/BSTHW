package LinkedListStack;

public class LinkedStack<T> implements StackInterface<T> {
    private LLNode<T> top;

    public LinkedStack(){
        this.top=null;
    }

    public void push(T element){
        LLNode<T> temp = new LLNode<T>(element);
        temp.setNext(top);
        top=temp;
    }
    public T pop(){
        if(isEmpty()){
            throw new RuntimeException("Cannot pop from an empty list");
        }
        LLNode<T> temp = top;
        top=top.getNext();
        return temp.getInfo();
    }
    public T top(){
        if(isEmpty()){
             throw new RuntimeException("Cannot get top from an empty list");
        }
        return top.getInfo();
    }

    public boolean isEmpty(){
        return top==null;
    }
    public boolean isFull(){
        return false;
    }
}