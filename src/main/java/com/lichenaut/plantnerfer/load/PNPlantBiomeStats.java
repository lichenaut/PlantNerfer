package com.lichenaut.plantnerfer.load;

import java.util.HashSet;

public class PNPlantBiomeStats {//represents the biome-specific stats of a plant

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
    private final int noSkyGrowthRate;
    private final int noSkyDeathRate;
    private final int minY;
    private final int maxY;
    private final HashSet<String> worlds;

    public PNPlantBiomeStats(boolean canPlace, int growthRate, int darkGrowthRate, int deathRate, int darkDeathRate, int boneMealRate, int darkBoneMealRate, int minLight, int maxLight, boolean ignoreLightWhenNight, boolean needsSky, int noSkyGrowthRate, int noSkyDeathRate, int minY, int maxY, HashSet<String> restrictToWorlds) {
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
        this.noSkyGrowthRate = noSkyGrowthRate;
        this.noSkyDeathRate = noSkyDeathRate;
        this.minY = minY;
        this.maxY = maxY;
        this.worlds = restrictToWorlds;
    }

    public boolean getCanPlace() {return canPlace;}
    public int getGrowthRate() {return growthRate;}
    public int getDeathRate() {return deathRate;}
    public int getDarkGrowthRate() {return darkGrowthRate;}
    public int getDarkDeathRate() {return darkDeathRate;}
    public int getBoneMealRate() {return boneMealRate;}
    public int getDarkBoneMealRate() {return darkBoneMealRate;}
    public int getMinLight() {return minLight;}
    public int getMaxLight() {return maxLight;}
    public boolean getIgnoreLightWhenNight() {return ignoreLightWhenNight;}
    public boolean getNeedsSky() {return needsSky;}
    public int getNoSkyGrowthRate() {return noSkyGrowthRate;}
    public int getNoSkyDeathRate() {return noSkyDeathRate;}
    public int getMinY() {return minY;}
    public int getMaxY() {return maxY;}
    public HashSet<String> getWorlds() {return worlds;}
}