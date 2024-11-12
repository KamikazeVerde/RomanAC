package it.creeper.roman.check.impl.movement;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.creeper.roman.check.Check;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MovementA extends Check implements PacketListener {
    double deltaY, Y, lastY, deltaX, X, lastX, deltaZ, Z, lastZ;
    double jumpMotion = 0.41999998688697815;
    @Override
    public void onPacketSend(PacketSendEvent e) {
        Player player = e.getPlayer();
        if(e.getPacketType() == PacketType.Play.Server.UPDATE_VIEW_POSITION) {
            Y = player.getLocation().getY();
            X = player.getLocation().getX();
            Z = player.getLocation().getZ();
        } else {
            /*
            lastY = Y;
            lastX = X;
            lastZ = Z;
            Y = player.getLocation().getY();
            X = player.getLocation().getX();
            Z = player.getLocation().getZ();
            deltaY = lastY - Y;
            deltaX = lastX - X;
            deltaZ = lastZ - Z;

             */
        }
    }
    @Override
    public void onPacketReceive(PacketReceiveEvent e) {

        Player player = e.getPlayer();
        lastY = Y;
        lastX = X;
        lastZ = Z;
        Y = player.getLocation().getY();
        X = player.getLocation().getX();
        Z = player.getLocation().getZ();
        deltaY = lastY - Y;
        deltaX = lastX - X;
        deltaZ = lastZ - Z;
        double predictedMotionX, predictedMotionZ, predictedMotionY;
        if(e.getPacketType() == PacketType.Play.Client.PLAYER_POSITION) {
            float f = player.getLocation().getYaw() * 0.017453292F;
            predictedMotionX = deltaX - (Math.sin(f) * 0.2F);
            predictedMotionZ = deltaZ + (Math.cos(f) * 0.2F);
            player.sendMessage("MotionX: " + deltaX + " MotionZ: " + deltaZ + " predictedX: " + predictedMotionX + " predictedZ: " + predictedMotionZ);

            /*

            if(deltaY == jumpMotion) {
                float f = player.getLocation().getYaw() * 0.017453292F;
                predictedMotionX = deltaX - (Math.sin(f) * 0.2F);
                predictedMotionZ = deltaZ - (Math.cos(f) * 0.2F);
                player.sendMessage("MotionX: " + deltaX + " MotionZ: " + deltaZ + " predictedX: " + predictedMotionX + " predictedZ: " + predictedMotionZ);
            }

             */
        }
    }
    /*
    public void onPacketReceive(PacketReceiveEvent e) {
        Player player = e.getPlayer();
        if(e.getPacketType() == PacketType.Play.Client.PLAYER_POSITION) {
            Y = player.getLocation().getY();
        } else {
            lastY = Y;
            Y = player.getLocation().getY();
            deltaY = lastY - Y;
        }
        if(deltaY != ) {
            player.sendMessage(String.valueOf(deltaY));
        }
    }

     */
}
