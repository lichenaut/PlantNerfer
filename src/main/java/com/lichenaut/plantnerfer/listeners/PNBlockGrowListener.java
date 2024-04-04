package com.lichenaut.plantnerfer.listeners;

import com.lichenaut.plantnerfer.PlantNerfer;
import com.lichenaut.plantnerfer.load.PNPlant;
import com.lichenaut.plantnerfer.load.PNPlantLoader;
import com.lichenaut.plantnerfer.util.PNListenerUtil;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.world.StructureGrowEvent;

public class PNBlockGrowListener extends PNListenerUtil implements Listener {

    private final boolean deathTurnsIntoBush;

    public PNBlockGrowListener(PlantNerfer plugin, PNPlantLoader loader, boolean deathTurnsIntoBush) {
        super(plugin, loader);
        this.deathTurnsIntoBush = deathTurnsIntoBush;
    }

    private void killPlant(Block block) {
        block.setType(Material.AIR);
        if (deathTurnsIntoBush) {
            Block below = block.getRelative(0, -1, 0);
            if (below.getType() == Material.FARMLAND) {below.setType(Material.DIRT);}
            block.setType(Material.DEAD_BUSH);
        }
    }

    @EventHandler
    private void onPlantGrowth(BlockGrowEvent e) {
        Block block = e.getBlock();
        if (plugin.getPlant(block.getType()) == null || loader.getReference().isNotPlantBlock(block.getType())) {return;}
        String worldName = block.getWorld().getName();
        if (invalidWorld(worldName)) {return;}
        PNPlant plant = plugin.getPlant(block.getType());
        if (plant == null) {return;}

        Biome biome = block.getBiome();
        if (plant.getNeedsSky(biome, block) && block.getWorld().getHighestBlockAt(block.getLocation()).getY() > block.getY()) {e.setCancelled(true);return;}

        int lightLevel = block.getRelative(0, 1,0 ).getLightLevel();
        if (notIgnoreLightWhenNight(block, plant) || lightLevel > plant.getMaxLight(biome)) {e.setCancelled(true);return;}

        if (block.getWorld().getHighestBlockAt(block.getLocation()).getY() > block.getY()) {
            if (chance(plant.getNoSkyDeathRate(biome))) {e.setCancelled(true);killPlant(block);return;}
            if (!chance(plant.getNoSkyGrowthRate(biome))) e.setCancelled(true);return;
        }

        if (lightLevel < 8) {if (chance(plant.getDarkDeathRate(biome))) {e.setCancelled(true);killPlant(block);return;}
        } else if (chance(plant.getDeathRate(biome))) {e.setCancelled(true);killPlant(block);return;}

        if (lightLevel < 8) {if (!chance(plant.getDarkGrowthRate(biome))) e.setCancelled(true);
        } else if (!chance(plant.getGrowthRate(biome))) e.setCancelled(true);
    }

    @EventHandler
    private void onPlantStructureGrowth(StructureGrowEvent e) {
        Block block = e.getLocation().getBlock();
        if (plugin.getPlant(block.getType()) == null || loader.getReference().isNotPlantBlock(block.getType())) {return;}
        String worldName = block.getWorld().getName();
        if (invalidWorld(worldName)) {return;}
        PNPlant plant = plugin.getPlant(block.getType());
        if (plant == null) {return;}

        Biome biome = block.getBiome();
        if (plant.getNeedsSky(biome, block) && block.getWorld().getHighestBlockAt(block.getLocation()).getY() > block.getY()) {e.setCancelled(true);return;}

        int lightLevel = block.getRelative(0, 1,0 ).getLightLevel();
        if (notIgnoreLightWhenNight(block, plant) || lightLevel > plant.getMaxLight(biome)) {e.setCancelled(true);return;}

        if (block.getWorld().getHighestBlockAt(block.getLocation()).getY() > block.getY()) {
            if (chance(plant.getNoSkyDeathRate(biome))) {e.setCancelled(true);killPlant(block);return;}
            if (!chance(plant.getNoSkyGrowthRate(biome))) e.setCancelled(true);return;
        }

        if (lightLevel < 8) {if (chance(plant.getDarkDeathRate(biome))) {e.setCancelled(true);killPlant(block);return;}
        } else if (chance(plant.getDeathRate(biome))) {e.setCancelled(true);killPlant(block);return;}

        if (lightLevel < 8) {if (!chance(plant.getDarkGrowthRate(biome))) e.setCancelled(true);
        } else if (!chance(plant.getGrowthRate(biome))) e.setCancelled(true);
    }

    @EventHandler
    private void onPlantSpread(BlockSpreadEvent e) {
        Block block = e.getBlock();
        if (plugin.getPlant(block.getType()) == null || loader.getReference().isNotPlantBlock(block.getType())) {return;}
        String worldName = block.getWorld().getName();
        if (invalidWorld(worldName)) {return;}
        PNPlant plant = plugin.getPlant(block.getType());
        if (plant == null) {return;}

        Biome biome = block.getBiome();
        if (plant.getNeedsSky(biome, block) && block.getWorld().getHighestBlockAt(block.getLocation()).getY() > block.getY()) {e.setCancelled(true);return;}

        int lightLevel = block.getRelative(0, 1,0 ).getLightLevel();
        if (notIgnoreLightWhenNight(block, plant) || lightLevel > plant.getMaxLight(biome)) {e.setCancelled(true);return;}

        if (block.getWorld().getHighestBlockAt(block.getLocation()).getY() > block.getY()) {
            if (chance(plant.getNoSkyDeathRate(biome))) {e.setCancelled(true);killPlant(block);return;}
            if (!chance(plant.getNoSkyGrowthRate(biome))) e.setCancelled(true);return;
        }

        if (lightLevel < 8) {if (chance(plant.getDarkDeathRate(biome))) {e.setCancelled(true);killPlant(block);return;}
        } else if (chance(plant.getDeathRate(biome))) {e.setCancelled(true);killPlant(block);return;}

        if (lightLevel < 8) {if (!chance(plant.getDarkGrowthRate(biome))) e.setCancelled(true);
        } else if (!chance(plant.getGrowthRate(biome))) e.setCancelled(true);
    }
}