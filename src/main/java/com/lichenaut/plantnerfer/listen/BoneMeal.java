package com.lichenaut.plantnerfer.listen;

import com.lichenaut.plantnerfer.Main;
import com.lichenaut.plantnerfer.load.Plant;
import com.lichenaut.plantnerfer.load.PlantLoader;
import com.lichenaut.plantnerfer.util.ListenerUtil;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFertilizeEvent;

public class BoneMeal extends ListenerUtil implements Listener {

    public BoneMeal(Main main, PlantLoader loader) {
        super(plugin, loader);
    }

    @EventHandler
    private void onBoneMealUse(BlockFertilizeEvent e) {
        Block block = e.getBlock();
        if (plugin.getPlant(block.getType()) == null || loader.getReference().isNotPlantBlock(block.getType())) {
            return;
        }
        String worldName = block.getWorld().getName();
        if (invalidWorld(worldName)) {
            return;
        }
        Plant plant = plugin.getPlant(block.getType());
        if (plant == null) {
            return;
        }

        Biome biome = block.getBiome();
        if (plant.getNeedsSky(biome, block)
                && block.getWorld().getHighestBlockAt(block.getLocation()).getY() > block.getY()) {
            e.setCancelled(true);
            return;
        }

        if (notIgnoreLightWhenNight(block, plant)
                || block.getRelative(0, 1, 0).getLightLevel() > plant.getMaxLight(biome)) {
            e.setCancelled(true);
            return;
        }

        int lightLevel = block.getRelative(0, 1, 0).getLightLevel();

        if (lightLevel < 8) {
            if (!chance(plant.getDarkBoneMealRate(biome)))
                e.setCancelled(true);
        } else if (!chance(plant.getBoneMealRate(biome)))
            e.setCancelled(true);
    }
}