package com.github.minecraftschurlimods.easydatagenlib.mods;

import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeBuilder;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;

public abstract class ArsNouveauDataProvider<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeProvider<T> {
    protected ArsNouveauDataProvider(String folder, String namespace, DataGenerator generator) {
        super(new ResourceLocation("arsnouveau", folder), namespace, generator);
    }
}
