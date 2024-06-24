package com.lichenaut.plantnerfer.load;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;

@RequiredArgsConstructor
@Getter
public class PlantBiomeStats {

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
    private final HashSet<String> worlds;
}