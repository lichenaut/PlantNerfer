package com.lichenaut.plantnerfer.listen;

import com.lichenaut.plantnerfer.Main;
import com.lichenaut.plantnerfer.load.PlantLoader;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Farmland;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.MoistureChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
public class Dehydrate implements Listener {

    private final int ticksToDehydrate;
    private final Main main;
    private final PlantLoader loader;

    @EventHandler
    private void onDehydrate(MoistureChangeEvent event) {
        Block block = event.getBlock();
        scheduleToDirt(block, block.getRelative(0, 1, 0));
    }

    @EventHandler
    private void onCropPlace(BlockPlaceEvent event) {
        Block above = event.getBlock();
        scheduleToDirt(above.getRelative(0, -1, 0), above);
    }

    private void scheduleToDirt(Block block, Block above) {
        if (block.getType() != Material.FARMLAND) {
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!(block instanceof Farmland)) {
                    return;
                }

                Farmland farmlandData = (Farmland) block.getBlockData();
                if (block.getType() != Material.FARMLAND
                        || farmlandData.getMoisture() == farmlandData.getMaximumMoisture()) {
                    cancel();
                    return;
                }

                Material toDrop = loader.getCropRef().getCropMap().get(above.getType());
                if (toDrop != null) {
                    block.getWorld().dropItemNaturally(above.getLocation(), new ItemStack(toDrop));
                    above.setType(Material.AIR);
                }
                block.setType(Material.DIRT);
            }
        }.runTaskLater(main, ticksToDehydrate);
    }
}