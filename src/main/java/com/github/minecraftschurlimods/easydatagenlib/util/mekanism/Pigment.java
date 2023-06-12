package com.github.minecraftschurlimods.easydatagenlib.util.mekanism;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

import java.util.HashMap;
import java.util.Map;

public class Pigment extends Chemical {
    private static final Map<DyeColor, Pigment> LOOKUP = new HashMap<>();
    // Mekanism Pigments
    public static final Pigment BLACK = new Pigment(new ResourceLocation("mekanism", "black"), DyeColor.BLACK);
    public static final Pigment BLUE = new Pigment(new ResourceLocation("mekanism", "blue"), DyeColor.BLUE);
    public static final Pigment BROWN = new Pigment(new ResourceLocation("mekanism", "brown"), DyeColor.BROWN);
    public static final Pigment CYAN = new Pigment(new ResourceLocation("mekanism", "cyan"), DyeColor.CYAN);
    public static final Pigment GRAY = new Pigment(new ResourceLocation("mekanism", "gray"), DyeColor.GRAY);
    public static final Pigment GREEN = new Pigment(new ResourceLocation("mekanism", "green"), DyeColor.GREEN);
    public static final Pigment LIGHT_BLUE = new Pigment(new ResourceLocation("mekanism", "light_blue"), DyeColor.LIGHT_BLUE);
    public static final Pigment LIGHT_GRAY = new Pigment(new ResourceLocation("mekanism", "light_gray"), DyeColor.LIGHT_GRAY);
    public static final Pigment LIME = new Pigment(new ResourceLocation("mekanism", "lime"), DyeColor.LIME);
    public static final Pigment MAGENTA = new Pigment(new ResourceLocation("mekanism", "magenta"), DyeColor.MAGENTA);
    public static final Pigment ORANGE = new Pigment(new ResourceLocation("mekanism", "orange"), DyeColor.ORANGE);
    public static final Pigment PINK = new Pigment(new ResourceLocation("mekanism", "pink"), DyeColor.PINK);
    public static final Pigment PURPLE = new Pigment(new ResourceLocation("mekanism", "purple"), DyeColor.PURPLE);
    public static final Pigment RED = new Pigment(new ResourceLocation("mekanism", "red"), DyeColor.RED);
    public static final Pigment WHITE = new Pigment(new ResourceLocation("mekanism", "white"), DyeColor.WHITE);
    public static final Pigment YELLOW = new Pigment(new ResourceLocation("mekanism", "yellow"), DyeColor.YELLOW);

    public Pigment(ResourceLocation id, DyeColor color) {
        super(id);
        LOOKUP.put(color, this);
    }

    @Override
    public String getName() {
        return "pigment";
    }

    public static Pigment byDyeColor(DyeColor color) {
        return LOOKUP.get(color);
    }
}
