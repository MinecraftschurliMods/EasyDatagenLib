package com.github.minecraftschurlimods.easydatagenlib.mods;

import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeBuilder;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;

public abstract class OccultismDataProvider<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeProvider<T> {
    protected OccultismDataProvider(String folder, String namespace, DataGenerator generator) {
        super(new ResourceLocation("occultism", folder), namespace, generator);
    }
}
