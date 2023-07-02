package com.lichenaut.plantnerfer.util;

import org.bukkit.Material;

import java.util.HashMap;

public class PNMaterialReference {

    HashMap<String, Material> matMap = new HashMap<>();

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

    public Material getMaterial(String name) {return matMap.get(name);}
}
