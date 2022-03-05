// Manfred Le
// COP4520: Concepts of Parrallel and Distributed Processing
// Outputs if every person has entered the maze and the time it took
// Optional Flag to change the default number of threads/people that will be used

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class Cupcake{
    public final static boolean DEBUG = false;
    AtomicBoolean cupcake = new AtomicBoolean(true);
    private static boolean callGame = false;
    public final int leader = 0;
    private static int numThreads;
    public static int lastEater;
    public ArrayList<Guest> guestID;
    public static void main(String[] args) {
        Cupcake main = new Cupcake();
        Random rand = new Random();
        long startTime, endTime, executionTime;
        numThreads = 100;
        boolean everyoneEaten = true;
        
        
        main.guestID = new ArrayList<Guest>();
        // Flag if you want to test with the different number of people.
        if(args.length == 1){
            main.setNumOfPeople(Integer.parseInt(args[0]));
        }

        
        // Start the clock and add each unique person
        startTime = System.nanoTime();
        // Each unique person will be stored in a list
        for (int i = 0; i < numThreads; i++){
            Guest guest = new Guest(i,main);
            main.guestID.add(guest);
        }

        // Main loop that will go until one thread calls the game off
        while(!callGame){
            // Random person assigned to enter the maze;
            int EntryGuest =  rand.nextInt(numThreads);
            Thread th = new Thread(main.guestID.get(EntryGuest));
            th.start();
            try{
                // wait until their finish
                th.join();
            }
            catch(Exception e){
                System.out.println(e);
            }
        }

        // Stop the clock
        endTime = System.nanoTime();
        executionTime = (endTime - startTime)/1000000;

        // Check if everyone entered the maze/ had cupcake
        for(Guest i : main.guestID){
            if (i.eatenCupcake() == false)
                everyoneEaten = false;
        }
        
        // If everyone did then yay else we all are dead.
        if(everyoneEaten)
            System.out.println("The Minotaur is please that everyone has eaten");
        else
            System.out.println("The Minotaur is angry that someone didn't get one");
        System.out.println("Runtime: "+executionTime+" ms");
    }
    
    // Debugging functions and helper values
    public int truthNum(){
        int count = 0;
        for (Guest guest : guestID) {
            if(guest.eatenCupcake()==true){
                count++;
            }
        }
        return count;
    }

    public void eatCupcake(){
        cupcake.set(false);
    }

    public void setLastEater(int i){
        lastEater = i;
    }

    public int getLastEater(){
        return lastEater;
    }
    
    public void newCupcake(){
        cupcake.set(true);
    }

    public int getNumOfPeople(){
        return numThreads;
    }

    public void setNumOfPeople(int i){
        numThreads = i;
    }

    public void callGame(){
        callGame = true;
    }

    public boolean isDebug(){
        return DEBUG;
    }

}

class Guest implements Runnable{
    private int guestNum;
    private int count;
    private boolean eaten;
    private Cupcake host;

    // Initialize the guest by their number.
    Guest (int guestNum, Cupcake main){
        this.host = main;
        this.guestNum = guestNum;
        count = 0;
        eaten = false;
    }

    @Override
    public void run() {
        // If they haven't eaten and they got a cupcake then eat
        if (host.cupcake.get() && !eaten){
                eaten = true;
                host.eatCupcake();
                host.setLastEater(guestNum);
        }
        else{
            // If you are the leader and there isn't a cupcake count 1 and request a new one
            if (!host.cupcake.get() && guestNum == host.leader){
                count++;
                host.newCupcake();
            }
        }
        // Since leader is the only one counting when he reaches 100 he will call the game.
        if (count == host.getNumOfPeople()){
            host.callGame();
        }
    }

    // See if the person has eaten a cupcake.
    public boolean eatenCupcake(){
        return eaten;
    }
}
