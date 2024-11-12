package it.creeper.roman.check.impl.scaffold;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.creeper.roman.check.Check;
import it.creeper.roman.check.CheckInfo;
import jdk.nashorn.internal.ir.Block;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.io.*;

@CheckInfo(name = "Scaffold", type = 'E', description = "Checks for constinc? overkidding")
public class ScaffoldE extends Check implements Listener {
    //dopo pe
    public static int NaN = 0;

    long ticksOnBlock;
    long lastJumpTick;
    int streak;
    long lastLastJumpTick;
    int first;
    int i;
    boolean onBlock;
    boolean blockPlace;

    File file = new File("scaffoldanalysis.txt");
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) throws IOException {
        double pitch = e.getPlayer().getLocation().getPitch();
        FileOutputStream fos = new FileOutputStream(file);
        //PrintStream ps = new PrintStream();
        PrintWriter ps = new PrintWriter(new FileWriter("ciaoamicomeo.txt", true));

        //e.getPlayer().sendMessage(String.valueOf(ticksOnBlock));
        if(onBlock) {
            //ticksOnBlock++;
            ticksOnBlock = System.currentTimeMillis();
        }
        if(!onBlock) {
            ticksOnBlock = 0;
        }
        if(i == 5) {
            lastLastJumpTick = lastJumpTick;
            i = 0;
        }
        if(!playerData.isOnGround(e.getPlayer())) {
            lastJumpTick = ticksOnBlock;
            i++;
        }
        long k = lastJumpTick - lastLastJumpTick;
        if(lastLastJumpTick == lastJumpTick && k <= 60 && k != 1) {
            streak++;
        }
        k = lastJumpTick - lastLastJumpTick;
        //if(!playerData.isOnGround(e.getPlayer())) e.getPlayer().sendMessage("differenza jump: "+ k);

        e.getPlayer().sendMessage("differenza: "+ k);
        //e.getPlayer().sendMessage("pitch: "+pitch);
        //streak == 5
        if(k <= 60 && pitch > 60 && pitch < 80) {
            cheatNotify.fail(e.getPlayer());
            //fail(e.getPlayer(), getCheckName(this.getClass()), getCheckType(this.getClass()), "AAAA");
            //e.getPlayer().sendMessage("blockticks:"+ticksOnBlock+" lastjumpticks:"+lastJumpTick+" lastlast"+lastLastJumpTick);
            streak = 0;
            first = 0;
            ticksOnBlock = 0;
            lastLastJumpTick = 0;
            lastJumpTick = 0;
            onBlock = false;
        }
        if(blockPlace) {
            ticksOnBlock = 0;
            blockPlace = false;
            first = 1;
        }

    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        blockPlace = true;
        Location loc = e.getPlayer().getLocation();
        loc.setY(loc.getY()-1);
        if(loc.getBlock().getType().isSolid()) {
            onBlock = true;
        }


    }

}
