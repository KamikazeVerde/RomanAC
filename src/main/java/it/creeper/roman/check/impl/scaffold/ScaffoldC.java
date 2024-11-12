package it.creeper.roman.check.impl.scaffold;

import it.creeper.roman.Roman;
import it.creeper.roman.check.Check;
import it.creeper.roman.check.CheckInfo;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;

@CheckInfo(name = "Scaffold", type = 'C', description = "Checks for Same Y Scaffold")
public class ScaffoldC extends Check implements Listener {
    Roman plugin = Roman.getInstance();

    double lastY;
    int flagY;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        lastY = e.getPlayer().getLocation().getY();
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        Location loc = player.getLocation();
        /*
        player.sendMessage("Last Y: "+(int)lastY);
        player.sendMessage("Block Place Y: "+(int)loc.getY());
         */

        if(playerData.isOnGround(player) || player.isSneaking() || (int) lastY != (int) player.getLocation().getY()) {
            //player.sendMessage("Statement 1");
            return;
        }
        flagY = (int)player.getLocation().getY();
        //player.sendMessage("Statement 2");
        if(playerData.jumpingPlayers.contains(player) && !playerData.isOnGround(player) && !player.isSneaking() && flagY == (int)lastY) {
            //fail(player, getCheckName(this.getClass()), getCheckType(this.getClass()), "same y");
            cheatNotify.fail(player);
            possiblyPunish(player, this.getClass());
            //player.sendMessage("Statement 3");
            possiblySetbackPlayer(player);
        } else {
            //player.sendMessage("Statement 4");
            lastY = loc.getY();
            flagY = 0;
        }
    }
}
