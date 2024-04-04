package com.lichenaut.plantnerfer.commands;

import com.lichenaut.plantnerfer.PlantNerfer;
import com.lichenaut.plantnerfer.util.PNMessageParser;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

//@SuppressWarnings("ALL")//ChatColor is deprecated?
public class PNCommand implements CommandExecutor {

    private final PlantNerfer plugin;
    private final PNMessageParser messageParser;

    public PNCommand(PlantNerfer plugin) {
        this.plugin = plugin;
        messageParser = plugin.getMessageParser();
    }

    private void messageSender(CommandSender sender, BaseComponent[] message) {
        if (sender instanceof Player) {sender.sendMessage(message);
        } else {plugin.getLog().info(new TextComponent(message).toLegacyText().replaceAll("§[0-9a-fA-FklmnoKLMNO]", ""));}
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        BaseComponent[] invalidMessage = messageParser.getInvalidCommand();
        if (args.length == 0) {messageSender(sender, invalidMessage);return false;}

        if (args[0].equals("help")) {
            if (sender instanceof Player && !sender.hasPermission("plantnerfer.help")) return false;
            messageSender(sender, messageParser.getHelpCommand());return true;
        } else if (args[0].equals("reload")) {
            if (sender instanceof Player && !sender.hasPermission("plantnerfer.reload")) return false;
            plugin.reloadPlugin();
            messageSender(sender, messageParser.getReloadCommand());
            return true;
        } else messageSender(sender, invalidMessage);

        return false;
    }
}