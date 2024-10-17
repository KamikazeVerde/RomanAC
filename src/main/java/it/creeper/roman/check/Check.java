package it.creeper.roman.check;

import it.creeper.roman.Roman;
import it.creeper.roman.math.Mathemathics;
import it.creeper.roman.notify.CheatNotify;
import it.creeper.roman.player.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Check {
    public Roman plugin = Roman.getInstance();
    public Mathemathics mathemathics = new Mathemathics();
    CheatNotify cheatNotify = plugin.getCheatNotify();
    public Data playerData = plugin.getData();
    public void fail(Player cheater, String check, char type, String debug) {
        cheatNotify.fail(cheater, check, type, debug);
    }
    public void possiblyBanPlayer(Player cheater) {
        plugin.getBanManager().banPlayer(cheater);
    }
    public void possiblyKickPlayer(Player cheater) {
        plugin.getBanManager().kickPlayer(cheater);
    }
    public void possiblySetbackPlayer(Player cheater) {
        plugin.getSetback().setbackPlayer(cheater);
    }
    public String getCheckName(Class<?> checkClass) {
        if(checkClass.isAnnotationPresent(CheckInfo.class)) {
            return checkClass.getAnnotation(CheckInfo.class).name();
        } else {
            return "// FAIL //";
        }
    }
    public char getCheckType(Class<?> typeClass) {
        if(typeClass.isAnnotationPresent(CheckInfo.class)) {
            return typeClass.getAnnotation(CheckInfo.class).type();
        } else {
            return '/';
        }
    }
    public void possiblyPunish(Player player, Class<?> punishmentClass) {
        String checkName = getCheckName(punishmentClass);
        String punishment = plugin.getConfig().getString("checks."+checkName.toLowerCase()+".punishment");
        if(punishment.equalsIgnoreCase("Kick")) {
            possiblyKickPlayer(player);
        } else if (punishment.equalsIgnoreCase("Ban")) {
            possiblyBanPlayer(player);
        } else if (punishment.equalsIgnoreCase("None")) {
            return;
        }


    }
}
