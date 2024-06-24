package com.lichenaut.plantnerfer.listen;

import com.lichenaut.plantnerfer.Main;
import com.lichenaut.plantnerfer.load.PlantLoader;
import com.lichenaut.plantnerfer.util.ListenerUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.MoistureChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class Farmland extends ListenerUtil implements Listener {

    private final int ticksToDehydrate;

    public Farmland(Main main, PlantLoader loader, int ticksToDehydrate) {
        super(plugin, loader);
        this.ticksToDehydrate = ticksToDehydrate;
    }

    @EventHandler
    private void onDehydrate(MoistureChangeEvent event) {
        Block block = e.getBlock();
        Block above = block.getRelative(0, 1, 0);
        if (block.getType() != Material.FARMLAND
                || !loader.getFarmlandReference().getFarmlandSet().contains(above.getType())) {
            return;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                if (block.getType() != Material.FARMLAND
                        || ((org.bukkit.block.data.type.Farmland) block.getBlockData()).getMoisture() != 0
                        || !loader.getFarmlandReference().getFarmlandSet().contains(above.getType())) {
                    cancel();
                    return;
                }
                block.getWorld().dropItemNaturally(above.getLocation(),
                        new ItemStack(loader.getCropReference().getCropMap().get(above.getType())));
                above.setType(Material.AIR);
                block.setType(Material.DIRT);
            }
        }.runTaskLater(plugin, ticksToDehydrate);
    }

    @EventHandler
    private void onCropPlace(BlockPlaceEvent event) {
        Block above = e.getBlock();
        Block block = above.getRelative(0, -1, 0);
        if (block.getType() != Material.FARMLAND
                || !loader.getFarmlandReference().getFarmlandSet().contains(above.getType())) {
            return;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                if (block.getType() != Material.FARMLAND
                        || ((org.bukkit.block.data.type.Farmland) block.getBlockData()).getMoisture() != 0
                        || !loader.getFarmlandReference().getFarmlandSet().contains(above.getType())) {
                    cancel();
                    return;
                }
                block.getWorld().dropItemNaturally(above.getLocation(),
                        new ItemStack(loader.getCropReference().getCropMap().get(above.getType())));
                above.setType(Material.AIR);
                block.setType(Material.DIRT);
            }
        }.runTaskLater(plugin, ticksToDehydrate);
    }
}