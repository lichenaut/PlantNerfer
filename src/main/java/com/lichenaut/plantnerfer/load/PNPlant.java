package com.lichenaut.plantnerfer.load;

import org.bukkit.Material;
import org.bukkit.block.Biome;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

public class PNPlant {

    private final Material material;
    private final int growthRate;
    private final int deathRate;
    private final int darkGrowthRate;
    private final int darkDeathRate;
    private final int boneMealRate;
    private final int minLight;
    private final int maxLight;
    private final int minY;
    private final int maxY;
    private final HashSet<String> restrictToWorlds;
    private final TreeMap<Biome, PNPlantBiomeGroupStats> biomeStats;

    public PNPlant(Material material, int growthRate, int deathRate, int darkGrowthRate, int darkDeathRate, int boneMealRate, int minLight, int maxLight, int minY, int maxY, HashSet<String> restrictToWorlds, TreeMap<Biome, PNPlantBiomeGroupStats> biomeStats) {
        this.material = material;
        this.growthRate = growthRate;
        this.deathRate = deathRate;
        this.darkGrowthRate = darkGrowthRate;
        this.darkDeathRate = darkDeathRate;
        this.boneMealRate = boneMealRate;
        this.minLight = minLight;
        this.maxLight = maxLight;
        this.minY = minY;
        this.maxY = maxY;
        this.restrictToWorlds = restrictToWorlds;
        this.biomeStats = biomeStats;
    }

    public Material getMaterial() {return material;}
    public int getGrowthRate(Biome b) {
        for (Map.Entry<Biome, PNPlantBiomeGroupStats> entry : biomeStats.entrySet()) if (entry.getKey().equals(b)) return entry.getValue().getGrowthRate();
        return growthRate;
    }
    public int getDeathRate(Biome b) {
        for (Map.Entry<Biome, PNPlantBiomeGroupStats> entry : biomeStats.entrySet()) if (entry.getKey().equals(b)) return entry.getValue().getDeathRate();
        return deathRate;
    }
    public int getDarkGrowthRate(Biome b) {
        for (Map.Entry<Biome, PNPlantBiomeGroupStats> entry : biomeStats.entrySet()) if (entry.getKey().equals(b)) return entry.getValue().getDarkGrowthRate();
        return darkGrowthRate;
    }
    public int getDarkDeathRate(Biome b) {
        for (Map.Entry<Biome, PNPlantBiomeGroupStats> entry : biomeStats.entrySet()) if (entry.getKey().equals(b)) return entry.getValue().getDarkDeathRate();
        return darkDeathRate;
    }
    public int getBoneMealRate(Biome b) {
        for (Map.Entry<Biome, PNPlantBiomeGroupStats> entry : biomeStats.entrySet()) if (entry.getKey().equals(b)) return entry.getValue().getBoneMealRate();
        return boneMealRate;
    }
    public int getMinLight(Biome b) {
        for (Map.Entry<Biome, PNPlantBiomeGroupStats> entry : biomeStats.entrySet()) if (entry.getKey().equals(b)) return entry.getValue().getMinLight();
        return minLight;
    }
    public int getMaxLight(Biome b) {
        for (Map.Entry<Biome, PNPlantBiomeGroupStats> entry : biomeStats.entrySet()) if (entry.getKey().equals(b)) return entry.getValue().getMaxLight();
        return maxLight;
    }
    public int getMinY(Biome b) {
        for (Map.Entry<Biome, PNPlantBiomeGroupStats> entry : biomeStats.entrySet()) if (entry.getKey().equals(b)) return entry.getValue().getMinY();
        return minY;
    }
    public int getMaxY(Biome b) {
        for (Map.Entry<Biome, PNPlantBiomeGroupStats> entry : biomeStats.entrySet()) if (entry.getKey().equals(b)) return entry.getValue().getMaxY();
        return maxY;
    }
    public HashSet<String> getWorlds(Biome b) {
        for (Map.Entry<Biome, PNPlantBiomeGroupStats> entry : biomeStats.entrySet()) if (entry.getKey().equals(b)) return entry.getValue().getWorlds();
        return restrictToWorlds;
    }
}