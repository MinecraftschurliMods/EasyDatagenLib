package com.github.minecraftschurlimods.easydatagenlib.mods;

import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeBuilder;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;

public abstract class ImmersiveEngineeringDataProvider<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeProvider<T> {
    protected ImmersiveEngineeringDataProvider(String folder, String namespace, DataGenerator generator) {
        super(new ResourceLocation("immersiveengineering", folder), namespace, generator);
    }
}
