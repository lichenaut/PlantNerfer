package com.lichenaut.plantnerfer.commands;

import com.lichenaut.plantnerfer.PlantNerfer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

//@SuppressWarnings("ALL")//ChatColor is deprecated?
public class PNCommand implements CommandExecutor {

    private final PlantNerfer plugin;

    public PNCommand(PlantNerfer plugin) {this.plugin = plugin;}

    public void messageSender(CommandSender sender, String message) {
        if (sender instanceof Player) {sender.sendMessage(message);
        } else {plugin.getLog().info(ChatColor.stripColor(message));}
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        String helpMessage = ChatColor.GRAY + "Guide for PlantNerfer linked here: " + ChatColor.GREEN + "https://github.com/lichenaut/PlantNerfer/blob/master/README.md";
        String invalidMessage = ChatColor.RED + "Invalid usage of '/plantnerfer'. Use '" + ChatColor.GRAY + "/pn help" + ChatColor.RED + "', or use '" + ChatColor.GRAY + "/pn reload" + ChatColor.RED + "'.";

        if (args.length == 0) {messageSender(sender, invalidMessage);return false;}

        if (args[0].equals("help")) {
            if (sender instanceof Player && !sender.hasPermission("plantnerfer.help")) return false;
            messageSender(sender, helpMessage);return true;
        } else if (args[0].equals("reload")) {
            if (sender instanceof Player && !sender.hasPermission("plantnerfer.reload")) return false;
            plugin.reloadPlugin();
            messageSender(sender, ChatColor.GREEN + "PlantNerfer reloaded.");
            return true;
        } else messageSender(sender, invalidMessage);

        return false;
    }
}