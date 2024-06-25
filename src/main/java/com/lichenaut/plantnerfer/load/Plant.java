package com.lichenaut.plantnerfer.load;

import com.lichenaut.plantnerfer.Main;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

@RequiredArgsConstructor
public class Plant {

    private final boolean canPlace;
    private final int growthRate;
    private final int deathRate;
    private final int darkGrowthRate;
    private final int darkDeathRate;
    private final int boneMealRate;
    private final int darkBoneMealRate;
    private final boolean needsHoeForDrops;
    private final boolean needsHoeForFarmlandRetain;
    private final int minLight;
    private final int maxLight;
    private final boolean needsSky;
    private final boolean transparentBlocksCountAsSky;
    private final int noSkyGrowthRate;
    private final int noSkyDeathRate;
    private final int minY;
    private final int maxY;
    private final HashMap<Biome, PlantBiomeStats> biomeStats;
    @Getter
    private final String allowedBiomes;
    @Getter
    private final String disallowedBiomes;
    private final Main main;
    @Getter
    private final Material material;
    private final HashSet<String> restrictToWorlds;

    private boolean getProperty(Biome biome, String worldName, String propertyName, boolean property,
            boolean defaultValue) {
        PlantBiomeStats stats = biomeStats.get(biome);
        if (stats == null) {
            if (restrictToWorlds.isEmpty() || restrictToWorlds.contains(worldName)) {
                return property;
            } else {
                return defaultValue;
            }
        }

        HashSet<String> worlds = stats.restrictToWorlds();
        if (worlds.isEmpty() || worlds.contains(worldName)) {
            return switch (propertyName) {
                case "canPlace" -> stats.canPlace();
                case "needsHoeForDrops" -> stats.needsHoeForDrops();
                case "needsHoeForFarmlandRetain" -> stats.needsHoeForFarmlandRetain();
                case "needsSky" -> stats.needsSky();
                case "transparentBlocksCountAsSky" -> stats.transparentBlocksCountAsSky();
                default -> defaultValue;
            };
        } else {
            return defaultValue;
        }
    }

    private int getProperty(Biome biome, String worldName, String propertyName, int property, int defaultValue) {
        PlantBiomeStats stats = biomeStats.get(biome);
        if (stats == null) {
            if (restrictToWorlds.isEmpty() || restrictToWorlds.contains(worldName)) {
                return property;
            } else {
                return defaultValue;
            }
        }

        HashSet<String> worlds = stats.restrictToWorlds();
        if (worlds.isEmpty() || worlds.contains(worldName)) {
            return switch (propertyName) {
                case "growthRate" -> stats.growthRate();
                case "deathRate" -> stats.deathRate();
                case "darkGrowthRate" -> stats.darkGrowthRate();
                case "darkDeathRate" -> stats.darkDeathRate();
                case "boneMealRate" -> stats.boneMealRate();
                case "darkBoneMealRate" -> stats.darkBoneMealRate();
                case "noSkyGrowthRate" -> stats.noSkyGrowthRate();
                case "noSkyDeathRate" -> stats.noSkyDeathRate();
                case "minLight" -> stats.minLight();
                case "maxLight" -> stats.maxLight();
                case "minY" -> stats.minY();
                case "maxY" -> stats.maxY();
                default -> defaultValue;
            };
        } else {
            return defaultValue;
        }
    }

    public boolean getCanPlace(Biome biome, String worldName) {
        return getProperty(biome, worldName, "canPlace", canPlace, true);
    }

    public int getGrowthRate(Biome biome, String worldName) {
        return getProperty(biome, worldName, "growthRate", growthRate, 100);
    }

    public int getDeathRate(Biome biome, String worldName) {
        return getProperty(biome, worldName, "deathRate", deathRate, 0);
    }

    public int getDarkGrowthRate(Biome biome, String worldName) {
        return getProperty(biome, worldName, "darkGrowthRate", darkGrowthRate, 100);
    }

    public int getDarkDeathRate(Biome biome, String worldName) {
        return getProperty(biome, worldName, "darkDeathRate", darkDeathRate, 0);
    }

    public int getBoneMealRate(Biome biome, String worldName) {
        return getProperty(biome, worldName, "boneMealRate", boneMealRate, 100);
    }

    public int getDarkBoneMealRate(Biome biome, String worldName) {
        return getProperty(biome, worldName, "darkBoneMealRate", darkBoneMealRate, 100);
    }

    public boolean getNeedsHoeForDrops(Biome biome, String worldName) {
        return getProperty(biome, worldName, "needsHoeForDrops", needsHoeForDrops, false);
    }

    public boolean getNeedsHoeForFarmlandRetain(Biome biome, String worldName) {
        return getProperty(biome, worldName, "needsHoeForFarmlandRetain", needsHoeForFarmlandRetain, false);
    }

    public int getMinLight(Biome biome, String worldName) {
        return getProperty(biome, worldName, "minLight", minLight, 0);
    }

    public int getMaxLight(Biome biome, String worldName) {
        return getProperty(biome, worldName, "maxLight", maxLight, 15);
    }

    public boolean getNeedsSky(Biome biome, String worldName, Block block) {
        boolean needsSkyAccess = getProperty(biome, worldName, "needsSky", needsSky, false);
        if (!needsSkyAccess) {
            return false;
        }

        boolean transparentBlocksCountAsSky = getTransparentBlocksCountAsSky(biome, worldName);
        if (!transparentBlocksCountAsSky) {
            return true;
        }

        int maxHeight = block.getWorld().getMaxHeight();
        while (block.getY() <= maxHeight) {
            if (block.getType().isOccluding()) {
                return true;
            }

            block = block.getRelative(0, 1, 0);
        }

        return false;
    }

    private boolean getTransparentBlocksCountAsSky(Biome biome, String worldName) {
        return getProperty(biome, worldName, "transparentBlocksCountAsSky", transparentBlocksCountAsSky, true);
    }

    public int getNoSkyGrowthRate(Biome biome, String worldName) {
        return getProperty(biome, worldName, "noSkyGrowthRate", noSkyGrowthRate, 100);
    }

    public int getNoSkyDeathRate(Biome biome, String worldName) {
        return getProperty(biome, worldName, "noSkyDeathRate", noSkyDeathRate, 0);
    }

    public int getMinY(Biome biome, String worldName) {
        return getProperty(biome, worldName, "minY", minY,
                Objects.requireNonNull(main.getServer().getWorld(worldName)).getMinHeight());
    }

    public int getMaxY(Biome biome, String worldName) {
        return getProperty(biome, worldName, "maxY", maxY,
                Objects.requireNonNull(main.getServer().getWorld(worldName)).getMaxHeight());
    }

    public boolean canPlaceByDefault() {
        return disallowedBiomes.isEmpty();
    }
}