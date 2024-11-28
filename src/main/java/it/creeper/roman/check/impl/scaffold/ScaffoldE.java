package it.creeper.roman.check.impl.scaffold;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerBlockPlacement;
import it.creeper.roman.check.Check;
import it.creeper.roman.check.CheckInfo;
import it.creeper.roman.check.annotations.PacketCheck;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@PacketCheck
@CheckInfo(name = "Scaffold", type='F', description = "Checks for scaffold alal")
public class ScaffoldE extends Check implements PacketListener {
    private WrapperPlayClientPlayerBlockPlacement lastPlacement;
    Map<Player, Integer> streak = new HashMap<>();
    int limit = 8;

    @Override
    public void onPacketReceive(PacketReceiveEvent e) {
        limit = 8;
        if(e.getPacketType() == PacketType.Play.Client.PLAYER_BLOCK_PLACEMENT) {
            Player player = e.getPlayer();
            WrapperPlayClientPlayerBlockPlacement packet = new WrapperPlayClientPlayerBlockPlacement(e);
            Material heldMaterial = player.getItemInHand().getType();
            if(lastPlacement != null) {
                if(heldMaterial != null && heldMaterial.isBlock()) {
                    // CHECK
                    float pitch = player.getLocation().getPitch();
                    if(!(playerData.deltasXZ.get(player) > 0.215) || !(playerData.deltasXZ.get(player) < 0.285)) {
                        return;
                    }
                    streak.put(player, streak.getOrDefault(player, 0)+1);
                    if(!playerData.isOnGround(player) && !(player.isSneaking()) && pitch > 0 &&  pitch < 68 || player.isSneaking() && playerData.isOnGround(player) && pitch > 70 && pitch < 88) {
                        streak.remove(player);
                        return;
                    }
                    if(playerData.jumpingPlayers.contains(player)) {
                        if(streak.get(player) == null) {
                            return;
                        }
                        Block blockUnderPlayer = player.getLocation().subtract(0, 1, 0).getBlock();
                        if(!(blockUnderPlayer.getType().isSolid())) {
                            streak.remove(player);
                            return;
                        }
                        return;
                    }
                    if(streak.get(player) == null) {
                        return;
                    }
                    if(streak.get(player) >= limit && playerData.isOnGround(player) && !(player.isSneaking()) && pitch > 70 && pitch  < 88) {
                        cheatNotify.fail(player);
                        //fail(player, getCheckName(this.getClass()), getCheckType(this.getClass()), "count=" + streak.get(player) + " Checks for max block bridge streak");
                        Bukkit.getScheduler().runTask(plugin, () -> {
                            possiblyPunish(player, this.getClass());
                        });
                    }
                }
            }

            lastPlacement = packet;
        }
    }
}
