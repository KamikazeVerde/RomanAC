package it.creeper.roman.player;

import it.creeper.roman.Roman;
import it.creeper.roman.check.Check;
import it.creeper.roman.notify.CheatNotify;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Ban {
    Roman plugin = Roman.getInstance();
    CheatNotify cheatNotify = plugin.getCheatNotify();
    String BAN_COMMAND = plugin.getConfig().getString("punishments.ban-command");
    int VL_TO_KICK = plugin.getConfig().getInt("violation-settings.kick-vl");
    int VL_TO_BAN = plugin.getConfig().getInt("violation-settings.ban-vl");
    public void banPlayer(Player cheater) {
        if(cheatNotify.vl.get(cheater) != null) {
            if(cheatNotify.vl.get(cheater) >= VL_TO_BAN) {
                //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + cheater.getName() + " 30d Cheating (AntiCheat)");
                BAN_COMMAND = PlaceholderAPI.setPlaceholders(cheater, BAN_COMMAND);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), BAN_COMMAND);
                cheatNotify.vl.replace(cheater, 0);
                BAN_COMMAND = plugin.getConfig().getString("punishments.ban-command");
            }
        }
    }
    String KICK_MESSAGE = plugin.getConfig().getString("punishments.kick-message");
    public void kickPlayer(Player cheater) {
        if(cheatNotify.vl.get(cheater) != null) {
            if(cheatNotify.vl.get(cheater) >= VL_TO_KICK) {
                cheater.kickPlayer(KICK_MESSAGE);
                cheatNotify.vl.replace(cheater, 0);
            }
        }
    }
}
