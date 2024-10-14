package it.creeper.roman.check.impl.combat.aura;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.User;
import it.creeper.roman.check.Check;
import it.creeper.roman.check.CheckInfo;
import org.bukkit.Bukkit;

@CheckInfo(name="KillAura", type='A', description = "Check for post flying packets, base Aura check")
public class AuraA extends Check implements PacketListener {
    long lastFlying;
    long delay;
    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if(event.getPacketType() == PacketType.Play.Client.INTERACT_ENTITY) {
            if(System.currentTimeMillis() - lastFlying > 2) {
                delay = System.currentTimeMillis() - lastFlying;
                fail(event.getPlayer(), getCheckName(this.getClass()), getCheckType(this.getClass()), "delay= "+delay);
                possiblySetbackPlayer(event.getPlayer());
                delay = 0;
            }
        } else {
            lastFlying = System.currentTimeMillis();
        }
    }
}
