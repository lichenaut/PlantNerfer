package com.lichenaut.plantnerfer.util;

import com.lichenaut.plantnerfer.Main;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class VersionGetter {

    private final JavaPlugin plugin;
    private final Main pnPlugin;

    public VersionGetter(JavaPlugin plugin, Main dlPlugin) {
        this.plugin = plugin;
        this.pnPlugin = dlPlugin;
    }

    public void getVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + 111002)
                    .openStream(); Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException e) {
                pnPlugin.getLog().warning("Unable to check for updates!");
                e.printStackTrace();
            }
        });
    }
}