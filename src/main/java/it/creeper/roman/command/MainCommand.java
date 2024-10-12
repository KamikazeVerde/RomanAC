package it.creeper.roman.command;

import it.creeper.roman.Roman;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {
    Roman plugin = Roman.getInstance();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player)) {
            Bukkit.getLogger().info("This server is running Roman AntiCheat made by Creeper215 with <3");
            Bukkit.getLogger().info("https://github.com/KamikazeVerde/RomanAC/");
            return true;
        }
        Player player = (Player) commandSender;
        if(!player.hasPermission("roman.main") || !player.hasPermission("roman.notify")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4★ Roman&6AC &7by &aCreeper&2215"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "GitHub: &bgithub.com/KamikazeVerde/RomanAC"));
        } else if (player.hasPermission("roman.main") && player.hasPermission("roman.notify")) {
            if(args.length == 0) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&4★ Roman&6AC &7by &aCreeper&2215"));
                player.sendMessage(ChatColor.BLUE + "- /roman player [vl {playername}]");
                return false;
            }
            else if(args[0].equalsIgnoreCase("player")) {
                if(args.length == 1) {
                    player.sendMessage(ChatColor.RED + "Format: /roman player vl [Player name]");
                    return false;
                }
                if(args[1].equalsIgnoreCase("vl")) {
                    if(args.length == 2) {
                        player.sendMessage(ChatColor.RED + "Format: /roman player vl [Player name]");
                        return false;
                    } else {
                        Player playerinfo = Bukkit.getPlayer(args[2]);
                        if (playerinfo == null) {
                            player.sendMessage(ChatColor.RED + "Player with that name doesn't exist");
                            return false;
                        } else if (plugin.getCheatNotify().vl.get(playerinfo) == null) {
                            player.sendMessage(ChatColor.RED + "This player has no VL");
                        } else {
                            int PLAYER_VL = plugin.getCheatNotify().vl.get(playerinfo);
                            player.sendMessage(ChatColor.GREEN + "VL Info for "+playerinfo.getName()+ChatColor.AQUA + " | VL: " + ChatColor.GOLD + PLAYER_VL + ChatColor.AQUA +" Setback Value: " + ChatColor.GOLD + PLAYER_VL/10);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
