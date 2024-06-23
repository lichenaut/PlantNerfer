package com.lichenaut.plantnerfer.util;

import com.lichenaut.plantnerfer.Main;
import com.lichenaut.plantnerfer.load.Plant;
import com.lichenaut.plantnerfer.load.PlantLoader;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;

public class ListenerUtil {

        protected final Main main;
        protected final PlantLoader loader;

        public ListenerUtil(Main main, PlantLoader loader) {
                this.plugin = plugin;
                this.loader = loader;
        }

        public void verboseDenial(String s, Player p) {
                if (!p.isOp() && ((!plugin.getConfig().getBoolean("verbose-denial")
                                && p.hasPermission("plantnerfer.verbose"))
                                || (plugin.getConfig().getBoolean("verbose-denial")
                                                && !p.hasPermission("plantnerfer.verbose.disabled")))) {
                        p.sendMessage(s);
                }
        }

        public void verboseDenial(BaseComponent[] c, Player p) {
                if (!p.isOp() && ((!plugin.getConfig().getBoolean("verbose-denial")
                                && p.hasPermission("plantnerfer.verbose"))
                                || (plugin.getConfig().getBoolean("verbose-denial")
                                                && !p.hasPermission("plantnerfer.verbose.disabled")))) {
                        p.sendMessage(c);
                }
        }

        public boolean invalidWorld(String worldName) {
                List<String> worlds = plugin.getConfig().getStringList("restrict-plugin-to-worlds");
                if (worlds.size() > 0)
                        return !worlds.contains(worldName);
                else
                        return false;
        }

        public boolean notIgnoreLightWhenNight(Block block, Plant plant) {
                if (block.getLocation().getWorld().getTime() > 12300
                                && block.getLocation().getWorld().getTime() < 23850) {
                        return !plant.getIgnoreLightWhenNight(block.getBiome());// return whether it ignores light when
                                                                                // night
                } else
                        return block.getRelative(0, 1, 0).getLightLevel() < plant.getMinLight(block.getBiome());
        }

        public boolean chance(int chance) {
                return Math.random() * 100 < chance;
        }
}