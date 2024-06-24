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
import org.bukkit.event.block.BlockFertilizeEvent;

@RequiredArgsConstructor
public class BoneMeal implements Listener {

    private final ListenerUtil listenerUtil;
    private final Main main;
    private final Messager messager;

    @EventHandler
    private void onBoneMealUse(BlockFertilizeEvent event) {
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

        if (world.getHighestBlockAt(block.getLocation()).getY() + 1 != block.getY() && plant.getNeedsSky(biome, worldName, block)) {
            if (player != null) {
                listenerUtil.verboseDenial(messager.getPlantNeedsSky(), player);
            }
            event.setCancelled(true);
            return;
        }

        int boneMealRate = (lightLevel < 8) ? plant.getDarkBoneMealRate(biome, worldName) : plant.getBoneMealRate(biome, worldName);
        if (!listenerUtil.chance(boneMealRate)) {
            event.setCancelled(true);
        }
    }
}