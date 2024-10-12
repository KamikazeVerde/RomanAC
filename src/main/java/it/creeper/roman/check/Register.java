package it.creeper.roman.check;

import it.creeper.roman.Roman;
import it.creeper.roman.check.impl.test.TestCheck;
import it.creeper.roman.check.impl.test.TestCheck2;

public class Register {
    Roman plugin = Roman.getInstance();
    boolean test1 = plugin.getConfig().getBoolean("checks.test-1");
    boolean test2 = plugin.getConfig().getBoolean("checks.test-2");
    public void registerChecks() {
        if(test1) {
            plugin.getServer().getPluginManager().registerEvents(new TestCheck(), plugin);
        }
        if(test2) {
            plugin.getServer().getPluginManager().registerEvents(new TestCheck2(), plugin);
        }
    }
}
