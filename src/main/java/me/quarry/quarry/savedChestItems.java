package me.quarry.quarry;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;

public  class  savedChestItems {
    File chestFile;
    FileWriter writer;
    BufferedWriter bWriter;
    private HashMap<Material, Integer> itemMap=new HashMap<Material,Integer>();
    minerData context;

    public void populateHashMap() {
        try {

            File file=new File("plugins/chestItems/inventoryForQuarry_" + context.getintId()+".txt");
            if (file.exists()) {
                BufferedReader bf = new BufferedReader(new FileReader(file));
                String itemsLine = bf.readLine();
                String[] items = itemsLine.split(":");
                for (int i = 0; i < items.length; i++) {
                    String[]parts=items[i].split(",");
                    itemMap.put(Material.getMaterial(String.valueOf(parts[0])), Integer.parseInt(parts[1]));
                }

            }
        }catch (Exception e){

        }

    }

    public savedChestItems(){
        File file= new File("plugins/chestItems");
        if (!file.exists())
            new File("plugins/chestItems").mkdirs();
        populateHashMap();
    }

    public void setContext(minerData context) {
        this.context = context;
    }
    //      TODO add synchornized Threads for saving to file and taking from file
//            when chest has items withdrawn saving to file stops otherwise continues
//              make joint file with a array of materials and the file is updated as the list is updated

    public void saveItemsToFile(int chestQuarryId, Block minedBlock) throws IOException {
        chestFile= new File("plugins/chestItems/inventoryForQuarry_" + chestQuarryId + ".txt");
        writer=new FileWriter("plugins/chestItems/inventoryForQuarry_"+chestQuarryId+".txt");
        bWriter=new BufferedWriter(writer);
        if(!checkUnwanted(minedBlock)) {
            addToItemHashMap(minedBlock);
        }
        writeToFileFromItemHashMap(bWriter);

    }
    private synchronized void addToItemHashMap(Block minedBlock){
        if(itemMap.get(changeType(minedBlock.getType()))!=null){
            int newCount=itemMap.get(changeType(minedBlock.getType()))+1;
            itemMap.put(changeType(minedBlock.getType()), newCount);
        }else{
            itemMap.put(changeType(minedBlock.getType()),1);
        }
    }
    private synchronized void writeToFileFromItemHashMap(BufferedWriter bWriter) throws IOException {
        for (Material material: itemMap.keySet()){
            this.bWriter.write(material.toString() + ","+itemMap.get(material).toString()+":");
        }
    }

    public synchronized Material takeFromItemHashMap() throws IOException {
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
        return null;
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
}
