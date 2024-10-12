package it.creeper.roman.check;

import it.creeper.roman.Roman;
import it.creeper.roman.notify.CheatNotify;
import org.bukkit.entity.Player;

public class Check {
    public Roman plugin = Roman.getInstance();
    CheatNotify cheatNotify = plugin.getCheatNotify();
    public void fail(Player cheater, String check, char type) {
        cheatNotify.fail(cheater, check, type);
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
}
