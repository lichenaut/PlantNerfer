package com.lichenaut.plantnerfer.listen;

import com.lichenaut.plantnerfer.Main;
import com.lichenaut.plantnerfer.load.Plant;
import com.lichenaut.plantnerfer.util.ListenerUtil;
import com.lichenaut.plantnerfer.util.Messager;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.chat.BaseComponent;
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
import org.bukkit.inventory.PlayerInventory;

import java.util.Objects;

@RequiredArgsConstructor
public class Interact implements Listener {

    private final ListenerUtil listenerUtil;
    private final Main main;
    private final Messager messager;

    @EventHandler
    private void onPlantInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.isOp()) {
            return;
        }

        PlayerInventory inventory = player.getInventory();
        ItemStack boneMeal = new ItemStack(Material.BONE_MEAL);
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || Objects.equals(event.getHand(), EquipmentSlot.OFF_HAND)
                || inventory.getItemInMainHand().isSimilar(boneMeal)
                || inventory.getItemInOffHand().isSimilar(boneMeal)) {
            return;
        }

        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }

        Material blockType = block.getType();
        Plant plant = main.getPlant(blockType);
        if (plant == null) {
            return;
        }

        String worldName = block.getWorld().getName();
        if (listenerUtil.isInvalidWorld(worldName)) {
            return;
        }

        Biome biome = block.getBiome();
        if ((player.hasPermission("plantnerfer.bonemealrate")
                && !main.getConfiguration().getBoolean("global-bone-meal-rate-reporting"))
                || (!player.hasPermission("plantnerfer.bonemealrate.disabled")
                        && main.getConfiguration().getBoolean("global-bone-meal-rate-reporting"))) {
            BaseComponent[] message = (block.getRelative(0, 1, 0).getLightLevel() < 8) ? messager.combineMessage(messager.getBoneMealSuccessRateDark(),
                    plant.getDarkBoneMealRate(biome, worldName) + "%") : messager.combineMessage(messager.getBoneMealSuccessRate(), plant.getBoneMealRate(biome, worldName) + "%");
            messager.sendMsg(player, message, true);
        }
    }
}