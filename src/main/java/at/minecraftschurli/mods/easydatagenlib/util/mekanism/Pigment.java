package at.minecraftschurli.mods.easydatagenlib.util.mekanism;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;

import java.util.HashMap;
import java.util.Map;

public class Pigment extends Chemical {
    private static final Map<DyeColor, Pigment> LOOKUP = new HashMap<>();
    // Mekanism Pigments
    public static final Pigment BLACK = new Pigment(Identifier.fromNamespaceAndPath("mekanism", "black"), DyeColor.BLACK);
    public static final Pigment BLUE = new Pigment(Identifier.fromNamespaceAndPath("mekanism", "blue"), DyeColor.BLUE);
    public static final Pigment BROWN = new Pigment(Identifier.fromNamespaceAndPath("mekanism", "brown"), DyeColor.BROWN);
    public static final Pigment CYAN = new Pigment(Identifier.fromNamespaceAndPath("mekanism", "cyan"), DyeColor.CYAN);
    public static final Pigment GRAY = new Pigment(Identifier.fromNamespaceAndPath("mekanism", "gray"), DyeColor.GRAY);
    public static final Pigment GREEN = new Pigment(Identifier.fromNamespaceAndPath("mekanism", "green"), DyeColor.GREEN);
    public static final Pigment LIGHT_BLUE = new Pigment(Identifier.fromNamespaceAndPath("mekanism", "light_blue"), DyeColor.LIGHT_BLUE);
    public static final Pigment LIGHT_GRAY = new Pigment(Identifier.fromNamespaceAndPath("mekanism", "light_gray"), DyeColor.LIGHT_GRAY);
    public static final Pigment LIME = new Pigment(Identifier.fromNamespaceAndPath("mekanism", "lime"), DyeColor.LIME);
    public static final Pigment MAGENTA = new Pigment(Identifier.fromNamespaceAndPath("mekanism", "magenta"), DyeColor.MAGENTA);
    public static final Pigment ORANGE = new Pigment(Identifier.fromNamespaceAndPath("mekanism", "orange"), DyeColor.ORANGE);
    public static final Pigment PINK = new Pigment(Identifier.fromNamespaceAndPath("mekanism", "pink"), DyeColor.PINK);
    public static final Pigment PURPLE = new Pigment(Identifier.fromNamespaceAndPath("mekanism", "purple"), DyeColor.PURPLE);
    public static final Pigment RED = new Pigment(Identifier.fromNamespaceAndPath("mekanism", "red"), DyeColor.RED);
    public static final Pigment WHITE = new Pigment(Identifier.fromNamespaceAndPath("mekanism", "white"), DyeColor.WHITE);
    public static final Pigment YELLOW = new Pigment(Identifier.fromNamespaceAndPath("mekanism", "yellow"), DyeColor.YELLOW);

    public Pigment(Identifier id, DyeColor color) {
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
