package com.lichenaut.plantnerfer.listen;

import com.lichenaut.plantnerfer.Main;
import com.lichenaut.plantnerfer.load.PlantLoader;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.MoistureChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;

@RequiredArgsConstructor
public class Farmland implements Listener {

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
        HashSet<Material> farmlandSet = loader.getFarmlandRef().getFarmlandSet();
        if (block.getType() != Material.FARMLAND
                || !farmlandSet.contains(above.getType())) {
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                Material aboveType = above.getType();
                if (block.getType() != Material.FARMLAND
                        || ((org.bukkit.block.data.type.Farmland) block.getBlockData()).getMoisture() != 0
                        || !farmlandSet.contains(aboveType)) {
                    cancel();
                    return;
                }

                block.getWorld().dropItemNaturally(above.getLocation(),
                        new ItemStack(loader.getCropRef().getCropMap().get(aboveType)));
                above.setType(Material.AIR);
                block.setType(Material.DIRT);
            }
        }.runTaskLater(main, ticksToDehydrate);
    }
}