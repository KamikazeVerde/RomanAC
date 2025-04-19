package it.creeper.roman.check.impl.combat.aura;

import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfo;
import it.creeper.roman.Roman;
import it.creeper.roman.check.Check;
import it.creeper.roman.check.CheckInfo;
import it.creeper.roman.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@CheckInfo(name = "IrregularCombat", type = 'X', description = "combat heuristics")
public class IrregularCombat extends Check implements Listener {
    private static final int MAX_ATTACK_ANGLE = 120;
    private static final double MAX_REACH = 3.5;
    private static final int MIN_ROTATION_YAW = 3;
    private static final int MAX_ATTACKS_PER_SECOND = 16;
    private void checkKillAura(Player player, Entity target) {
        UUID playerId = player.getUniqueId();
        boolean flagged = false;
        String hackType = "KillAura";

        long currentTime = System.currentTimeMillis();
        long lastTime = Roman.getInstance().getData().lastAttackTime.getOrDefault(playerId, 0L);
        Roman.getInstance().getData().lastAttackTime.put(playerId, currentTime);

        List<Entity> attacked = Roman.getInstance().getData().attackedEntities.getOrDefault(playerId, new ArrayList<>());
        if (!attacked.contains(target)) {
            attacked.add(target);
        }

        if (attacked.size() > 10) {
            attacked.remove(0);
        }

        double angle =  Roman.getInstance().getData().getAngleBetweenEntities(player, target);

        PlayerData data = Roman.getInstance().getData().playerDataMap.get(playerId);
        float yawDiff = 0;
        if (data != null && data.lastYaw != 0) {
            yawDiff = Math.abs(player.getLocation().getYaw() - data.lastYaw);
        }

        if (data != null) {
            data.lastYaw = player.getLocation().getYaw();
        }

        double distance = player.getLocation().distance(target.getLocation());

        if (lastTime > 0 && currentTime - lastTime < 1000.0 / MAX_ATTACKS_PER_SECOND) {
            flagged = true;
        }

        if (angle > MAX_ATTACK_ANGLE) {
            flagged = true;
        }

        if (distance > MAX_REACH) {
            flagged = true;
        }

        if (yawDiff < MIN_ROTATION_YAW && yawDiff > 0 && angle > 30) {
            flagged = true;
        }

        if (flagged) {
            cheatNotify.fail(player, "");
            possiblyPunish(player, this.getClass());
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getDamager();
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }

        UUID playerId = player.getUniqueId();
        Entity target = event.getEntity();
        checkKillAura(player, target);
    }
}
