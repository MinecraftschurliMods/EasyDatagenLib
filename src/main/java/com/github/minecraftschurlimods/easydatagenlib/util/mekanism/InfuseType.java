package com.github.minecraftschurlimods.easydatagenlib.util.mekanism;

import net.minecraft.resources.ResourceLocation;

public class InfuseType extends Chemical {
    //Mekanism Infuse Types
    public static final InfuseType BIO = new InfuseType(new ResourceLocation("mekanism", "bio"));
    public static final InfuseType CARBON = new InfuseType(new ResourceLocation("mekanism", "carbon"));
    public static final InfuseType DIAMOND = new InfuseType(new ResourceLocation("mekanism", "diamond"));
    public static final InfuseType FUNGI = new InfuseType(new ResourceLocation("mekanism", "fungi"));
    public static final InfuseType GOLD = new InfuseType(new ResourceLocation("mekanism", "gold"));
    public static final InfuseType REDSTONE = new InfuseType(new ResourceLocation("mekanism", "redstone"));
    public static final InfuseType REFINED_OBSIDIAN = new InfuseType(new ResourceLocation("mekanism", "refined_obsidian"));
    public static final InfuseType TIN = new InfuseType(new ResourceLocation("mekanism", "tin"));

    public InfuseType(ResourceLocation id) {
        super(id);
    }

    @Override
    public String getName() {
        return "infuse_type";
    }
}
