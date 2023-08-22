package com.lichenaut.plantnerfer.load;

import com.lichenaut.plantnerfer.PlantNerfer;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;

import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

public class PNPlant {

    private final PlantNerfer plugin;
    private final Material material;
    private final boolean canPlace;
    private final int growthRate;
    private final int deathRate;
    private final int darkGrowthRate;
    private final int darkDeathRate;
    private final int boneMealRate;
    private final int darkBoneMealRate;
    private final int minLight;
    private final int maxLight;
    private final boolean ignoreLightWhenNight;
    private final boolean needsSky;
    private final boolean transparentBlocksCountAsSky;
    private final int noSkyGrowthRate;
    private final int noSkyDeathRate;
    private final int minY;
    private final int maxY;
    private final HashSet<String> restrictToWorlds;
    private final TreeMap<Biome, PNPlantBiomeStats> biomeStats;

    public PNPlant(PlantNerfer plugin, Material material, boolean canPlace, int growthRate, int deathRate, int darkGrowthRate, int darkDeathRate, int boneMealRate, int darkBoneMealRate, int minLight, int maxLight, boolean ignoreLightWhenNight, boolean needsSky, boolean transparentBlocksCountAsSky, int noSkyGrowthRate, int noSkyDeathRate, int minY, int maxY, HashSet<String> restrictToWorlds, TreeMap<Biome, PNPlantBiomeStats> biomeStats) {
        this.plugin = plugin;
        this.material = material;
        this.canPlace = canPlace;
        this.growthRate = growthRate;
        this.deathRate = deathRate;
        this.darkGrowthRate = darkGrowthRate;
        this.darkDeathRate = darkDeathRate;
        this.boneMealRate = boneMealRate;
        this.darkBoneMealRate = darkBoneMealRate;
        this.minLight = minLight;
        this.maxLight = maxLight;
        this.ignoreLightWhenNight = ignoreLightWhenNight;
        this.needsSky = needsSky;
        this.transparentBlocksCountAsSky = transparentBlocksCountAsSky;
        this.noSkyGrowthRate = noSkyGrowthRate;
        this.noSkyDeathRate = noSkyDeathRate;
        this.minY = minY;
        this.maxY = maxY;
        this.restrictToWorlds = restrictToWorlds;
        this.biomeStats = biomeStats;
    }

