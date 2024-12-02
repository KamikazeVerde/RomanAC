package it.creeper.roman.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MitigateEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private String mitigateType;

    public MitigateEvent(Player player, String mitigateType) {
        this.player = player;
        this.mitigateType = mitigateType;
    }

    public Player getPlayer() { return player; }

    public String getMitigateType() { return mitigateType; }

    public Location getLocation() { return player.getLocation(); }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
