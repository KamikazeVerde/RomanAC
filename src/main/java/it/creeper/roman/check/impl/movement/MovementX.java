package it.creeper.roman.check.impl.movement;

import it.creeper.roman.Roman;
import it.creeper.roman.check.Check;
import it.creeper.roman.check.CheckInfo;
import it.creeper.roman.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CheckInfo(name = "Movement", type = 'X', description = "Irregular movement")
public class MovementX extends Check implements Listener {
    private void checkPlayer(Player player) {
        UUID playerId = player.getUniqueId();
        PlayerData data = Roman.getInstance().getData().playerDataMap.get(playerId);

        if (data == null) {
            return;
        }
        List<Location> history = Roman.getInstance().getData().recentLocations.getOrDefault(playerId, new ArrayList<>());
        if (history.size() >= 10) {
            boolean irregularPattern = checkForIrregularMovement(history);
            if (irregularPattern) {
                cheatNotify.fail(player, "Anomaly");
                possiblySetbackPlayer(player);
            }
        }
        data.allowedFastMovement = false;
        data.allowedHighJump = false;
    }
    public MovementX() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Roman.getInstance(), () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
                    continue;
                }

                checkPlayer(player);
            }
        }, 20L, 20L);
    }

    private boolean checkForIrregularMovement(List<Location> history) {
        if (history.size() < 3) {
            return false;
        }
        double avgSpeedX = 0;
        double avgSpeedZ = 0;

        for (int i = 1; i < history.size(); i++) {
            Location prev = history.get(i - 1);
            Location curr = history.get(i);

            avgSpeedX += curr.getX() - prev.getX();
            avgSpeedZ += curr.getZ() - prev.getZ();
        }
        avgSpeedX /= (history.size() - 1);
        avgSpeedZ /= (history.size() - 1);
        Location prev = history.get(history.size() - 2);
        Location curr = history.get(history.size() - 1);
        double currSpeedX = curr.getX() - prev.getX();
        double currSpeedZ = curr.getZ() - prev.getZ();
        double deviationX = Math.abs(currSpeedX - avgSpeedX);
        double deviationZ = Math.abs(currSpeedZ - avgSpeedZ);
        return (deviationX + deviationZ) > 0.8;
    }

    private static final double MAX_SPEED = 0.6;
    private static final double MAX_Y_SPEED = 0.42;

    public void checkMovement(Player player, PlayerData data, Location from, Location to, double horizontalSpeed, double verticalSpeed) {
        UUID playerId = player.getUniqueId();
        boolean flagged = false;
        String hackType = "";

        boolean inWater = player.getLocation().getBlock().isLiquid() || to.getBlock().isLiquid();
        boolean blockAbove = player.getEyeLocation().clone().add(0, 0.2, 0).getBlock().getType().isSolid();
        boolean onLadder = player.getLocation().getBlock().getType().toString().contains("LADDER") ||
                player.getLocation().getBlock().getType().toString().contains("VINE");

        double maxAllowedSpeed = MAX_SPEED;
        if (inWater) maxAllowedSpeed = MAX_SPEED * 0.8;
        if (blockAbove) maxAllowedSpeed = MAX_SPEED * 1.3;

        float yawDiff = Math.abs(to.getYaw() - from.getYaw());
        if (yawDiff > 30 && player.isSprinting()) {
            maxAllowedSpeed = MAX_SPEED * 1.15;
        }

        if (player.hasPotionEffect(org.bukkit.potion.PotionEffectType.SPEED)) {
            int speedLevel = getEffectLevel(player, org.bukkit.potion.PotionEffectType.SPEED);
            maxAllowedSpeed *= (1.0 + (speedLevel * 0.2));
        }

        if (horizontalSpeed > maxAllowedSpeed * 0.95 && !player.isFlying() && !data.allowedFastMovement) {
            if (data.teleportTicks <= 0) {
                data.speedViolations++;
                if (data.speedViolations >= 2) {
                    flagged = true;
                    hackType = "Speed";
                }
            } else {
                data.teleportTicks--;
            }
        } else {
            data.speedViolations = Math.max(0, data.speedViolations - 1);
        }

        if (data.lastOnGround && !player.isOnGround() && horizontalSpeed > maxAllowedSpeed * 0.8) {
            if (!inWater && !blockAbove && data.teleportTicks <= 0) {
                data.bhopViolations++;
                if (data.bhopViolations >= 2) {
                    flagged = true;
                    hackType = "BHop";
                }
            }
        } else {
            data.bhopViolations = Math.max(0, data.bhopViolations - 1);
        }

        double maxYSpeed = MAX_Y_SPEED;
        if (onLadder) maxYSpeed = 0.8;

        if (player.hasPotionEffect(org.bukkit.potion.PotionEffectType.JUMP)) {
            int jumpLevel = getEffectLevel(player, org.bukkit.potion.PotionEffectType.JUMP);
            maxYSpeed += (jumpLevel * 0.1);
        }

        if (verticalSpeed > maxYSpeed * 1.05 && !player.isFlying() && !data.allowedHighJump && !inWater) {
            if (System.currentTimeMillis() - data.lastDamageTime > 1000 && data.teleportTicks <= 0) {
                data.vhopViolations++;
                if (data.vhopViolations >= 2) {
                    flagged = true;
                    hackType = "VHop";
                }
            }
        } else {
            data.vhopViolations = Math.max(0, data.vhopViolations - 1);
        }

        if (!player.isOnGround() && data.airTicks > 40 && verticalSpeed >= 0 && !player.isFlying() && !inWater && !onLadder) {
            boolean nearBlock = hasNearbyBlocks(player);
            if (!nearBlock) {
                data.flyViolations++;
                if (data.flyViolations >= 3) {
                    flagged = true;
                    hackType = "Fly";
                }
            }
        } else {
            data.flyViolations = Math.max(0, data.flyViolations - 1);
        }

        if (!player.isOnGround()) {
            data.airTicks++;
        } else {
            data.airTicks = 0;
        }

        data.lastOnGround = player.isOnGround();

        if (flagged) {
            cheatNotify.fail(player, hackType);
            possiblySetbackPlayer(player);
            possiblyPunish(player, this.getClass());
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }
        double deltaX = e.getTo().getX() - e.getFrom().getX();
        double deltaY = e.getTo().getY() - e.getFrom().getY();
        double deltaZ = e.getTo().getZ() - e.getFrom().getZ();

        double horizontalSpeed = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        //checkMovement(player, Roman.getInstance().getData().playerDataMap.get(player), e.getFrom(), e.getTo(), horizontalSpeed, deltaY);
    }

    private boolean hasNearbyBlocks(Player player) {
        Location loc = player.getLocation();
        int radius = 2;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    if (x == 0 && y == 0 && z == 0) continue;

                    if (loc.clone().add(x, y, z).getBlock().getType().isSolid()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private int getEffectLevel(Player player, org.bukkit.potion.PotionEffectType type) {
        for (org.bukkit.potion.PotionEffect effect : player.getActivePotionEffects()) {
            if (effect.getType().equals(type)) {
                return effect.getAmplifier() + 1;
            }
        }
        return 0;
    }

    @EventHandler
    public void onPlayerDamage(org.bukkit.event.entity.EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            UUID playerId = player.getUniqueId();

            PlayerData data = Roman.getInstance().getData().playerDataMap.get(playerId);
            if (data != null) {
                data.lastDamageTime = System.currentTimeMillis();
                data.allowedFastMovement = true;
                data.allowedHighJump = true;
            }
        }
    }

    @EventHandler
    public void onPlayerTeleport(org.bukkit.event.player.PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        PlayerData data = Roman.getInstance().getData().playerDataMap.get(playerId);
        if (data != null) {
            data.teleportTicks = 20;
            data.speedViolations = 0;
            data.bhopViolations = 0;
            data.vhopViolations = 0;
            data.flyViolations = 0;
        }
    }

}