    public Material getMaterial() {return material;}
    public boolean getCanPlace(Biome b) {
        for (Map.Entry<Biome, PNPlantBiomeStats> entry : biomeStats.entrySet()) if (entry.getKey().equals(b)) return entry.getValue().getCanPlace();
        return canPlace;
    }
    public int getGrowthRate(Biome b) {
        for (Map.Entry<Biome, PNPlantBiomeStats> entry : biomeStats.entrySet()) if (entry.getKey().equals(b)) return entry.getValue().getGrowthRate();
        return growthRate;
    }
    public int getDeathRate(Biome b) {
        for (Map.Entry<Biome, PNPlantBiomeStats> entry : biomeStats.entrySet()) if (entry.getKey().equals(b)) return entry.getValue().getDeathRate();
        return deathRate;
    }
    public int getDarkGrowthRate(Biome b) {
        for (Map.Entry<Biome, PNPlantBiomeStats> entry : biomeStats.entrySet()) if (entry.getKey().equals(b)) return entry.getValue().getDarkGrowthRate();
        return darkGrowthRate;
    }
    public int getDarkDeathRate(Biome b) {
        for (Map.Entry<Biome, PNPlantBiomeStats> entry : biomeStats.entrySet()) if (entry.getKey().equals(b)) return entry.getValue().getDarkDeathRate();
        return darkDeathRate;
    }
    public int getBoneMealRate(Biome b) {
        for (Map.Entry<Biome, PNPlantBiomeStats> entry : biomeStats.entrySet()) if (entry.getKey().equals(b)) return entry.getValue().getBoneMealRate();
        return boneMealRate;
    }
    public int getDarkBoneMealRate(Biome b) {
        for (Map.Entry<Biome, PNPlantBiomeStats> entry : biomeStats.entrySet()) if (entry.getKey().equals(b)) return entry.getValue().getDarkBoneMealRate();
        return darkBoneMealRate;
    }
    public int getMinLight(Biome b) {
        for (Map.Entry<Biome, PNPlantBiomeStats> entry : biomeStats.entrySet()) if (entry.getKey().equals(b)) return entry.getValue().getMinLight();
        return minLight;
    }
    public int getMaxLight(Biome b) {
        for (Map.Entry<Biome, PNPlantBiomeStats> entry : biomeStats.entrySet()) if (entry.getKey().equals(b)) return entry.getValue().getMaxLight();
        return maxLight;
    }
    public boolean getIgnoreLightWhenNight(Biome b) {
        for (Map.Entry<Biome, PNPlantBiomeStats> entry : biomeStats.entrySet()) if (entry.getKey().equals(b)) return entry.getValue().getIgnoreLightWhenNight();
        return ignoreLightWhenNight;
    }
    public boolean getNeedsSky(Biome biome, Block block) {
        if (!getTransparentBlocksCountAsSky(biome)) {
            for (Map.Entry<Biome, PNPlantBiomeStats> entry : biomeStats.entrySet()) if (entry.getKey().equals(biome)) return entry.getValue().getNeedsSky();
            return needsSky;
        } else {
            while (block.getY() < block.getWorld().getMaxHeight()) {// Checking for non-transparent blocks above, then combining this return value to whether or not the block's height is the highest in its X and Z in other parts of the code can be optimized
                // Once this finds a non-transparent block, figuring out the latter shouldn't need to happen. Perhaps combine into a single method?
                if (!block.getType().isOccluding()) block = block.getRelative(0, 1, 0);else return true;
            }
            return false;
        }
    }
    public boolean getTransparentBlocksCountAsSky(Biome b) {
        for (Map.Entry<Biome, PNPlantBiomeStats> entry : biomeStats.entrySet()) if (entry.getKey().equals(b)) return entry.getValue().getTransparentBlocksCountAsSky();
        return transparentBlocksCountAsSky;
    }
    public int getNoSkyGrowthRate(Biome b) {
        for (Map.Entry<Biome, PNPlantBiomeStats> entry : biomeStats.entrySet()) if (entry.getKey().equals(b)) return entry.getValue().getNoSkyGrowthRate();
        return noSkyGrowthRate;
    }
    public int getNoSkyDeathRate(Biome b) {
        for (Map.Entry<Biome, PNPlantBiomeStats> entry : biomeStats.entrySet()) if (entry.getKey().equals(b)) return entry.getValue().getNoSkyDeathRate();
        return noSkyDeathRate;
    }
    public int getMinY(Biome b) {
        for (Map.Entry<Biome, PNPlantBiomeStats> entry : biomeStats.entrySet()) if (entry.getKey().equals(b)) return entry.getValue().getMinY();
        return minY;
    }
    public int getMaxY(Biome b) {
        for (Map.Entry<Biome, PNPlantBiomeStats> entry : biomeStats.entrySet()) if (entry.getKey().equals(b)) return entry.getValue().getMaxY();
        return maxY;
    }
    public String getBiomes() {//for verbose denial
        StringBuilder biomes = new StringBuilder();
        for (Map.Entry<Biome, PNPlantBiomeStats> entry : biomeStats.entrySet()) biomes.append(entry.getKey().toString()).append(", ");
        return biomes.substring(0, biomes.length() - 2) + ".";
    }
    public boolean isValidWorldAndBiome(Biome b, String worldName) {//does it have any biome groups that have both this biome and this world?
        for (Map.Entry<Biome, PNPlantBiomeStats> entry : biomeStats.entrySet()) {
            if (entry.getKey().equals(b)) {
                for (String definedWorldName : entry.getValue().getWorlds()) {
                    if (definedWorldName.equals(worldName)) return true;
                }
            }
        }
        if (!restrictToWorlds.isEmpty()) return restrictToWorlds.contains(worldName); else if (plugin.getConfig().getStringList("restrict-plugin-to-worlds").isEmpty()) {return true;} else return plugin.getConfig().getStringList("restrict-plugin-to-worlds").contains(worldName);
    }
}