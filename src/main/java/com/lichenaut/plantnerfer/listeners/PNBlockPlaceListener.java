package com.lichenaut.plantnerfer.listeners;

import com.lichenaut.plantnerfer.PlantNerfer;
import com.lichenaut.plantnerfer.load.PNPlant;
import com.lichenaut.plantnerfer.load.PNPlantLoader;
import com.lichenaut.plantnerfer.util.PNListenerUtil;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;


public class PNBlockPlaceListener extends PNListenerUtil implements Listener {

    public PNBlockPlaceListener(PlantNerfer plugin, PNPlantLoader loader) {super(plugin, loader);}

    @EventHandler
    public void onPlantPlace(BlockPlaceEvent e) {
        Block block = e.getBlock();
        if (plugin.getPlant(block.getType()) == null || loader.getReference().isNotPlantBlock(block.getType())) {return;}
        String worldName = block.getWorld().getName();
        if (invalidWorld(worldName)) {return;}
        PNPlant plant = plugin.getPlant(block.getType());
        if (plant == null) {return;}

        Biome biome = block.getBiome();
        Player player = e.getPlayer();
        if (!plant.getCanPlace(biome)) {
            verboseDenial("Cannot place this plant in this biome. Try the following biomes: " + plant.getBiomes(), player);
            e.setCancelled(true);
            return;
        }

        if (plant.getNeedsSky(biome, block) && block.getWorld().getHighestBlockAt(block.getLocation()).getY() > block.getY()) {
            verboseDenial("This plant needs sky access to grow.", player);
            e.setCancelled(true);
            return;
        }

        if (notIgnoreLightWhenNight(block, plant)) {
            verboseDenial("Plant cannot be placed in light levels below " + plant.getMinLight(biome) + ".", player);
            e.setCancelled(true);
            return;
        }
        if (block.getRelative(0, 1, 0).getLightLevel() > plant.getMaxLight(biome)) {
            verboseDenial("Plant cannot be placed in light levels above " + plant.getMaxLight(biome) + ".", player);
            e.setCancelled(true);
            return;
        }

        int y = block.getY();
        if (y < plant.getMinY(biome)) {
            verboseDenial("Plant cannot be placed below Y=" + plant.getMinY(biome), player);
            e.setCancelled(true);
            return;
        }
        if (y > plant.getMaxY(biome)) {
            verboseDenial("Plant cannot be placed above Y=" + plant.getMaxY(biome), player);
            e.setCancelled(true);
            return;
        }

        if (!plant.isValidWorldAndBiome(biome, worldName)) {
            verboseDenial("Plant cannot be placed in this world and biome.", player);
            e.setCancelled(true);
        }
    }
}