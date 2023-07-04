package com.lichenaut.plantnerfer;

import com.lichenaut.plantnerfer.load.PNPlant;
import com.lichenaut.plantnerfer.load.PNPlantLoader;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public final class PlantNerfer extends JavaPlugin {

    private final PlantNerfer plugin = this;
    private final Logger log = getLogger();
    private final Configuration config = getConfig();
    private final TreeMap<String, HashSet<Biome>> biomeGroups = new TreeMap<>();//preserve order in anything biome group-related so that results are consistent
    private final HashMap<Material, PNPlant> plants = new HashMap<>();

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        //int pluginId = ?????;
        //Metrics metrics = new Metrics(plugin, pluginId);

        if (config.getBoolean("disable-plugin")) log.info("Plugin disabled in config.yml.");
        else {
            //new PNUpdateChecker(this, plugin).getVersion(version -> {if (!this.getDescription().getVersion().equals(version)) {getLog().info("Update available.");}});

            String sVersion = Bukkit.getServer().getBukkitVersion();
            int version = Integer.parseInt(sVersion.split("-")[0].split(Pattern.quote("."))[1]);

            if (version >= 13) {
                if (config.getConfigurationSection("biome-groups") != null) {
                    for (String group : config.getConfigurationSection("biome-groups").getKeys(false)) {//for each biome group, add a HashSet of biome strings to biomeGroups
                        HashSet<Biome> biomes = new HashSet<>();
                        if (config.getConfigurationSection("biome-groups").getConfigurationSection(group) != null) for (String biome : config.getConfigurationSection("biome-groups").getConfigurationSection(group).getKeys(false)) biomes.add(Biome.valueOf(biome));
                        biomeGroups.put(group, biomes);
                    }
                }

                new PNPlantLoader(plugin).loadPlants(version);
            } else log.severe("Unsupported version detected: " + sVersion + "! Disabling plugin.");
        }
    }

    public Configuration getPluginConfig() {return config;}
    public TreeMap<String, HashSet<Biome>> getBiomeGroups() {return biomeGroups;}
    public void addPlant(PNPlant plant) {plants.put(plant.getMaterial(), plant);}
    public PNPlant getPlant(Material material) {return plants.get(material);}
}