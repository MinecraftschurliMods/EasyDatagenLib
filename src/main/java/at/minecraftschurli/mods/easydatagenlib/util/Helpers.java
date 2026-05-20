package at.minecraftschurli.mods.easydatagenlib.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Util;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.util.Lazy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public interface Helpers {
    Identifier AIR = Identifier.withDefaultNamespace("air");
    Identifier CHEST = Identifier.withDefaultNamespace("chest");
    Identifier GRAVEL = Identifier.withDefaultNamespace("gravel");
    Identifier STICK = Identifier.withDefaultNamespace("stick");
    Identifier SHROOMLIGHT = Identifier.withDefaultNamespace("shroomlight");
    Identifier EXPERIENCE_NUGGET = Identifier.fromNamespaceAndPath("create", "experience_nugget");
    Identifier TREE_BARK = Identifier.fromNamespaceAndPath("farmersdelight", "tree_bark");
    Identifier BIO_FUEL = Identifier.fromNamespaceAndPath("mekanism", "bio_fuel");
    Identifier MEKANISM_SAWDUST = Identifier.fromNamespaceAndPath("mekanism", "sawdust");
    Identifier RICH_SLAG = Identifier.fromNamespaceAndPath("thermal", "rich_slag");
    Identifier THERMAL_SAWDUST = Identifier.fromNamespaceAndPath("thermal", "sawdust");
    Ingredient MUSHROOM_SOIL = Ingredient.of(Items.MYCELIUM, Items.PODZOL);
    Ingredient PRESS_PACKING_3x3_DIE = PotentiallyAbsentIngredient.of(Identifier.fromNamespaceAndPath("thermal", "press_packing_3x3_die"));
    Ingredient PRESS_UNPACKING_DIE = PotentiallyAbsentIngredient.of(Identifier.fromNamespaceAndPath("thermal", "press_unpacking_die"));
    TagKey<Item> KNIVES = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "tools/knives"));
    TagKey<Item> SLAG = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "slag"));
    TagKey<Item> WOOD_DUST = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "dusts/wood"));
    Map<Item, DyeColor> DYES = Map.ofEntries(
        Map.entry(Items.BLACK_DYE, DyeColor.BLACK),
        Map.entry(Items.BLUE_DYE, DyeColor.BLUE),
        Map.entry(Items.BROWN_DYE, DyeColor.BROWN),
        Map.entry(Items.CYAN_DYE, DyeColor.CYAN),
        Map.entry(Items.GRAY_DYE, DyeColor.GRAY),
        Map.entry(Items.GREEN_DYE, DyeColor.GREEN),
        Map.entry(Items.LIGHT_BLUE_DYE, DyeColor.LIGHT_BLUE),
        Map.entry(Items.LIGHT_GRAY_DYE, DyeColor.LIGHT_GRAY),
        Map.entry(Items.LIME_DYE, DyeColor.LIME),
        Map.entry(Items.MAGENTA_DYE, DyeColor.MAGENTA),
        Map.entry(Items.ORANGE_DYE, DyeColor.ORANGE),
        Map.entry(Items.PINK_DYE, DyeColor.PINK),
        Map.entry(Items.PURPLE_DYE, DyeColor.PURPLE),
        Map.entry(Items.RED_DYE, DyeColor.RED),
        Map.entry(Items.WHITE_DYE, DyeColor.WHITE),
        Map.entry(Items.YELLOW_DYE, DyeColor.YELLOW));
}
