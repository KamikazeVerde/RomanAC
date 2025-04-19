package it.creeper.roman.notify;

import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfo;
import it.creeper.roman.Roman;
import it.creeper.roman.player.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerJoinUpdateNotify implements Listener {
    Roman plugin = Roman.getInstance();
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        UUID playerId = event.getPlayer().getUniqueId();

        Roman.getInstance().getData().playerDataMap.put(playerId, new PlayerData());
        Roman.getInstance().getData().recentLocations.put(playerId, new ArrayList<>());
        Roman.getInstance().getData().attackedEntities.put(playerId, new ArrayList<>());
        Player player = event.getPlayer();
        if(player.hasPermission("roman.notify")) {
            plugin.cheatNotify.subscribed.add(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.subscribed")));
        }
    }
}
