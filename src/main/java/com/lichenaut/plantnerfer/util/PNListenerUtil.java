package com.lichenaut.plantnerfer.util;

import com.lichenaut.plantnerfer.PlantNerfer;
import com.lichenaut.plantnerfer.load.PNPlantLoader;

import java.util.List;

public class PNListenerUtil {

        protected final PlantNerfer plugin;
        protected final PNPlantLoader loader;

        public PNListenerUtil(PlantNerfer plugin, PNPlantLoader loader) {this.plugin = plugin;this.loader = loader;}

        public boolean invalidWorld(String worldName) {
                List<String> worlds = plugin.getConfig().getStringList("restrict-plugin-to-worlds");
                if (worlds.size() > 0) return !worlds.contains(worldName); else return false;
        }

        public boolean chance(int chance) {return Math.random() * 100 < chance;}
}
