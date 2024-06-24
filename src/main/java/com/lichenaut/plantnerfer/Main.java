package com.lichenaut.plantnerfer;

import com.lichenaut.plantnerfer.cmd.PNCmd;
import com.lichenaut.plantnerfer.cmd.PNTab;
import com.lichenaut.plantnerfer.listen.*;
import com.lichenaut.plantnerfer.load.Plant;
import com.lichenaut.plantnerfer.load.PlantLoader;
import com.lichenaut.plantnerfer.util.Copier;
import com.lichenaut.plantnerfer.util.Messager;
import com.lichenaut.plantnerfer.util.VersionGetter;
import lombok.Getter;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Pattern;

@Getter
public final class Main extends JavaPlugin {

    private final Logger log = getLogger();
    private Configuration config = getConfig();
    private final HashMap<String, HashSet<Biome>> biomeGroups = new HashMap<>();// preserve order in anything biome
                                                                                // group-related so that results are
                                                                                // consistent
    private static final HashMap<Material, Plant> plants = new HashMap<>();
    private final PluginManager pMan = Bukkit.getPluginManager();// didn't include BlockPhysicsEvent for when crops get
                                                                 // destroyed at low light levels (the vanilla mechanic)
                                                                 // because it's a scary event to work with! it would
                                                                 // not be worth the performance hit.
    private Messager messager;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        messager = new Messager(this);

        int pluginId = 18989;
        Metrics metrics = new Metrics(plugin, pluginId);

        reloadable();

        Objects.requireNonNull(getCommand("pn")).setExecutor(new PNCmd(this));
        Objects.requireNonNull(getCommand("pn")).setTabCompleter(new PNTab());
    }

    public void reloadPlugin() {
        reloadConfig();
        config = getConfig();
        biomeGroups.clear();
        plants.clear();
        HandlerList.unregisterAll(this);
        reloadable();
    }

    public void reloadable() {
        if (config.getBoolean("disable-plugin")) {
            log.info("Plugin disabled in config.yml.");
            return;
        }

        new VersionGetter(this, plugin).getVersion(version -> {
            if (!this.getDescription().getVersion().equals(version)) {
                getLog().info("Update available.");
            }
        });

        String localesFolderPath = getDataFolder().getPath() + PNSep.getSep() + "locales";
        File localesFolder = new File(localesFolderPath);
        if (!localesFolder.exists()) {
            localesFolder.mkdirs();
        }
        String dePath = localesFolderPath + PNSep.getSep() + "de.properties";
        if (!new File(dePath).exists()) {
            try {
                Copier.smallCopy(this.getResource("locales" + PNSep.getSep() + "de.properties"), dePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        String enPath = localesFolderPath + PNSep.getSep() + "en.properties";
        if (!new File(enPath).exists()) {
            try {
                Copier.smallCopy(this.getResource("locales" + PNSep.getSep() + "en.properties"), enPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        String esPath = localesFolderPath + PNSep.getSep() + "es.properties";
        if (!new File(esPath).exists()) {
            try {
                Copier.smallCopy(this.getResource("locales" + PNSep.getSep() + "es.properties"), esPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        String frPath = localesFolderPath + PNSep.getSep() + "fr.properties";
        if (!new File(frPath).exists()) {
            try {
                Copier.smallCopy(this.getResource("locales" + PNSep.getSep() + "fr.properties"), frPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        String ruPath = localesFolderPath + PNSep.getSep() + "ru.properties";
        if (!new File(ruPath).exists()) {
            try {
                Copier.smallCopy(this.getResource("locales" + PNSep.getSep() + "ru.properties"), ruPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            messager.loadLocaleMessages();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String sVersion = Bukkit.getServer().getBukkitVersion();
        int version = Integer.parseInt(sVersion.split("-")[0].split(Pattern.quote("."))[1]);

        if (version >= 13) {
            if (config.getConfigurationSection("biome-group-list") != null) {
                for (String group : Objects.requireNonNull(config.getConfigurationSection("biome-group-list"))
                        .getKeys(false)) {// for each biome group, add a HashSet of biome strings to biomeGroups
                    HashSet<Biome> biomes = new HashSet<>();
                    for (String biome : Objects.requireNonNull(config.getConfigurationSection("biome-group-list"))
                            .getStringList(group))
                        biomes.add(Biome.valueOf(biome));
                    biomeGroups.put(group, biomes);
                }
            }

            PlantLoader plantLoader = new PlantLoader(plugin);
            plantLoader.loadPlants(version);
            pMan.registerEvents(new BlockGrow(plugin, plantLoader, config.getBoolean("death-turns-into-bush")), plugin);
            pMan.registerEvents(new BlockPlace(plugin, plantLoader), plugin);
            pMan.registerEvents(new BoneMeal(plugin, plantLoader), plugin);
            pMan.registerEvents(new Interact(plugin, plantLoader), plugin);
            pMan.registerEvents(new BlockBreak(plugin, plantLoader, config.getInt("farmed-farmland-turns-into-dirt")),
                    plugin);
            int ticksToDehydrate = config.getInt("ticks-dehydrated-crop-dirt");
            if (ticksToDehydrate >= 0)
                pMan.registerEvents(new Farmland(plugin, plantLoader, ticksToDehydrate), plugin);
        } else
            log.severe("Unsupported version detected: " + sVersion + "! Disabling plugin.");
    }

    public void addPlant(Plant plant) {
        plants.put(plant.getMaterial(), plant);
    }

    public Plant getPlant(Material material) {
        return plants.get(material);
    }
}