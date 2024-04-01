package com.lichenaut.plantnerfer.listeners;

import com.lichenaut.plantnerfer.PlantNerfer;
import com.lichenaut.plantnerfer.load.PNPlant;
import com.lichenaut.plantnerfer.load.PNPlantLoader;
import com.lichenaut.plantnerfer.util.PNListenerUtil;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class PNInteractListener extends PNListenerUtil implements Listener {

    public PNInteractListener(PlantNerfer plugin, PNPlantLoader loader) {super(plugin, loader);}

    @EventHandler
    public void onPlantInteract(PlayerInteractEvent e) {
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || Objects.equals(e.getHand(), EquipmentSlot.OFF_HAND) || e.getPlayer().getInventory().getItemInMainHand().isSimilar(new ItemStack(Material.BONE_MEAL)) || e.getPlayer().getInventory().getItemInOffHand().isSimilar(new ItemStack(Material.BONE_MEAL))) {return;}
        Block block = e.getClickedBlock();
        if (block == null || plugin.getPlant(block.getType()) == null || loader.getReference().isNotPlantBlock(block.getType())) {return;}
        String worldName = block.getWorld().getName();
        if (invalidWorld(worldName)) {return;}
        PNPlant plant = plugin.getPlant(block.getType());
        if (plant == null) {return;}
        Biome biome = block.getBiome();

        Player player = e.getPlayer();
        if (player.isOp()) {return;}
        if ((player.hasPermission("plantnerfer.bonemealrate") && !plugin.getConfig().getBoolean("global-bone-meal-rate-reporting")) || (!player.hasPermission("plantnerfer.bonemealrate.disabled") && plugin.getConfig().getBoolean("global-bone-meal-rate-reporting"))) {
            if (block.getRelative(0, 1, 0).getLightLevel() < 8) {player.sendMessage("Bone Meal Success Rate: " + plant.getDarkBoneMealRate(biome) + "%");
            } else player.sendMessage("Bone Meal Success Rate: " + plant.getBoneMealRate(biome) + "%");
        }
    }
}