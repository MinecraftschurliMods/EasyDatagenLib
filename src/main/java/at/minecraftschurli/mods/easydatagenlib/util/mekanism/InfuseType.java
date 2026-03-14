package at.minecraftschurli.mods.easydatagenlib.util.mekanism;

import net.minecraft.resources.Identifier;

public class InfuseType extends Chemical {
    //Mekanism Infuse Types
    public static final InfuseType BIO = new InfuseType(Identifier.fromNamespaceAndPath("mekanism", "bio"));
    public static final InfuseType CARBON = new InfuseType(Identifier.fromNamespaceAndPath("mekanism", "carbon"));
    public static final InfuseType DIAMOND = new InfuseType(Identifier.fromNamespaceAndPath("mekanism", "diamond"));
    public static final InfuseType FUNGI = new InfuseType(Identifier.fromNamespaceAndPath("mekanism", "fungi"));
    public static final InfuseType GOLD = new InfuseType(Identifier.fromNamespaceAndPath("mekanism", "gold"));
    public static final InfuseType REDSTONE = new InfuseType(Identifier.fromNamespaceAndPath("mekanism", "redstone"));
    public static final InfuseType REFINED_OBSIDIAN = new InfuseType(Identifier.fromNamespaceAndPath("mekanism", "refined_obsidian"));
    public static final InfuseType TIN = new InfuseType(Identifier.fromNamespaceAndPath("mekanism", "tin"));

    public InfuseType(Identifier id) {
        super(id);
    }

    @Override
    public String getName() {
        return "infuse_type";
    }
}
