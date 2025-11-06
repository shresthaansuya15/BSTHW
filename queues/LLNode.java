package queues;

public class LLNode<T> {
    private LLNode<T> next;
    private T info;

    public LLNode(T info){
        this.next = null;
        this.info = info;
    }

    public void setNext(LLNode<T> next){
        this.next = next;
    }
    public void setInfo(T info){
        this.info = info;
    }
    public LLNode<T> getNext(){
        return this.next;
    }
    public T getInfo(){  
        return this.info;
    }
}