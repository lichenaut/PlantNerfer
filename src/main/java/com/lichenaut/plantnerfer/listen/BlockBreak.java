package com.lichenaut.plantnerfer.listen;

import com.lichenaut.plantnerfer.Main;
import com.lichenaut.plantnerfer.load.Plant;
import com.lichenaut.plantnerfer.load.PlantLoader;
import com.lichenaut.plantnerfer.util.ListenerUtil;
import com.lichenaut.plantnerfer.util.Messager;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak extends ListenerUtil implements Listener {

    private final int farmedFarmlandTurnsIntoDirt;
    private final Messager messager;

    public BlockBreak(Main main, PlantLoader loader, int farmedFarmlandTurnsIntoDirt) {
        super(plugin, loader);
        this.farmedFarmlandTurnsIntoDirt = farmedFarmlandTurnsIntoDirt;
        this.messager = plugin.getMessageParser();
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent e) {
        Block above = e.getBlock();
        Plant plant = plugin.getPlant(above.getType());
        if (plant == null) {
            return;
        }
        String worldName = above.getWorld().getName();
        if (invalidWorld(worldName)) {
            return;
        }

        Player player = e.getPlayer();
        boolean holdingHoe = loader.getHoeReference().getHoeSet()
                .contains(player.getInventory().getItemInMainHand().getType());
        Biome biome = above.getBiome();

        if (plant.getNeedsHoeForDrops(biome) && !holdingHoe) {
            e.setCancelled(true);
            above.setType(Material.AIR);
            verboseDenial(messager.getPlantDroppedNothing(), player);
        }

        Block below = above.getRelative(0, -1, 0);
        if (below.getType() != Material.FARMLAND) {
            return;
        }
        if (farmedFarmlandTurnsIntoDirt > 0)
            if (chance(farmedFarmlandTurnsIntoDirt)) {
                below.setType(Material.DIRT);
                return;
            }

        if (plant.getNeedsHoeForFarmlandRetain(biome) && !holdingHoe) {
            below.setType(Material.DIRT);
            verboseDenial(messager.getFarmlandIntoDirt(), player);
        }
    }
}
