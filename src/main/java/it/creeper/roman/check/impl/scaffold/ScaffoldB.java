package it.creeper.roman.check.impl.scaffold;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.world.BlockFace;
import com.github.retrooper.packetevents.util.Vector3i;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerBlockPlacement;
import it.creeper.roman.Roman;
import it.creeper.roman.check.Check;
import it.creeper.roman.check.CheckInfo;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@CheckInfo(name = "Scaffold", type = 'B', description = "Checks for single clicking bridge (Heuristics)")
public class ScaffoldB extends Check implements PacketListener {
    private WrapperPlayClientPlayerBlockPlacement lastPlacement;
    private int ticks;
    private int rightClicksBeforePlace;
    private int streak;


    // Credits to Overblurred for making a tutorial for this check.

    @Override
    public void onPacketReceive(PacketReceiveEvent e) {
        if(e.getPacketType() == PacketType.Play.Client.PLAYER_BLOCK_PLACEMENT) {
            Player player = e.getPlayer();
            WrapperPlayClientPlayerBlockPlacement packet = new WrapperPlayClientPlayerBlockPlacement(e);
            if(packet.getFace() != BlockFace.OTHER) {
                if(lastPlacement != null) {
                    Vector3i pos = packet.getBlockPosition().offset(packet.getFace());
                    Vector3i posPlaced = packet.getBlockPosition();
                    Vector3i lastPos = lastPlacement.getBlockPosition().offset(lastPlacement.getFace());

                    Material heldMaterial = player.getItemInHand().getType();

                    if(heldMaterial != null && heldMaterial.isBlock() && player.getLocation().getY() >= pos.getY() + 1 && packet.getFace() != BlockFace.UP && packet.getFace() != BlockFace.DOWN && posPlaced.equals(lastPos)) {
                        int minimumTicks = player.isSneaking() ? 6 : 10;
                        if(ticks < minimumTicks && rightClicksBeforePlace == 0) {
                            if(++streak >= 10) {
                                fail(player, getCheckName(this.getClass()), getCheckType(this.getClass()), "Perfect rightclick streak:"+streak);
                                //possiblyKickPlayer(player);
                                //possiblyPunish(player, this.getClass());
                                Bukkit.getScheduler().runTask(Roman.getInstance(), () -> possiblyPunish(player, this.getClass()));
                            }
                        } else {
                            streak = 0;
                        }
                    }
                }

                lastPlacement = packet;
                ticks = 0;
                rightClicksBeforePlace = 0;
            } else {
                rightClicksBeforePlace++;
            }
        } else if (e.getPacketType() == PacketType.Play.Client.PLAYER_FLYING) {
            ticks++;
        }
    }
}
