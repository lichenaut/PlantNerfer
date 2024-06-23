package com.lichenaut.plantnerfer.listen;

import com.lichenaut.plantnerfer.Main;
import com.lichenaut.plantnerfer.load.Plant;
import com.lichenaut.plantnerfer.load.PlantLoader;
import com.lichenaut.plantnerfer.util.ListenerUtil;
import com.lichenaut.plantnerfer.util.Messager;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Arrays;

public class BlockPlace extends ListenerUtil implements Listener {

    private final Messager messager;

    public BlockPlace(Main main, PlantLoader loader) {
        super(plugin, loader);
        this.messager = plugin.getMessageParser();
    }

    @EventHandler
    private void onPlantPlace(BlockPlaceEvent e) {
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
        Player player = e.getPlayer();
        if (!plant.getCanPlace(biome)) {
            String[] biomes = plant.getBiomes();
            if (biomes == null) {
                verboseDenial(messager.getCannotPlaceAnyBiome(), player);
            } else {
                if (biomes[1] != null)
                    verboseDenial(Arrays.toString(messager.getCannotPlaceFollowingBiomes()) + biomes[1], player);
                if (biomes[0] != null)
                    verboseDenial(Arrays.toString(messager.getTryFollowingBiomes()) + biomes[0], player);
                else
                    verboseDenial(messager.getTryOtherBiomes(), player);
            }
            e.setCancelled(true);
            return;
        }

        if (plant.getNeedsSky(biome, block)
                && block.getWorld().getHighestBlockAt(block.getLocation()).getY() > block.getY()) {
            verboseDenial(messager.getPlantNeedsSky(), player);
            e.setCancelled(true);
            return;
        }

        if (notIgnoreLightWhenNight(block, plant)) {
            verboseDenial(messager.combineMessage(messager.getCannotPlaceDark(), plant.getMinLight(biome) + "."),
                    player);
            e.setCancelled(true);
            return;
        }
        int maxLight = plant.getMaxLight(biome);
        if (block.getRelative(0, 1, 0).getLightLevel() > maxLight) {
            verboseDenial(messager.combineMessage(messager.getCannotPlaceBright(), maxLight + "."), player);
            e.setCancelled(true);
            return;
        }

        int y = block.getY();
        int minY = plant.getMinY(biome);
        if (y < minY) {
            verboseDenial(messager.combineMessage(messager.getCannotPlaceBelow(), minY + "."), player);
            e.setCancelled(true);
            return;
        }
        int maxY = plant.getMaxY(biome);
        if (y > maxY) {
            verboseDenial(messager.combineMessage(messager.getCannotPlaceAbove(), maxY + "."), player);
            e.setCancelled(true);
            return;
        }

        if (!plant.isValidWorldAndBiome(biome, worldName)) {
            verboseDenial(messager.getCannotPlaceSpecific(), player);
            e.setCancelled(true);
        }
    }
}