package com.lichenaut.plantnerfer.listeners;

import com.lichenaut.plantnerfer.PlantNerfer;
import com.lichenaut.plantnerfer.load.PNPlant;
import com.lichenaut.plantnerfer.load.PNPlantLoader;
import com.lichenaut.plantnerfer.util.PNListenerUtil;
import com.lichenaut.plantnerfer.util.PNMessageParser;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class PNBlockBreakListener extends PNListenerUtil implements Listener {

    private final int farmedFarmlandTurnsIntoDirt;
    private final PNMessageParser messageParser;

    public PNBlockBreakListener(PlantNerfer plugin, PNPlantLoader loader, int farmedFarmlandTurnsIntoDirt) {
        super(plugin, loader);
        this.farmedFarmlandTurnsIntoDirt = farmedFarmlandTurnsIntoDirt;
        this.messageParser = plugin.getMessageParser();
    }

    @EventHandler
    private void onBlockBreakDirt(BlockBreakEvent e) {
        Block above = e.getBlock();
        Block block = above.getRelative(0, -1, 0);
        if (block.getType() != Material.FARMLAND || !loader.getFarmlandReference().getFarmlandSet().contains(above.getType())) {return;}
        String worldName = above.getWorld().getName();
        if (invalidWorld(worldName)) {return;}
        PNPlant plant = plugin.getPlant(above.getType());
        if (plant == null) {return;}
        if (chance(farmedFarmlandTurnsIntoDirt)) {block.setType(Material.DIRT);return;}
        Biome biome = above.getBiome();
        if (!plant.getNeedsHoeForFarmlandRetain(biome)) {return;}

        Player player = e.getPlayer();
        if (loader.getHoeReference().getHoeSet().contains(player.getInventory().getItemInMainHand().getType())) {return;}

        block.setType(Material.DIRT);
        verboseDenial(messageParser.getFarmlandIntoDirt(), player);
    }

    @EventHandler
    private void onBlockBreakHoeDrops(BlockBreakEvent e) {
        Block block = e.getBlock();
        String worldName = block.getWorld().getName();
        if (invalidWorld(worldName)) {return;}
        PNPlant plant = plugin.getPlant(block.getType());
        if (plant == null) {return;}
        Biome biome = block.getBiome();
        if (!plant.getNeedsHoeForDrops(biome)) {return;}

        Player player = e.getPlayer();
        //I do not check for offhand, as this would enable players to hold the hoe in the offhand and break crop with their mainhand fist.
        if (loader.getHoeReference().getHoeSet().contains(player.getInventory().getItemInMainHand().getType())) {return;}

        e.setCancelled(true);
        block.setType(Material.AIR);
        verboseDenial(messageParser.getPlantDroppedNothing(), player);
    }
}
