package it.creeper.roman.player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

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

    public int maxblockscaffold = 8;

    public boolean isNear(Player player1, Player player2, double radius) {
        Vector pos1 = player1.getLocation().toVector();
        Vector pos2 = player2.getLocation().toVector();

        double distance = pos1.distance(pos2);

        return distance <= radius;
    }

    // Credits: W3schools
    public float gcd(float a, float b)
    {
        // stores minimum(a, b)
        float i;
        if (a < b)
            i = a;
        else
            i = b;

        // take a loop iterating through smaller number to 1
        for (i = i; i > 1; i--) {

            // check if the current value of i divides both
            // numbers with remainder 0 if yes, then i is
            // the GCD of a and b
            if (a % i == 0 && b % i == 0)
                return i;
        }

        // if there are no common factors for a and b other
        // than 1, then GCD of a and b is 1
        return 1;
    }

}
