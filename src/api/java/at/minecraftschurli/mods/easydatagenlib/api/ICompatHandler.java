package at.minecraftschurli.mods.easydatagenlib.api;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public interface ICompatHandler {

    /**
     * Shortcut to get a block's registry name.
     *
     * @param block The block to get the registry name for.
     * @return The registry name of the given block.
     */
    @SuppressWarnings("ConstantConditions")
    static Identifier blockId(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    /**
     * Shortcut to get an item's registry name.
     *
     * @param item The item to get the registry name for.
     * @return The registry name of the given item.
     */
    @SuppressWarnings("ConstantConditions")
    static Identifier itemId(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }

    /**
     * Shortcut to get a fluid's registry name.
     *
     * @param fluid The fluid to get the registry name for.
     * @return The registry name of the given fluid.
     */
    @SuppressWarnings("ConstantConditions")
    static Identifier fluidId(Fluid fluid) {
        return BuiltInRegistries.FLUID.getKey(fluid);
    }

    /**
     * Transforms a resource location into a string ready to be used in recipe names.
     *
     * @param rl The resource location to transform.
     * @return A string representation of the resource location.
     */
    static String toName(Identifier rl) {
        return rl.getPath().replace('/', '_');
    }

    /**
     * Transforms a tag into a string ready to be used in recipe names.
     *
     * @param tag The tag to transform.
     * @return A string representation of the tag name.
     */
    static String toName(TagKey<?> tag) {
        return toName(tag.location());
    }

    /**
     * Transforms a block into a string ready to be used in recipe names.
     *
     * @param block The block to transform.
     * @return A string representation of the block name.
     */
    static String toName(Block block) {
        return toName(blockId(block));
    }

    /**
     * Transforms an item into a string ready to be used in recipe names.
     *
     * @param item The item to transform.
     * @return A string representation of the item name.
     */
    static String toName(Item item) {
        return toName(itemId(item));
    }

    /**
     * Transforms a fluid into a string ready to be used in recipe names.
     *
     * @param fluid The fluid to transform.
     * @return A string representation of the fluid name.
     */
    static String toName(Fluid fluid) {
        return toName(fluidId(fluid));
    }

    CompletableFuture<?> run(CachedOutput output);

    /**
     * Adds processing for a gem ore, such as diamonds or emeralds. This assumes that the ores drop one item by default, and thus does not support ores that drop multiple items, such as lapis.
     *
     * @param ore          The ore to be processed.
     * @param deepslateOre The deepslate ore to be processed.
     * @param oreTag       The ore tag to be processed.
     * @param gem          The gem item.
     * @param block        The gem block item. Can be null. If null, no recipes involving this item will be generated.
     * @param dust         The dust item. Can be null. If null, no recipes involving this item will be generated.
     */
    default void addGemOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item gem, @Nullable Item block, @Nullable Item dust) {}

    /**
     * Adds processing for a gem ore, such as diamonds or emeralds. This assumes that the ores drop one item by default, and thus does not support ores that drop multiple items, such as lapis.
     *
     * @param ore          The ore to be processed.
     * @param deepslateOre The deepslate ore to be processed.
     * @param oreTag       The ore tag to be processed.
     * @param gem          The gem item.
     * @param block        The gem block item. Can be null. If null, no recipes involving this item will be generated.
     */
    default void addGemOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item gem, @Nullable Item block) {
        addGemOreProcessing(ore, deepslateOre, oreTag, gem, block, null);
    }

    /**
     * Adds processing for a gem ore, such as diamonds or emeralds. This assumes that the ores drop one item by default, and thus does not support ores that drop multiple items, such as lapis.
     *
     * @param ore          The ore to be processed.
     * @param deepslateOre The deepslate ore to be processed.
     * @param oreTag       The ore tag to be processed.
     * @param gem          The gem item.
     */
    default void addGemOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item gem) {
        addGemOreProcessing(ore, deepslateOre, oreTag, gem, null);
    }

    /**
     * Adds processing for a metal ore, such as iron or gold. This assumes that the ores drop one item by default, and thus does not support ores that drop multiple items, such as copper.
     *
     * @param ore              The ore to be processed.
     * @param deepslateOre     The deepslate ore to be processed.
     * @param oreTag           The ore tag to be processed.
     * @param rawOre           The raw ore item.
     * @param rawOreTag        The raw ore tag.
     * @param rawOreBlock      The raw ore block item. Can be null. If null, no recipes involving this item will be generated.
     * @param rawOreBlockTag   The raw ore block tag.
     * @param ingot            The ingot item.
     * @param ingotTag         The ingot tag.
     * @param ingotBlock       The ingot block item. Can be null. If null, no recipes involving this item will be generated.
     * @param ingotBlockTag    The ingot block tag.
     * @param nugget           The nugget item. Can be null. If null, no recipes involving this item will be generated.
     * @param nuggetTag        The nugget tag.
     * @param dust             The dust item. Used in various mods for ore duplication. Can be null. If null, no recipes involving this item will be generated.
     * @param dustTag          The dust tag.
     * @param crushedOre       The id of the crushed ore item. Used in Create crushing. Can be null. If null, no recipes involving this item will be generated.
     * @param secondaryIngot   The secondary ingot item. Used in Thermal smelting. Can be null. If null, no recipes involving this item will be generated.
     * @param secondaryDust    The secondary dust item. Used in Thermal pulverizing. Can be null. If null, no recipes involving this item will be generated.
     * @param secondaryDustTag The secondary dust tag. Used in Immersive Engineering crushing. Can be null. If null, no recipes involving this item will be generated.
     */
    default void addMetalOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item rawOre, TagKey<Item> rawOreTag, @Nullable Item rawOreBlock, @Nullable TagKey<Item> rawOreBlockTag, Item ingot, TagKey<Item> ingotTag, @Nullable Item ingotBlock, @Nullable TagKey<Item> ingotBlockTag, @Nullable Item nugget, @Nullable TagKey<Item> nuggetTag, @Nullable Item dust, @Nullable TagKey<Item> dustTag, @Nullable Identifier crushedOre, @Nullable Identifier secondaryIngot, @Nullable Identifier secondaryDust, @Nullable TagKey<Item> secondaryDustTag) {}

    /**
     * Adds processing for a metal ore, such as iron or gold. This assumes that the ores drop one item by default, and thus does not support ores that drop multiple items, such as copper.
     *
     * @param ore            The ore to be processed.
     * @param deepslateOre   The deepslate ore to be processed.
     * @param oreTag         The ore tag to be processed.
     * @param rawOre         The raw ore item.
     * @param rawOreTag      The raw ore tag.
     * @param rawOreBlock    The raw ore block item. Can be null. If null, no recipes involving this item will be generated.
     * @param rawOreBlockTag The raw ore block tag.
     * @param ingot          The ingot item.
     * @param ingotTag       The ingot tag.
     * @param ingotBlock     The ingot block item. Can be null. If null, no recipes involving this item will be generated.
     * @param ingotBlockTag  The ingot block tag.
     * @param nugget         The nugget item. Can be null. If null, no recipes involving this item will be generated.
     * @param nuggetTag      The nugget tag.
     * @param dust           The dust item. Used in various mods for ore duplication. Can be null. If null, no recipes involving this item will be generated.
     * @param dustTag        The dust tag.
     * @param crushedOre     The id of the crushed ore item. Used in Create crushing. Can be null. If null, no recipes involving this item will be generated.
     */
    default void addMetalOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item rawOre, TagKey<Item> rawOreTag, @Nullable Item rawOreBlock, @Nullable TagKey<Item> rawOreBlockTag, Item ingot, TagKey<Item> ingotTag, @Nullable Item ingotBlock, @Nullable TagKey<Item> ingotBlockTag, @Nullable Item nugget, @Nullable TagKey<Item> nuggetTag, @Nullable Item dust, @Nullable TagKey<Item> dustTag, @Nullable Identifier crushedOre) {
        addMetalOreProcessing(ore, deepslateOre, oreTag, rawOre, rawOreTag, rawOreBlock, rawOreBlockTag, ingot, ingotTag, ingotBlock, ingotBlockTag, nugget, nuggetTag, dust, dustTag, crushedOre, null, null, null);
    }

    /**
     * Adds processing for a metal ore, such as iron or gold. This assumes that the ores drop one item by default, and thus does not support ores that drop multiple items, such as copper.
     *
     * @param ore            The ore to be processed.
     * @param deepslateOre   The deepslate ore to be processed.
     * @param oreTag         The ore tag to be processed.
     * @param rawOre         The raw ore item.
     * @param rawOreTag      The raw ore tag.
     * @param rawOreBlock    The raw ore block item. Can be null. If null, no recipes involving this item will be generated.
     * @param rawOreBlockTag The raw ore block tag.
     * @param ingot          The ingot item.
     * @param ingotTag       The ingot tag.
     * @param ingotBlock     The ingot block item. Can be null. If null, no recipes involving this item will be generated.
     * @param ingotBlockTag  The ingot block tag.
     * @param nugget         The nugget item. Can be null. If null, no recipes involving this item will be generated.
     * @param nuggetTag      The nugget tag.
     * @param dust           The dust item. Used in various mods for ore duplication. Can be null. If null, no recipes involving this item will be generated.
     * @param dustTag        The dust tag.
     */
    default void addMetalOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item rawOre, TagKey<Item> rawOreTag, @Nullable Item rawOreBlock, @Nullable TagKey<Item> rawOreBlockTag, Item ingot, TagKey<Item> ingotTag, @Nullable Item ingotBlock, @Nullable TagKey<Item> ingotBlockTag, @Nullable Item nugget, @Nullable TagKey<Item> nuggetTag, @Nullable Item dust, @Nullable TagKey<Item> dustTag) {
        addMetalOreProcessing(ore, deepslateOre, oreTag, rawOre, rawOreTag, rawOreBlock, rawOreBlockTag, ingot, ingotTag, ingotBlock, ingotBlockTag, nugget, nuggetTag, dust, dustTag, null);
    }

    /**
     * Adds processing for a metal ore, such as iron or gold. This assumes that the ores drop one item by default, and thus does not support ores that drop multiple items, such as copper.
     *
     * @param ore            The ore to be processed.
     * @param deepslateOre   The deepslate ore to be processed.
     * @param oreTag         The ore tag to be processed.
     * @param rawOre         The raw ore item.
     * @param rawOreTag      The raw ore tag.
     * @param rawOreBlock    The raw ore block item. Can be null. If null, no recipes involving this item will be generated.
     * @param rawOreBlockTag The raw ore block tag.
     * @param ingot          The ingot item.
     * @param ingotTag       The ingot tag.
     * @param ingotBlock     The ingot block item. Can be null. If null, no recipes involving this item will be generated.
     * @param ingotBlockTag  The ingot block tag.
     * @param nugget         The nugget item. Can be null. If null, no recipes involving this item will be generated.
     * @param nuggetTag      The nugget tag.
     */
    default void addMetalOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item rawOre, TagKey<Item> rawOreTag, @Nullable Item rawOreBlock, @Nullable TagKey<Item> rawOreBlockTag, Item ingot, TagKey<Item> ingotTag, @Nullable Item ingotBlock, @Nullable TagKey<Item> ingotBlockTag, @Nullable Item nugget, @Nullable TagKey<Item> nuggetTag) {
        addMetalOreProcessing(ore, deepslateOre, oreTag, rawOre, rawOreTag, rawOreBlock, rawOreBlockTag, ingot, ingotTag, ingotBlock, ingotBlockTag, nugget, nuggetTag, null, null);
    }

    /**
     * Adds processing for a metal ore, such as iron or gold. This assumes that the ores drop one item by default, and thus does not support ores that drop multiple items, such as copper.
     *
     * @param ore            The ore to be processed.
     * @param deepslateOre   The deepslate ore to be processed.
     * @param oreTag         The ore tag to be processed.
     * @param rawOre         The raw ore item.
     * @param rawOreTag      The raw ore tag.
     * @param rawOreBlock    The raw ore block item. Can be null. If null, no recipes involving this item will be generated.
     * @param rawOreBlockTag The raw ore block tag.
     * @param ingot          The ingot item.
     * @param ingotTag       The ingot tag.
     * @param ingotBlock     The ingot block item. Can be null. If null, no recipes involving this item will be generated.
     * @param ingotBlockTag  The ingot block tag.
     */
    default void addMetalOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item rawOre, TagKey<Item> rawOreTag, @Nullable Item rawOreBlock, @Nullable TagKey<Item> rawOreBlockTag, Item ingot, TagKey<Item> ingotTag, @Nullable Item ingotBlock, @Nullable TagKey<Item> ingotBlockTag) {
        addMetalOreProcessing(ore, deepslateOre, oreTag, rawOre, rawOreTag, rawOreBlock, rawOreBlockTag, ingot, ingotTag, ingotBlock, ingotBlockTag, null, null);
    }

    /**
     * Adds processing for a metal ore, such as iron or gold. This assumes that the ores drop one item by default, and thus does not support ores that drop multiple items, such as copper.
     *
     * @param ore          The ore to be processed.
     * @param deepslateOre The deepslate ore to be processed.
     * @param oreTag       The ore tag to be processed.
     * @param rawOre       The raw ore item.
     * @param rawOreTag    The raw ore tag.
     * @param ingot        The ingot item.
     * @param ingotTag     The ingot tag.
     */
    default void addMetalOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item rawOre, TagKey<Item> rawOreTag, Item ingot, TagKey<Item> ingotTag) {
        addMetalOreProcessing(ore, deepslateOre, oreTag, rawOre, rawOreTag, null, null, ingot, ingotTag, null, null);
    }

    /**
     * Adds processing for a small flower.
     *
     * @param flower  The flower to be processed.
     * @param output1 The primary output. Should ideally be a {@link DyeItem}.
     * @param count1  The count of the primary output. Used in Create milling. Some mods may use different, fixed amounts.
     * @param output2 The id of the secondary output. Used in Create milling. Can be null. If null, no recipes involving this item will be generated.
     * @param count2  The count of the secondary output. Used in Create milling.
     * @param chance2 The chance that the secondary output will actually appear. Used in Create milling.
     * @param output3 The id of the tertiary output. Used in Create milling. Can be null. If null, no recipes involving this item will be generated.
     * @param count3  The count of the tertiary output. Used in Create milling.
     * @param chance3 The chance that the tertiary output will actually appear. Used in Create milling.
     */
    default void addFlowerProcessing(Item flower, Item output1, int count1, @Nullable Identifier output2, int count2, float chance2, @Nullable Identifier output3, int count3, float chance3) {}

    /**
     * Adds processing for a small flower.
     *
     * @param flower  The flower to be processed.
     * @param output1 The primary output. Should ideally be a {@link DyeItem}.
     * @param count1  The count of the primary output. Used in Create milling. Some mods may use different, fixed amounts.
     * @param output2 The id of the secondary output. Used in Create milling. Can be null. If null, no recipes involving this item will be generated.
     * @param chance2 The chance that the secondary output will actually appear. Used in Create milling.
     * @param output3 The id of the tertiary output. Used in Create milling. Can be null. If null, no recipes involving this item will be generated.
     * @param chance3 The chance that the tertiary output will actually appear. Used in Create milling.
     */
    default void addFlowerProcessing(Item flower, Item output1, int count1, @Nullable Identifier output2, float chance2, @Nullable Identifier output3, float chance3) {
        addFlowerProcessing(flower, output1, count1, output2, 1, chance2, output3, 1, chance3);
    }

    /**
     * Adds processing for a small flower.
     *
     * @param flower  The flower to be processed.
     * @param output1 The primary output. Should ideally be a {@link DyeItem}.
     * @param count1  The count of the primary output. Used in Create milling. Some mods may use different, fixed amounts.
     * @param output2 The id of the secondary output. Used in Create milling. Can be null. If null, no recipes involving this item will be generated.
     * @param count2  The count of the secondary output. Used in Create milling.
     * @param chance2 The chance that the secondary output will actually appear. Used in Create milling.
     */
    default void addFlowerProcessing(Item flower, Item output1, int count1, @Nullable Identifier output2, int count2, float chance2) {
        addFlowerProcessing(flower, output1, count1, output2, count2, chance2, null, 0, 0);
    }

    /**
     * Adds processing for a small flower.
     *
     * @param flower  The flower to be processed.
     * @param output1 The primary output. Should ideally be a {@link DyeItem}.
     * @param count1  The count of the primary output. Used in Create milling. Some mods may use different, fixed amounts.
     * @param output2 The id of the secondary output. Used in Create milling. Can be null. If null, no recipes involving this item will be generated.
     * @param chance2 The chance that the secondary output will actually appear. Used in Create milling.
     */
    default void addFlowerProcessing(Item flower, Item output1, int count1, @Nullable Identifier output2, float chance2) {
        addFlowerProcessing(flower, output1, count1, output2, 1, chance2);
    }

    /**
     * Adds processing for a small flower.
     *
     * @param flower  The flower to be processed.
     * @param output1 The primary output. Should ideally be a {@link DyeItem}.
     * @param count1  The count of the primary output. Used in Create milling. Some mods may use different, fixed amounts.
     */
    default void addFlowerProcessing(Item flower, Item output1, int count1) {
        addFlowerProcessing(flower, output1, count1, null, 0, 0, null, 0, 0);
    }

    /**
     * Adds processing for a tall flower.
     *
     * @param flower  The flower to be processed.
     * @param output1 The primary output. Should ideally be a {@link DyeItem}.
     * @param count1  The count of the primary output. Used in Create milling. Some mods may use different, fixed amounts.
     * @param output2 The id of the secondary output. Used in Create milling. Can be null. If null, no recipes involving this item will be generated.
     * @param count2  The count of the secondary output. Used in Create milling.
     * @param chance2 The chance that the secondary output will actually appear. Used in Create milling.
     * @param output3 The id of the tertiary output. Used in Create milling. Can be null. If null, no recipes involving this item will be generated.
     * @param count3  The count of the tertiary output. Used in Create milling.
     * @param chance3 The chance that the tertiary output will actually appear. Used in Create milling.
     */
    default void addTallFlowerProcessing(Item flower, Item output1, int count1, @Nullable Identifier output2, int count2, float chance2, @Nullable Identifier output3, int count3, float chance3) {}

    /**
     * Adds processing for a tall flower.
     *
     * @param flower  The flower to be processed.
     * @param output1 The primary output. Should ideally be a {@link DyeItem}.
     * @param count1  The count of the primary output. Used in Create milling. Some mods may use different, fixed amounts.
     * @param output2 The id of the secondary output. Used in Create milling. Can be null. If null, no recipes involving this item will be generated.
     * @param chance2 The chance that the secondary output will actually appear. Used in Create milling.
     * @param output3 The id of the tertiary output. Used in Create milling. Can be null. If null, no recipes involving this item will be generated.
     * @param chance3 The chance that the tertiary output will actually appear. Used in Create milling.
     */
    default void addTallFlowerProcessing(Item flower, Item output1, int count1, @Nullable Identifier output2, float chance2, @Nullable Identifier output3, float chance3) {
        addTallFlowerProcessing(flower, output1, count1, output2, 1, chance2, output3, 1, chance3);
    }

    /**
     * Adds processing for a tall flower.
     *
     * @param flower  The flower to be processed.
     * @param output1 The primary output. Should ideally be a {@link DyeItem}.
     * @param count1  The count of the primary output. Used in Create milling. Some mods may use different, fixed amounts.
     * @param output2 The id of the secondary output. Used in Create milling. Can be null. If null, no recipes involving this item will be generated.
     * @param count2  The count of the secondary output. Used in Create milling.
     * @param chance2 The chance that the secondary output will actually appear. Used in Create milling.
     */
    default void addTallFlowerProcessing(Item flower, Item output1, int count1, @Nullable Identifier output2, int count2, float chance2) {
        addTallFlowerProcessing(flower, output1, count1, output2, count2, chance2, null, 0, 0);
    }

    /**
     * Adds processing for a tall flower.
     *
     * @param flower  The flower to be processed.
     * @param output1 The primary output. Should ideally be a {@link DyeItem}.
     * @param count1  The count of the primary output. Used in Create milling. Some mods may use different, fixed amounts.
     * @param output2 The id of the secondary output. Used in Create milling. Can be null. If null, no recipes involving this item will be generated.
     * @param chance2 The chance that the secondary output will actually appear. Used in Create milling.
     */
    default void addTallFlowerProcessing(Item flower, Item output1, int count1, @Nullable Identifier output2, float chance2) {
        addTallFlowerProcessing(flower, output1, count1, output2, 1, chance2);
    }

    /**
     * Adds processing for a tall flower.
     *
     * @param flower  The flower to be processed.
     * @param output1 The primary output. Should ideally be a {@link DyeItem}.
     * @param count1  The count of the primary output. Used in Create milling. Some mods may use different, fixed amounts.
     */
    default void addTallFlowerProcessing(Item flower, Item output1, int count1) {
        addTallFlowerProcessing(flower, output1, count1, null, 0, 0, null, 0, 0);
    }

    /**
     * Adds processing for a mushroom.
     *
     * @param mushroom      The mushroom to be processed.
     * @param mushroomBlock The mushroom block to be processed. Can be null. If null, no recipes involving this item will be generated.
     */
    default void addMushroomProcessing(Item mushroom, @Nullable Item mushroomBlock) {}

    /**
     * Adds processing for a mushroom.
     *
     * @param mushroom The mushroom to be processed.
     */
    default void addMushroomProcessing(Item mushroom) {
        addMushroomProcessing(mushroom, null);
    }

    /**
     * Adds processing for a fungus and roots type.
     *
     * @param fungus    The fungus to be processed.
     * @param roots     The roots to be processed.
     * @param nylium    The id of the nylium item associated with the fungus.
     * @param stem      The id of the stem item associated with the fungus.
     * @param wartBlock The id of the wart block item associated with the fungus.
     */
    default void addFungusAndRootsProcessing(Item fungus, Item roots, Identifier nylium, Identifier stem, Identifier wartBlock) {}

    /**
     * Adds processing for a wooden block family.
     *
     * @param family    The wooden block family to be processed.
     * @param boat      The boat item associated with the wooden block family.
     * @param chestBoat The chest boat item associated with the wooden block family.
     * @param logs      The logs tag associated with the wooden block family.
     */
    default void addWoodenProcessing(BlockFamily family, @Nullable Item boat, @Nullable Item chestBoat, @Nullable TagKey<Item> logs) {}

    /**
     * Adds processing for a wooden block family.
     *
     * @param family The wooden block family to be processed.
     * @param logs   The logs tag associated with the wooden block family.
     */
    default void addWoodenProcessing(BlockFamily family, @Nullable TagKey<Item> logs) {
        addWoodenProcessing(family, null, null, logs);
    }

    /**
     * Adds processing for a wooden block family.
     *
     * @param family The wooden block family to be processed.
     */
    default void addWoodenProcessing(BlockFamily family) {
        addWoodenProcessing(family, null);
    }

    /**
     * Adds processing for wooden logs.
     *
     * @param log          The log item to process.
     * @param wood         The wood item to process.
     * @param strippedLog  The stripped log item to process.
     * @param strippedWood The stripped wood item to process.
     * @param planks       The id of the planks item associated with the wooden logs.
     * @param leaves       The leaves item associated with the wooden block family. Can be null. If null, no recipes involving this item will be generated.
     * @param sapling      The sapling item associated with the wooden block family. Can be null. If null, no recipes involving this item will be generated.
     */
    default void addLogsProcessing(Item log, Item wood, Item strippedLog, Item strippedWood, Identifier planks, @Nullable Item leaves, @Nullable Item sapling) {}

    /**
     * Adds processing for wooden logs.
     *
     * @param log          The log item to process.
     * @param wood         The wood item to process.
     * @param strippedLog  The stripped log item to process.
     * @param strippedWood The stripped wood item to process.
     * @param planks       The id of the planks item associated with the wooden logs.
     */
    default void addLogsProcessing(Item log, Item wood, Item strippedLog, Item strippedWood, Identifier planks) {
        addLogsProcessing(log, wood, strippedLog, strippedWood, planks, null, null);
    }

    @FunctionalInterface
    interface Factory {
        ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries);
    }
}
