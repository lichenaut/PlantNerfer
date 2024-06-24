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
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockFertilizeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class Interact extends ListenerUtil implements Listener {

    private final Messager messager;

    public Interact(Main main, PlantLoader loader) {
        super(plugin, loader);
        this.messager = plugin.getMessageParser();
    }

    @EventHandler
    private void onPlantInteract(PlayerInteractEvent event) {
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || Objects.equals(e.getHand(), EquipmentSlot.OFF_HAND)
                || e.getPlayer().getInventory().getItemInMainHand().isSimilar(new ItemStack(Material.BONE_MEAL))
                || e.getPlayer().getInventory().getItemInOffHand().isSimilar(new ItemStack(Material.BONE_MEAL))) {
            return;
        }
        Block block = e.getClickedBlock();
        if (block == null || plugin.getPlant(block.getType()) == null
                || loader.getReference().isNotPlantBlock(block.getType())) {
            return;
        }
        String worldName = block.getWorld().getName();
        if (isInvalidWorld(worldName)) {
            return;
        }
        Plant plant = plugin.getPlant(block.getType());
        if (plant == null) {
            return;
        }
        Biome biome = block.getBiome();

        Player player = e.getPlayer();
        if (player.isOp()) {
            return;
        }
        if ((player.hasPermission("plantnerfer.bonemealrate")
                && !plugin.getConfig().getBoolean("global-bone-meal-rate-reporting"))
                || (!player.hasPermission("plantnerfer.bonemealrate.disabled")
                        && plugin.getConfig().getBoolean("global-bone-meal-rate-reporting"))) {
            if (block.getRelative(0, 1, 0).getLightLevel() < 8) {
                player.sendMessage(messager.combineMessage(messager.getBoneMealSuccessRateDark(),
                        plant.getDarkBoneMealRate(biome) + "%"));
            } else {
                player.sendMessage(
                        messager.combineMessage(messager.getBoneMealSuccessRate(), plant.getBoneMealRate(biome) + "%"));
            }
        }
    }
}