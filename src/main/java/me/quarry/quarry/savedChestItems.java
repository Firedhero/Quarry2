package me.quarry.quarry;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;

public  class  savedChestItems {
    File chestFile;
    FileWriter writer;
    BufferedWriter bWriter;
    private HashMap<Material, Integer> itemMap=new HashMap<Material,Integer>();
    minerData context;

    savedChestItems(minerData miner){
        context=miner;

    }
    public void populateHashMap() {
        try {
//            System.out.println("we working here");
            File file=new File("plugins/chestItems/inventoryForQuarry_" + context.id+".txt");
//            Bukkit.broadcastMessage("Chest id "+context.id);
            if (file.exists()) {
                BufferedReader bf = new BufferedReader(new FileReader(file));
                String itemsLine = bf.readLine();
                String[] items = itemsLine.split(":");
                for (int i = 0; i < items.length; i++) {
                    String[]parts=items[i].split(",");
                    itemMap.put(Material.getMaterial(String.valueOf(parts[0])), Integer.parseInt(parts[1]));
                    for (int j = 0; j < Integer.parseInt(parts[1]); j++) {
//                        Bukkit.broadcastMessage("populating chest for quarry "+context.id);
                        context.menu.updateInventories(Material.getMaterial(String.valueOf(parts[0])));
                    }
                }

            }
        }catch (Exception e){

        }
//        try {
//            for (Map.Entry<Material, Integer> i:itemMap.entrySet())
//                context.menu.updateInventories(i.getKey(),1);
//        }catch (Exception e){
//
//        }


    }

    public savedChestItems(){
        File file= new File("plugins/chestItems");
        if (!file.exists())
            new File("plugins/chestItems").mkdirs();
//        populateHashMap();
    }




//TODO change to get what would have dropped naturally
    public void saveItemsToFile(String chestQuarryId, Block minedBlock) throws IOException {
        chestFile= new File("plugins/chestItems/inventoryForQuarry_" + chestQuarryId + ".txt");
        writer=new FileWriter("plugins/chestItems/inventoryForQuarry_"+chestQuarryId+".txt");
        bWriter=new BufferedWriter(writer);
        if(!checkUnwanted(minedBlock)) {
            addToItemHashMap(minedBlock);
//            Bukkit.broadcastMessage("writing to inventory");
//            Bukkit.broadcastMessage(context.toString());
            writeToFileFromItemHashMap(bWriter);
        }
        bWriter.close();
        writer.close();


    }
    ItemStack pickaxe=new ItemStack(Material.DIAMOND_PICKAXE);



    private void addToItemHashMap(Block minedBlock){
        if(itemMap.get(changeType(minedBlock.getType()))!=null){
            int newCount=itemMap.get(changeType(minedBlock.getType()))+1;
//            Bukkit.broadcastMessage("adding "+minedBlock.getType().toString()+":"+newCount);
            itemMap.put(changeType(minedBlock.getType()), newCount);
            context.menu.updateInventories(minedBlock.getType());
//            context.menu.updateInventories(minedBlock.getType(),1);
        }else{
//            Bukkit.broadcastMessage("adding "+minedBlock.getType().toString()+":"+1);
            itemMap.put(changeType(minedBlock.getType()),1);
//            context.menu.updateInventories(minedBlock.getType(),1);
        }
    }
    private void writeToFileFromItemHashMap(BufferedWriter bWriter) throws IOException {
        for (Material material: itemMap.keySet()){
//            Bukkit.broadcastMessage(material.toString() + ","+itemMap.get(material).toString()+":");
            bWriter.write(material.toString() + ","+itemMap.get(material).toString()+":");
        }
    }

    public Material takeFromItemHashMap() throws IOException {
        Iterator<Material> iter=itemMap.keySet().iterator();
        int count=0;
        Material material = null;
            while (iter.hasNext()){
                material= iter.next();
                count=itemMap.get(material);

            if (count>=1) {
                itemMap.put(material,count-1);
                writeToFileFromItemHashMap(bWriter);
                return material;
            }
        }
        return Material.AIR;
    }

    private Material changeType(Material type) {
        switch (type){
            case GRASS:
            case GRASS_BLOCK:
                return Material.DIRT;
            default:
                return type;
        }
    }


    private boolean checkUnwanted(Block block) {

        Material material=block.getType();
        switch (material){
            case AIR:
            case WATER:
            case LAVA:
                return true;
            default:
                return false;
        }
    }

    public HashMap<Material, Integer> getHashMap() {
        return itemMap;
    }
}
