package me.quarry.quarry;

import org.bukkit.Chunk;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;
import org.bukkit.inventory.Inventory;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Random;

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




    public void setId(String newId) {
        id = newId;
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
    savedChestItems savedItems;
    final chestDepositer depositer;
    chestMenu menu;
    String id;
    minerData(){

            StringBuilder sb = new StringBuilder(10);
            String alphaBet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
            for (int i = 0; i < 10; i++) {
                int index = (int) (alphaBet.length() * Math.random());
                sb.append(alphaBet.charAt(index));
            }
            id = sb.toString();

        menu=new chestMenu(this);
        depositer=new chestDepositer(this);
        savedItems=new savedChestItems(this);

    }
    minerData(String defaultId){
        id = defaultId;

        menu=new chestMenu(this);
        depositer=new chestDepositer(this);
        savedItems=new savedChestItems(this);


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
