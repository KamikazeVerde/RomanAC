package it.creeper.roman.mitigation;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Attack {
    public void mitigateDamage(EntityDamageByEntityEvent event, Player player) {
        /*
        Devel:
        Need to get the player that got hitted from the listed cheater
        then diminsh the damage inflicted.
         */
        double stdDamage = event.getDamage();
        Bukkit.getLogger().info("Damage for " + player + ": " + stdDamage);
        event.setDamage(stdDamage-3);
    }
}
