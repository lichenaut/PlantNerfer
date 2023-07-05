package com.lichenaut.plantnerfer.listeners;

import com.lichenaut.plantnerfer.PlantNerfer;
import com.lichenaut.plantnerfer.load.PNPlant;
import com.lichenaut.plantnerfer.load.PNPlantLoader;
import com.lichenaut.plantnerfer.util.PNListenerUtil;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFertilizeEvent;

public class PNBoneMealListener extends PNListenerUtil implements Listener {

    public PNBoneMealListener(PlantNerfer plugin, PNPlantLoader loader) {super(plugin, loader);}

    @EventHandler
    public void onBoneMealUse(BlockFertilizeEvent e) {
        Block block = e.getBlock();
        if (loader.getReference().isNotPlantBlock(block.getType())) {return;}
        String worldName = block.getWorld().getName();
        if (invalidWorld(worldName)) {return;}
        PNPlant plant = plugin.getPlant(block.getType());
        if (plant == null) {return;}

        Biome biome = block.getBiome();
        if (plant.getNeedsSky(biome) && block.getWorld().getHighestBlockAt(block.getLocation()).getY() > block.getY()) {e.setCancelled(true);return;}

        if (notIgnoreLightWhenNight(block, plant) || block.getRelative(0, 1, 0).getLightLevel() > plant.getMaxLight(biome)) {e.setCancelled(true);return;}

        int lightLevel = block.getRelative(0, 1,0 ).getLightLevel();

        if (lightLevel < 8) {if (!chance(plant.getDarkBoneMealRate(biome))) e.setCancelled(true);
        } else if (!chance(plant.getBoneMealRate(biome))) e.setCancelled(true);
    }
}