package me.quarry.quarry;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class mineCustom extends Thread{


    public mineCustom(Quarry quarry,Location quarryLoc) {
        quarryLocation=quarryLoc;
        markedSpots=quarry.custMap.map.get(quarryLocation).markedSpots;
        this.quarry=quarry;
    }

    public Location getQuarryLocation() {
        return quarryLocation;
    }

    public void setQuarryLocation(Location quarryLocation) {
        this.quarryLocation = quarryLocation;
    }
    Chunk breakChunk;
    Location quarryLocation;
    Player player;
    Quarry quarry;
    Thread thread;


    public void setRunning(boolean running) {
        isRunning = running;
        if(running) {
            thread.interrupt();
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    private boolean isRunning;
    HashMap<Location,Location> markedSpots;


    public void run() {
        isRunning=true;
//        need to find the positions to be mined the Z will be fixed with the initial marked Location

        Block block;
//        int[]leftOff=quarry.map.map.get(quarryLocation).getPos();
        int minX=Integer.MAX_VALUE;
        int maxX=Integer.MIN_VALUE;
        int y = 1;
        int maxZ=Integer.MIN_VALUE;
        int minZ=Integer.MAX_VALUE;

//        x=leftOff[0];
//        y=leftOff[1];
//        z=leftOff[2];
        //find smallest x in locations;
        for(Map.Entry<Location, Location> map:markedSpots.entrySet()){
            //TODO remove after finding way to get initial marked Y
            y=(int)map.getValue().getY();

            if(map.getValue().getBlock().getLocation().getBlockX()<minX){
                minX=(int)map.getValue().getX();
            }

            if(map.getValue().getBlock().getLocation().getBlockX()>maxX){
                maxX=(int)map.getValue().getX();
            }

            if(map.getValue().getBlock().getLocation().getBlockZ()<minZ){
                minZ=(int)map.getValue().getZ();
            }

            if(map.getValue().getBlock().getLocation().getBlockZ()>maxZ){
                maxZ=(int)map.getValue().getZ();
            }

        }


        //take highest y from list
        for (; y > 1; y--) {//no change
            for (int x=minX;x < maxX; x++) {//need to get the marked world positions
                for (int z=minZ; z < maxZ; z++) {
//                    Bukkit.broadcastMessage("X:"+x);
//                    Bukkit.broadcastMessage("Z:"+z);
//                    Bukkit.broadcastMessage("----------------");
                    try {


                        if (quarry.custMap.map.get(quarryLocation) != null) {
                            while (!quarry.custMap.map.get(quarryLocation).isRunning) {
                                try {

                                    sleep(500);
                                } catch (InterruptedException e) {
                                    //                            e.printStackTrace();
                                }
                            }

//
                            quarry.changeBlock(x, y, z);
                            try {

                                sleep(50);
                            } catch (InterruptedException e) {
                                //                            e.printStackTrace();
                            }
//                            quarry.map.map.get(quarryLocation).setPos(x,y,z);

                        }
                    }catch (Exception e){

                    }
//                    Bukkit.broadcastMessage("Finished Z");
                }
//                z=0;
//                Bukkit.broadcastMessage("Finished X");
//                    quarry.getServer().getWorld(breakChunk.getWorld().getName()).getChunkAt(quarryLocation).load();

            }
//            x=0;
//            Bukkit.broadcastMessage("Finished Y");

        }
//        quarry.map.map.get(quarryLocation).isRunning=false;
    }









    public void setThread(Thread thread) {
        this.thread=thread;
    }
}
