package it.creeper.roman.check;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import it.creeper.roman.Roman;
import it.creeper.roman.check.annotations.PacketCheck;
import it.creeper.roman.check.impl.combat.aura.AuraA;
import it.creeper.roman.check.impl.combat.aura.AuraB;
import it.creeper.roman.check.impl.movement.FakeGround;
import it.creeper.roman.check.impl.scaffold.ScaffoldB;
import it.creeper.roman.check.impl.scaffold.ScaffoldC;
import it.creeper.roman.check.impl.scaffold.ScaffoldD;
import it.creeper.roman.check.impl.scaffold.ScaffoldE;
import it.creeper.roman.notify.PlayerJoinUpdateNotify;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class NewRegister {
    public boolean aurab;
    Roman plugin = Roman.getInstance();
    ArrayList<Check> modules = new ArrayList<>();
    public ArrayList<Listener> recorderA = new ArrayList<>();
    public void registerModulesNew() {
        modules.add(new ScaffoldE());
        modules.add(new ScaffoldB());
        modules.add(new ScaffoldD());
        modules.add(new ScaffoldC());
        modules.add(new FakeGround());
        modules.add(new AuraA());
        //modules.add(new AuraB());

        plugin.getServer().getPluginManager().registerEvents(new PlayerJoinUpdateNotify(), plugin);

        //Basic register
        /*
        for(Check m : modules) {
            PacketEvents.getAPI().getEventManager().registerListener((PacketListener) m, PacketListenerPriority.LOW);
        }

         */



         /*
        for(Listener l : recorderA) {
            Bukkit.getServer().getPluginManager().registerEvents(l, plugin);
        }

          */

        PacketEvents.getAPI().getEventManager().registerListener(new AuraB(), PacketListenerPriority.LOW);



        for(Check m : modules) {
            if(m.getClass().isAnnotationPresent(PacketCheck.class)) {
                if (plugin.getConfig().getBoolean("checks." + m.getClass().getAnnotation(CheckInfo.class).name().toLowerCase() + "." + String.valueOf(m.getClass().getAnnotation(CheckInfo.class).type()).toLowerCase())) {
                    PacketEvents.getAPI().getEventManager().registerListener((PacketListener) m, PacketListenerPriority.LOW);
                }
            } else {
                if (plugin.getConfig().getBoolean("checks." + m.getClass().getAnnotation(CheckInfo.class).name().toLowerCase() + "." + String.valueOf(m.getClass().getAnnotation(CheckInfo.class).type()).toLowerCase())) {
                    plugin.getServer().getPluginManager().registerEvents((Listener) m, plugin);
                }
            }
        }




    }
}
