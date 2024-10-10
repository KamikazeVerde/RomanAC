package it.creeper.roman;

import it.creeper.roman.check.Register;
import it.creeper.roman.check.impl.TestCheck;
import it.creeper.roman.mitigation.Setback;
import it.creeper.roman.notify.CheatNotify;
import it.creeper.roman.player.Ban;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import it.creeper.roman.check.Register;

public final class Roman extends JavaPlugin {
    private static Roman instance;
    private Setback setback;
    public CheatNotify cheatNotify;
    public Ban banManager;


    //public Register register = new Register();
    //private final Ban banManager = new Ban();
    //private final CheatNotify cheatNotify = new CheatNotify();




    public void onEnable() {
        instance = this;
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        getLogger().info("Booting up Roman AntiCheat by Creeper215 :)");
        //register.registerChecks();
        this.cheatNotify = new CheatNotify();
        this.banManager = new Ban();
        this.setback = new Setback();
        this.getServer().getPluginManager().registerEvents(new TestCheck(), this);
        // VL reset
        this.getServer().getScheduler().runTaskLaterAsynchronously(this, () -> {
            this.cheatNotify.vl.clear();
            Bukkit.broadcast(ChatColor.translateAlternateColorCodes('&', "&7[&4&lRoman&7] &6Violazioni resettate per tutti i giocatori online"), "roman.notify");
        }, 600L);
        this.getServer().getScheduler().runTaskLaterAsynchronously(this, () -> {
            for(Player player : getServer().getOnlinePlayers()) {

                this.setback.updatePlayerPos(player, player.getLocation());
            }
        }, 1L);
    }


    public void onDisable() {
        getLogger().info("Shutting down Roman AntiCheat by Creeper215 :(");
    }

    // GETTER

    public static Roman getInstance() {
        return instance;
    }

    public Setback getSetback() {
        return setback;
    }

    public CheatNotify getCheatNotify() {
        return cheatNotify;
    }


    public Ban getBanManager() {
        return this.banManager;
    }




}
