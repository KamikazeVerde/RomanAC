package it.creeper.roman.player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Data implements Listener {
    public Server server = Bukkit.getServer();
    //TODO: Player Data
    public Map<Player, Double> deltasXZ = new HashMap<>();
    public Set<Player> jumpingPlayers = new HashSet<>();
    double deltaXZ;
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        //System.out.println("deltaXZ raccolto");
        Player player = e.getPlayer();
        Location from = e.getFrom();
        Location to = e.getTo();
        deltasXZ.remove(player);
        double deltaX = to.getX() - from.getX();
        double deltaZ = to.getZ() - from.getZ();
        deltaXZ = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        //System.out.println(deltaXZ);
        //System.out.println("deltaXZ from Data Class: "+deltaXZ);
        deltasXZ.put(player, deltaXZ);
        if(player.getLocation().getY() > 0 && !isOnGround(player)) {
            jumpingPlayers.add(player);
        } else {

            jumpingPlayers.remove(player);
        }
    }
    public boolean isOnGround(Player player) {
        Location loc = player.getLocation();
        loc.setY(loc.getY() - 0.1);
        return loc.getBlock().getType().isSolid();
    }
}
