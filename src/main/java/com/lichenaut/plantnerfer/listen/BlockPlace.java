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
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;

@RequiredArgsConstructor
public class BlockPlace implements Listener {

    private final ListenerUtil listenerUtil;
    private final Main main;
    private final Messager messager;

    @EventHandler
    private void onPlantPlace(BlockPlaceEvent event) {
        processPlantPlace(event, event.getBlock());
    }

    @EventHandler
    private void onPlantDispense(EntityChangeBlockEvent event) {
        processPlantPlace(event, event.getBlock());
    }

    private <Event extends Cancellable> void processPlantPlace(Event event, Block block) {
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
        Player player = null;
        if (event instanceof BlockPlaceEvent) {
            player = ((BlockPlaceEvent) event).getPlayer();
        }
        if (!plant.getCanPlace(biome, worldName)) {
            if (player != null) {
                if (plant.canPlaceByDefault()) {
                    listenerUtil.verboseDenial(messager.combineMessage(messager.getCannotFollowingBiomes(),
                            plant.getDisallowedBiomes()), player);
                } else {
                    listenerUtil.verboseDenial(messager.combineMessage(messager.getOnlyFollowingBiomes(),
                            plant.getAllowedBiomes()), player);
                }
            }
            event.setCancelled(true);
            return;
        }

        int lightLevel = block.getRelative(0, 1, 0).getLightLevel();
        int minLight = plant.getMinLight(biome, worldName);
        if (lightLevel < minLight) {
            if (player != null) {
                listenerUtil.verboseDenial(messager.combineMessage(messager.getCannotDark(), minLight + "."), player);
            }
            event.setCancelled(true);
            return;
        }

        int maxLight = plant.getMaxLight(biome, worldName);
        if (lightLevel > maxLight) {
            if (player != null) {
                listenerUtil.verboseDenial(messager.combineMessage(messager.getCannotBright(), maxLight + "."), player);
            }
            event.setCancelled(true);
            return;
        }

        int blockHeight = block.getY();
        if (world.getHighestBlockAt(block.getLocation()).getY() + 1 != blockHeight
                && plant.getNeedsSky(biome, worldName, block)) {
            if (player != null) {
                listenerUtil.verboseDenial(messager.getPlantNeedsSky(), player);
            }
            event.setCancelled(true);
            return;
        }

        int minY = plant.getMinY(biome, worldName);
        if (blockHeight < minY) {
            if (player != null) {
                listenerUtil.verboseDenial(messager.combineMessage(messager.getCannotBelow(), minY + "."), player);
            }
            event.setCancelled(true);
            return;
        }

        int maxY = plant.getMaxY(biome, worldName);
        if (blockHeight > maxY) {
            if (player != null) {
                listenerUtil.verboseDenial(messager.combineMessage(messager.getCannotAbove(), maxY + "."), player);
            }
            event.setCancelled(true);
        }
    }
}