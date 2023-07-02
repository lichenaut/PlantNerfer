package com.lichenaut.plantnerfer.load;

import com.lichenaut.plantnerfer.PlantNerfer;
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

    public PNPlantLoader(PlantNerfer plugin) {this.plugin = plugin;}

    private void loadPlant(String plantName) {
        int growthRate = 100;
        int growthRateDark = 100;
        int deathRate = 0;
        int deathRateDark = 0;
        int boneMealSuccessRate = 100;
        int minLight = 0;
        int maxLight = 15;
        int minY = 0;
        int maxY = 255;
        HashSet<String> restrictToWorlds = new HashSet<>();
        TreeMap<Biome, PNPlantBiomeGroupStats> biomeStats = new TreeMap<>();

        ConfigurationSection plantSection = plugin.getPluginConfig().getConfigurationSection(plantName);
        if (plantSection != null) {
            for (String key : plantSection.getKeys(false)) {
                if (key.equals("growth-rate")) {
                    growthRate = plantSection.getInt(key);
                } else if (key.equals("growth-rate-dark")) {
                    growthRateDark = plantSection.getInt(key);
                } else if (key.equals("death-rate")) {
                    deathRate = plantSection.getInt(key);
                } else if (key.equals("death-rate-dark")) {
                    deathRateDark = plantSection.getInt(key);
                } else if (key.equals("bone-meal-success-rate")) {
                    boneMealSuccessRate = plantSection.getInt(key);
                } else if (key.equals("min-light")) {
                    minLight = plantSection.getInt(key);
                } else if (key.equals("max-light")) {
                    maxLight = plantSection.getInt(key);
                } else if (key.equals("min-y")) {
                    minY = plantSection.getInt(key);
                } else if (key.equals("max-y")) {
                    maxY = plantSection.getInt(key);
                } else if (key.equals("restrict-to-worlds")) {
                    restrictToWorlds = new HashSet<>(plantSection.getStringList(key));
                } else if (key.equals("biome-groups")) {
                    for (String group : plantSection.getConfigurationSection(key).getKeys(false)) {
                        if (!plugin.getBiomeGroups().containsKey(group)) {plugin.getLogger().warning("Biome group '" + group + "' does not exist!");return;}

                        int growthRateGroup = 100;
                        int growthRateDarkGroup = 100;
                        int deathRateGroup = 0;
                        int deathRateDarkGroup = 0;
                        int boneMealSuccessRateGroup = 100;
                        int minLightGroup = 0;
                        int maxLightGroup = 15;
                        int minYGroup = 0;
                        int maxYGroup = 255;
                        HashSet<String> restrictToWorldsGroup = new HashSet<>();
                        HashSet<Biome> biomes = plugin.getBiomeGroups().get(group);

                        ConfigurationSection groupSection = plantSection.getConfigurationSection(key).getConfigurationSection(group);
                        for (String groupKey : groupSection.getKeys(false)) {
                            if (groupKey.equals("growth-rate")) {
                                growthRateGroup = groupSection.getInt(groupKey);
                            } else if (groupKey.equals("growth-rate-dark")) {
                                growthRateDarkGroup = groupSection.getInt(groupKey);
                            } else if (groupKey.equals("death-rate")) {
                                deathRateGroup = groupSection.getInt(groupKey);
                            } else if (groupKey.equals("death-rate-dark")) {
                                deathRateDarkGroup = groupSection.getInt(groupKey);
                            } else if (groupKey.equals("bone-meal-success-rate")) {
                                boneMealSuccessRateGroup = groupSection.getInt(groupKey);
                            } else if (groupKey.equals("min-light")) {
                                minLightGroup = groupSection.getInt(groupKey);
                            } else if (groupKey.equals("max-light")) {
                                maxLightGroup = groupSection.getInt(groupKey);
                            } else if (groupKey.equals("min-y")) {
                                minYGroup = groupSection.getInt(groupKey);
                            } else if (groupKey.equals("max-y")) {
                                maxYGroup = groupSection.getInt(groupKey);
                            } else if (groupKey.equals("restrict-to-worlds")) {
                                restrictToWorldsGroup = new HashSet<>(groupSection.getStringList(groupKey));
                            }
                        }
                        biomeStats.put(Biome.valueOf(group), new PNPlantBiomeGroupStats(growthRateGroup, growthRateDarkGroup, deathRateGroup, deathRateDarkGroup, boneMealSuccessRateGroup, minLightGroup, maxLightGroup, minYGroup, maxYGroup, restrictToWorldsGroup, biomes));
                    }
                }
            }
        }
        plugin.addPlant(new PNPlant(matRef.getMaterial(plantName), growthRate, growthRateDark, deathRate, deathRateDark, boneMealSuccessRate, minLight, maxLight, minY, maxY, restrictToWorlds, biomeStats));
    }

    public void loadPlants() {
        int version = plugin.getVersion();
        HashSet<String> matRefs = new HashSet<>();//does not add lower version material references if one has already been added
        for (int i = version; i > 0; i--) {
            if (i == 20) {
            /*} else if (i == 19) {
            } else if (i == 18) {*/
            } else if (i == 17) {
                if (matRefs.add("ref")) matRef.buildMatMap17();
                loadPlant("moss-carpet");
                loadPlant("moss-block");
            } else if (i == 16) {
                if (matRefs.add("ref")) matRef.buildMatMap16();
                loadPlant("twisting-vines");
                loadPlant("weeping-vines");
                loadPlant("crimson-fungus");
                loadPlant("warped-fungus");
            //} else if (i == 15) {
            } else if (i == 14) {
                if (matRefs.add("ref")) matRef.buildMatMap14();
                loadPlant("bamboo");
                loadPlant("bamboo-sapling");
                loadPlant("sweet-berries");
                loadPlant("sweet-berry-bush");
            } else if (i == 13) {
                if (matRefs.add("ref")) matRef.buildMatMap13();
                loadPlant("oak-sapling");
                loadPlant("dark-oak-sapling");
                loadPlant("spruce-sapling");
                loadPlant("birch-sapling");
                loadPlant("jungle-sapling");
                loadPlant("acacia-sapling");
                loadPlant("melon-seeds");
                loadPlant("pumpkin-seeds");
                loadPlant("wheat-seeds");
                loadPlant("beetroot-seeds");
                loadPlant("cocoa-beans");
                loadPlant("sugar-cane");
                loadPlant("kelp");
                loadPlant("seagrass");
                loadPlant("vine");
                loadPlant("lily-pad");
                loadPlant("melon");
                loadPlant("pumpkin");
                loadPlant("carrot");
                loadPlant("potato");
                loadPlant("beetroot");
                loadPlant("wheat");
                loadPlant("cactus");
                loadPlant("cocoa");
                loadPlant("brown-mushroom");
                loadPlant("red-mushroom");
                loadPlant("nether-wart");
                loadPlant("chorus-plant");
                break;
            }
        }
    }
}
