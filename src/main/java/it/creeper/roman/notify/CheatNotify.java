package it.creeper.roman.notify;

import it.creeper.roman.Roman;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CheatNotify {
    Roman plugin = Roman.getInstance();


    public int VL_TO_ALERT = plugin.getConfig().getInt("violation-settings.alert-vl");
    public String ALERT_PREFIX = plugin.getConfig().getString("messages.prefix");
    public String ALERT_MESSAGE = plugin.getConfig().getString("messages.alert-message");
    public Map<Player, String> currentPlayerCheck = new HashMap<>();
    public Map<Player, Character> currentPlayerCheckType = new HashMap<>();
    public Map<Player, Integer> flagCount = new HashMap<>();
    public Map<Player, Integer> vl = new HashMap<>();
    //Server server = Bukkit.getServer();
    public void fail(Player cheater, String check, char type, String debug) {
        currentPlayerCheck.put(cheater, check);
        currentPlayerCheckType.put(cheater, type);
        flagCount.put(cheater, flagCount.getOrDefault(cheater, 0) + 1);
        if(flagCount.get(cheater) % VL_TO_ALERT == 0) {

            //ALERT_MESSAGE = PlaceholderAPI.setPlaceholders((OfflinePlayer) cheater, ALERT_MESSAGE);
            vl.put(cheater, vl.getOrDefault(cheater, 0)+flagCount.get(cheater));
            ALERT_MESSAGE = PlaceholderAPI.setPlaceholders(cheater, ALERT_MESSAGE);
            flagCount.replace(cheater, 0);
            //System.out.println("Funziona");
            //Bukkit.broadcast(ChatColor.translateAlternateColorCodes('&', ALERT_PREFIX + " "+"&6"+ cheater.getName() + " &r&7potrebbe star utilizzando &2&l"+check+" &r&7VL: " + vl.get(cheater)), "roman.notify");
            Bukkit.broadcast(ChatColor.translateAlternateColorCodes('&', ALERT_PREFIX + " "+ALERT_MESSAGE+" &7"+debug), "roman.notify");
            ALERT_MESSAGE = plugin.getConfig().getString("messages.alert-message");

        }
    }
}
