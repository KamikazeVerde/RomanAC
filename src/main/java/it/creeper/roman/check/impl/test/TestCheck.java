package it.creeper.roman.check.impl.test;

import it.creeper.roman.check.Check;
import it.creeper.roman.check.CheckInfo;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

@CheckInfo(name="Test", type='Z', description = "Test Check 1 (Block Place flag)")
public class TestCheck extends Check implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        possiblySetbackPlayer(e.getPlayer());
        fail(e.getPlayer(), getCheckName(this.getClass()), getCheckType(this.getClass()), "test1");
        possiblyKickPlayer(e.getPlayer());
        e.setCancelled(true);
    }
}
