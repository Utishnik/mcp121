package net.minecraft.world.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public record SeededContainerLoot(ResourceKey<LootTable> lootTable, long seed) {
    public static final Codec<SeededContainerLoot> CODEC = RecordCodecBuilder.create(
        p_328588_ -> p_328588_.group(
                    ResourceKey.codec(Registries.LOOT_TABLE).fieldOf("loot_table").forGetter(SeededContainerLoot::lootTable),
                    Codec.LONG.optionalFieldOf("seed", Long.valueOf(0L)).forGetter(SeededContainerLoot::seed)
                )
                .apply(p_328588_, SeededContainerLoot::new)
    );
}