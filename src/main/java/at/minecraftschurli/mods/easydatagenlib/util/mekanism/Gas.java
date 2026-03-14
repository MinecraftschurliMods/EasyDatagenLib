package at.minecraftschurli.mods.easydatagenlib.util.mekanism;

import net.minecraft.resources.Identifier;

public class Gas extends Chemical {
    // Mekanism Gases
    public static final Gas HYDROGEN = new Gas(Identifier.fromNamespaceAndPath("mekanism", "hydrogen"));
    public static final Gas OXYGEN = new Gas(Identifier.fromNamespaceAndPath("mekanism", "oxygen"));
    public static final Gas STEAM = new Gas(Identifier.fromNamespaceAndPath("mekanism", "steam"));
    public static final Gas CHLORINE = new Gas(Identifier.fromNamespaceAndPath("mekanism", "chlorine"));
    public static final Gas SULFUR_DIOXIDE = new Gas(Identifier.fromNamespaceAndPath("mekanism", "sulfur_dioxide"));
    public static final Gas SULFUR_TRIOXIDE = new Gas(Identifier.fromNamespaceAndPath("mekanism", "sulfur_trioxide"));
    public static final Gas SULFURIC_ACID = new Gas(Identifier.fromNamespaceAndPath("mekanism", "sulfuric_acid"));
    public static final Gas HYDROGEN_CHLORIDE = new Gas(Identifier.fromNamespaceAndPath("mekanism", "hydrogen_chloride"));
    public static final Gas HYDROFLUORIC_ACID = new Gas(Identifier.fromNamespaceAndPath("mekanism", "hydrofluoric_acid"));
    public static final Gas URANIUM_OXIDE = new Gas(Identifier.fromNamespaceAndPath("mekanism", "uranium_oxide"));
    public static final Gas URANIUM_HEXAFLUORIDE = new Gas(Identifier.fromNamespaceAndPath("mekanism", "uranium_hexafluoride"));
    public static final Gas ETHENE = new Gas(Identifier.fromNamespaceAndPath("mekanism", "ethene"));
    public static final Gas SODIUM = new Gas(Identifier.fromNamespaceAndPath("mekanism", "sodium"));
    public static final Gas SUPERHEATED_SODIUM = new Gas(Identifier.fromNamespaceAndPath("mekanism", "superheated_sodium"));
    public static final Gas BRINE = new Gas(Identifier.fromNamespaceAndPath("mekanism", "brine"));
    public static final Gas LITHIUM = new Gas(Identifier.fromNamespaceAndPath("mekanism", "lithium"));
    public static final Gas OSMIUM = new Gas(Identifier.fromNamespaceAndPath("mekanism", "osmium"));
    public static final Gas FISSILE_FUEL = new Gas(Identifier.fromNamespaceAndPath("mekanism", "fissile_fuel"));
    public static final Gas NUCLEAR_WASTE = new Gas(Identifier.fromNamespaceAndPath("mekanism", "nuclear_waste"));
    public static final Gas SPENT_NUCLEAR_WASTE = new Gas(Identifier.fromNamespaceAndPath("mekanism", "spent_nuclear_waste"));
    public static final Gas PLUTONIUM = new Gas(Identifier.fromNamespaceAndPath("mekanism", "plutonium"));
    public static final Gas POLONIUM = new Gas(Identifier.fromNamespaceAndPath("mekanism", "polonium"));
    public static final Gas ANTIMATTER = new Gas(Identifier.fromNamespaceAndPath("mekanism", "antimatter"));

    public Gas(Identifier id) {
        super(id);
    }

    @Override
    public String getName() {
        return "gas";
    }
}
