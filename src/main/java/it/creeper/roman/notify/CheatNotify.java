package it.creeper.roman.notify;

import it.creeper.roman.Roman;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CheatNotify {
    Roman plugin = Roman.getInstance();
    public int VL_TO_ALERT = plugin.getConfig().getInt("alert-vl");
    public Map<Player, Integer> flagCount = new HashMap<>();
    public Map<Player, Integer> vl = new HashMap<>();
    //Server server = Bukkit.getServer();
    /*
    public CheatNotify() {
        //metto 0 di vl a tutti i player così ho qualcosa di inizializzato
        Collection<? extends Player> onlinePlayers = server.getOnlinePlayers();
        for(Player player : onlinePlayers){
            vl.put(player, 0);
        }
    }

     */
    public void fail(Player cheater, String check) {
        flagCount.put(cheater, flagCount.getOrDefault(cheater, 0) + 1);
        if(flagCount.get(cheater) % VL_TO_ALERT == 0) {
            vl.put(cheater, vl.getOrDefault(cheater, 0)+flagCount.get(cheater));
            flagCount.replace(cheater, 0);
            //System.out.println("Funziona");
            Bukkit.broadcast(ChatColor.translateAlternateColorCodes('&', "&7[&4&lRoman&r&7] "+"&6"+ cheater.getName() + " &r&7potrebbe star utilizzando &2&l"+check+" &r&7VL: " + vl.get(cheater)), "roman.notify");
        }
    }
}
