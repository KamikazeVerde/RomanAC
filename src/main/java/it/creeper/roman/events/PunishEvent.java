package it.creeper.roman.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PunishEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private String type;

    public PunishEvent(Player player, String type) {
        this.player = player;
        this.type = type;
    }

    public Player getPlayer() { return player; }
    public String getType() {  return type; }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
