package com.lichenaut.plantnerfer.cmd;

import com.lichenaut.plantnerfer.Main;
import com.lichenaut.plantnerfer.util.Messager;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class PNCmd implements CommandExecutor {

    private static CompletableFuture<Void> commandFuture = CompletableFuture.completedFuture(null);
    private final Main main;
    private final Messager messager;

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label,
            @Nonnull String[] args) {
        if (checkDisallowed(sender, "plantnerfer.command")) {
            return true;
        }

        if (args.length != 1) {
            commandFuture = commandFuture
                    .thenAcceptAsync(processed -> messager.sendMsg(sender, messager.getInvalidCommand(), true));
            return true;
        }

        if (args[0].equals("help")) {
            if (checkDisallowed(sender, "plantnerfer.command.help")) {
                return true;
            }

            commandFuture = commandFuture
                    .thenAcceptAsync(processed -> messager.sendMsg(sender, messager.getHelpCommand(), true));
            return true;
        } else if (args[0].equals("reload")) {
            if (checkDisallowed(sender, "plantnerfer.command.reload")) {
                return true;
            }

            main.reloadPlugin();
            commandFuture = commandFuture
                    .thenAcceptAsync(processed -> messager.sendMsg(sender, messager.getReloadCommand(), true));
            return true;
        }

        commandFuture = commandFuture
                .thenAcceptAsync(processed -> messager.sendMsg(sender, messager.getInvalidCommand(), true));
        return true;
    }

    public boolean checkDisallowed(CommandSender sender, String permission) {
        return sender instanceof Player && !sender.hasPermission(permission);
    }
}