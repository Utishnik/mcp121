package net.minecraft.world.level.block.state.properties;

import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

public enum StructureMode implements StringRepresentable {
    SAVE("save"),
    LOAD("load"),
    CORNER("corner"),
    DATA("data");

    private final String name;
    private final Component displayName;

    private StructureMode(final String p_61809_) {
        this.name = p_61809_;
        this.displayName = Component.translatable("structure_block.mode_info." + p_61809_);
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public Component getDisplayName() {
        return this.displayName;
    }
}