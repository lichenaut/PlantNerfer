package com.lichenaut.plantnerfer.load;

import com.lichenaut.plantnerfer.Main;
import com.lichenaut.plantnerfer.util.MaterialReference;
import com.lichenaut.plantnerfer.util.Messager;
import lombok.RequiredArgsConstructor;
import org.bukkit.block.Biome;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

@RequiredArgsConstructor
public class PlantLoader {

    private final Main main;
    private final MaterialReference matRef = new MaterialReference();// general reference
    private final MaterialReference farmlandRef = new MaterialReference();// farmland crop reference
    private final MaterialReference cropRef = new MaterialReference();// items that can be planted on farmland (dropped
                                                                      // when farmland turns to dirt)

    private void loadPlant(String plantName) {
        boolean canPlace = true;
        int growthRate = 100;
        int growthRateDark = 100;
        int deathRate = 0;
        int deathRateDark = 0;
        int boneMealSuccessRate = 100;
        int boneMealSuccessRateDark = 100;
        boolean needsHoeForDrops = false;
        boolean needsHoeForFarmlandRetain = false;
        int minLight = 0;
        int maxLight = 15;
        boolean needsSky = false;
        boolean transparentBlocksCountAsSky = true;
        int noSkyGrowthRate = 100;
        int noSkyDeathRate = 0;
        int minY = -64;
        int maxY = 255;
        HashSet<String> disallowedBiomes = new HashSet<>();
        HashSet<String> restrictToWorlds = new HashSet<>();
        TreeMap<Biome, PlantBiomeStats> biomeStats = new TreeMap<>();

        ConfigurationSection plantSection = plugin.getConfig().getConfigurationSection(plantName);
        if (plantSection == null) {
            return;
        }
        for (String key : plantSection.getKeys(false)) {// set biome-less data
            switch (key) {
                case "can-place":
                    canPlace = plantSection.getBoolean(key);
                    break;
                case "growth-rate":
                    growthRate = plantSection.getInt(key);
                    break;
                case "growth-rate-dark":
                    growthRateDark = plantSection.getInt(key);
                    break;
                case "death-rate":
                    deathRate = plantSection.getInt(key);
                    break;
                case "death-rate-dark":
                    deathRateDark = plantSection.getInt(key);
                    break;
                case "bone-meal-success-rate":
                    boneMealSuccessRate = plantSection.getInt(key);
                    break;
                case "bone-meal-success-rate-dark":
                    boneMealSuccessRateDark = plantSection.getInt(key);
                    break;
                case "needs-hoe-for-drops":
                    needsHoeForDrops = plantSection.getBoolean(key);
                    break;
                case "needs-hoe-for-farmland-retain":
                    needsHoeForFarmlandRetain = plantSection.getBoolean(key);
                    break;
                case "min-light":
                    minLight = plantSection.getInt(key);
                    break;
                case "max-light":
                    maxLight = plantSection.getInt(key);
                    break;
                case "needs-sky":
                    needsSky = plantSection.getBoolean(key);
                    break;
                case "transparent-blocks-count-as-sky":
                    transparentBlocksCountAsSky = plantSection.getBoolean(key);
                    break;
                case "no-sky-growth-rate":
                    noSkyGrowthRate = plantSection.getInt(key);
                    break;
                case "no-sky-death-rate":
                    noSkyDeathRate = plantSection.getInt(key);
                    break;
                case "min-y":
                    minY = plantSection.getInt(key);
                    break;
                case "max-y":
                    maxY = plantSection.getInt(key);
                    break;
                case "restrict-to-worlds":
                    restrictToWorlds = new HashSet<>(plantSection.getStringList(key));
                    break;
            }

            if (plantSection.getConfigurationSection("biome-groups") != null) {
                for (String group : Objects.requireNonNull(plantSection.getConfigurationSection("biome-groups"))
                        .getKeys(false)) {// for each biome group, create an object for each biome in this group
                    if (!plugin.getBiomeGroups().containsKey(group)) {
                        plugin.getLogger().warning("Biome group '" + group + "' does not exist!");
                        return;
                    }

                    boolean canPlaceGroup = canPlace;
                    int growthRateGroup = growthRate;
                    int growthRateDarkGroup = growthRateDark;
                    int deathRateGroup = deathRate;
                    int deathRateDarkGroup = deathRateDark;
                    int boneMealSuccessRateGroup = boneMealSuccessRate;
                    int boneMealSuccessRateDarkGroup = boneMealSuccessRateDark;
                    boolean needsHoeForDropsGroup = needsHoeForDrops;
                    boolean needsHoeForFarmlandRetainGroup = needsHoeForFarmlandRetain;
                    int minLightGroup = minLight;
                    int maxLightGroup = maxLight;
                    boolean needsSkyGroup = needsSky;
                    boolean transparentBlocksCountAsSkyGroup = transparentBlocksCountAsSky;
                    int noSkyGrowthRateGroup = 100;
                    int noSkyDeathRateGroup = 0;
                    int minYGroup = minY;
                    int maxYGroup = maxY;
                    HashSet<String> restrictToWorldsGroup = restrictToWorlds;

                    ConfigurationSection groupSection = Objects
                            .requireNonNull(plantSection.getConfigurationSection("biome-groups"))
                            .getConfigurationSection(group);
                    if (groupSection != null) {
                        for (String groupKey : groupSection.getKeys(false)) {
                            switch (groupKey) {
                                case "can-place":
                                    canPlaceGroup = groupSection.getBoolean(groupKey);
                                    break;
                                case "growth-rate":
                                    growthRateGroup = groupSection.getInt(groupKey);
                                    break;
                                case "growth-rate-dark":
                                    growthRateDarkGroup = groupSection.getInt(groupKey);
                                    break;
                                case "death-rate":
                                    deathRateGroup = groupSection.getInt(groupKey);
                                    break;
                                case "death-rate-dark":
                                    deathRateDarkGroup = groupSection.getInt(groupKey);
                                    break;
                                case "bone-meal-success-rate":
                                    boneMealSuccessRateGroup = groupSection.getInt(groupKey);
                                    break;
                                case "bone-meal-success-rate-dark":
                                    boneMealSuccessRateDarkGroup = groupSection.getInt(groupKey);
                                    break;
                                case "needs-hoe-for-drops":
                                    needsHoeForDropsGroup = groupSection.getBoolean(groupKey);
                                    break;
                                case "needs-hoe-for-farmland-retain":
                                    needsHoeForFarmlandRetainGroup = groupSection.getBoolean(groupKey);
                                    break;
                                case "min-light":
                                    minLightGroup = groupSection.getInt(groupKey);
                                    break;
                                case "max-light":
                                    maxLightGroup = groupSection.getInt(groupKey);
                                    break;
                                case "needs-sky":
                                    needsSkyGroup = groupSection.getBoolean(groupKey);
                                    break;
                                case "transparent-blocks-count-as-sky":
                                    transparentBlocksCountAsSkyGroup = groupSection.getBoolean(groupKey);
                                    break;
                                case "no-sky-growth-rate":
                                    noSkyGrowthRateGroup = groupSection.getInt(groupKey);
                                    break;
                                case "no-sky-death-rate":
                                    noSkyDeathRateGroup = groupSection.getInt(groupKey);
                                    break;
                                case "min-y":
                                    minYGroup = groupSection.getInt(groupKey);
                                    break;
                                case "max-y":
                                    maxYGroup = groupSection.getInt(groupKey);
                                    break;
                                case "restrict-to-worlds":
                                    restrictToWorldsGroup = new HashSet<>(groupSection.getStringList(groupKey));
                                    break;
                            }
                        }
                    }

                    for (Biome biome : plugin.getBiomeGroups().get(group)) {
                        biomeStats.put(biome,
                                new PlantBiomeStats(canPlaceGroup, growthRateGroup, deathRateGroup, growthRateDarkGroup,
                                        deathRateDarkGroup, boneMealSuccessRateGroup, boneMealSuccessRateDarkGroup,
                                        needsHoeForDropsGroup, needsHoeForFarmlandRetainGroup, minLightGroup,
                                        maxLightGroup, needsSkyGroup,
                                        transparentBlocksCountAsSkyGroup, noSkyGrowthRateGroup, noSkyDeathRateGroup,
                                        minYGroup, maxYGroup, restrictToWorldsGroup));

                        if (!canPlaceGroup) {
                            if (restrictToWorldsGroup.isEmpty()) {
                                disallowedBiomes.add("*:" + biome.name());
                            } else {
                                for (String worldName : restrictToWorldsGroup) {
                                    disallowedBiomes.add(worldName + ":" + biome.name());
                                }
                            }
                        }
                    }
                }
            }
        }

        if (!canPlace) {
            if (restrictToWorlds.isEmpty()) {
                disallowedBiomes.add("*:_");
            } else {
                for (String worldName : restrictToWorlds) {
                    disallowedBiomes.add(worldName + ":_");
                }
            }
        }

        plugin.addPlant(new Plant(plugin, matRef.getMaterial(plantName), canPlace, growthRate, deathRate,
                growthRateDark, deathRateDark, boneMealSuccessRate, boneMealSuccessRateDark, needsHoeForDrops,
                needsHoeForFarmlandRetain, minLight, maxLight, needsSky,
                transparentBlocksCountAsSky, noSkyGrowthRate, noSkyDeathRate, minY, maxY, restrictToWorlds, disallowedBiomes,
                biomeStats));
    }

