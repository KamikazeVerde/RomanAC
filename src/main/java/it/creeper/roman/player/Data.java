package it.creeper.roman.player;

import it.creeper.roman.Roman;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Data implements Listener {
    public Server server = Bukkit.getServer();
    private static final int HISTORY_SIZE = 20;
    public Map<Player, Double> deltasXZ = new HashMap<>();
    public Map<UUID, List<Location>> recentLocations = new ConcurrentHashMap<>();
    public Map<UUID, PlayerData> playerDataMap = new HashMap<UUID, PlayerData>();
    public Set<Player> jumpingPlayers = new HashSet<>();
    public Map<Player, Integer> offGroundTicks = new HashMap<>();
    public Map<UUID, Long> lastAttackTime = new ConcurrentHashMap<>();
    public Map<UUID, List<Entity>> attackedEntities = new ConcurrentHashMap<>();
    double deltaXZ;
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {

        Player player = e.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }

        UUID playerId = player.getUniqueId();
        Location from = e.getFrom();
        Location to = e.getTo();
        PlayerData data = playerDataMap.get(playerId);
        if (data == null) {
            data = new PlayerData();
            playerDataMap.put(playerId, data);
        }
        List<Location> locations = recentLocations.get(playerId);
        if (locations == null) {
            locations = new ArrayList<>();
            recentLocations.put(playerId, locations);
        }

        locations.add(to);
        if (locations.size() > HISTORY_SIZE) {
            locations.remove(0);
        }

        // Calcola velocità
        double deltaX = to.getX() - from.getX();
        double deltaY = to.getY() - from.getY();
        double deltaZ = to.getZ() - from.getZ();

        double horizontalSpeed = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

        data.lastSpeed = horizontalSpeed;
        data.lastYSpeed = deltaY;
        data.inAir = !player.isOnGround();
        data.lastLocation = to;


        Roman.getInstance().newCheckRegister.movementX.checkMovement(player, data, from, to, horizontalSpeed, deltaY);
        /// /// ///

        if(!this.isOnGround(e.getPlayer())) {
            offGroundTicks.put(e.getPlayer(), offGroundTicks.getOrDefault(e.getPlayer(), 0) + 1);
        } else if (this.isOnGround(e.getPlayer())) {
            offGroundTicks.replace(e.getPlayer(), 0);
        }
        //System.out.println("deltaXZ raccolto");
        Player playerr = e.getPlayer();
        deltasXZ.remove(player);
        double deltaXx = to.getX() - from.getX();
        double deltaZz = to.getZ() - from.getZ();
        deltaXZ = Math.sqrt(deltaXx * deltaXx + deltaZz * deltaZz);
        //System.out.println(deltaXZ);
        //System.out.println("deltaXZ from Data Class: "+deltaXZ);
        deltasXZ.put(playerr, deltaXZ);
        if(playerr.getLocation().getY() > 0 && !isOnGround(playerr)) {
            jumpingPlayers.add(playerr);
        } else {
            jumpingPlayers.remove(playerr);
        }
    }
    public boolean isOnGround(Player player) {
        Location loc = player.getLocation();
        loc.setY(loc.getY() - 0.1);
        return loc.getBlock().getType().isSolid();
    }
    public double getAngleBetweenEntities(Player player, Entity target) {
        Vector playerDir = player.getLocation().getDirection();
        Vector targetDir = target.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();

        return playerDir.angle(targetDir) * 180 / Math.PI;
    }

    public int getOffGroundTicks(Player player) {
        return offGroundTicks.get(player);
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
