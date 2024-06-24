package com.lichenaut.plantnerfer.listen;

import com.lichenaut.plantnerfer.Main;
import com.lichenaut.plantnerfer.load.Plant;
import com.lichenaut.plantnerfer.load.PlantLoader;
import com.lichenaut.plantnerfer.util.ListenerUtil;
import com.lichenaut.plantnerfer.util.Messager;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

@RequiredArgsConstructor
public class BlockBreak implements Listener {

    private final int farmedFarmlandTurnsIntoDirt;
    private final ListenerUtil listenerUtil;
    private final Main main;
    private final Messager messager;
    private final PlantLoader plantLoader;

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
        Block above = event.getBlock();
        Plant plant = main.getPlant(above.getType());
        if (plant == null) {
            return;
        }

        String worldName = above.getWorld().getName();
        if (listenerUtil.isInvalidWorld(worldName)) {
            return;
        }

        Player player = event.getPlayer();
        Biome biome = above.getBiome();
        boolean holdingHoe = plantLoader.getHoeRef().getHoeSet()
                .contains(player.getInventory().getItemInMainHand().getType());
        if (!holdingHoe && plant.getNeedsHoeForDrops(biome, worldName)) {
            event.setCancelled(true);
            above.setType(Material.AIR);
            listenerUtil.verboseDenial(messager.getPlantDroppedNothing(), player);
        }

        Block below = above.getRelative(0, -1, 0);
        if (below.getType() != Material.FARMLAND) {
            return;
        }

        if (!holdingHoe && plant.getNeedsHoeForFarmlandRetain(biome, worldName)) {
            below.setType(Material.DIRT);
            listenerUtil.verboseDenial(messager.getFarmlandIntoDirt(), player);
            return;
        }

        if (farmedFarmlandTurnsIntoDirt > 0 && listenerUtil.chance(farmedFarmlandTurnsIntoDirt)) {
            below.setType(Material.DIRT);
        }
    }
}
