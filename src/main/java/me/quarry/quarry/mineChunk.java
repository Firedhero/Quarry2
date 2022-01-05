package me.quarry.quarry;
import java.lang.Math;
import com.google.common.util.concurrent.AbstractScheduledService;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.graalvm.compiler.lir.alloc.lsra.LinearScan;

public class mineChunk extends Thread {
    Chunk breakChunk;
    ChunkSnapshot snap;
    Chunk clone;

    public Location getQuarryLocation() {
        return quarryLocation;
    }

    public void setQuarryLocation(Location quarryLocation) {
        this.quarryLocation = quarryLocation;
        String[] chara={"a"};

    }

    Location quarryLocation;
    Player player;
    Quarry quarry;
    Thread thread;


    public void setRunning(boolean running) {
        isRunning = running;
        if(running==true) {
            thread.interrupt();
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    private boolean isRunning;
    public void run() {
        isRunning=true;

//        BlockData broken;
        Block block;
        int[]leftOff=quarry.map.map.get(quarryLocation).getPos();
        int x,y,z;
        x=leftOff[0];
        y=leftOff[1];
        z=leftOff[2];


        for (; y > -63; y--) {
            for (; x < 16; x++) {
                for (; z < 16; z++) {
                    try {


                        if (quarry.map.map.get(quarryLocation) != null) {
                            while (!quarry.map.map.get(quarryLocation).isRunning) {
                                try {

                                    sleep(500);
                                } catch (InterruptedException e) {
    //                            e.printStackTrace();
                                }
                            }
                            Chunk chunk = breakChunk.getWorld().getChunkAt(quarryLocation);
                            quarry.changeBlock(chunk, x, y, z,quarryLocation);
                            quarry.map.map.get(quarryLocation).setPos(x,y,z);
                            try {
                                sleep(100);
                            } catch (InterruptedException e) {
    //                            e.printStackTrace();
                            }
                        }
                    }catch (Exception e){

                    }
//                    Bukkit.broadcastMessage("Finished Z");
                }
                z=0;//to reset the z to zero in chunk

                }
            x=0;//to reset the x in chunk
//            Bukkit.broadcastMessage("Finished Y");

        }
        quarry.map.map.get(quarryLocation).isRunning=false;
    }
    void changeBlock(Chunk chunk,int x,int y,int z){
        Block bloc=chunk.getBlock(x,y,z);
        bloc.setType(Material.AIR);
        bloc.getWorld().unloadChunk(chunk);
        bloc.getWorld().loadChunk(chunk);
    }
    mineChunk(Location quarryLoc, Chunk breakChun,Quarry q,int id){

        quarryLocation=quarryLoc;
        breakChunk=breakChun;
        quarry=q;
    }
    int id;
    mineChunk(Location quarryLoc, Chunk breakChun, Player user,Quarry q,int id){

        player=user;
        quarryLocation=quarryLoc;
        breakChunk=breakChun;
        quarry=q;
    }
    mineChunk(){

    }

    public void setThread(Thread thread) {
            this.thread=thread;
    }
}
