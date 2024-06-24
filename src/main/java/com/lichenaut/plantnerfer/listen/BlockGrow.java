package com.lichenaut.plantnerfer.listen;

import com.lichenaut.plantnerfer.Main;
import com.lichenaut.plantnerfer.load.Plant;
import com.lichenaut.plantnerfer.util.ListenerUtil;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.world.StructureGrowEvent;

@RequiredArgsConstructor
public class BlockGrow implements Listener {

    private final boolean deathTurnsIntoBush;
    private final ListenerUtil listenerUtil;
    private final Main main;

    @EventHandler
    private void onPlantGrowth(BlockGrowEvent event) {
        Block block = event.getBlock();
        processGrowEvent(event, block);
    }

    @EventHandler
    private void onPlantStructureGrowth(StructureGrowEvent event) {
        Block block = event.getLocation().getBlock();
        processGrowEvent(event, block);
    }

    @EventHandler
    private void onPlantSpread(BlockSpreadEvent event) {
        Block block = event.getBlock();
        processGrowEvent(event, block);
    }

    private <Event extends Cancellable> void processGrowEvent(Event event, Block block) {
        Plant plant = main.getPlant(block.getType());
        if (plant == null) {
            return;
        }

        World world = block.getWorld();
        String worldName = world.getName();
        if (listenerUtil.isInvalidWorld(worldName)) {
            return;
        }

        Biome biome = block.getBiome();
        int lightLevel = block.getRelative(0, 1, 0).getLightLevel();
        int deathRate = (lightLevel < 8) ? plant.getDarkDeathRate(biome, worldName) : plant.getDeathRate(biome, worldName);
        if (listenerUtil.chance(deathRate)) {
            killPlant(event, block);
            return;
        }

        boolean isHighestBlock = world.getHighestBlockAt(block.getLocation()).getY() == block.getY();
        if (!isHighestBlock && listenerUtil.chance(plant.getNoSkyDeathRate(biome, worldName))) {
            killPlant(event, block);
            return;
        }

        if (!isHighestBlock && plant.getNeedsSky(biome, worldName, block)) {
            event.setCancelled(true);
            return;
        }

        if (lightLevel < plant.getMinLight(biome, worldName)) {
            event.setCancelled(true);
            return;
        }

        if (lightLevel > plant.getMaxLight(biome, worldName)) {
            event.setCancelled(true);
            return;
        }

        if (!isHighestBlock && !listenerUtil.chance(plant.getNoSkyGrowthRate(biome, worldName))) {
            event.setCancelled(true);
            return;
        }

        int growthRate = (lightLevel < 8) ? plant.getDarkGrowthRate(biome, worldName) : plant.getGrowthRate(biome, worldName);
        if (!listenerUtil.chance(growthRate)) {
            event.setCancelled(true);
        }
    }

    private <Event extends Cancellable> void killPlant(Event event, Block block) {
        event.setCancelled(true);
        block.setType(Material.AIR);
        if (!deathTurnsIntoBush) {
            return;
        }

        Block below = block.getRelative(0, -1, 0);
        if (below.getType() == Material.FARMLAND) {
            below.setType(Material.DIRT);
        }
        block.setType(Material.DEAD_BUSH);
    }
}