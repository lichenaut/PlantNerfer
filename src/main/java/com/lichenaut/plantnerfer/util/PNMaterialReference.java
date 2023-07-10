package com.lichenaut.plantnerfer.util;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.HashSet;

public class PNMaterialReference {

    HashMap<String, Material> matMap = new HashMap<>();
    HashSet<Material> farmlandSet = new HashSet<>();
    HashMap<Material, Material> cropMap = new HashMap<>();

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
        matMap.put("sea-pickle", Material.SEA_PICKLE);
        matMap.put("kelp", Material.KELP);
        matMap.put("seagrass", Material.SEAGRASS);
        matMap.put("grass", Material.GRASS);
        matMap.put("tall-grass", Material.TALL_GRASS);
        matMap.put("grass-block", Material.GRASS_BLOCK);
        matMap.put("fern", Material.FERN);
        matMap.put("large-fern", Material.LARGE_FERN);
        matMap.put("vine", Material.VINE);
        matMap.put("lily-pad", Material.LILY_PAD);
        matMap.put("melon-stem", Material.MELON_STEM);
        matMap.put("pumpkin-stem", Material.PUMPKIN_STEM);
        matMap.put("carrot", Material.CARROT);
        matMap.put("potato", Material.POTATO);
        matMap.put("beetroots", Material.BEETROOTS);
        matMap.put("wheat", Material.WHEAT);
        matMap.put("cactus", Material.CACTUS);
        matMap.put("cocoa", Material.COCOA);
        matMap.put("brown-mushroom", Material.BROWN_MUSHROOM);
        matMap.put("red-mushroom", Material.RED_MUSHROOM);
        matMap.put("nether-wart", Material.NETHER_WART);
        matMap.put("chorus-plant", Material.CHORUS_PLANT);
        matMap.put("sunflower", Material.SUNFLOWER);
        matMap.put("lilac", Material.LILAC);
        matMap.put("rose-bush", Material.ROSE_BUSH);
        matMap.put("peony", Material.PEONY);
        matMap.put("dandelion", Material.DANDELION);
        matMap.put("poppy", Material.POPPY);
        matMap.put("blue-orchid", Material.BLUE_ORCHID);
        matMap.put("allium", Material.ALLIUM);
        matMap.put("azure-bluet", Material.AZURE_BLUET);
        matMap.put("red-tulip", Material.RED_TULIP);
        matMap.put("orange-tulip", Material.ORANGE_TULIP);
        matMap.put("white-tulip", Material.WHITE_TULIP);
        matMap.put("pink-tulip", Material.PINK_TULIP);
        matMap.put("oxeye-daisy", Material.OXEYE_DAISY);
    }

    public void buildMatMap14() {
        matMap.put("bamboo", Material.getMaterial("BAMBOO"));
        matMap.put("bamboo-sapling", Material.getMaterial("BAMBOO_SAPLING"));
        matMap.put("sweet-berries", Material.getMaterial("SWEET_BERRIES"));
        matMap.put("sweet-berry-bush", Material.getMaterial("SWEET_BERRY_BUSH"));
        matMap.put("cornflower", Material.getMaterial("CORNFLOWER"));
        matMap.put("lily-of-the-valley", Material.getMaterial("LILY_OF_THE_VALLEY"));
        buildMatMap13();
    }

    public void buildMatMap16() {
        matMap.put("twisting-vines", Material.getMaterial("TWISTING_VINES"));
        matMap.put("weeping-vines", Material.getMaterial("WEEPING_VINES"));
        matMap.put("crimson-fungus", Material.getMaterial("CRIMSON_FUNGUS"));
        matMap.put("warped-fungus", Material.getMaterial("WARPED_FUNGUS"));
        buildMatMap14();
    }

    public void buildMatMap17() {
        matMap.put("azalea", Material.getMaterial("AZALEA"));
        matMap.put("flowering-azalea", Material.getMaterial("FLOWERING_AZALEA"));
        matMap.put("moss-carpet", Material.getMaterial("MOSS_CARPET"));
        matMap.put("moss-block", Material.getMaterial("MOSS_BLOCK"));
        matMap.put("big-dripleaf", Material.getMaterial("BIG_DRIPLEAF"));
        matMap.put("small-dripleaf", Material.getMaterial("SMALL_DRIPLEAF"));
        matMap.put("rooted-dirt", Material.getMaterial("ROOTED_DIRT"));
        buildMatMap16();
    }

    public void buildMatMap19() {
        matMap.put("mangrove-propagule", Material.getMaterial("MANGROVE_PROPAGULE"));
        matMap.put("mangrove-leaves", Material.getMaterial("MANGROVE_LEAVES"));
        buildMatMap17();
    }

    public void buildMatMap20() {
        matMap.put("cherry-sapling", Material.getMaterial("CHERRY_SAPLING"));
        matMap.put("pink-petals", Material.getMaterial("PINK_PETALS"));
        matMap.put("torchflower-seeds", Material.getMaterial("TORCHFLOWER_SEEDS"));
        matMap.put("pitcher-pod", Material.getMaterial("PITCHER_POD"));
        matMap.put("torchflower-crop", Material.getMaterial("TORCHFLOWER_CROP"));
        matMap.put("pitcher-crop", Material.getMaterial("PITCHER_CROP"));
        buildMatMap19();
    }

    public void buildFarmlandCropSet13() {
        farmlandSet.add(Material.MELON_STEM);
        farmlandSet.add(Material.ATTACHED_MELON_STEM);
        farmlandSet.add(Material.PUMPKIN_STEM);
        farmlandSet.add(Material.ATTACHED_PUMPKIN_STEM);
        farmlandSet.add(Material.CARROT);
        farmlandSet.add(Material.POTATO);
        farmlandSet.add(Material.BEETROOTS);
        farmlandSet.add(Material.WHEAT);
    }

    public void buildFarmlandCropSet20() {
        farmlandSet.add(Material.getMaterial("TORCHFLOWER_CROP"));
        farmlandSet.add(Material.getMaterial("PITCHER_CROP"));
        buildFarmlandCropSet13();
    }

    public void buildCropDropMap13() {
        cropMap.put(Material.MELON_STEM, Material.MELON_SEEDS);
        cropMap.put(Material.ATTACHED_MELON_STEM, Material.MELON_SEEDS);
        cropMap.put(Material.PUMPKIN_STEM, Material.PUMPKIN_SEEDS);
        cropMap.put(Material.ATTACHED_PUMPKIN_STEM, Material.PUMPKIN_SEEDS);
        cropMap.put(Material.CARROT, Material.CARROT);
        cropMap.put(Material.POTATO, Material.POTATO);
        cropMap.put(Material.BEETROOTS, Material.BEETROOT_SEEDS);
        cropMap.put(Material.WHEAT, Material.WHEAT_SEEDS);
    }

    public void buildCropDropMap20() {
        cropMap.put(Material.getMaterial("TORCHFLOWER_CROP"), Material.getMaterial("TORCHFLOWER_SEEDS"));
        cropMap.put(Material.getMaterial("PITCHER_CROP"), Material.getMaterial("PITCHER_POD"));
        buildCropDropMap13();
    }

    public HashMap<String, Material> getMatMap() {return matMap;}
    public HashSet<Material> getFarmlandSet() {return farmlandSet;}
    public HashMap<Material, Material> getCropMap() {return cropMap;}
    public Material getMaterial(String name) {return matMap.get(name);}
    public boolean isNotPlantBlock(Material material) {return !matMap.containsValue(material);}
}
