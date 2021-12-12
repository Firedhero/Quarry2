package me.quarry.quarry;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class customMap {
    public void saveMap(){
        //for storing the locations;
        File file = new File("plugins/hashMapCustomLocations.txt");
        File file2 = new File("plugins/hashCustomMinerData.txt");
        //for storing objects
        FileOutputStream f = null;
        FileOutputStream o = null;
        BufferedWriter bf = null;
        BufferedWriter bo = null;
        try {

            // create new BufferedWriter for the output file
            bf = new BufferedWriter(new FileWriter(file));
            bo = new BufferedWriter(new FileWriter(file2));
            // iterate map entries
            for (Map.Entry<Location, customData> entry : map.entrySet()) {

                // put key and value separated by a colon
                bf.write(entry.getKey() + " ");
                bo.write(entry.getValue().quarryLocation+":"+ Arrays.toString(entry.getValue().getPos())+":"+entry.getValue().chestLocation+":"+entry.getValue().isRunning());
                // new line
                bf.newLine();
                bo.newLine();
            }


            bf.flush();
            bo.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {

            try {

                // always close the writer
                bf.close();
                bo.close();
            }
            catch (Exception e) {
            }
        }
    }








    public void setLinkedChest(Location linkedChest) {
//        LinkedChest = linkedChest;
    }

    Location quarryLocation;

    int quarryId;
    int[]minerId=new int[100];
//    public Location getLinkedChest() {
////        return LinkedChest;
//    }

    //    Location LinkedChest;
    HashMap<Location,customData> map=new HashMap<Location, customData>();

    public Location getQuarryLocation() {
        return quarryLocation;
    }

    public void setQuarryLocation(Location quarryLocation) {
        this.quarryLocation = quarryLocation;
    }
    public customData getCustomData(Location location){
        customData mine=map.get(location);
        return mine;
    }

    public customMap readMap() {
        customMap q=new customMap();
        BufferedReader br = null;
        BufferedReader bf = null;

        // create file object
        File file = new File("plugins/hashMapCustomLocations.txt");
        File file2 = new File("plugins/hashCustomMinerData.txt");
        try {

            // create BufferedReader object from the File
            br = new BufferedReader(new FileReader(file));
            bf = new BufferedReader(new InputStreamReader(new FileInputStream(file2)));



            String line = null;

            // read file line by line
            while ((line = br.readLine()) != null) {

                // split the line by :
                String[] parts = line.split(":");
                String locationKey = parts[0];
                String[] partstwo=locationKey.split(",");
                String[] x=partstwo[1].split("=");
                String[] y=partstwo[2].split("=");
                String[] z=partstwo[3].split("=");

//                Location loc=null;
                Location loc= Bukkit.getServer().getWorld("world").getBlockAt((int)Double.parseDouble(x[1]),(int)Double.parseDouble(y[1]),(int)Double.parseDouble(z[1])).getLocation();
//                loc.setX(Double.parseDouble(x[1]));
//                loc.setY(Double.parseDouble(y[1]));
//                loc.setZ(Double.parseDouble(z[1]));
                // first part is name, second is number



                customData miner=new customData();
                String minerData = bf.readLine();
                String[] partsminer = minerData.split(":");




                String minerLoaction=partsminer[0];
                minerLoaction=minerLoaction.replace("[","");
                minerLoaction=minerLoaction.replace("]","");
                String[] xyz=minerLoaction.split(",");
//                Bukkit.broadcastMessage(minerLoaction);
//                String[] x2=partsthree[1].split("=");
//                String[] y2=partsthree[2].split("=");
//                String[] z2=partsthree[3].split("=");

//                Location minerLoc = null;
//                minerLoc.setX(Double.parseDouble(x2[1]));
//                Bukkit.broadcastMessage("Fuck"+Double.parseDouble(x2[1]));
//                minerLoc.setY(Double.parseDouble(y2[1]));
//                minerLoc.setZ(Double.parseDouble(z2[1]));

//                4 chestlocation 5 running
                String chestLoaction=partsminer[1];
                if(!chestLoaction.equals("null")) {
                    String[] chestxyz = chestLoaction.split(",");
                    String[] chestx = chestxyz[1].split("=");
                    String[] chesty = chestxyz[2].split("=");
                    String[] chestz = chestxyz[3].split("=");
                    Location chest = Bukkit.getServer().getWorld("world").getBlockAt((int) Double.parseDouble(chestx[1].trim()), (int) Double.parseDouble(chesty[1].trim()), (int) Double.parseDouble(chestz[1].trim())).getLocation();
                    miner.setChestLocation(chest);
                }

                miner.setRunning(Boolean.parseBoolean(partsminer[3]));
                miner.setQuarryLocation(loc);
//                miner.setPos(Integer.parseInt(xyz[0].trim()),Integer.parseInt(xyz[1].trim()),Integer.parseInt(xyz[2].trim()));
                // put name, number in HashMap if they are
                // not empty
                if (loc!=null) {
                    q.map.put(loc, miner);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {

            // Always close the BufferedReader
            if (br != null) {
                try {
                    br.close();
                }
                catch (Exception e) {
                };
            }
        }


        // create BufferedReader object from the File


        return q;
    }
}
