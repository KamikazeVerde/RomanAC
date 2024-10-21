package it.creeper.roman.check.impl.movement;

import it.creeper.roman.check.Check;
import it.creeper.roman.check.CheckInfo;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

@CheckInfo(name = "Movement", type = 'A', description = "Checks for ground spoofing")
public class FakeGround extends Check implements Listener {
    private int buff = 0;
    private static double groundY = 1 / 64.;
    public boolean lastServerGround = true;



    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        boolean localGround = e.getPlayer().isOnGround();
        boolean serverGround = e.getTo().getY() % groundY < 0.0001;
        if(localGround != lastServerGround) {
            if(++buff > 1) {
                fail(e.getPlayer(), getCheckName(this.getClass()), getCheckType(this.getClass()), "localGround=" + localGround + " serverGround="+lastServerGround);
                possiblySetbackPlayer(e.getPlayer());
                //possiblyKickPlayer(e.getPlayer());
                possiblyPunish(e.getPlayer(), this.getClass());
            }
        } else if (buff > 0) buff--;

        lastServerGround = serverGround;
    }
}
