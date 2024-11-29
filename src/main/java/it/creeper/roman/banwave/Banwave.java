package it.creeper.roman.banwave;

import it.creeper.roman.Roman;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Banwave {

    Roman plugin = Roman.getInstance();
    String BAN_COMMAND = plugin.getConfig().getString("punishments.ban-command");
    String KICK_MESSAGE = plugin.getConfig().getString("punishments.kick-message");

    public void initBanwaveSystem() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            for(Player p : plugin.getCheatNotify().vl.keySet()) {
                if(plugin.getCheatNotify().vl.get(p) >= plugin.getConfig().getInt("banwave-settings.banwave-vl")) {
                    String punishmentType = plugin.getConfig().getString("banwave-settings.banwave-type");
                    switch (punishmentType) {
                        case "ban":
                            BAN_COMMAND = PlaceholderAPI.setPlaceholders(p, BAN_COMMAND);
                            plugin.getCheatNotify().vl.remove(p);
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), BAN_COMMAND);
                            BAN_COMMAND = plugin.getConfig().getString("punishments.ban-command");
                        case "kick":
                            plugin.getCheatNotify().vl.remove(p);
                            p.kickPlayer(KICK_MESSAGE);
                    }
                }
            }
        }, 5, Roman.minToTick(plugin.getConfig().getLong("banwave-settings.banwave-time")));
    }
}
