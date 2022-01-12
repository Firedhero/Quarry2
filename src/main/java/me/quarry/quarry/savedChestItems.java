package me.quarry.quarry;


import org.bukkit.block.Block;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class savedChestItems {
    File chestFile;
    FileWriter writer;
    BufferedWriter bWriter;
    public savedChestItems(){
        File file= new File("plugins/chestItems");
        if (!file.exists())
            new File("plugins/chestItems").mkdirs();

    }

//      TODO add synchornized Threads for saving to file and taking from file
//            when chest has items withdrawn saving to file stops otherwise continues
//              make joint file with a array of materials and the file is updated as the list is updated

    public void saveItems(int chestQuarryId, Block minedBlock) throws IOException {
        chestFile= new File("plugins/chestItems/" + chestQuarryId + ".txt");
        if (chestFile.exists()){

            this.writer=new FileWriter("plugins/chestItems/"+chestQuarryId+".txt",true);
            this.bWriter=new BufferedWriter(writer);
            this.bWriter.append(minedBlock.toString());



        }else{
            this.writer=new FileWriter("plugins/chestItems/"+chestQuarryId+".txt");
            this.bWriter=new BufferedWriter(writer);

        }
    }
}
