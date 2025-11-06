package queues;

public class Simulation {
    final int MAXTIME = Integer.MAX_VALUE; // max value an int can have
    CustomGenerator custGen;

    float averageWaitTime = 0.0f;

    public Simulation(int maxIAT, int minIAT, int maxST, int minST){
        custGen = new CustomGenerator(minIAT, maxIAT, minST, maxST);
    }
    public float getAvgWaitTime(){
        return averageWaitTime;
    }
    public void simulate(int numQueues, int numCustomers){
        LinkedGlassQueue<Customer>[] queues = new LinkedGlassQueue[numQueues]; // An array of glass queues, if NQ=3 -> [null, null, null]

        Customer nextCust; // next customer from generator
        Customer cust; //temp holder for customers

        int totWaitTime=0; // Total wait time
        int custInCount=0; // count of customers started so far
        int custOutCount=0; // number of customers finished so far

        int nextArrTime; // next arrival time
        int nextDepTime; //next departure time
        int nextQueue; //index of queue for next departure

        int shortest; // index of shortest queue
        int shortestSize; // size of shortest queue
        Customer rearCust; // customer at the rear of shortest queue
        int finishTime; // calculated finish time for customer being enqueued 

        // instantiate the queues
        for (int i = 0; i < numQueues; i++){
            queues[i] = new LinkedGlassQueue<Customer>();
        }
        custGen.reset();
        nextCust = custGen.nextCustomer();

        while(custOutCount < numCustomers) { // while there are still more customers
            // get next arrival time
            if (custInCount != numCustomers)
                nextArrTime = nextCust.getArrivalTime();
            else
                nextArrTime = MAXTIME;
                
            // get next departure time and set nextQueue
            nextDepTime = MAXTIME;
            nextQueue = -1;
            for (int i = 0; i < numQueues; i++) {
                if (queues[i].length() != 0) {
                    cust = queues[i].peekFront();
                    if (cust.getFinishTime() < nextDepTime) {
                        nextDepTime = cust.getFinishTime();
                        nextQueue = i;
                    }
                }
            }
            
            if (nextArrTime < nextDepTime) {
                // handle customer arriving
                // determine shortest queue
                shortest = 0;
                shortestSize = queues[0].length();
                for (int i = 1; i < numQueues; i++) {
                    if (queues[i].length() < shortestSize) {
                        shortest = i;
                        shortestSize = queues[i].length();
                    }
                }
                // determine the finish time
                if (shortestSize == 0)
                    finishTime = nextCust.getArrivalTime() + nextCust.getServiceTime();
                else {
                    rearCust = queues[shortest].peekRear();
                    finishTime = rearCust.getFinishTime() + nextCust.getServiceTime();
                }
                // set finish time and enqueue customer
                nextCust.setFinishTime(finishTime);
                queues[shortest].enqueue(nextCust);
                custInCount = custInCount + 1;
                // if needed, get next customer to enqueue
                if (custInCount < numCustomers)
                    nextCust = custGen.nextCustomer();
            } else {
                cust = queues[nextQueue].dequeue();
                totWaitTime = totWaitTime + cust.getWaitTime();
                custOutCount = custOutCount + 1;
            }
        } // end while
        averageWaitTime = totWaitTime/(float)numCustomers;
    }
}
