package it.creeper.roman.check.impl.combat.aura;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.creeper.roman.check.Check;
import it.creeper.roman.check.CheckInfo;
import it.creeper.roman.check.annotations.PacketCheck;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@PacketCheck
@CheckInfo(name = "Aura", type = 'B', description = "Rotation deviation testing")
public class AuraB extends Check implements PacketListener {

    List<Float> yaw, pitch = new ArrayList<Float>();
    int ticks;
    float deviationyaw, deviationpitch;

    @Override
    public void onPacketReceive(PacketReceiveEvent e) {
        Player player = e.getPlayer();
        if(e.getPacketType() == PacketType.Play.Client.PLAYER_POSITION_AND_ROTATION || e.getPacketType() == PacketType.Play.Client.PLAYER_ROTATION) {
            if(plugin.newCheckRegister.aurab) {
                Bukkit.getLogger().info("ciao");
                Bukkit.getScheduler().runTaskTimer(plugin, () -> {
                    yaw.add(player.getLocation().getYaw());
                    pitch.add(player.getLocation().getPitch());
                    ticks++;
                }, 1L, 40L);
                deviationyaw = mathemathics.deviation(convertListToArray(yaw));
                deviationpitch = mathemathics.deviation(convertListToArray(pitch));
                if(ticks >= 40) {
                    ticks = 0;
                    String fileName = "output_list.csv";
                    Bukkit.getLogger().info("AAAAA");
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
                        writer.write("PITCH,YAW");
                        writer.newLine();
                        int rowCount = Math.min(pitch.size(), yaw.size());
                        for (int i = 0; i < rowCount; i++) {
                            writer.write(pitch.get(i) + "," + yaw.get(i));
                            writer.newLine();
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
    }

    public float[] convertListToArray(List<Float> list) {
        float[] array = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }
}
