package com.github.minecraftschurlimods.easydatagenlib.util.mekanism;

import net.minecraft.resources.ResourceLocation;

public class Gas extends Chemical {
    // Mekanism Gases
    public static final Gas HYDROGEN = new Gas(new ResourceLocation("mekanism", "hydrogen"));
    public static final Gas OXYGEN = new Gas(new ResourceLocation("mekanism", "oxygen"));
    public static final Gas STEAM = new Gas(new ResourceLocation("mekanism", "steam"));
    public static final Gas CHLORINE = new Gas(new ResourceLocation("mekanism", "chlorine"));
    public static final Gas SULFUR_DIOXIDE = new Gas(new ResourceLocation("mekanism", "sulfur_dioxide"));
    public static final Gas SULFUR_TRIOXIDE = new Gas(new ResourceLocation("mekanism", "sulfur_trioxide"));
    public static final Gas SULFURIC_ACID = new Gas(new ResourceLocation("mekanism", "sulfuric_acid"));
    public static final Gas HYDROGEN_CHLORIDE = new Gas(new ResourceLocation("mekanism", "hydrogen_chloride"));
    public static final Gas HYDROFLUORIC_ACID = new Gas(new ResourceLocation("mekanism", "hydrofluoric_acid"));
    public static final Gas URANIUM_OXIDE = new Gas(new ResourceLocation("mekanism", "uranium_oxide"));
    public static final Gas URANIUM_HEXAFLUORIDE = new Gas(new ResourceLocation("mekanism", "uranium_hexafluoride"));
    public static final Gas ETHENE = new Gas(new ResourceLocation("mekanism", "ethene"));
    public static final Gas SODIUM = new Gas(new ResourceLocation("mekanism", "sodium"));
    public static final Gas SUPERHEATED_SODIUM = new Gas(new ResourceLocation("mekanism", "superheated_sodium"));
    public static final Gas BRINE = new Gas(new ResourceLocation("mekanism", "brine"));
    public static final Gas LITHIUM = new Gas(new ResourceLocation("mekanism", "lithium"));
    public static final Gas OSMIUM = new Gas(new ResourceLocation("mekanism", "osmium"));
    public static final Gas FISSILE_FUEL = new Gas(new ResourceLocation("mekanism", "fissile_fuel"));
    public static final Gas NUCLEAR_WASTE = new Gas(new ResourceLocation("mekanism", "nuclear_waste"));
    public static final Gas SPENT_NUCLEAR_WASTE = new Gas(new ResourceLocation("mekanism", "spent_nuclear_waste"));
    public static final Gas PLUTONIUM = new Gas(new ResourceLocation("mekanism", "plutonium"));
    public static final Gas POLONIUM = new Gas(new ResourceLocation("mekanism", "polonium"));
    public static final Gas ANTIMATTER = new Gas(new ResourceLocation("mekanism", "antimatter"));

    public Gas(ResourceLocation id) {
        super(id);
    }

    @Override
    public String getName() {
        return "gas";
    }
}
