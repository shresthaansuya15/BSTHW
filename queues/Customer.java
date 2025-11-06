package queues;

public class Customer {
    private int arrivalTime;
    private int serviceTime;
    private int finishTime;
    public Customer(int arrivalTime, int serviceTime){
        this.arrivalTime=arrivalTime;
        this.serviceTime=serviceTime;
    }
    public int getArrivalTime(){return arrivalTime;}
    public int getServiceTime(){return serviceTime;}
    public void setFinishTime(int time){finishTime = time;}
    public int getFinishTime(){return finishTime;}
    public int getWaitTime(){
        return (finishTime - arrivalTime - serviceTime);
    }
}
