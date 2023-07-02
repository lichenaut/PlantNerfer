package com.lichenaut.plantnerfer.load;

import org.bukkit.block.Biome;

import java.util.HashSet;

public class PNPlantBiomeGroupStats {

    private final int growthRate;
    private final int deathRate;
    private final int darkGrowthRate;
    private final int darkDeathRate;
    private final int boneMealRate;
    private final int minLight;
    private final int maxLight;
    private final int minY;
    private final int maxY;
    private final HashSet<String> worlds;
    private final HashSet<Biome> biomes;

    public PNPlantBiomeGroupStats(int growthRate, int darkGrowthRate, int deathRate, int darkDeathRate, int boneMealRate, int minLight, int maxLight, int minY, int maxY, HashSet<String> restrictToWorlds, HashSet<Biome> biomes) {
        this.growthRate = growthRate;
        this.deathRate = deathRate;
        this.darkGrowthRate = darkGrowthRate;
        this.darkDeathRate = darkDeathRate;
        this.boneMealRate = boneMealRate;
        this.minLight = minLight;
        this.maxLight = maxLight;
        this.minY = minY;
        this.maxY = maxY;
        this.worlds = restrictToWorlds;
        this.biomes = biomes;
    }

    public int getGrowthRate() {return growthRate;}
    public int getDeathRate() {return deathRate;}
    public int getDarkGrowthRate() {return darkGrowthRate;}
    public int getDarkDeathRate() {return darkDeathRate;}
    public int getBoneMealRate() {return boneMealRate;}
    public int getMinLight() {return minLight;}
    public int getMaxLight() {return maxLight;}
    public int getMinY() {return minY;}
    public int getMaxY() {return maxY;}
    public HashSet<String> getWorlds() {return worlds;}
    public HashSet<Biome> getBiomes() {return biomes;}
}