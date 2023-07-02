package com.lichenaut.plantnerfer.util;

import com.lichenaut.plantnerfer.PlantNerfer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.HashSet;

public class PNPlantLoader {

    private final PlantNerfer plugin;

    public PNPlantLoader(PlantNerfer plugin) {this.plugin = plugin;}

    public void loadPlants() {
        int version = plugin.getVersion();
        PluginManager pMan = Bukkit.getPluginManager();
        for (int i = version; i > 0; i--) {
            if (i == 20) {

            } else if (i == 19) {

            } else if (i == 18) {

            } else if (i == 17) {

            } else if (i == 16) {

            } else if (i == 15) {

            } else if (i == 14) {

            } else if (i == 13) {

                break;
            }
        }
    }
}
