import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Vase {
    private static int numPeople;
    public ArrayList<Guest> guestID;
    private static boolean everyoneVisited = false;
    public static void main(String[] args) {
        numPeople = 100;
        Vase main = new Vase();
        Random rand = new Random();
        long startTime, endTime, executionTime;
        
        main.guestID = new ArrayList<Guest>();
        // Flag if you want to test with the different number of people.
        if(args.length == 1){
            main.setNumOfPeople(Integer.parseInt(args[0]));
        }

        for (int i = 0; i < numPeople; i++){
            Guest guest = new Guest(i);
            main.guestID.add(guest);
        }

        // Start the clock and add each unique person
        startTime = System.nanoTime();
        ExecutorService threadPool = Executors.newFixedThreadPool(1);
        while(!everyoneVisited){
            int EntryGuest =  rand.nextInt(numPeople);
            threadPool.execute(main.guestID.get(EntryGuest));
            main.everyoneVisited();
        }
        threadPool.shutdown();
        try {
            threadPool.awaitTermination(2147483647, TimeUnit.SECONDS);
        }catch(InterruptedException e){
            e.printStackTrace();
        }


        endTime = System.nanoTime();
        executionTime = (endTime - startTime)/1000000;
        System.out.println("Runtime: "+executionTime+" ms");
    }

    public void everyoneVisited(){
        int count = 0;
        for (Guest guest : guestID) {
            if(guest.visit()==true){
                count++;
            }
        }
        if (count == numPeople)
            everyoneVisited = true;
    }

    private void setNumOfPeople(int parseInt) {
        numPeople = parseInt;
    }
}

class Guest implements Runnable{
    private int guestNum;
    private boolean visited;

    public Guest(int i){
        guestNum = i;
        visited = false;
    }
    @Override
    public void run() {
        visited = true;
    }
    
    public boolean visit(){
        return visited;
    }
}
