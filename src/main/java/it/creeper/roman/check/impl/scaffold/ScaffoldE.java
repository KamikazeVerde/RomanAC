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
@CheckInfo(name = "Scaffold", type = 'F', description = "Checks for scaffold alal")
public class ScaffoldE extends Check implements PacketListener {
    private WrapperPlayClientPlayerBlockPlacement lastPlacement;
    Map<Player, Integer> streak = new HashMap<>();
    int limit = 8;

    @Override
    public void onPacketReceive(PacketReceiveEvent e) {
        limit = 8;
        if (e.getPacketType() == PacketType.Play.Client.PLAYER_BLOCK_PLACEMENT) {
            Player player = e.getPlayer();
            if (player == null || playerData == null || cheatNotify == null || plugin == null) return;

            WrapperPlayClientPlayerBlockPlacement packet = new WrapperPlayClientPlayerBlockPlacement(e);

            if (player.getItemInHand() == null) return;
            Material heldMaterial = player.getItemInHand().getType();

            if (lastPlacement != null) {
                if (heldMaterial != null && heldMaterial.isBlock()) {
                    Double deltaXZ = playerData.deltasXZ.get(player);
                    if (deltaXZ == null || !(deltaXZ > 0.215f) || !(deltaXZ < 0.285f)) {
                        return;
                    }

                    streak.put(player, streak.getOrDefault(player, 0) + 1);

                    float pitch = player.getLocation().getPitch();

                    boolean onGround = playerData.isOnGround(player);
                    boolean sneaking = player.isSneaking();

                    if (!onGround && !sneaking && pitch > 0 && pitch < 68
                            || sneaking && onGround && pitch > 70 && pitch < 88) {
                        streak.remove(player);
                        return;
                    }

                    if (playerData.jumpingPlayers != null && playerData.jumpingPlayers.contains(player)) {
                        Integer playerStreak = streak.get(player);
                        if (playerStreak == null) {
                            return;
                        }

                        Block blockUnderPlayer = player.getLocation().subtract(0, 1, 0).getBlock();
                        if (blockUnderPlayer == null || !blockUnderPlayer.getType().isSolid()) {
                            streak.remove(player);
                            return;
                        }
                        return;
                    }

                    Integer playerStreak = streak.get(player);
                    if (playerStreak == null) {
                        return;
                    }

                    if (playerStreak >= limit && onGround && !sneaking && pitch > 70 && pitch < 88) {
                        cheatNotify.fail(player);
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
