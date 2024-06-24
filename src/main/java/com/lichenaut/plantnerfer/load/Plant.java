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
    private final HashSet<String> disallowedBiomes;
    private final Main main;
    @Getter
    private final Material material;
    private final HashSet<String> restrictToWorlds;

    private <T> T getProperty(Biome biome, String worldName, T property, T defaultValue) {
        if (!biomeStats.containsKey(biome)) {
            if (restrictToWorlds.isEmpty() || restrictToWorlds.contains(worldName)) {
                return property;
            } else {
                return defaultValue;
            }
        }

        PlantBiomeStats stats = biomeStats.get(biome);
        HashSet<String> worlds = stats.getWorlds();
        if (worlds.isEmpty() || worlds.contains(worldName)) {
            return property;
        } else {
            return defaultValue;
        }
    }

    public boolean getCanPlace(Biome biome, String worldName) {
        return getProperty(biome, worldName, canPlace, true);
    }

    public int getGrowthRate(Biome biome, String worldName) {
        return getProperty(biome, worldName, growthRate, 100);
    }

    public int getDeathRate(Biome biome, String worldName) {
        return getProperty(biome, worldName, deathRate, 0);
    }

    public int getDarkGrowthRate(Biome biome, String worldName) {
        return getProperty(biome, worldName, darkGrowthRate, 100);
    }

    public int getDarkDeathRate(Biome biome, String worldName) {
        return getProperty(biome, worldName, darkDeathRate, 0);
    }

    public int getBoneMealRate(Biome biome, String worldName) {
        return getProperty(biome, worldName, boneMealRate, 100);
    }

    public int getDarkBoneMealRate(Biome biome, String worldName) {
        return getProperty(biome, worldName, darkBoneMealRate, 100);
    }

    public boolean getNeedsHoeForDrops(Biome biome, String worldName) {
        return getProperty(biome, worldName, needsHoeForDrops, false);
    }

    public boolean getNeedsHoeForFarmlandRetain(Biome biome, String worldName) {
        return getProperty(biome, worldName, needsHoeForFarmlandRetain, false);
    }

    public int getMinLight(Biome biome, String worldName) {
        return getProperty(biome, worldName, minLight, 0);
    }

    public int getMaxLight(Biome biome, String worldName) {
        return getProperty(biome, worldName, maxLight, 15);
    }

    public boolean getNeedsSky(Biome biome, String worldName, Block block) {
        if (getTransparentBlocksCountAsSky(biome, worldName)) {
            int maxHeight = block.getWorld().getMaxHeight();
            while (block.getY() <= maxHeight) {
                if (block.getType().isOccluding()) {
                    return true;
                }

                block = block.getRelative(0, 1, 0);
            }

            return false;
        }

        return getProperty(biome, worldName, needsSky, false);
    }

    public boolean getTransparentBlocksCountAsSky(Biome biome, String worldName) {
        return getProperty(biome, worldName, transparentBlocksCountAsSky, true);
    }

    public int getNoSkyGrowthRate(Biome biome, String worldName) {
        return getProperty(biome, worldName, noSkyGrowthRate, 100);
    }

    public int getNoSkyDeathRate(Biome biome, String worldName) {
        return getProperty(biome, worldName, noSkyDeathRate, 0);
    }

    public int getMinY(Biome biome, String worldName) {
        return getProperty(biome, worldName, minY, Objects.requireNonNull(main.getServer().getWorld(worldName)).getMinHeight());
    }

    public int getMaxY(Biome biome, String worldName) {
        return getProperty(biome, worldName, maxY, Objects.requireNonNull(main.getServer().getWorld(worldName)).getMaxHeight());
    }
}