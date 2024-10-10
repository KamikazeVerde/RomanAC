package it.creeper.roman.check.impl;

import it.creeper.roman.check.Check;
import it.creeper.roman.check.CheckInfo;
import it.creeper.roman.notify.CheatNotify;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

@CheckInfo(name="Test")
public class TestCheck extends Check implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        fail(e.getPlayer(), this.getClass().getAnnotation(CheckInfo.class).name());
        possiblyKickPlayer(e.getPlayer());
        e.setCancelled(true);
    }
}
