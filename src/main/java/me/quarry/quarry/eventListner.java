package me.quarry.quarry;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import javax.swing.*;
import java.util.*;

public class eventListner implements Listener {
    private Location player;
    private  Player user;
    private Location quarryLocation;

    private final Quarry quarryThis;
    private Chunk breakChunk;
    private int id;
    minerData quarry;


    public eventListner(Quarry quarry) {
        quarryThis=quarry;
    }

    Location chestLocation;
    @EventHandler
    public void watchForChestPlacement(BlockPlaceEvent event) {
        Player p = event.getPlayer();
        ItemStack meta = p.getInventory().getItemInMainHand();
        if (meta.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Quarry Chest")) {
            chestLocation=event.getBlock().getLocation();
            setQuarryToChest(p);
        }
    }

    public void setQuarryToChest(Player p){
        Inventory inventoryOfQuarryLocation=Bukkit.createInventory(p,54,ChatColor.RED+"Menu Of Selection");
        ItemStack item;
        ItemMeta meta;
        int iter=0;
        for (Map.Entry<Location,minerData> map:quarryThis.map.map.entrySet()){
            if(iter%2==0)
                item=new ItemStack(Material.GREEN_STAINED_GLASS_PANE,1);
            else
                item=new ItemStack(Material.BLACK_STAINED_GLASS_PANE,1);
            meta=item.getItemMeta();
            meta.setDisplayName("Quarry");
            ArrayList<String>lore=new ArrayList<>();
            lore.add(String.valueOf(map.getKey().getBlockX()));
            lore.add(String.valueOf(map.getKey().getBlockY()));
            lore.add(String.valueOf(map.getKey().getBlockZ()));
            meta.setLore(lore);
            item.setItemMeta(meta);
            inventoryOfQuarryLocation.setItem(iter,item);
            iter++;
        }
        p.openInventory(inventoryOfQuarryLocation);
    }
    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        if(event.getView().getTitle().equalsIgnoreCase(ChatColor.RED+"Menu Of Selection")){
            if(event.getCurrentItem()==null)
                return;
            for (Map.Entry<Location,minerData> map:quarryThis.map.map.entrySet()){
                ArrayList<String>lore=new ArrayList<>();
                lore.add(String.valueOf(map.getKey().getBlockX()));
                lore.add(String.valueOf(map.getKey().getBlockY()));
                lore.add(String.valueOf(map.getKey().getBlockZ()));
                Bukkit.broadcastMessage("ASD");
                if (lore.toString().equals(event.getCurrentItem().getItemMeta().getLore().toString())){
                    map.getValue().setChestLocation(chestLocation);
                    Bukkit.broadcastMessage("DAS");
                    break;
                }
            }
            event.setCancelled(true);
        }
            for (Map.Entry<Location,minerData> map:quarryThis.map.map.entrySet()) {
                if(event.getView().getTitle().equalsIgnoreCase(ChatColor.GOLD+"Chest Of Holding ID "+map.getValue().id)) {
                    if(map.getValue().getChestLocation()!=null) {
                        if (event.getCurrentItem() == null)
                            return;
    //                    if (map.getValue().getChestLocation().equals(event.getInventory().getLocation())) {
                            if (event.getCurrentItem().getItemMeta().getDisplayName().equals("Forward")) {
                                event.setCancelled(true);
                                map.getValue().menu.forward((Player) event.getWhoClicked());

                            }
                            if (event.getCurrentItem().getItemMeta().getDisplayName().equals("Back")) {
                                event.setCancelled(true);
                                map.getValue().menu.back((Player) event.getWhoClicked());

                            }
                        }

                    }
                }
            }
    @EventHandler
    public void rightClick(PlayerInteractEvent event){
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            boolean isChest = false;
            Location temp = null;
            minerData miner = null;
            for (Map.Entry<Location, minerData> map : quarryThis.map.map.entrySet()) {
                if (map.getValue().chestLocation != null) {
                    temp = map.getValue().getChestLocation();

                    if (temp.equals((event.getClickedBlock()).getLocation())) {
                        isChest = true;
                        miner = map.getValue();
                    }
                }
            }

            if (isChest) {
                event.setCancelled(true);
                event.getPlayer().openInventory(miner.menu.chestInv.get(0));
            }
        }
    }
    @EventHandler
    public void work(BlockPlaceEvent event){
        user=event.getPlayer();
        ItemStack meta=user.getInventory().getItemInMainHand();
        if(meta.getItemMeta().getDisplayName().equals(ChatColor.RED+"Quarry")) {
            quarryLocation = event.getBlockPlaced().getLocation();
            breakChunk=quarryLocation.getWorld().getChunkAt(quarryLocation);
            user.sendMessage("You placed a Quarry");
            quarry=new minerData();
            quarry.setQuarryLocation(quarryLocation);
            quarry.setChunk(breakChunk);
            //relative to chunk
            quarry.setPos(0,(int)quarryLocation.getY()-1,0);
            quarry.setPlayer(user);
            quarry.setContext(quarryThis);
            quarry.setPlayer(user);
            quarryThis.map.map.put(quarryLocation,quarry);

        }
    }
//    TODO add destruction of quarry to stop mining
    @EventHandler
    public void exploded(BlockBreakEvent event){

    }
    @EventHandler
    public void exploded(BlockExplodeEvent event){
        if(quarryThis.map.map.containsKey(event.getBlock().getLocation())){
            quarryThis.map.map.remove(event.getBlock().getLocation());
            user.sendMessage("Quarry broken");
        }
    }
    @EventHandler
    public void qClick(PlayerInteractEvent event){
        user=event.getPlayer();
        Block block=event.getClickedBlock();
        ItemStack mainHand=user.getInventory().getItemInMainHand();
        String itemName;
        try {
            itemName= Objects.requireNonNull(mainHand.getItemMeta()).getDisplayName();
        }catch (Exception e){
            itemName="";
        }
//        TODO make most events if statments that call methoads to clean up bulk in click listner
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)&& quarryThis.map.map.containsKey(event.getClickedBlock().getLocation())&& !itemName.equals(ChatColor.RED + "Marker")){//
            event.setCancelled(true);//cancels furnace menu
            minerData miner=quarryThis.map.map.get(event.getClickedBlock().getLocation());
            if(miner.isRunning){
                user.sendMessage("You stopped a Quarry");
                miner.setRunning(false);
                quarryThis.map.map.put(event.getClickedBlock().getLocation(),miner);
            }else {
                user.sendMessage("You Started a Quarry");
                miner.setRunning(true);
                quarryThis.map.map.put(event.getClickedBlock().getLocation(),miner);
                quarryThis.runMiner(miner.quarryLocation,miner.chunk,miner.Id);
            }
        }
    }
    HashMap<Location,Location>spots=new HashMap<>();
    Location markedChest = null;
}
