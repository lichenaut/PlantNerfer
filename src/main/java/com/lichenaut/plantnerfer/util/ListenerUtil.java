package com.lichenaut.plantnerfer.util;

import com.lichenaut.plantnerfer.Main;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;

import java.util.List;

@RequiredArgsConstructor
public class ListenerUtil {

        private final Main main;
        private final Messager messager;

        public boolean chance(int chance) {
                return Math.random() * 100 < chance;
        }

        public boolean isInvalidWorld(String worldName) {
                List<String> worlds = main.getConfig().getStringList("restrict-plugin-to-worlds");
                return !worlds.isEmpty() && !worlds.contains(worldName);
        }

        public void verboseDenial(BaseComponent[] message, Player player) {
                if (!player.isOp() && ((!main.getConfig().getBoolean("verbose-denial")
                                && player.hasPermission("plantnerfer.verbose"))
                                || (main.getConfig().getBoolean("verbose-denial")
                                                && !player.hasPermission("plantnerfer.verbose.disabled")))) {
                        messager.sendMsg(player, message, true);
                }
        }
}