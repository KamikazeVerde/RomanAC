package it.creeper.roman.check.impl.test;

import it.creeper.roman.check.Check;
import it.creeper.roman.check.CheckInfo;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

@CheckInfo(name="TestCheck2", type='B', description = "Test Check 2 (Flag on move)")
public class TestCheck2 extends Check implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        cheatNotify.fail(e.getPlayer());
        //fail(e.getPlayer(), getCheckName(this.getClass()), getCheckType(this.getClass()), "test2" + "");
        possiblyBanPlayer(e.getPlayer());
    }
}
