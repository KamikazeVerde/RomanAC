package it.creeper.roman.check;

import it.creeper.roman.Roman;
import it.creeper.roman.check.impl.TestCheck;
import it.creeper.roman.check.impl.TestCheck2;

public class Register {
    Roman plugin = Roman.getInstance();

    public void registerChecks() {
        plugin.getServer().getPluginManager().registerEvents(new TestCheck(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new TestCheck2(), plugin);
    }
}
