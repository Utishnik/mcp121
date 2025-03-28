package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;

public final class CavesAndCliffsRenames {
    public static final ImmutableMap<String, String> RENAMES = ImmutableMap.<String, String>builder()
        .put("minecraft:badlands_plateau", "minecraft:badlands")
        .put("minecraft:bamboo_jungle_hills", "minecraft:bamboo_jungle")
        .put("minecraft:birch_forest_hills", "minecraft:birch_forest")
        .put("minecraft:dark_forest_hills", "minecraft:dark_forest")
        .put("minecraft:desert_hills", "minecraft:desert")
        .put("minecraft:desert_lakes", "minecraft:desert")
        .put("minecraft:giant_spruce_taiga_hills", "minecraft:old_growth_spruce_taiga")
        .put("minecraft:giant_spruce_taiga", "minecraft:old_growth_spruce_taiga")
        .put("minecraft:giant_tree_taiga_hills", "minecraft:old_growth_pine_taiga")
        .put("minecraft:giant_tree_taiga", "minecraft:old_growth_pine_taiga")
        .put("minecraft:gravelly_mountains", "minecraft:windswept_gravelly_hills")
        .put("minecraft:jungle_edge", "minecraft:sparse_jungle")
        .put("minecraft:jungle_hills", "minecraft:jungle")
        .put("minecraft:modified_badlands_plateau", "minecraft:badlands")
        .put("minecraft:modified_gravelly_mountains", "minecraft:windswept_gravelly_hills")
        .put("minecraft:modified_jungle_edge", "minecraft:sparse_jungle")
        .put("minecraft:modified_jungle", "minecraft:jungle")
        .put("minecraft:modified_wooded_badlands_plateau", "minecraft:wooded_badlands")
        .put("minecraft:mountain_edge", "minecraft:windswept_hills")
        .put("minecraft:mountains", "minecraft:windswept_hills")
        .put("minecraft:mushroom_field_shore", "minecraft:mushroom_fields")
        .put("minecraft:shattered_savanna", "minecraft:windswept_savanna")
        .put("minecraft:shattered_savanna_plateau", "minecraft:windswept_savanna")
        .put("minecraft:snowy_mountains", "minecraft:snowy_plains")
        .put("minecraft:snowy_taiga_hills", "minecraft:snowy_taiga")
        .put("minecraft:snowy_taiga_mountains", "minecraft:snowy_taiga")
        .put("minecraft:snowy_tundra", "minecraft:snowy_plains")
        .put("minecraft:stone_shore", "minecraft:stony_shore")
        .put("minecraft:swamp_hills", "minecraft:swamp")
        .put("minecraft:taiga_hills", "minecraft:taiga")
        .put("minecraft:taiga_mountains", "minecraft:taiga")
        .put("minecraft:tall_birch_forest", "minecraft:old_growth_birch_forest")
        .put("minecraft:tall_birch_hills", "minecraft:old_growth_birch_forest")
        .put("minecraft:wooded_badlands_plateau", "minecraft:wooded_badlands")
        .put("minecraft:wooded_hills", "minecraft:forest")
        .put("minecraft:wooded_mountains", "minecraft:windswept_forest")
        .put("minecraft:lofty_peaks", "minecraft:jagged_peaks")
        .put("minecraft:snowcapped_peaks", "minecraft:frozen_peaks")
        .build();

    private CavesAndCliffsRenames() {
    }
}