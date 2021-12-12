package me.quarry.quarry;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

import java.io.Serializable;
import java.util.HashMap;

public class customData {
        public Location getQuarryLocation() {
            return quarryLocation;
        }
    HashMap<Location,Location> markedSpots;

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
        Location chestLocation;
        Location quarryLocation;
        Chunk chunk;

        public int getId() {
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


        public void setChestLocation(Location chestLocation) {

            this.chestLocation=chestLocation;
        }

    public void setMarkedSpots(HashMap<Location, Location> spots) {
            markedSpots=spots;
    }
}


