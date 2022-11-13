package me.quarry.quarry;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.loot.LootTable;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class chestMenu implements Listener {


    minerData minerChest;
    HashMap<Material, Integer>  clonedItems=new HashMap<>();
    chestMenu(minerData context){
        minerChest=context;
        makeButtons();
        initialChest();
    }

    Inventory quarry;
    LinkedList<Inventory> inventories=new LinkedList<>();
    LinkedList<Chest> chestList=new LinkedList<>();
    LinkedList<Inventory> chestInv=new LinkedList<>();
    ItemStack forwardButton;
    ItemMeta forwardMeta;
    ItemStack backButton;
    ItemMeta backMeta;

    public void makeButtons(){
        forwardButton = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1);
        forwardMeta = forwardButton.getItemMeta();
        forwardMeta.setDisplayName("Forward");
        forwardButton.setItemMeta(forwardMeta);

        backButton = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        backMeta = backButton.getItemMeta();
        backMeta.setDisplayName("Back");
        backButton.setItemMeta(backMeta);

    }


    public Chest makeChest(){
        Chest chest=new Chest() {
            @Override
            public Inventory getBlockInventory() {
                return null;
            }

            @Override
            public Inventory getInventory() {
                return null;
            }

            @Override
            public Inventory getSnapshotInventory() {
                return null;
            }

            @Override
            public String getCustomName() {
                return null;
            }

            @Override
            public void setCustomName(String name) {

            }

            @Override
            public PersistentDataContainer getPersistentDataContainer() {
                return null;
            }

            @Override
            public Block getBlock() {
                return null;
            }

            @Override
            public MaterialData getData() {
                return null;
            }

            @Override
            public BlockData getBlockData() {
                return null;
            }

            @Override
            public Material getType() {
                return null;
            }

            @Override
            public byte getLightLevel() {
                return 0;
            }

            @Override
            public World getWorld() {
                return null;
            }

            @Override
            public int getX() {
                return 0;
            }

            @Override
            public int getY() {
                return 0;
            }

            @Override
            public int getZ() {
                return 0;
            }

            @Override
            public Location getLocation() {
                return null;
            }

            @Override
            public Location getLocation(Location loc) {
                return null;
            }

            @Override
            public Chunk getChunk() {
                return null;
            }

            @Override
            public void setData(MaterialData data) {

            }

            @Override
            public void setBlockData(BlockData data) {

            }

            @Override
            public void setType(Material type) {

            }

            @Override
            public boolean update() {
                return false;
            }

            @Override
            public boolean update(boolean force) {
                return false;
            }

            @Override
            public boolean update(boolean force, boolean applyPhysics) {
                return false;
            }

            @Override
            public byte getRawData() {
                return 0;
            }

            @Override
            public void setRawData(byte data) {

            }

            @Override
            public boolean isPlaced() {
                return false;
            }

            @Override
            public void open() {

            }

            @Override
            public void close() {

            }

            @Override
            public boolean isLocked() {
                return false;
            }

            @Override
            public String getLock() {
                return null;
            }

            @Override
            public void setLock(String key) {

            }

            @Override
            public void setLootTable(LootTable table) {

            }

            @Override
            public LootTable getLootTable() {
                return null;
            }

            @Override
            public void setSeed(long seed) {

            }

            @Override
            public long getSeed() {
                return 0;
            }

            @Override
            public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {

            }

            @Override
            public List<MetadataValue> getMetadata(String metadataKey) {
                return null;
            }

            @Override
            public boolean hasMetadata(String metadataKey) {
                return false;
            }

            @Override
            public void removeMetadata(String metadataKey, Plugin owningPlugin) {

            }
        };
         return chest;
    }
    public void createChest(int index){
        chestList.add(makeChest());
        Inventory temp=Bukkit.createInventory(chestList.get(index), 54, ChatColor.GOLD + "Chest Of Holding");
        temp.setItem(53, forwardButton);
        temp.setItem(45, backButton);
        chestInv.add(temp);
    }
    public void initialChest(){
        chestList.add(makeChest());
        Inventory temp=Bukkit.createInventory(chestList.get(0), 54, ChatColor.GOLD + "Chest Of Holding");
        temp.setItem(53, forwardButton);
        temp.setItem(45, backButton);
        chestInv.add(temp);

    }
    public void createInventory(Player p){
        HashMap<Material, Integer> temp=minerChest.savedItems.getHashMap();
        clonedItems.putAll(temp);

        if(quarry==null) {
            quarry = Bukkit.createInventory(p, 54, ChatColor.GOLD + "Chest Of Holding");

        /*
        0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0
        45 0 0 0 0 0 0 0 53
         */
            makeButtons();
            quarry.setItem(53, forwardButton);
            quarry.setItem(45, backButton);
            inventories.add(quarry);
        }

        makeInventories(p);
        p.openInventory(inventories.get(0));

    }
    int position=0;
    public void updateInventories(Material type, int i) {
        BukkitRunnable runner = new BukkitRunnable() {
            @Override
            public void run() {
                boolean deposited=false;

                int index=0;
                while (!deposited) {
                    if (checkChestSpace(type, index)) {

                        deposited=true;
                    } else{
                        index++;
                        Bukkit.broadcastMessage("made new chest");
                        createChest(index);
                    }
                }


            }

        };runner.runTask(minerChest.context);

    }

    private void makeInventories(Player p) {
        //52 slots Available to fill with items
        //64 max stack size
        int index=0;
        int size=0;
        for(Map.Entry<Material, Integer> i:clonedItems.entrySet()){

//            size++;
            int count=i.getValue();
            for(int it=0;it<count;it++) {
                if (checkChestSpace(i.getKey(), index)) {
                    ItemStack itemStack = new ItemStack(Material.getMaterial(String.valueOf(i.getKey())), 1);
//                updateInventory(itemStack,iter);
                    inventories.get(index).addItem(itemStack);
//                    Bukkit.broadcastMessage(i.getKey().toString() + " " + (i.getValue() - 1));
                    clonedItems.put(i.getKey(), i.getValue() - 1);
                } else {
                    index++;
                    Inventory temp = Bukkit.createInventory(p, 54, ChatColor.GOLD + "Chest Of Holding");
                    Bukkit.broadcastMessage("Making new Inventory");
                    temp.setItem(53, forwardButton);
                    temp.setItem(45, backButton);
                    inventories.add(temp);
                }
            }
        }
    }

    private boolean checkChestSpace(Material material,int index) {

        int count=1;
        //------------------------------------------------
        //https://bukkit.org/threads/checking-if-a-chest-is-full.51617/
        //         Credit to bergerkiller
        //-------------------------------------------------
        boolean hasEmptySlot = false;
        for (ItemStack stack : chestInv.get(index).getContents()) {
            if (stack == null) {
                hasEmptySlot = true;
                break;
            }
        }
        //method b: if it contains room for any sort of item
        int foundcount = 0;
        ItemStack itemToAdd=new ItemStack(material,count);
        foundcount = itemToAdd.getAmount();
        for (ItemStack stack : chestInv.get(index).getContents()) {
            if (stack == null) foundcount -= itemToAdd.getMaxStackSize();
            else if(stack.getType() == itemToAdd.getType()) {
                if (stack.getDurability() == itemToAdd.getDurability()) {
                    foundcount -= itemToAdd.getMaxStackSize() - stack.getAmount();
                }
            }
        }

        boolean canContainitem = foundcount <= 0;
        //---------------------------end credit--------------------------------------------
        if(hasEmptySlot||canContainitem) {
            return true;
        }
        return false;
    }

    private int index=0;
    public void forward(Player p){
        position++;
        if(position>=this.inventories.size())
            position--;
        Bukkit.broadcastMessage(String.valueOf(inventories.size()));
        p.openInventory(this.inventories.get(position));
    }
    public void back(Player p){
        position--;
        if(position<=0)
            position=0;
        p.openInventory(this.inventories.get(position));
    }

    //TODO EVENT HANDLER DONT WORK
    Location chestLocation;



}
