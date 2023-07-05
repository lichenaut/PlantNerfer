package com.lichenaut.plantnerfer.load;

import com.lichenaut.plantnerfer.PlantNerfer;
import com.lichenaut.plantnerfer.listeners.PNBlockGrowListener;
import com.lichenaut.plantnerfer.listeners.PNBlockPlaceListener;
import com.lichenaut.plantnerfer.listeners.PNBoneMealListener;
import com.lichenaut.plantnerfer.listeners.PNInteractListener;
import com.lichenaut.plantnerfer.util.PNMaterialReference;
import org.bukkit.Bukkit;
import org.bukkit.block.Biome;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.PluginManager;

import java.util.HashSet;
import java.util.TreeMap;

public class PNPlantLoader {

    private final PlantNerfer plugin;
    private final PNMaterialReference matRef = new PNMaterialReference();

    public PNPlantLoader(PlantNerfer plugin) {
        this.plugin = plugin;
    }

    private void loadPlant(String plantName) {
        boolean canPlace = true;
        int growthRate = 100;
        int growthRateDark = 100;
        int deathRate = 0;
        int deathRateDark = 0;
        int boneMealSuccessRate = 100;
        int boneMealSuccessRateDark = 100;
        int minLight = 0;
        int maxLight = 15;
        boolean ignoreLightWhenNight = true;
        boolean needsSky = false;
        int minY = 0;
        int maxY = 255;
        HashSet<String> restrictToWorlds = new HashSet<>();
        TreeMap<Biome, PNPlantBiomeStats> biomeStats = new TreeMap<>();

        ConfigurationSection plantSection = plugin.getPluginConfig().getConfigurationSection(plantName);
        if (plantSection != null) {
            for (String key : plantSection.getKeys(false)) {//set biome-less data
                if (key.equals("can-place")) {canPlace = plantSection.getBoolean(key);
                } else if (key.equals("growth-rate")) {growthRate = plantSection.getInt(key);
                } else if (key.equals("growth-rate-dark")) {growthRateDark = plantSection.getInt(key);
                } else if (key.equals("death-rate")) {deathRate = plantSection.getInt(key);
                } else if (key.equals("death-rate-dark")) {deathRateDark = plantSection.getInt(key);
                } else if (key.equals("bone-meal-success-rate")) {boneMealSuccessRate = plantSection.getInt(key);
                } else if (key.equals("bone-meal-success-rate-dark")) {boneMealSuccessRateDark = plantSection.getInt(key);
                } else if (key.equals("min-light")) {minLight = plantSection.getInt(key);
                } else if (key.equals("max-light")) {maxLight = plantSection.getInt(key);
                } else if (key.equals("place-and-bone-meal-ignores-min-light-at-night")) {ignoreLightWhenNight = plantSection.getBoolean(key);
                } else if (key.equals("needs-sky")) {needsSky = plantSection.getBoolean(key);
                } else if (key.equals("min-y")) {minY = plantSection.getInt(key);
                } else if (key.equals("max-y")) {maxY = plantSection.getInt(key);
                } else if (key.equals("restrict-to-worlds")) {restrictToWorlds = new HashSet<>(plantSection.getStringList(key));
                } else if (key.equals("biome-groups")) {
                    if (plantSection.getConfigurationSection(key) != null) {
                        for (String group : plantSection.getConfigurationSection(key).getKeys(false)) {//for each biome group, create an object for each biome in this group
                            if (!plugin.getBiomeGroups().containsKey(group)) {
                                plugin.getLogger().warning("Biome group '" + group + "' does not exist!");
                                return;
                            }

                            boolean canPlaceGroup = true;
                            int growthRateGroup = 100;
                            int growthRateDarkGroup = 100;
                            int deathRateGroup = 0;
                            int deathRateDarkGroup = 0;
                            int boneMealSuccessRateGroup = 100;
                            int boneMealSuccessRateDarkGroup = 100;
                            int minLightGroup = 0;
                            int maxLightGroup = 15;
                            boolean ignoreLightWhenNightGroup = true;
                            boolean needsSkyGroup = false;
                            int minYGroup = 0;
                            int maxYGroup = 255;
                            HashSet<String> restrictToWorldsGroup = new HashSet<>();

                            ConfigurationSection groupSection = plantSection.getConfigurationSection(key).getConfigurationSection(group);
                            if (groupSection != null) {
                                for (String groupKey : groupSection.getKeys(false)) {//change biome group data away from defaults to config info
                                    if (groupKey.equals("can-place")) {canPlaceGroup = groupSection.getBoolean(groupKey);
                                    } else if (groupKey.equals("growth-rate")) {growthRateGroup = groupSection.getInt(groupKey);
                                    } else if (groupKey.equals("growth-rate-dark")) {growthRateDarkGroup = groupSection.getInt(groupKey);
                                    } else if (groupKey.equals("death-rate")) {deathRateGroup = groupSection.getInt(groupKey);
                                    } else if (groupKey.equals("death-rate-dark")) {deathRateDarkGroup = groupSection.getInt(groupKey);
                                    } else if (groupKey.equals("bone-meal-success-rate")) {boneMealSuccessRateGroup = groupSection.getInt(groupKey);
                                    } else if (groupKey.equals("bone-meal-success-rate-dark")) {boneMealSuccessRateDarkGroup = groupSection.getInt(groupKey);
                                    } else if (groupKey.equals("min-light")) {minLightGroup = groupSection.getInt(groupKey);
                                    } else if (groupKey.equals("max-light")) {maxLightGroup = groupSection.getInt(groupKey);
                                    } else if (groupKey.equals("place-and-bone-meal-ignores-min-light-at-night")) {ignoreLightWhenNightGroup = groupSection.getBoolean(groupKey);
                                    } else if (groupKey.equals("needs-sky")) {needsSkyGroup = groupSection.getBoolean(groupKey);
                                    } else if (groupKey.equals("min-y")) {minYGroup = groupSection.getInt(groupKey);
                                    } else if (groupKey.equals("max-y")) {maxYGroup = groupSection.getInt(groupKey);
                                    } else if (groupKey.equals("restrict-to-worlds")) restrictToWorldsGroup = new HashSet<>(groupSection.getStringList(groupKey));
                                }
                            }

                            for (Biome biome : plugin.getBiomeGroups().get(group))
                                biomeStats.put(biome, new PNPlantBiomeStats(canPlaceGroup, growthRateGroup, growthRateDarkGroup, deathRateGroup, deathRateDarkGroup, boneMealSuccessRateGroup, boneMealSuccessRateDarkGroup, minLightGroup, maxLightGroup, ignoreLightWhenNightGroup, needsSkyGroup, minYGroup, maxYGroup, restrictToWorldsGroup));//add biome-plant data to biomeStats
                        }
                    }
                }
            }
        }

        plugin.addPlant(new PNPlant(plugin, matRef.getMaterial(plantName), canPlace, growthRate, growthRateDark, deathRate, deathRateDark, boneMealSuccessRate, boneMealSuccessRateDark, minLight, maxLight, ignoreLightWhenNight, needsSky, minY, maxY, restrictToWorlds, biomeStats));
    }

    public void loadPlants(int version) {
        PluginManager pMan = Bukkit.getPluginManager();//didn't include BlockPhysicsEvent for when crops get destroyed at low light levels (the vanilla mechanic) because it's a scary event to work with! it would not be worth the performance hit.
        pMan.registerEvents(new PNBlockGrowListener(plugin, this), plugin);
        pMan.registerEvents(new PNBlockPlaceListener(plugin, this), plugin);
        pMan.registerEvents(new PNBoneMealListener(plugin, this), plugin);
        pMan.registerEvents(new PNInteractListener(plugin, this), plugin);

        if (version == 20) {matRef.buildMatMap20();
        } else if (version == 19) {matRef.buildMatMap19();
            //} else if (i == 18) {
        } else if (version == 17) {matRef.buildMatMap17();
        } else if (version == 16) {matRef.buildMatMap16();
            //} else if (i == 15) {
        } else if (version == 14) {matRef.buildMatMap14();
        } else if (version == 13) matRef.buildMatMap13();

        for (String key : matRef.getMatMap().keySet()) loadPlant(key);
    }

    public PNMaterialReference getReference() {return matRef;}
}