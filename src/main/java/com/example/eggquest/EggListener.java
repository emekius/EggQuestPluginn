package com.example.eggquest;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class EggListener implements Listener {
    private final EggQuestPlugin plugin;

    public EggListener(EggQuestPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDragonDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof EnderDragon) {
            plugin.initialEggLocation = e.getEntity().getLocation();
            plugin.getConfig().set("initialEggLocation", plugin.initialEggLocation);
            plugin.saveConfig();
        }
    }

    @EventHandler
    public void onEggPickup(EntityPickupItemEvent e) {
        if (e.getItem().getItemStack().getType() == Material.DRAGON_EGG && e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0));
            plugin.currentHolder = player.getUniqueId();
            plugin.timerStartTime = System.currentTimeMillis();
        }
    }

    @EventHandler
    public void onEggPlace(BlockPlaceEvent e) {
        if (e.getBlock().getType() == Material.DRAGON_EGG && e.getBlock().getLocation().equals(plugin.getShrineLocation())) {
            Bukkit.broadcastMessage(ChatColor.GOLD + e.getPlayer().getName() + " has won the prize!");
            plugin.currentHolder = null;
        }
    }
}
