package com.lichenaut.plantnerfer.load;

import com.lichenaut.plantnerfer.PlantNerfer;
import com.lichenaut.plantnerfer.util.PNMaterialReference;
import org.bukkit.block.Biome;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashSet;
import java.util.Objects;
import java.util.TreeMap;

public class PNPlantLoader {

    private final PlantNerfer plugin;
    private final PNMaterialReference matRef = new PNMaterialReference();//general reference
    private final PNMaterialReference farmlandRef = new PNMaterialReference();//farmland crop reference
    private final PNMaterialReference cropRef = new PNMaterialReference();//items that can be planted on farmland (dropped when farmland turns to dirt)

    public PNPlantLoader(PlantNerfer plugin) {this.plugin = plugin;}

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
        boolean transparentBlocksCountAsSky = true;
        int noSkyGrowthRate = 100;
        int noSkyDeathRate = 0;
        int minY = -64;
        int maxY = 255;
        HashSet<String> restrictToWorlds = new HashSet<>();
        TreeMap<Biome, PNPlantBiomeStats> biomeStats = new TreeMap<>();

        ConfigurationSection plantSection = plugin.getConfig().getConfigurationSection(plantName);
        if (plantSection == null) {return;}
        for (String key : plantSection.getKeys(false)) {//set biome-less data
            switch (key) {
                case "can-place": canPlace = plantSection.getBoolean(key);break;
                case "growth-rate": growthRate = plantSection.getInt(key);break;
                case "growth-rate-dark": growthRateDark = plantSection.getInt(key);break;
                case "death-rate": deathRate = plantSection.getInt(key);break;
                case "death-rate-dark": deathRateDark = plantSection.getInt(key);break;
                case "bone-meal-success-rate": boneMealSuccessRate = plantSection.getInt(key);break;
                case "bone-meal-success-rate-dark": boneMealSuccessRateDark = plantSection.getInt(key);break;
                case "min-light": minLight = plantSection.getInt(key);break;
                case "max-light": maxLight = plantSection.getInt(key);break;
                case "place-and-bone-meal-ignores-min-light-at-night": ignoreLightWhenNight = plantSection.getBoolean(key);break;
                case "needs-sky": needsSky = plantSection.getBoolean(key);break;
                case "transparent-blocks-count-as-sky": transparentBlocksCountAsSky = plantSection.getBoolean(key);break;
                case "no-sky-growth-rate": noSkyGrowthRate = plantSection.getInt(key);break;
                case "no-sky-death-rate": noSkyDeathRate = plantSection.getInt(key);break;
                case "min-y": minY = plantSection.getInt(key);break;
                case "max-y": maxY = plantSection.getInt(key);break;
                case "restrict-to-worlds": restrictToWorlds = new HashSet<>(plantSection.getStringList(key));break;
            }

            if (plantSection.getConfigurationSection("biome-groups") != null) {
                for (String group : Objects.requireNonNull(plantSection.getConfigurationSection("biome-groups")).getKeys(false)) {//for each biome group, create an object for each biome in this group
                    if (!plugin.getBiomeGroups().containsKey(group)) {plugin.getLogger().warning("Biome group '" + group + "' does not exist!");return;}

                    boolean canPlaceGroup = canPlace;//get biome group defaults from general plant settings
                    int growthRateGroup = growthRate;
                    int growthRateDarkGroup = growthRateDark;
                    int deathRateGroup = deathRate;
                    int deathRateDarkGroup = deathRateDark;
                    int boneMealSuccessRateGroup = boneMealSuccessRate;
                    int boneMealSuccessRateDarkGroup = boneMealSuccessRateDark;
                    int minLightGroup = minLight;
                    int maxLightGroup = maxLight;
                    boolean ignoreLightWhenNightGroup = ignoreLightWhenNight;
                    boolean needsSkyGroup = needsSky;
                    boolean transparentBlocksCountAsSkyGroup = transparentBlocksCountAsSky;
                    int noSkyGrowthRateGroup = 100;
                    int noSkyDeathRateGroup = 0;
                    int minYGroup = minY;
                    int maxYGroup = maxY;
                    HashSet<String> restrictToWorldsGroup = restrictToWorlds;

                    ConfigurationSection groupSection = Objects.requireNonNull(plantSection.getConfigurationSection("biome-groups")).getConfigurationSection(group);
                    if (groupSection != null) {
                        for (String groupKey : groupSection.getKeys(false)) {//change biome group data away from defaults to config info
                            switch (groupKey) {
                                case "can-place": canPlaceGroup = groupSection.getBoolean(groupKey);break;
                                case "growth-rate": growthRateGroup = groupSection.getInt(groupKey);break;
                                case "growth-rate-dark": growthRateDarkGroup = groupSection.getInt(groupKey);break;
                                case "death-rate": deathRateGroup = groupSection.getInt(groupKey);break;
                                case "death-rate-dark": deathRateDarkGroup = groupSection.getInt(groupKey);break;
                                case "bone-meal-success-rate": boneMealSuccessRateGroup = groupSection.getInt(groupKey);break;
                                case "bone-meal-success-rate-dark": boneMealSuccessRateDarkGroup = groupSection.getInt(groupKey);break;
                                case "min-light": minLightGroup = groupSection.getInt(groupKey);break;
                                case "max-light": maxLightGroup = groupSection.getInt(groupKey);break;
                                case "place-and-bone-meal-ignores-min-light-at-night": ignoreLightWhenNightGroup = groupSection.getBoolean(groupKey);break;
                                case "needs-sky": needsSkyGroup = groupSection.getBoolean(groupKey);break;
                                case "transparent-blocks-count-as-sky": transparentBlocksCountAsSkyGroup = groupSection.getBoolean(groupKey);break;
                                case "no-sky-growth-rate": noSkyGrowthRateGroup = groupSection.getInt(groupKey);break;
                                case "no-sky-death-rate": noSkyDeathRateGroup = groupSection.getInt(groupKey);break;
                                case "min-y": minYGroup = groupSection.getInt(groupKey);break;
                                case "max-y": maxYGroup = groupSection.getInt(groupKey);break;
                                case "restrict-to-worlds": restrictToWorldsGroup = new HashSet<>(groupSection.getStringList(groupKey));break;
                            }
                        }
                    }

                    for (Biome biome : plugin.getBiomeGroups().get(group)) biomeStats.put(biome, new PNPlantBiomeStats(canPlaceGroup, growthRateGroup, growthRateDarkGroup, deathRateGroup, deathRateDarkGroup, boneMealSuccessRateGroup, boneMealSuccessRateDarkGroup, minLightGroup, maxLightGroup, ignoreLightWhenNightGroup, needsSkyGroup, transparentBlocksCountAsSkyGroup, noSkyGrowthRateGroup, noSkyDeathRateGroup, minYGroup, maxYGroup, restrictToWorldsGroup));//add biome-plant data to biomeStats
                }
            }
        }

