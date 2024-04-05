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
    private void onBlockBreak(BlockBreakEvent e) {
        Block above = e.getBlock();
        PNPlant plant = plugin.getPlant(above.getType());
        if (plant == null) {return;}
        String worldName = above.getWorld().getName();
        if (invalidWorld(worldName)) {return;}

        Player player = e.getPlayer();
        boolean holdingHoe = loader.getHoeReference().getHoeSet().contains(player.getInventory().getItemInMainHand().getType());
        Biome biome = above.getBiome();

        if (plant.getNeedsHoeForDrops(biome) && !holdingHoe) {
            e.setCancelled(true);
            above.setType(Material.AIR);
            verboseDenial(messageParser.getPlantDroppedNothing(), player);
        }

        Block below = above.getRelative(0, -1, 0);
        if (below.getType() != Material.FARMLAND) {return;}
        if (farmedFarmlandTurnsIntoDirt > 0) if (chance(farmedFarmlandTurnsIntoDirt)) {below.setType(Material.DIRT);return;}

        if (plant.getNeedsHoeForFarmlandRetain(biome) && !holdingHoe) {
            below.setType(Material.DIRT);
            System.out.println("last");
            verboseDenial(messageParser.getFarmlandIntoDirt(), player);
        }
    }
}
