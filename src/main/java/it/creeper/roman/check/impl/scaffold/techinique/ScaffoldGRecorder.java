package it.creeper.roman.check.impl.scaffold.techinique;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class ScaffoldGRecorder implements Listener {

    boolean place;

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        place = true;
    }
}
