package queues;

public class LinkedGlassQueue<T> extends LinkedQueue<T> {
    public LinkedGlassQueue(){
        super();
    }    
    public T peekFront(){
        if(isEmpty()){
            return null;
        }
        return front.getInfo();
    }
    public T peekRear(){
        if(isEmpty()){
            return null;
        }
        return rear.getInfo();
    }
}
