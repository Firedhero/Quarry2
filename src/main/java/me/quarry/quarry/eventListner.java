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
import org.bukkit.inventory.ItemStack;

import javax.swing.*;
import java.util.*;

public class eventListner implements Listener {
    private Location player;
    private  Player user;
    private Location quarryLocation;
    private Location customLocation;
    private Quarry quarryThis;
    private Chunk breakChunk;
    private int id;
    minerData quarry;
    customData customQuarry;

    public eventListner(Quarry quarry) {
        quarryThis=quarry;
    }

    //for 3x3 pickaxe dig
    @EventHandler
    public void onPowered(BlockBreakEvent event){
        Block brokenBlock=event.getBlock();
        user=event.getPlayer();
        ItemStack mainHand=user.getInventory().getItemInMainHand();
        String itemName;
        try {
            itemName= Objects.requireNonNull(mainHand.getItemMeta()).getDisplayName();
        }catch (Exception e){
            itemName="";
        }
        if(quarryThis.map.map.containsKey(event.getBlock().getLocation())){
            quarryThis.map.map.remove(event.getBlock().getLocation());
            user.sendMessage("Quarry broken");
        }
        if(quarryThis.map.map.containsKey(event.getBlock().getLocation())){
            quarryThis.map.map.entrySet();
        }
        if(quarryThis.map.chestLocations.containsKey(event.getBlock().getLocation())){
            quarryThis.map.chestLocations.get(event.getBlock().getLocation()).setChestLocation(null);
        }

        if(itemName.equals(ChatColor.RED+"Big Dig")){
            //Block location
            Location bLoc = brokenBlock.getLocation();
            int bLocX = (int) bLoc.getX();
            int bLocY = (int) bLoc.getY();
            int bLocZ = (int) bLoc.getZ();

            Location pLoc = event.getPlayer().getLocation();

            player = pLoc;
            int pLocX = (int) pLoc.getX();
            int pLocY = (int) pLoc.getY();
            int pLocZ = (int) pLoc.getZ();
//        brokenBlock.isBlockPowered()
            //3x3 dig area
            ItemStack brokenWith;
            //layer broken on for up down
            int z = bLocZ;
            Player user = event.getPlayer();
            String direction = getCardinalDirection(user);
            if (direction.equals("East") || direction.equals(("West"))) {
                breakZ(bLocX, bLocY, z);

            } else if (direction.equals("North") || direction.equals("South")) {
                breakX(bLocX, bLocY, z);

            } else {
//                breakDiag(bLocX, bLocY, z);
            }
        }else{

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
            if(quarryThis.map.map.isEmpty()){

                quarry.setId(id);
            }else{
//                int numberQuarries=0;
//                for(int i=0;i<quarryThis.map.map.size();i++) {
//                    numberQuarries = i;
//                }
                quarry.setId(quarryThis.map.map.size());
            }
            quarry.setPlayer(user);
            quarryThis.map.map.put(quarryLocation,quarry);

        }

        if(meta.getItemMeta().getDisplayName().equals(ChatColor.RED+"Custom Quarry")){
            customLocation=event.getBlock().getLocation();
            customQuarry=new customData();
            customQuarry.setQuarryLocation(customLocation);
            quarryThis.custMap.map.put(customLocation,customQuarry);
        }


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

//        TODO add in frames that popup displaying all placed quarries stored inventory
        //  --------------------------------------------------------


        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)&& itemName.equals(ChatColor.RED + "Stick Of Holding")) {
            final JFrame parent = new JFrame();
            //all selection options should be buttons or have a drop down menu
            //also make visibility of menu see thru ish
            JButton button = new JButton();

            button.setText("Click me to show dialog!");
            parent.add(button);
            parent.pack();
            parent.setVisible(true);

            button.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    String name = JOptionPane.showInputDialog(parent,
                            "What is your name?", null);
                }
            });
        }
