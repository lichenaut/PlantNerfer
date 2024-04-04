package com.lichenaut.plantnerfer.listeners;

import com.lichenaut.plantnerfer.PlantNerfer;
import com.lichenaut.plantnerfer.load.PNPlant;
import com.lichenaut.plantnerfer.load.PNPlantLoader;
import com.lichenaut.plantnerfer.util.PNListenerUtil;
import com.lichenaut.plantnerfer.util.PNMessageParser;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Arrays;

public class PNBlockPlaceListener extends PNListenerUtil implements Listener {

    private final PNMessageParser messageParser;

    public PNBlockPlaceListener(PlantNerfer plugin, PNPlantLoader loader) {
        super(plugin, loader);
        this.messageParser = plugin.getMessageParser();
    }

    @EventHandler
    private void onPlantPlace(BlockPlaceEvent e) {
        Block block = e.getBlock();
        if (plugin.getPlant(block.getType()) == null || loader.getReference().isNotPlantBlock(block.getType())) {return;}
        String worldName = block.getWorld().getName();
        if (invalidWorld(worldName)) {return;}
        PNPlant plant = plugin.getPlant(block.getType());
        if (plant == null) {return;}

        Biome biome = block.getBiome();
        Player player = e.getPlayer();
        if (!plant.getCanPlace(biome)) {
            String[] biomes = plant.getBiomes();
            if (biomes == null) {verboseDenial(messageParser.getCannotPlaceAnyBiome(), player);
            } else {
                if (biomes[1] != null) verboseDenial(Arrays.toString(messageParser.getCannotPlaceFollowingBiomes()) + biomes[1], player);
                if (biomes[0] != null) verboseDenial(Arrays.toString(messageParser.getTryFollowingBiomes()) + biomes[0], player);
                else verboseDenial(messageParser.getTryOtherBiomes(), player);
            }
            e.setCancelled(true);
            return;
        }

        if (plant.getNeedsSky(biome, block) && block.getWorld().getHighestBlockAt(block.getLocation()).getY() > block.getY()) {
            verboseDenial(messageParser.getPlantNeedsSky(), player);
            e.setCancelled(true);
            return;
        }

        if (notIgnoreLightWhenNight(block, plant)) {
            verboseDenial(messageParser.combineMessage(messageParser.getCannotPlaceDark(), plant.getMinLight(biome) + "."), player);
            e.setCancelled(true);
            return;
        }
        int maxLight = plant.getMaxLight(biome);
        if (block.getRelative(0, 1, 0).getLightLevel() > maxLight) {
            verboseDenial(messageParser.combineMessage(messageParser.getCannotPlaceBright(), maxLight + "."), player);
            e.setCancelled(true);
            return;
        }

        int y = block.getY();
        int minY = plant.getMinY(biome);
        if (y < minY) {
            verboseDenial(messageParser.combineMessage(messageParser.getCannotPlaceBelow(), minY + "."), player);
            e.setCancelled(true);
            return;
        }
        int maxY = plant.getMaxY(biome);
        if (y > maxY) {
            verboseDenial(messageParser.combineMessage(messageParser.getCannotPlaceAbove(), maxY + "."), player);
            e.setCancelled(true);
            return;
        }

        if (!plant.isValidWorldAndBiome(biome, worldName)) {
            verboseDenial(messageParser.getCannotPlaceSpecific(), player);
            e.setCancelled(true);
        }
    }
}