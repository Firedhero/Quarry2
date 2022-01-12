package me.quarry.quarry;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class savedChestItems {
    File chestFile;
    FileWriter writer;
    BufferedWriter bWriter;
    public savedChestItems(){
//        chestFile = new File("plugins/chestItems.txt");

    }

    private void saveItems(String chestQuarryId) throws IOException {
        if (chestFile.exists()){
            writer=new FileWriter("plugins/chestItems.txt",true);
            bWriter=new BufferedWriter(writer);



        }else{

        }
    }
}
