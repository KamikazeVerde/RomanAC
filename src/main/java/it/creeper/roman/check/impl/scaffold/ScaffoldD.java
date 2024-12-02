package it.creeper.roman.check.impl.scaffold;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerBlockPlacement;
import it.creeper.roman.Roman;
import it.creeper.roman.check.Check;
import it.creeper.roman.check.CheckInfo;
import it.creeper.roman.check.annotations.PacketCheck;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

@PacketCheck
@CheckInfo(name = "Scaffold", type = 'D', description = "Checks for strict yaw change")
public class ScaffoldD extends Check implements PacketListener {
    int ticks;
    float pitch;
    float lastPitch;
    float yaw;
    float lastYaw;
    float diffP;
    float diffY;

    WrapperPlayClientPlayerBlockPlacement lastPlacement;

    boolean isSnap(float pitch, float lastPitch) {
        if(Math.abs(pitch - lastPitch) >= 24) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent e) {
        Player player = e.getPlayer();
        if(e.getPacketType() == PacketType.Play.Client.PLAYER_BLOCK_PLACEMENT) {
            WrapperPlayClientPlayerBlockPlacement packet = new WrapperPlayClientPlayerBlockPlacement(e);
            if(player.getItemInHand().getType().isBlock()) {
                if(lastPlacement != null) {
                    ticks = 0;
                    Location loc = player.getLocation();
                    pitch = loc.getPitch();
                    yaw = loc.getYaw();
                    if(!(playerData.deltasXZ.get(player) > 0.215) || !(playerData.deltasXZ.get(player) < 0.285) || player.isSprinting() || !playerData.isOnGround(player)) {
                        return;
                    }
                    if(ticks <= 20 && isSnap(pitch, lastPitch) && !player.isSneaking() && playerData.isOnGround(player)) {

                        diffP = pitch - lastPitch;
                        diffY = yaw - lastYaw;
                        cheatNotify.fail(player);
                        //fail(player, getCheckName(this.getClass()), getCheckType(this.getClass()), "pitch diff:"+diffP+" yaw diff:"+diffY);
                    } else {
                        pitch = lastPitch;
                        yaw = lastPitch;
                        diffY = 0;
                        diffP = 0;
                    }
                }
                lastPlacement = packet;
            }
        }
        else if(e.getPacketType() == PacketType.Play.Client.PLAYER_FLYING) {
            ticks++;
            lastPitch = player.getLocation().getPitch();
            lastYaw = player.getLocation().getYaw();
        }
    }
    /*
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        ticks = 0;
        Player player = e.getPlayer();
        Location loc = player.getLocation();
        pitch = loc.getPitch();
        yaw = loc.getYaw();
        if(ticks <= 10 && (int)pitch != (int)lastPitch && !player.isSneaking()) {
            player.sendMessage("Pitch: "+pitch+" Last Pitch: "+lastPitch);
            player.sendMessage("Yaw: "+yaw+" Last Yaw: "+lastYaw);
            diffP = pitch - lastPitch;
            diffY = yaw - lastYaw;
            fail(player, getCheckName(this.getClass()), getCheckType(this.getClass()), "pitch diff:"+diffP+" yaw diff:"+diffY);
        } else {
            pitch = lastPitch;
            yaw = lastPitch;
            diffY = 0;
            diffP = 0;
        }
    }

     */
}
