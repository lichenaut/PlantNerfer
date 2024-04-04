package com.lichenaut.plantnerfer;

import com.lichenaut.plantnerfer.commands.PNCommand;
import com.lichenaut.plantnerfer.commands.PNTabCompleter;
import com.lichenaut.plantnerfer.listeners.*;
import com.lichenaut.plantnerfer.load.PNPlant;
import com.lichenaut.plantnerfer.load.PNPlantLoader;
import com.lichenaut.plantnerfer.util.PNCopier;
import com.lichenaut.plantnerfer.util.PNMessageParser;
import com.lichenaut.plantnerfer.util.PNSep;
import com.lichenaut.plantnerfer.util.PNUpdateChecker;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public final class PlantNerfer extends JavaPlugin {

    private final PlantNerfer plugin = this;
    private final Logger log = getLogger();
    private final Configuration config = getConfig();
    private final TreeMap<String, HashSet<Biome>> biomeGroups = new TreeMap<>();//preserve order in anything biome group-related so that results are consistent
    private final HashMap<Material, PNPlant> plants = new HashMap<>();
    private final PluginManager pMan = Bukkit.getPluginManager();//didn't include BlockPhysicsEvent for when crops get destroyed at low light levels (the vanilla mechanic) because it's a scary event to work with! it would not be worth the performance hit.
    private PNMessageParser messageParser;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        try {messageParser = new PNMessageParser(this);} catch (IOException e) {throw new RuntimeException(e);}

        int pluginId = 18989;
        Metrics metrics = new Metrics(plugin, pluginId);

        reloadable();

        Objects.requireNonNull(getCommand("pn")).setExecutor(new PNCommand(this));
        Objects.requireNonNull(getCommand("pn")).setTabCompleter(new PNTabCompleter());
    }

    public void reloadPlugin() {
        reloadConfig();
        biomeGroups.clear();
        plants.clear();
        HandlerList.unregisterAll(this);
        reloadable();
    }

    public void reloadable() {
        if (config.getBoolean("disable-plugin")) {log.info("Plugin disabled in config.yml.");return;}

        new PNUpdateChecker(this, plugin).getVersion(version -> {if (!this.getDescription().getVersion().equals(version)) {getLog().info("Update available.");}});

        String localesFolderPath = getDataFolder().getPath() + PNSep.getSep() + "locales";
        File localesFolder = new File(localesFolderPath);
        if (!localesFolder.exists()) {localesFolder.mkdirs();}
        String dePath = localesFolderPath + PNSep.getSep() + "de.properties";
        if (!new File(dePath).exists()) {try {PNCopier.smallCopy(this.getResource("locales" + PNSep.getSep() + "de.properties"), dePath);} catch (IOException e) {throw new RuntimeException(e);}}
        String enPath = localesFolderPath + PNSep.getSep() + "en.properties";
        if (!new File(enPath).exists()) {try {PNCopier.smallCopy(this.getResource("locales" + PNSep.getSep() + "en.properties"), enPath);} catch (IOException e) {throw new RuntimeException(e);}}
        String esPath = localesFolderPath + PNSep.getSep() + "es.properties";
        if (!new File(esPath).exists()) {try {PNCopier.smallCopy(this.getResource("locales" + PNSep.getSep() + "es.properties"), esPath);} catch (IOException e) {throw new RuntimeException(e);}}
        String frPath = localesFolderPath + PNSep.getSep() + "fr.properties";
        if (!new File(frPath).exists()) {try {PNCopier.smallCopy(this.getResource("locales" + PNSep.getSep() + "fr.properties"), frPath);} catch (IOException e) {throw new RuntimeException(e);}}
        try {messageParser.loadLocaleMessages();} catch (IOException e) {throw new RuntimeException(e);}

        String sVersion = Bukkit.getServer().getBukkitVersion();
        int version = Integer.parseInt(sVersion.split("-")[0].split(Pattern.quote("."))[1]);

        if (version >= 13) {
            if (config.getConfigurationSection("biome-group-list") != null) {
                for (String group : Objects.requireNonNull(config.getConfigurationSection("biome-group-list")).getKeys(false)) {//for each biome group, add a HashSet of biome strings to biomeGroups
                    HashSet<Biome> biomes = new HashSet<>();
                    for (String biome : Objects.requireNonNull(config.getConfigurationSection("biome-group-list")).getStringList(group)) biomes.add(Biome.valueOf(biome));
                    biomeGroups.put(group, biomes);
                }
            }

            PNPlantLoader plantLoader = new PNPlantLoader(plugin);
            plantLoader.loadPlants(version);
            pMan.registerEvents(new PNBlockGrowListener(plugin, plantLoader, config.getBoolean("death-turns-into-bush")), plugin);
            pMan.registerEvents(new PNBlockPlaceListener(plugin, plantLoader), plugin);
            pMan.registerEvents(new PNBoneMealListener(plugin, plantLoader), plugin);
            pMan.registerEvents(new PNInteractListener(plugin, plantLoader), plugin);
            pMan.registerEvents(new PNBlockBreakListener(plugin, plantLoader, config.getInt("farmed-farmland-turns-into-dirt")), plugin);
            int ticksToDehydrate = config.getInt("ticks-dehydrated-crop-dirt");
            if (ticksToDehydrate >= 0) pMan.registerEvents(new PNFarmlandListener(plugin, plantLoader, ticksToDehydrate), plugin);
        } else log.severe("Unsupported version detected: " + sVersion + "! Disabling plugin.");
    }

    public Logger getLog() {return log;}
    public TreeMap<String, HashSet<Biome>> getBiomeGroups() {return biomeGroups;}
    public void addPlant(PNPlant plant) {plants.put(plant.getMaterial(), plant);}
    public PNPlant getPlant(Material material) {return plants.get(material);}
    public PNMessageParser getMessageParser() {return messageParser;}
}