        plugin.addPlant(new PNPlant(plugin, matRef.getMaterial(plantName), canPlace, growthRate, growthRateDark, deathRate, deathRateDark, boneMealSuccessRate, boneMealSuccessRateDark, minLight, maxLight, ignoreLightWhenNight, needsSky, transparentBlocksCountAsSky, noSkyGrowthRate, noSkyDeathRate, minY, maxY, restrictToWorlds, biomeStats));
    }

    public void loadPlants(int version) {// Code for version 1.13 is an artifact, that version is not supported.
        if (version >= 20) {
            matRef.buildMatMap20();
            farmlandRef.buildFarmlandCropSet20();
            cropRef.buildCropDropMap20();
        } else if (version == 19) {matRef.buildMatMap19();
        } else if (version >= 17) {matRef.buildMatMap17();
        } else if (version == 16) {matRef.buildMatMap16();
        } else if (version == 14) {matRef.buildMatMap14();
        } else if (version == 13) {
            matRef.buildMatMap13();
            farmlandRef.buildFarmlandCropSet13();
            cropRef.buildCropDropMap13();
        }

        for (String key : matRef.getMatMap().keySet()) loadPlant(key);
    }

    public PNMaterialReference getReference() {return matRef;}
    public PNMaterialReference getFarmlandReference() {return farmlandRef;}
    public PNMaterialReference getCropReference() {return cropRef;}
}