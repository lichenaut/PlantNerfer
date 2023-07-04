package com.lichenaut.plantnerfer.listeners;

import com.lichenaut.plantnerfer.PlantNerfer;
import com.lichenaut.plantnerfer.load.PNPlant;
import com.lichenaut.plantnerfer.load.PNPlantLoader;
import com.lichenaut.plantnerfer.util.PNListenerUtil;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;


public class PNBlockPlaceListener extends PNListenerUtil implements Listener {

    public PNBlockPlaceListener(PlantNerfer plugin, PNPlantLoader loader) {super(plugin, loader);}

    @EventHandler
    public void onPlantPlace(BlockPlaceEvent e) {
        Block block = e.getBlock();
        if (loader.getPbRef().isNotPlantBlock(block.getType())) {return;}
        String worldName = block.getWorld().getName();
        if (invalidWorld(worldName)) {return;}
        PNPlant plant = plugin.getPlant(block.getType());
        if (plant == null) {return;}
        Biome biome = block.getBiome();

        int lightLevel = block.getRelative(0, 1, 0).getLightLevel();
        if (lightLevel < plant.getMinLight(biome) || lightLevel > plant.getMaxLight(biome)) {e.setCancelled(true);return;}

        int y = block.getY();
        if (y < plant.getMinY(biome) || y > plant.getMaxY(biome)) {e.setCancelled(true);return;}

        if (!plant.isValidWorldAndBiome(biome, worldName)) e.setCancelled(true);
    }
}