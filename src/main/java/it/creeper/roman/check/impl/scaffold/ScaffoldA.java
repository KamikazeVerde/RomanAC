package it.creeper.roman.check.impl.scaffold;

import it.creeper.roman.Roman;
import it.creeper.roman.check.Check;
import it.creeper.roman.check.CheckInfo;
import it.creeper.roman.player.Data;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.HashMap;
import java.util.Map;

@CheckInfo(name="Scaffold", type='A', description="Checks for impossible godbridge style scaffold")
public class ScaffoldA extends Check implements Listener {

    //Data data = plugin.getData();
    Map<Player, Integer> blockCount = new HashMap<>();
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player cheater = e.getPlayer();
        Location loc = cheater.getLocation();
        double pitch = loc.getPitch();

        if(!(playerData.deltasXZ.get(cheater) > 0.215) || !(playerData.deltasXZ.get(cheater) < 0.285)) {
            return;
        }
        this.blockCount.put(cheater, this.blockCount.getOrDefault(cheater, 0)+1);
        if(!playerData.isOnGround(cheater) && !(cheater.isSneaking()) && pitch > 0 &&  pitch < 68 || cheater.isSneaking() && playerData.isOnGround(cheater) && pitch > 70 && pitch < 88) {
            this.blockCount.remove(cheater);
            return;
        }
        //System.out.println("Statement 1");
        if(playerData.jumpingPlayers.contains(cheater)) {
            if(this.blockCount.get(cheater) == null) {
                //System.out.println("Statement 1.2");
                return;
            }
            Block blockUnderPlayer = cheater.getLocation().subtract(0, 1, 0).getBlock();
            //this.blockCount.remove(cheater);
            if(!(blockUnderPlayer.getType().isSolid())) {
                //System.out.println("Statement 1.4");
                this.blockCount.remove(cheater);
                return;
            }
            return;
        }
        if(this.blockCount.get(cheater) == null) {
            //System.out.println("Statement 1.2");
            return;
        }
        //System.out.println("Statement 2");
        if(this.blockCount.get(cheater) >= 10 && playerData.isOnGround(cheater) && !(cheater.isSneaking()) && pitch > 70 && pitch  < 88) {
            //System.out.println("Cheating Scaffold");
            fail(cheater, getCheckName(this.getClass()), getCheckType(this.getClass()), "count=" + this.blockCount.get(cheater));
            //possiblyKickPlayer(cheater);
            possiblyPunish(cheater, this.getClass());
        }

    }
    /*
    private boolean isOnGround(Player player) {
        Location loc = player.getLocation();
        loc.setY(loc.getY() - 0.1);
        return loc.getBlock().getType().isSolid();
    }

     */
}
