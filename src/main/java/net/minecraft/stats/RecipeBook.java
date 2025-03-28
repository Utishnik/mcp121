package net.minecraft.stats;

import com.google.common.collect.Sets;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.RecipeHolder;

public class RecipeBook {
    protected final Set<ResourceLocation> known = Sets.newHashSet();
    protected final Set<ResourceLocation> highlight = Sets.newHashSet();
    private final RecipeBookSettings bookSettings = new RecipeBookSettings();

    public void copyOverData(RecipeBook p_12686_) {
        this.known.clear();
        this.highlight.clear();
        this.bookSettings.replaceFrom(p_12686_.bookSettings);
        this.known.addAll(p_12686_.known);
        this.highlight.addAll(p_12686_.highlight);
    }

    public void add(RecipeHolder<?> p_301033_) {
        if (!p_301033_.value().isSpecial()) {
            this.add(p_301033_.id());
        }
    }

    protected void add(ResourceLocation p_12703_) {
        this.known.add(p_12703_);
    }

    public boolean contains(@Nullable RecipeHolder<?> p_298018_) {
        return p_298018_ == null ? false : this.known.contains(p_298018_.id());
    }

    public boolean contains(ResourceLocation p_12712_) {
        return this.known.contains(p_12712_);
    }

    public void remove(RecipeHolder<?> p_301343_) {
        this.remove(p_301343_.id());
    }

    protected void remove(ResourceLocation p_12716_) {
        this.known.remove(p_12716_);
        this.highlight.remove(p_12716_);
    }

    public boolean willHighlight(RecipeHolder<?> p_300661_) {
        return this.highlight.contains(p_300661_.id());
    }

    public void removeHighlight(RecipeHolder<?> p_298729_) {
        this.highlight.remove(p_298729_.id());
    }

    public void addHighlight(RecipeHolder<?> p_300059_) {
        this.addHighlight(p_300059_.id());
    }

    protected void addHighlight(ResourceLocation p_12720_) {
        this.highlight.add(p_12720_);
    }

    public boolean isOpen(RecipeBookType p_12692_) {
        return this.bookSettings.isOpen(p_12692_);
    }

    public void setOpen(RecipeBookType p_12694_, boolean p_12695_) {
        this.bookSettings.setOpen(p_12694_, p_12695_);
    }

    public boolean isFiltering(RecipeBookMenu<?, ?> p_12690_) {
        return this.isFiltering(p_12690_.getRecipeBookType());
    }

    public boolean isFiltering(RecipeBookType p_12705_) {
        return this.bookSettings.isFiltering(p_12705_);
    }

    public void setFiltering(RecipeBookType p_12707_, boolean p_12708_) {
        this.bookSettings.setFiltering(p_12707_, p_12708_);
    }

    public void setBookSettings(RecipeBookSettings p_12688_) {
        this.bookSettings.replaceFrom(p_12688_);
    }

    public RecipeBookSettings getBookSettings() {
        return this.bookSettings.copy();
    }

    public void setBookSetting(RecipeBookType p_12697_, boolean p_12698_, boolean p_12699_) {
        this.bookSettings.setOpen(p_12697_, p_12698_);
        this.bookSettings.setFiltering(p_12697_, p_12699_);
    }
}