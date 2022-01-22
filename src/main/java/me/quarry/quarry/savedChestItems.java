package me.quarry.quarry;


import org.bukkit.Material;
import org.bukkit.block.Block;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public  class  savedChestItems {
    File chestFile;
    FileWriter writer;
    BufferedWriter bWriter;
    private HashMap<Material, Integer> itemMap=new HashMap<Material,Integer>();
    Boolean writingFile;
    Boolean readingFile;
    Quarry context;


    public savedChestItems(){
        File file= new File("plugins/chestItems");
        if (!file.exists())
            new File("plugins/chestItems").mkdirs();

    }

    public void setContext(Quarry context) {
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
        bWriter.close();
        writer.close();
    }
    private synchronized void addToItemHashMap(Block minedBlock){
        if(itemMap.get(minedBlock.getType())!=null){
            int newCount=itemMap.get(minedBlock.getType())+1;
            itemMap.put(minedBlock.getType(), newCount);
        }else{
            itemMap.put(changeType(minedBlock.getType()),1);
        }
    }
    private synchronized void writeToFileFromItemHashMap(BufferedWriter bWriter) throws IOException {
        for (Material material: itemMap.keySet()){
            this.bWriter.write(material.toString() + ","+itemMap.get(material).toString()+":");
        }
    }
    public synchronized int decrementFromItemHashMap(Block removedBlock){
        int count=itemMap.get(removedBlock.getType());
        if (count==0){
            return 0;
        }
        itemMap.put(removedBlock.getType(),count-1);
        return count;
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
