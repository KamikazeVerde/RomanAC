package it.creeper.roman.check;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import it.creeper.roman.Roman;
import it.creeper.roman.check.impl.combat.aura.AuraA;
import it.creeper.roman.check.impl.movement.FakeGround;
import it.creeper.roman.check.impl.test.TestCheck;
import it.creeper.roman.check.impl.test.TestCheck2;

public class Register {
    Roman plugin = Roman.getInstance();
    boolean test1 = plugin.getConfig().getBoolean("checks.test-1");
    boolean aura_a = plugin.getConfig().getBoolean("checks.combat.aura.a");
    //boolean deceleration = plugin.getConfig().getBoolean("checks.movement.deceleration");
    boolean test2 = plugin.getConfig().getBoolean("checks.test-2");
    boolean groundspoof = plugin.getConfig().getBoolean("checks.movement.groundspoof");
    public void registerChecks() {
        if(test1) {
            plugin.getServer().getPluginManager().registerEvents(new TestCheck(), plugin);
        }
        if(test2) {
            plugin.getServer().getPluginManager().registerEvents(new TestCheck2(), plugin);
        }
        if(aura_a) {
            PacketEvents.getAPI().getEventManager().registerListener(new AuraA(), PacketListenerPriority.LOW);
        }
        if(groundspoof) {
            plugin.getServer().getPluginManager().registerEvents(new FakeGround(), plugin);
        }
        //PacketEvents.getAPI().getEventManager().registerListener(new Deceleration(), PacketListenerPriority.LOW);
    }
}
