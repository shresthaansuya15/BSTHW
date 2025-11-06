package queues;

public class ArrayUnboundedQueue<T> implements QueueInterface<T> {
    private int DEFCAP =100;
    private T[] elements;
    private int origCap;
    private int numElements =0;
    private int front =0;
    private int rear;
    
    
    public ArrayUnboundedQueue() {
        elements = (T[]) new Object[DEFCAP];
        rear= DEFCAP-1;
        origCap = DEFCAP;
    }

    private void enlarge() {
        T[] larger = (T[]) new Object[elements.length + origCap];
        for (int i = 0; i < elements.length; i++) {
            larger[i] = elements[i];
        }
        elements = larger;
    }
    
    public T dequeue(){
        T temp = elements[front];
        if(front==DEFCAP-1){
            front=0;
            numElements--;
            return temp;
        }
        front++;
        numElements--;
        return temp;
    }

    public void enqueue(T item){
        if(numElements == elements.length){
            enlarge();
        }
        if(rear==DEFCAP-1){
            rear=0;
            elements[rear]=item;
            numElements++;
            return;
        }        
        rear++;
        elements[rear]=item;
        numElements++;
        return;
    }
    public boolean isEmpty(){
        return numElements==0;
    }
    public boolean isFull(){
        return numElements==elements.length;
    }
    public Integer length(){
        return numElements;
    }
}
