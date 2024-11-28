package it.creeper.roman.notify;

import it.creeper.roman.Roman;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinUpdateNotify implements Listener {
    Roman plugin = Roman.getInstance();
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(player.hasPermission("roman.notify")) {
            plugin.cheatNotify.subscribed.add(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.subscribed")));
        }
    }
}
