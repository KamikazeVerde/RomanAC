package it.creeper.roman.events;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
public class FlagEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private char type;
    private String check;

    public FlagEvent(Player player, String check, char type) {
        this.player = player;
        this.check = check;
        this.type = type;
    }

    public Player getPlayer() {  return player;  }
    public char getType() { return type; }
    public String getCheckName() { return check; }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
