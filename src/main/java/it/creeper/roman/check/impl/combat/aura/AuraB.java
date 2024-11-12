package it.creeper.roman.check.impl.combat.aura;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.creeper.roman.check.Check;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class AuraB extends Check implements PacketListener {
    ArrayList<Float> yaw = new ArrayList<>();
    float currentGCD;
    float yaw1, lastYaw = 0;
    @Override
    public void onPacketReceive(PacketReceiveEvent e) {
        Player player = e.getPlayer();
        if(e.getPacketType() == PacketType.Play.Client.PLAYER_POSITION_AND_ROTATION) {
            yaw1 = player.getLocation().getYaw();
            yaw.add(player.getLocation().getYaw());
            //System.out.println(player.getLocation().getYaw());
        }
        if(yaw != null) {
            if(yaw.size() == 2) {
                currentGCD = playerData.gcd(yaw.get(0), yaw.get(1));
                lastYaw = yaw1;
                yaw1 = player.getLocation().getYaw();
                player.sendMessage("GCD: " + currentGCD);

                yaw.clear();
            }
        }
    }
}
