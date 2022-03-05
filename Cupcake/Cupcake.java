



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
    public static int enterNumber;
    public ArrayList<Guest> guestID;
    public ArrayList<Thread> guestThread;
    public final Semaphore lock = new Semaphore(1);
    public static void main(String[] args) {
        Cupcake main = new Cupcake();
        Random rand = new Random();
        numThreads = 100;
        boolean everyoneEaten = true;
        main.guestID = new ArrayList<Guest>();
        main.guestThread = new ArrayList<Thread>();
        if(args.length == 1){
            main.setNumOfPeople(Integer.parseInt(args[0]));
        }

        for (int i = 0; i < numThreads; i++){
            Guest guest = new Guest(i,main);
            main.guestID.add(guest);
            Thread thread = new Thread(guest);
            main.guestThread.add(thread);
        }

        if (DEBUG){
            for (int i = 0; i < 2; i++){
                if (main.guestThread.get(0).getState() == Thread.State.NEW){
                    main.guestThread.get(0).start();
                    try{
                        main.guestThread.get(0).join();
                    }
                    catch(Exception e){
                        System.out.println(e);
                    }
                }
                else{
                    main.guestThread.get(0).run();
                    try{
                        main.guestThread.get(0).join();
                    }
                    catch(Exception e){
                        System.out.println(e);
                    }
                }
            }
        }
        else{
            while(!callGame){
                // send in this thread
                int EntryGuest =  rand.nextInt(numThreads);
                Thread th = new Thread(main.guestID.get(EntryGuest));
                th.start();
                try{
                    th.join();
                }
                catch(Exception e){
                    System.out.println(e);
                }
            }
    
            for(Guest i : main.guestID){
                if (i.eatenCupcake() == false)
                    everyoneEaten = false;
            }
        }
        
        if(everyoneEaten)
            System.out.println("The Minotaur is please that everyone has eaten");
        else
            System.out.println("The Minotaur is angry that someone didn't get one");

    }
    
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

    Guest (int guestNum, Cupcake main){
        this.host = main;
        this.guestNum = guestNum;
        count = 0;
        eaten = false;
    }

    @Override
    public void run() {
        // System.out.println("Entering as guestno "+guestNum);
        if (host.cupcake.get() && !eaten){
                eaten = true;
                host.eatCupcake();
                host.setLastEater(guestNum);
                // System.out.print(guestNum+" Took the cupcake");
        }
        else{
            if (!host.cupcake.get() && guestNum == host.leader){
                
                // System.out.println(host.getLastEater()+" Leader adding count ");
                count++;
                // if (count != host.truthNum()){
                //     System.out.println("ERROR");
                // }
                host.newCupcake();
                // System.out.println(host.cupcake.get());
            }
        }
        if (count == host.getNumOfPeople()){
            // System.out.println(guestNum+" is calling the game");
            host.callGame();
        }
    }

    public boolean eatenCupcake(){
        return eaten;
    }
}
