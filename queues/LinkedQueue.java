package queues;

public class LinkedQueue<T> implements QueueInterface<T> {
    LLNode<T> rear;
    LLNode<T> front;
    Integer numElements;

    public LinkedQueue(){
        rear=null;
        front=null;
        numElements=0;
    }
    public boolean isFull(){
        return false;
    }
    public boolean isEmpty(){
        return numElements==0;
    }
    public void enqueue(T item){
        LLNode<T> a = new LLNode<T>(item);
        if(isEmpty()){
            front = a;
            rear = a;
        } else {
            rear.setNext(a);
            rear = a;
        }
        numElements++;
    }
    public T dequeue(){
        if(isEmpty()){
            return null;
        }
        T value = front.getInfo();
        front = front.getNext();
        if(front == null){
            rear = null;
        }
        numElements--;
        return value;
    }
    public Integer length(){
        return numElements;
    }

}
