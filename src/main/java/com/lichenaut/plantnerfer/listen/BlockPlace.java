package com.lichenaut.plantnerfer.listen;

import com.lichenaut.plantnerfer.Main;
import com.lichenaut.plantnerfer.load.Plant;
import com.lichenaut.plantnerfer.util.ListenerUtil;
import com.lichenaut.plantnerfer.util.Messager;
import lombok.RequiredArgsConstructor;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

@RequiredArgsConstructor
public class BlockPlace implements Listener {

    private final ListenerUtil listenerUtil;
    private final Main main;
    private final Messager messager;

    @EventHandler
    private void onPlantPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
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
        Player player = event.getPlayer();
        if (!plant.getCanPlace(biome, worldName)) {
            listenerUtil.verboseDenial(messager.combineMessage(messager.getCannotFollowingBiomes(), String.valueOf(plant.getDisallowedBiomes())), player); //TODO: set -> string beforehand?
            event.setCancelled(true);
            return;
        }

        int lightLevel = block.getRelative(0, 1, 0).getLightLevel();
        int minLight = plant.getMinLight(biome, worldName);
        if (lightLevel < minLight) {
            listenerUtil.verboseDenial(messager.combineMessage(messager.getCannotDark(), minLight + "."), player);
            event.setCancelled(true);
            return;
        }

        int maxLight = plant.getMaxLight(biome, worldName);
        if (lightLevel > maxLight) {
            listenerUtil.verboseDenial(messager.combineMessage(messager.getCannotBright(), maxLight + "."), player);
            event.setCancelled(true);
            return;
        }

        int blockHeight = block.getY();
        if (world.getHighestBlockAt(block.getLocation()).getY() != blockHeight && plant.getNeedsSky(biome, worldName, block)) {
            listenerUtil.verboseDenial(messager.getPlantNeedsSky(), player);
            event.setCancelled(true);
            return;
        }

        int minY = plant.getMinY(biome, worldName);
        if (blockHeight < minY) {
            listenerUtil.verboseDenial(messager.combineMessage(messager.getCannotBelow(), minY + "."), player);
            event.setCancelled(true);
            return;
        }

        int maxY = plant.getMaxY(biome, worldName);
        if (blockHeight > maxY) {
            listenerUtil.verboseDenial(messager.combineMessage(messager.getCannotAbove(), maxY + "."), player);
            event.setCancelled(true);
        }
    }
}