    public void loadPlants(int version) {// Code for version 1.13 is an artifact, that version is not supported.
        if (version >= 20) {
            matRef.buildMatMap20();
            farmlandRef.buildFarmlandCropSet20();
            cropRef.buildCropDropMap20();
            hoeRef.buildHoeSet16();
        } else if (version == 19) {
            matRef.buildMatMap19();
            farmlandRef.buildFarmlandCropSet13();
            cropRef.buildCropDropMap13();
            hoeRef.buildHoeSet16();
        } else if (version == 18) {
            matRef.buildMatMap17();
            farmlandRef.buildFarmlandCropSet13();
            cropRef.buildCropDropMap13();
            hoeRef.buildHoeSet16();
        } else if (version == 17) {
            matRef.buildMatMap17();
            farmlandRef.buildFarmlandCropSet13();
            cropRef.buildCropDropMap13();
            hoeRef.buildHoeSet16();
        } else if (version == 16) {
            matRef.buildMatMap16();
            farmlandRef.buildFarmlandCropSet13();
            cropRef.buildCropDropMap13();
            hoeRef.buildHoeSet16();
        } else if (version == 15) {
            matRef.buildMatMap14();
            farmlandRef.buildFarmlandCropSet13();
            cropRef.buildCropDropMap13();
            hoeRef.buildHoeSet13();
        } else if (version == 14) {
            matRef.buildMatMap14();
            farmlandRef.buildFarmlandCropSet13();
            cropRef.buildCropDropMap13();
            hoeRef.buildHoeSet13();
        } else if (version == 13) {
            matRef.buildMatMap13();
            farmlandRef.buildFarmlandCropSet13();
            cropRef.buildCropDropMap13();
            hoeRef.buildHoeSet13();
        }

        for (String key : matRef.getMatMap().keySet())
            loadPlant(key);
    }

    public MaterialReference getReference() {
        return matRef;
    }

    public MaterialReference getFarmlandReference() {
        return farmlandRef;
    }

    public MaterialReference getCropReference() {
        return cropRef;
    }

    public MaterialReference getHoeReference() {
        return hoeRef;
    }
}