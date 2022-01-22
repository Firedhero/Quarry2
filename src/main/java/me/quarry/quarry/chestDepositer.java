package me.quarry.quarry;

import org.bukkit.Location;
import org.bukkit.Material;

public class chestDepositer implements Runnable {
    Location chestLocation;
    minerData context;
    chestDepositer(minerData context){
        this.context=context;
        chestLocation=context.chestLocation;
//        if(chestLocation!=null){
//            Thread thread=new Thread();
//            thread.start();
//        }
    }

    public void setChestLocation(Location chestLocation) {
        this.chestLocation = chestLocation;
    }

    @Override
    public void run() {
//        TODO add loop, add notify method that alerts thread when items removed from chest otherwise it permanently sleeps saving resources
//        deposit(context.savedItems);
    }

    public void attemptDeposit(Material savedBlock){

        context.savedItems.decrementFromItemHashMap(savedBlock)


        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        deposit();
    }
}
