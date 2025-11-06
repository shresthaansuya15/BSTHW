package queues;

public class ArrayBoundedQueue<T> implements QueueInterface<T> {
    private int DEFCAP =100;
    private T[] elements;
    private int numElements =0;
    private int front =0;
    private int rear;
    
    
    public ArrayBoundedQueue() {
        elements = (T[]) new Object[DEFCAP];
        rear= DEFCAP-1;
    }
    
    
    public T dequeue(){
        T temp = elements[front];
        if(front==DEFCAP-1){
            front=0;
            return temp;
        }
        front++;
        return temp;
    }
    public void enqueue(T item){
        if(rear==DEFCAP-1){
            rear=0;
            elements[rear]=item;
            return;
        }        
        rear++;
        elements[rear]=item;
        return;
    }
    public boolean isEmpty(){
        return numElements==0;
    }
    public boolean isFull(){
        return numElements==DEFCAP;
    }
    public Integer length(){
        return numElements;
    }
}
