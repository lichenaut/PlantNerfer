package com.lichenaut.plantnerfer.listeners;

import com.lichenaut.plantnerfer.PlantNerfer;
import com.lichenaut.plantnerfer.load.PNPlantLoader;
import com.lichenaut.plantnerfer.util.PNListenerUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Farmland;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.MoistureChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class PNFarmlandListener extends PNListenerUtil implements Listener {

    private final int ticksToDehydrate;

    public PNFarmlandListener(PlantNerfer plugin, PNPlantLoader loader, int ticksToDehydrate) {
        super(plugin, loader);
        this.ticksToDehydrate = ticksToDehydrate;
    }

    @EventHandler
    private void onDehydrate(MoistureChangeEvent e) {
        Block block = e.getBlock();
        Block above = block.getRelative(0, 1, 0);
        if (block.getType() != Material.FARMLAND || !loader.getFarmlandReference().getFarmlandSet().contains(above.getType())) {return;}
        new BukkitRunnable() {
            @Override
            public void run() {
                if (block.getType() != Material.FARMLAND || ((Farmland) block.getBlockData()).getMoisture() != 0 || !loader.getFarmlandReference().getFarmlandSet().contains(above.getType())) {cancel();return;}
                block.getWorld().dropItemNaturally(above.getLocation(), new ItemStack(loader.getCropReference().getCropMap().get(above.getType())));
                above.setType(Material.AIR);
                block.setType(Material.DIRT);
            }
        }.runTaskLater(plugin, ticksToDehydrate);
    }

    @EventHandler
    private void onCropPlace(BlockPlaceEvent e) {
        Block above = e.getBlock();
        Block block = above.getRelative(0, -1, 0);
        if (block.getType() != Material.FARMLAND || !loader.getFarmlandReference().getFarmlandSet().contains(above.getType())) {return;}
        new BukkitRunnable() {
            @Override
            public void run() {
                if (block.getType() != Material.FARMLAND || ((Farmland) block.getBlockData()).getMoisture() != 0 || !loader.getFarmlandReference().getFarmlandSet().contains(above.getType())) {cancel();return;}
                block.getWorld().dropItemNaturally(above.getLocation(), new ItemStack(loader.getCropReference().getCropMap().get(above.getType())));
                above.setType(Material.AIR);
                block.setType(Material.DIRT);
            }
        }.runTaskLater(plugin, ticksToDehydrate);
    }
}