package com.example.eggquest;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.UUID;

public class EggQuestPlugin extends JavaPlugin {
    private Location shrineLocation;
    private Location initialEggLocation;
    private UUID currentHolder;
    private long timerStartTime;
    private boolean isTimerActive;

    @Override
    public void onEnable() {
        shrineLocation = getConfig().getLocation("shrine");
        initialEggLocation = getConfig().getLocation("initialEggLocation");
        timerStartTime = getConfig().getLong("timerStart");
        isTimerActive = getConfig().getBoolean("isTimerActive");
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new EggListener(this), this);
        Bukkit.getScheduler().runTaskTimer(this, this::checkTimer, 0L, 1200L);

        ShapedRecipe compassRecipe = new ShapedRecipe(
            new NamespacedKey(this, "tracker_compass"),
            new ItemStack(Material.COMPASS)
        );
        compassRecipe.shape(" R ", "RNR", " R ");
        compassRecipe.setIngredient('R', Material.NETHER_STAR);
        compassRecipe.setIngredient('N', Material.NETHERITE_SCRAP);
        Bukkit.addRecipe(compassRecipe);
    }

    private void checkTimer() {
        if (isTimerActive && System.currentTimeMillis() - timerStartTime > 259200000L) {
            // Auto-return egg to shrine (add code later)
        }
    }
}
