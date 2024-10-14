package it.creeper.roman.notify;

import it.creeper.roman.Roman;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Placeholders extends PlaceholderExpansion {
    private final Roman plugin;

    public Placeholders(Roman plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "roman";
    }

    @Override
    public @NotNull String getAuthor() {
        return "RomanAC";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }
    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if(player == null) {
            return "";
        }
        if(params.equalsIgnoreCase("vl")) {
            return String.valueOf(plugin.cheatNotify.vl.get((Player) player));
        }
        if(params.equalsIgnoreCase("check")) {
            return plugin.cheatNotify.currentPlayerCheck.get((Player) player);
        }
        if(params.equalsIgnoreCase("type")) {
            return String.valueOf(plugin.cheatNotify.currentPlayerCheckType.get((Player) player));
        }
        return null;
    }
}
