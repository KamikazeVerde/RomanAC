package it.creeper.roman.player;

import it.creeper.roman.Roman;
import it.creeper.roman.check.Check;
import it.creeper.roman.notify.CheatNotify;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Ban {
    Roman plugin = Roman.getInstance();
    CheatNotify cheatNotify = plugin.getCheatNotify();
    int VL_TO_KICK = plugin.getConfig().getInt("violation-settings.kick-vl");
    int VL_TO_BAN = plugin.getConfig().getInt("violation-settings.ban-vl");
    public void banPlayer(Player cheater) {
        if(cheatNotify.vl.get(cheater) != null) {
            if(cheatNotify.vl.get(cheater) >= VL_TO_BAN) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + cheater.getName() + " 30d Cheating (AntiCheat)");
                cheatNotify.vl.replace(cheater, 0);
            }
        }
    }
    public void kickPlayer(Player cheater) {
        if(cheatNotify.vl.get(cheater) != null) {
            if(cheatNotify.vl.get(cheater) >= VL_TO_KICK) {
                cheater.kickPlayer("§cSei stato rimosso a causa di una violazione del regolamento, in caso di problemi contatta creeper215 su Discord. [AntiCheat]");
                cheatNotify.vl.replace(cheater, 0);
            }
        }
    }
}
