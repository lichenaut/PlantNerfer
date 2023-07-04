package com.lichenaut.plantnerfer.util;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.HashSet;

public class PNMaterialReference {

    HashMap<String, Material> matMap = new HashMap<>();

    HashSet<Material> plantBlocks = new HashSet<>();

    public void buildMatMap13() {
        matMap.put("oak-sapling", Material.OAK_SAPLING);
        matMap.put("dark-oak-sapling", Material.DARK_OAK_SAPLING);
        matMap.put("spruce-sapling", Material.SPRUCE_SAPLING);
        matMap.put("birch-sapling", Material.BIRCH_SAPLING);
        matMap.put("jungle-sapling", Material.JUNGLE_SAPLING);
        matMap.put("acacia-sapling", Material.ACACIA_SAPLING);
        matMap.put("melon-seeds", Material.MELON_SEEDS);
        matMap.put("pumpkin-seeds", Material.PUMPKIN_SEEDS);
        matMap.put("wheat-seeds", Material.WHEAT_SEEDS);
        matMap.put("beetroot-seeds", Material.BEETROOT_SEEDS);
        matMap.put("cocoa-beans", Material.COCOA_BEANS);
        matMap.put("sugar-cane", Material.SUGAR_CANE);
        matMap.put("kelp", Material.KELP);
        matMap.put("seagrass", Material.SEAGRASS);
        matMap.put("vine", Material.VINE);
        matMap.put("lily-pad", Material.LILY_PAD);
        matMap.put("melon", Material.MELON);
        matMap.put("pumpkin", Material.PUMPKIN);
        matMap.put("carrot", Material.CARROT);
        matMap.put("potato", Material.POTATO);
        matMap.put("beetroot", Material.BEETROOT);
        matMap.put("wheat", Material.WHEAT);
        matMap.put("cactus", Material.CACTUS);
        matMap.put("cocoa", Material.COCOA);
        matMap.put("brown-mushroom", Material.BROWN_MUSHROOM);
        matMap.put("red-mushroom", Material.RED_MUSHROOM);
        matMap.put("nether-wart", Material.NETHER_WART);
        matMap.put("chorus-plant", Material.CHORUS_PLANT);
    }

    public void buildMatMap14() {
        buildMatMap13();
        matMap.put("bamboo", Material.getMaterial("BAMBOO"));
        matMap.put("bamboo-sapling", Material.getMaterial("BAMBOO_SAPLING"));
        matMap.put("sweet-berries", Material.getMaterial("SWEET_BERRIES"));
        matMap.put("sweet-berry-bush", Material.getMaterial("SWEET_BERRY_BUSH"));
    }

    public void buildMatMap16() {
        buildMatMap14();
        matMap.put("twisting-vines", Material.getMaterial("TWISTING_VINES"));
        matMap.put("weeping-vines", Material.getMaterial("WEEPING_VINES"));
        matMap.put("crimson-fungus", Material.getMaterial("CRIMSON_FUNGUS"));
        matMap.put("warped-fungus", Material.getMaterial("WARPED_FUNGUS"));
    }

    public void buildMatMap17() {
        buildMatMap16();
        matMap.put("moss-carpet", Material.getMaterial("MOSS_CARPET"));
        matMap.put("moss-block", Material.getMaterial("MOSS_BLOCK"));
    }

    public void buildMatMap20() {
        buildMatMap17();
        matMap.put("torchflower-seeds", Material.getMaterial("TORCHFLOWER_SEEDS"));
        matMap.put("pitcher-pod", Material.getMaterial("PITCHER_POD"));
    }

    public void buildPlantBlocks13() {
        plantBlocks.add(Material.OAK_SAPLING);
        plantBlocks.add(Material.DARK_OAK_SAPLING);
        plantBlocks.add(Material.SPRUCE_SAPLING);
        plantBlocks.add(Material.BIRCH_SAPLING);
        plantBlocks.add(Material.JUNGLE_SAPLING);
        plantBlocks.add(Material.ACACIA_SAPLING);
        plantBlocks.add(Material.MELON_STEM);
        plantBlocks.add(Material.PUMPKIN_STEM);
        plantBlocks.add(Material.SUGAR_CANE);
        plantBlocks.add(Material.KELP);
        plantBlocks.add(Material.SEAGRASS);
        plantBlocks.add(Material.VINE);
        plantBlocks.add(Material.LILY_PAD);
        plantBlocks.add(Material.CARROT);
        plantBlocks.add(Material.POTATO);
        plantBlocks.add(Material.BEETROOT);
        plantBlocks.add(Material.WHEAT);
        plantBlocks.add(Material.CACTUS);
        plantBlocks.add(Material.COCOA);
        plantBlocks.add(Material.BROWN_MUSHROOM);
        plantBlocks.add(Material.RED_MUSHROOM);
        plantBlocks.add(Material.NETHER_WART);
        plantBlocks.add(Material.CHORUS_PLANT);
    }

    public void buildPlantBlocks14() {
        buildPlantBlocks13();
        plantBlocks.add(Material.getMaterial("BAMBOO"));
        plantBlocks.add(Material.getMaterial("BAMBOO_SAPLING"));
        plantBlocks.add(Material.getMaterial("SWEET_BERRY_BUSH"));
    }

    public void buildPlantBlocks16() {
        buildPlantBlocks14();
        plantBlocks.add(Material.getMaterial("TWISTING_VINES"));
        plantBlocks.add(Material.getMaterial("WEEPING_VINES"));
        plantBlocks.add(Material.getMaterial("CRIMSON_FUNGUS"));
        plantBlocks.add(Material.getMaterial("WARPED_FUNGUS"));
    }

    public void buildPlantBlocks17() {
        buildPlantBlocks16();
        plantBlocks.add(Material.getMaterial("MOSS_CARPET"));
        plantBlocks.add(Material.getMaterial("MOSS_BLOCK"));
    }

    public void buildPlantBlocks20() {
        buildPlantBlocks17();
        plantBlocks.add(Material.getMaterial("TORCHFLOWER_CROP"));
        plantBlocks.add(Material.getMaterial("PITCHER_CROP"));
    }

    public Material getMaterial(String name) {return matMap.get(name);}

    public boolean isNotPlantBlock(Material mat) {return !plantBlocks.contains(mat);}
}
