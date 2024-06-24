package com.lichenaut.plantnerfer;

import com.lichenaut.plantnerfer.cmd.PNCmd;
import com.lichenaut.plantnerfer.cmd.PNTab;
import com.lichenaut.plantnerfer.listen.*;
import com.lichenaut.plantnerfer.load.Plant;
import com.lichenaut.plantnerfer.load.PlantLoader;
import com.lichenaut.plantnerfer.util.Copier;
import com.lichenaut.plantnerfer.util.ListenerUtil;
import com.lichenaut.plantnerfer.util.Messager;
import com.lichenaut.plantnerfer.util.VersionGetter;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

@Getter
public final class Main extends JavaPlugin {

    private static final Logger logger = LogManager.getLogger("PlantNerfer");
    private static final HashMap<Material, Plant> plants = new HashMap<>();
    private final HashMap<String, HashSet<Biome>> biomeGroups = new HashMap<>();
    private final PluginManager pMan = Bukkit.getPluginManager();// Didn't include BlockPhysicsEvent for when crops get
                                                                 // destroyed at low light levels (the vanilla mechanic)
                                                                 // because it's so common.
    private final String separator = FileSystems.getDefault().getSeparator();
    private Configuration config;
    private CompletableFuture<Void> mainFuture = CompletableFuture.completedFuture(null);
    private Messager messager;
    private PluginCommand pnCommand;

    @Override
    public void onEnable() {
        new Metrics(this, 18989);
        new VersionGetter(this).getVersion(version -> {
            if (!this.getDescription().getVersion().equals(version)) {
                logger.info("Update available.");
            }
        });

        if (Integer.parseInt(Bukkit.getServer().getBukkitVersion().split("-")[0].split(Pattern.quote("."))[1]) < 20) {
            logger.error("Minecraft version under 1.20 detected! Disabling plugin.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        getConfig().options().copyDefaults();
        saveDefaultConfig();
        pnCommand = getCommand("pn");
        reloadable();
    }

    public void reloadPlugin() {
        reloadConfig();
        biomeGroups.clear();
        plants.clear();
        HandlerList.unregisterAll(this);
        reloadable();
    }

    public void reloadable() {
        config = getConfig();
        if (config.getBoolean("disable-plugin")) {
            logger.info("Plugin disabled in config.yml.");
            return;
        }

        mainFuture = mainFuture
                .thenAcceptAsync(declared -> {
                    String localesFolderPath = getDataFolder().getPath() + separator + "locales";
                    File localesFolder = new File(localesFolderPath);
                    if (!localesFolder.mkdirs() && !localesFolder.exists()) {
                        throw new RuntimeException("Failed to create 'locales' folder!");
                    }

                    String localesSeparator = "locales" + separator;
                    copyLocale(localesFolderPath, localesSeparator, "de.properties");
                    copyLocale(localesFolderPath, localesSeparator, "en.properties");
                    copyLocale(localesFolderPath, localesSeparator, "es.properties");
                    copyLocale(localesFolderPath, localesSeparator, "fr.properties");
                    copyLocale(localesFolderPath, localesSeparator, "ru.properties");
                    messager = new Messager(logger,  this, config.getString("locale"), separator);
                    try {
                        messager.loadLocaleMessages();
                    } catch (IOException e) {
                        throw new RuntimeException("IOException: Failed to load locale messages!", e);
                    }

                    ConfigurationSection biomeGroupList = config.getConfigurationSection("biome-group-list");
                    if (biomeGroupList != null) {
                        for (String group : biomeGroupList.getKeys(false)) {
                            HashSet<Biome> biomes = new HashSet<>();
                            for (String biome : biomeGroupList.getStringList(group)) {
                                biomes.add(Biome.valueOf(biome));
                            }
                            biomeGroups.put(group, biomes);
                        }
                    }
                    PlantLoader plantLoader = new PlantLoader(logger, this);
                    plantLoader.loadPlants();

                    ListenerUtil listenerUtil = new ListenerUtil(this, messager);
                    pMan.registerEvents(new BlockGrow(config.getBoolean("death-turns-into-bush"), listenerUtil, this), this);
                    pMan.registerEvents(new BlockPlace(listenerUtil, this, messager), this);
                    pMan.registerEvents(new BoneMeal(listenerUtil, this, messager), this);
                    pMan.registerEvents(new Interact(listenerUtil, this, messager), this);
                    pMan.registerEvents(new BlockBreak(config.getInt("farmed-farmland-turns-into-dirt"), listenerUtil, this, messager, plantLoader), this);
                    int ticksToDehydrate = config.getInt("ticks-dehydrated-crop-dirt");
                    if (ticksToDehydrate >= 0) {
                        pMan.registerEvents(new Farmland(ticksToDehydrate, this, plantLoader), this);
                    }
                    pnCommand.setExecutor(new PNCmd(this, messager));
                    pnCommand.setTabCompleter(new PNTab());
                });
    }

    private void copyLocale(String localesFolderPath, String localesSeparator, String locale) {
        String localePath = localesFolderPath + separator + locale;
        if (!new File(localePath).exists()) {
            try {
                Copier.smallCopy(this.getResource(localesSeparator + locale), localePath);
            } catch (IOException e) {
                throw new RuntimeException("IOException: Failed to copy " + locale + "!", e);
            }
        }
    }

    public void addPlant(Plant plant) {
        plants.put(plant.getMaterial(), plant);
    }

    public Plant getPlant(Material material) {
        return plants.get(material);
    }
}