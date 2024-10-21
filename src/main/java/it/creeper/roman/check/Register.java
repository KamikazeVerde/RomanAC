package it.creeper.roman.check;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import it.creeper.roman.Roman;
import it.creeper.roman.check.impl.combat.aura.AuraA;
import it.creeper.roman.check.impl.movement.FakeGround;
import it.creeper.roman.check.impl.scaffold.ScaffoldA;
import it.creeper.roman.check.impl.scaffold.ScaffoldB;
import it.creeper.roman.check.impl.scaffold.ScaffoldC;
import it.creeper.roman.check.impl.scaffold.ScaffoldD;
import it.creeper.roman.check.impl.test.TestCheck;
import it.creeper.roman.check.impl.test.TestCheck2;

public class Register {
    Roman plugin = Roman.getInstance();
    boolean test1 = plugin.getConfig().getBoolean("checks.test-1");
    boolean aura_a = plugin.getConfig().getBoolean("checks.combat.aura.a");
    //boolean deceleration = plugin.getConfig().getBoolean("checks.movement.deceleration");
    boolean test2 = plugin.getConfig().getBoolean("checks.test-2");
    boolean groundspoof = plugin.getConfig().getBoolean("checks.movement.groundspoof");
    boolean scaffold_a = plugin.getConfig().getBoolean("checks.scaffold.a");
    boolean scaffold_b = plugin.getConfig().getBoolean("checks.scaffold.b");
    boolean scaffold_c = plugin.getConfig().getBoolean("checks.scaffold.c");
    boolean scaffold_d = plugin.getConfig().getBoolean("checks.scaffold.d");
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
        if(scaffold_a) {
            plugin.getServer().getPluginManager().registerEvents(new ScaffoldA(), plugin);
        }
        if(scaffold_b) {
            PacketEvents.getAPI().getEventManager().registerListener(new ScaffoldB(), PacketListenerPriority.LOW);
        }
        if(scaffold_c) {
            plugin.getServer().getPluginManager().registerEvents(new ScaffoldC(), plugin);
        }
        if(scaffold_d) {
            PacketEvents.getAPI().getEventManager().registerListener(new ScaffoldD(), PacketListenerPriority.LOW);
        }
        //plugin.getServer().getPluginManager().registerEvents(new ScaffoldD(), plugin);
    }
}
