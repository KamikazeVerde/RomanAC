package it.creeper.roman.check.impl.scaffold;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerBlockPlacement;
import it.creeper.roman.Roman;
import it.creeper.roman.check.Check;
import it.creeper.roman.check.CheckInfo;
import it.creeper.roman.check.annotations.PacketCheck;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;

@PacketCheck
@CheckInfo(name = "Scaffold", type = 'C', description = "Checks for Same Y Scaffold")
public class ScaffoldC extends Check implements PacketListener {
    Roman plugin = Roman.getInstance();
    double Y = 0, lastY = 0;
    Map<Player, Integer> streak = new HashMap<>();
    Map<Player, Boolean> flag = new HashMap<>();
    WrapperPlayClientPlayerBlockPlacement lastPlacement;
    int buff;
    @Override
    public void onPacketReceive(PacketReceiveEvent e) {
        Player player = e.getPlayer();
        if(e.getPacketType() == PacketType.Play.Client.PLAYER_BLOCK_PLACEMENT) {
            double pitch = player.getLocation().getPitch();
            WrapperPlayClientPlayerBlockPlacement packet = new WrapperPlayClientPlayerBlockPlacement(e);
            Material heldMaterial = player.getItemInHand().getType();
            if(lastPlacement != null) {
                if(heldMaterial.isBlock()) {
                    //Check
                    lastY = Y;
                    if(!playerData.isOnGround(player)) {
                        //Y = (int) player.getLocation().getY();
                        streak.put(player, streak.getOrDefault(player, 0)+1);
                    }
                    Y = player.getLocation().getY();

                    if(streak.get(player) >= 3) {
                        if(!playerData.isOnGround(player) && !player.isSneaking() && lastY == Y && pitch > 70 && pitch < 88 && playerData.jumpingPlayers.contains(player)) {
                            //player.sendMessage("lastY: " + lastY + " Y: " +  Y);
                            flag.put(player, true);
                            //cheatNotify.fail(player, "samey checks");
                        }
                    }
                }
            }
            lastPlacement = packet;
            buff++;
            if(buff > 10) {
                if(flag.get(player) && playerData.getOffGroundTicks(player) >= 18) {
                    cheatNotify.fail(player);
                    flag.put(player, false);
                }
            }
        }

    }
}
