package me.quarry.quarry;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.Serializable;

public class minerData implements Serializable {
    public Location getQuarryLocation() {
        return quarryLocation;
    }

    public void setQuarryLocation(Location quarryLocation) {
        this.quarryLocation = quarryLocation;
    }

    public Chunk getChunk() {
        return chunk;
    }

    public void setChunk(Chunk chunk) {
        this.chunk = chunk;
    }

    public int getMinedBlocksNumber() {
        return minedBlocksNumber;
    }

    public void setMinedBlocksNumber(int minedBlocksNumber) {
        this.minedBlocksNumber = minedBlocksNumber;
    }

    public int[] getPos() {
        return pos;
    }

    public void setPos(int x,int y,int z) {
        this.pos=new int[]{x,y,z};
    }


    public int getintId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    boolean isRunning;
    int Id;
    int minedBlocksNumber;
    int[]pos;//for left off at
    Inventory chestInventory;
    Location chestLocation;
    Location quarryLocation;
    Chunk chunk;
    Quarry context;
    savedChestItems savedItems=new savedChestItems();
    final chestDepositer depositer=new chestDepositer(this);
//    final Thread depo=new Thread(depositer);
    minerData(){
        savedItems.setContext(this);
//        depo.start();
//        depositer.setThread(depo);
    }
    public void noDepo(){
//        depo.notify();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    Player player;

    public void setChestLocation(Location chestLocation) {

       this.chestLocation=chestLocation;
    }
    public Location getChestLocation() {

        return this.chestLocation;
    }

    public void setContext(Quarry quarry) {
        context=quarry;
    }
    public Quarry getContext(){
        return context;
    }
}
