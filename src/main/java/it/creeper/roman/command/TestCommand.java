package it.creeper.roman.command;

import it.creeper.roman.Roman;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Bukkit.getLogger().info("ciao");
        Roman.getInstance().newCheckRegister.aurab = true;
        return false;
    }

    public String CC(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
