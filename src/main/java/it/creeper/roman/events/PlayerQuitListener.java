package it.creeper.roman.events;

import it.creeper.roman.Roman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class PlayerQuitListener implements Listener {
    @EventHandler
    public void onPlayerQuit(org.bukkit.event.player.PlayerQuitEvent event) {
        UUID playerId = event.getPlayer().getUniqueId();

        Roman.getInstance().getData().playerDataMap.remove(playerId);
        Roman.getInstance().getData().attackedEntities.remove(playerId);
        Roman.getInstance().getData().lastAttackTime.remove(playerId);
        Roman.getInstance().getData().recentLocations.remove(playerId);
    }
}