//--------------------------------------------------------------------------------------------------

        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)&& quarryThis.map.map.containsKey(event.getClickedBlock().getLocation())&& !itemName.equals(ChatColor.RED + "Marker")){//
            event.setCancelled(true);//cancels furnace menu
            //TODO------------------------------------------

            minerData miner=quarryThis.map.map.get(event.getClickedBlock().getLocation());
            if(miner.isRunning){
                user.sendMessage("You stopped a Quarry");
                miner.setRunning(false);
                quarryThis.map.map.put(event.getClickedBlock().getLocation(),miner);
            }else {
                user.sendMessage("You Clicked a Quarry now running");
                miner.setRunning(true);
                quarryThis.map.map.put(event.getClickedBlock().getLocation(),miner);
                quarryThis.runMiner(miner.quarryLocation,miner.chunk,miner.Id);
//                mineChun();
            }
            //TODO-END--------------------------------------
        }


        if(itemName.equals(ChatColor.RED+"Marker")&&event.getAction().equals(Action.RIGHT_CLICK_BLOCK)&& event.getClickedBlock().getType().equals(Material.CHEST)){
            user.sendMessage("you have marked chest for quarry dump");
            markedChest=event.getClickedBlock().getLocation();

//            quarryThis.map.setLinkedChest(event.getClickedBlock().getLocation());
        }
        if(itemName.equals(ChatColor.RED+"Marker")&&event.getAction().equals(Action.RIGHT_CLICK_BLOCK)&& quarryThis.map.map.containsKey(event.getClickedBlock().getLocation())){
            user.sendMessage("you have marked quarry for chest at "+ markedChest);
            Location markedQuarry=event.getClickedBlock().getLocation();
            quarryThis.map.map.get(event.getClickedBlock().getLocation()).setChestLocation(markedChest);
            if(quarryThis.map.map.get(event.getClickedBlock().getLocation()).chestLocation!=null){
                synchronized (quarryThis.map.map.get(event.getClickedBlock().getLocation()).depositer){
                    quarryThis.map.map.get(event.getClickedBlock().getLocation()).depositer.notify();
                }

            }
        }



        if(itemName.equals(ChatColor.RED+"custom marker")&&event.getAction().equals(Action.RIGHT_CLICK_BLOCK)&&!event.getClickedBlock().getType().equals(Material.FURNACE)){
            user.sendMessage("you have marked spot for custom quarry Click Custom Quarry to start"+event.getClickedBlock().getLocation());
            spots.put(event.getClickedBlock().getLocation(),event.getClickedBlock().getLocation());
            int x=event.getClickedBlock().getLocation().getBlockX();
            int y=event.getClickedBlock().getLocation().getBlockY();
            int z=event.getClickedBlock().getLocation().getBlockZ();
            Bukkit.getServer().getWorld("world").getBlockAt(x,y+1,z).setType(Material.OAK_FENCE);

        }
        if(itemName.equals(ChatColor.RED+"custom marker")&&event.getAction().equals(Action.RIGHT_CLICK_BLOCK)&&event.getClickedBlock().getType().equals(Material.FURNACE)){
            event.setCancelled(true);//cancels furnace menu
            customData custMiner = quarryThis.custMap.map.get(event.getClickedBlock().getLocation());
            if(custMiner.isRunning) {
                user.sendMessage("You stopped a Quarry");
                custMiner.setRunning(false);
                quarryThis.custMap.map.put(event.getClickedBlock().getLocation(), custMiner);
            }else{
                if (!spots.isEmpty()&&spots.size()<=2) {
                    custMiner.setRunning(true);
                    custMiner.setMarkedSpots(spots);
                    quarryThis.custMap.map.put(event.getClickedBlock().getLocation(), custMiner);
//                    quarryThis.runCustom(event.getClickedBlock().getLocation());
                    spots = new HashMap<>();
                }else{
                    user.sendMessage("You need to mark spots for the quarry to run");
                }


            }
        }

    }

    HashMap<Location,Location>spots=new HashMap<>();
    Location markedChest = null;

//only works for northEast
    public void breakDiag(int bLocX, int bLocY, int z) {
        int zeta=z-1;

        for (int i = bLocX-1; i < bLocX + 2; i++) {

            for (int j = bLocY - 1; j < bLocY + 2; j++) {
                breakBlock(i, j, zeta);
            }
            zeta+=1;
        }

    }
//    also corrdinates are X Y=height Z

    //west not breaking all the time

    public void breakZ(int bLocX,int bLocY ,int z){
        for (int i = bLocY - 1; i < bLocY + 2; i++) {
            for (int j = z - 1; j < z + 2; j++) {
                breakBlock(bLocX, i, j);
            }
        }
    }
    public void breakX(int bLocX,int bLocY ,int z){
        for (int i = bLocX - 1; i < bLocX + 2; i++) {
            for (int j = bLocY - 1; j < bLocY + 2; j++) {
                breakBlock(i, j, z);
            }
        }
    }
    public void breakBlock(int x,int y,int z){
        Block block = null;
        block=player.getWorld().getBlockAt(x,y,z);
        block.breakNaturally();


    }
    /**
     * Get the cardinal compass direction of a player.
     *
     * @param player
     * @return
     */
    public static String getCardinalDirection(Player player) {
        double rot = (player.getLocation().getYaw() - 90) % 360;
        if (rot < 0) {
            rot += 360.0;
        }
        return getDirection(rot);
    }

    /**
     * Converts a rotation to a cardinal direction name.
     *
     * @param rot
     * @return
     */
    private static String getDirection(double rot) {
        if (0 <= rot && rot < 22.5) {
            return "Northwest";
        } else if (22.5 <= rot && rot < 67.5) {
            return "Northwest";
        } else if (67.5 <= rot && rot < 112.5) {
            return "North";
        } else if (112.5 <= rot && rot < 157.5) {
            return "Northeast";
        } else if (157.5 <= rot && rot < 202.5) {
            return "East";
        } else if (202.5 <= rot && rot < 247.5) {
            return "Southeast";
        } else if (247.5 <= rot && rot < 292.5) {
            return "South";
        } else if (292.5 <= rot && rot < 337.5) {
            return "Southwest";
        } else if (337.5 <= rot && rot < 360.0) {
            return "West";
        } else {
            return null;
        }
    }


}
