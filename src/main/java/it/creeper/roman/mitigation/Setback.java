package it.creeper.roman.mitigation;

import it.creeper.roman.Roman;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Setback {
    private final HashMap<Player, Location> currentPlayersLocations = new HashMap<>();
    private final HashMap<Player, Location> previousPlayersLocations = new HashMap<>();

    Roman plugin = Roman.getInstance();

    int VL_TO_SETBACK = plugin.getConfig().getInt("violation-settings.setback-vl");

    public void updatePlayerPos(Player player, Location location) {
        Location previous = this.currentPlayersLocations.get(player);
        this.currentPlayersLocations.put(player, location);
        this.previousPlayersLocations.put(player, previous);
    }


    public Location getPlayerLocation(Player player) {
        return this.previousPlayersLocations.get(player);
    }

    public void setbackPlayer(Player cheater) {
        if(plugin.getCheatNotify().vl.get(cheater) != null) {
            if(plugin.getCheatNotify().vl.get(cheater) >= VL_TO_SETBACK) {
                Location setbackLocation = plugin.getSetback().getPlayerLocation(cheater);
                cheater.teleport(setbackLocation);
            }
        }
    }
}
