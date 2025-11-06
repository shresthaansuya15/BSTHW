package queues;

import java.util.Random;
public class CustomGenerator {
    private int minIAT;
    private int maxIAT;
    private int minST;
    private int maxST;

    protected int currTime=0;

    Random rand = new Random();

    public CustomGenerator(int minIAT, int maxIAT, int minST, int maxST){
        this.maxIAT= maxIAT;
        this.minIAT = minIAT;
        this.minST = minST;
        this.maxST=maxST;
    }
    public void reset(){
        currTime=0;
    }

    public Customer nextCustomer(){
        int IAT; // next interarrival time
        int ST; //next service time
        IAT = minIAT + rand.nextInt(maxIAT-minIAT+1);
        ST = minST + rand.nextInt(maxST - minST +1);
        currTime = currTime + IAT; // updates the current time to the arrival time of the next customer
        Customer next = new Customer(currTime, ST);
        return next;
    }



}

