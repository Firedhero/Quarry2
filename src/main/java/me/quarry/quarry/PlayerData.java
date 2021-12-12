package me.quarry.quarry;

import org.bukkit.entity.Player;

import javax.xml.stream.Location;

public class PlayerData {

    private Player player;
    private Location Loc;
    private String name;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    public Location getLoc() {
        return Loc;
    }

    public void setLoc(Location loc) {
        Loc = loc;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }





}
