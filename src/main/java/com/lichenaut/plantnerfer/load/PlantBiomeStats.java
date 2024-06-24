package com.lichenaut.plantnerfer.load;

import lombok.Getter;

import java.util.HashSet;

@Getter
public record PlantBiomeStats(boolean canPlace, int growthRate, int deathRate, int darkGrowthRate, int darkDeathRate,
                              int boneMealRate, int darkBoneMealRate, boolean needsHoeForDrops,
                              boolean needsHoeForFarmlandRetain, int minLight, int maxLight, boolean needsSky,
                              boolean transparentBlocksCountAsSky, int noSkyGrowthRate, int noSkyDeathRate, int minY,
                              int maxY, HashSet<String> worlds) {}