package net.minecraft.world.item.crafting;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public interface SmithingRecipe extends Recipe<SmithingRecipeInput> {
    @Override
    default RecipeType<?> getType() {
        return RecipeType.SMITHING;
    }

    @Override
    default boolean canCraftInDimensions(int p_266835_, int p_266829_) {
        return p_266835_ >= 3 && p_266829_ >= 1;
    }

    @Override
    default ItemStack getToastSymbol() {
        return new ItemStack(Blocks.SMITHING_TABLE);
    }

    boolean isTemplateIngredient(ItemStack p_266982_);

    boolean isBaseIngredient(ItemStack p_266962_);

    boolean isAdditionIngredient(ItemStack p_267132_);
}