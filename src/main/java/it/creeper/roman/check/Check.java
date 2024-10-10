package it.creeper.roman.check;

import it.creeper.roman.Roman;
import it.creeper.roman.notify.CheatNotify;
import it.creeper.roman.player.Ban;
import org.bukkit.entity.Player;

public class Check {
    public Roman plugin = Roman.getInstance();
    CheatNotify cheatNotify = plugin.getCheatNotify();
    public void fail(Player cheater, String check) {
        cheatNotify.fail(cheater, check);
    }
    public void possiblyBanPlayer(Player cheater) {
        plugin.getBanManager().banPlayer(cheater);
    }
    public void possiblyKickPlayer(Player cheater) {
        plugin.getBanManager().kickPlayer(cheater);
    }

}
