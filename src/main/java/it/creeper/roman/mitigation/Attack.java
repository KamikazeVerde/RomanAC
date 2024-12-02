package it.creeper.roman.mitigation;

import it.creeper.roman.Roman;
import it.creeper.roman.events.MitigateEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Attack implements Listener {
    // NEED TO FIX
    Roman plugin = Roman.getInstance();
    int VL_TO_MITIGATE = plugin.getConfig().getInt("violation-settings.attack-mitigation-vl");
    int REDUCE_DAMAGE_FACTOR = plugin.getConfig().getInt("mitigation.attack-reduce-factor");
    public void mitigatePlayer(int seconds,  Player player) {
        AtomicInteger ticks = new AtomicInteger();
        mitigatingPlayers.add(player);
        BukkitTask task = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            ticks.set(ticks.get() + 1);
        }, 20L, seconds / 20);
        if(ticks.get() == seconds / 20) {
            task.cancel();
            mitigatingPlayers.remove(player);
        }
    }
    ArrayList<Player> mitigatingPlayers = new ArrayList<>();
    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        /*
        Devel:
        Need to get the player that got hitted from the listed cheater
        then diminsh the damage inflicted.
         */
        if(event.getDamager() instanceof Player) {
            if(plugin.getCheatNotify().vl.get((Player) event.getDamager()) != null) {
                if(plugin.getCheatNotify().vl.get((Player) event.getEntity()) >= VL_TO_MITIGATE) {
                    if(mitigatingPlayers.contains((Player) event.getDamager())) {
                        Bukkit.getPluginManager().callEvent(new MitigateEvent((Player) event.getDamager(), "attack"));
                        event.setDamage(event.getDamage() - REDUCE_DAMAGE_FACTOR);
                    }
                }
            }
        }
    }
}
