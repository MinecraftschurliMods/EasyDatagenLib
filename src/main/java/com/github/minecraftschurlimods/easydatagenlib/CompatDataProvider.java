package com.github.minecraftschurlimods.easydatagenlib;

import com.github.minecraftschurlimods.easydatagenlib.api.AbstractDataProvider;
import com.github.minecraftschurlimods.easydatagenlib.mods.ArsNouveauDataProvider;
import com.github.minecraftschurlimods.easydatagenlib.mods.BotaniaDataProvider;
import com.github.minecraftschurlimods.easydatagenlib.mods.BotanyPotsDataProvider;
import com.github.minecraftschurlimods.easydatagenlib.mods.CorailWoodcutterDataProvider;
import com.github.minecraftschurlimods.easydatagenlib.mods.CreateDataProvider;
import com.github.minecraftschurlimods.easydatagenlib.mods.ElementalcraftDataProvider;
import com.github.minecraftschurlimods.easydatagenlib.mods.FarmersDelightDataProvider;
import com.github.minecraftschurlimods.easydatagenlib.mods.ImmersiveEngineeringDataProvider;
import com.github.minecraftschurlimods.easydatagenlib.mods.IntegratedDynamicsDataProvider;
import com.github.minecraftschurlimods.easydatagenlib.mods.MekanismDataProvider;
import com.github.minecraftschurlimods.easydatagenlib.mods.OccultismDataProvider;
import com.github.minecraftschurlimods.easydatagenlib.mods.ThermalDataProvider;
import com.github.minecraftschurlimods.easydatagenlib.mods.TwilightForestDataProvider;
import com.github.minecraftschurlimods.easydatagenlib.util.PotentiallyAbsentIngredient;
import com.github.minecraftschurlimods.easydatagenlib.util.botanypots.DisplayState;
import com.github.minecraftschurlimods.easydatagenlib.util.farmersdelight.ToolActionIngredient;
import com.github.minecraftschurlimods.easydatagenlib.util.immersiveengineering.ClocheRenderType;
import com.github.minecraftschurlimods.easydatagenlib.util.mekanism.Chemical;
import com.github.minecraftschurlimods.easydatagenlib.util.mekanism.Pigment;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.common.crafting.conditions.TagEmptyCondition;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Extend this class and override {@link CompatDataProvider#generate()} to add your own datagen entries.
 */
@SuppressWarnings({"unused", "DuplicatedCode", "SameParameterValue"})
public abstract class CompatDataProvider {
    //region CORE
    private final List<AbstractDataProvider<?>> SERVER_PROVIDERS = new ArrayList<>();
    private final List<AbstractDataProvider<?>> CLIENT_PROVIDERS = new ArrayList<>();
    private final List<AbstractDataProvider<?>> COMMON_PROVIDERS = new ArrayList<>();
    protected final ArsNouveauDataProvider.Crushing ARS_NOUVEAU_CRUSHING;
    protected final ArsNouveauDataProvider.Glyph ARS_NOUVEAU_GLYPH;
    protected final ArsNouveauDataProvider.Imbueing ARS_NOUVEAU_IMBUEING;
    protected final BotaniaDataProvider.Infusing BOTANIA_MANA_INFUSION;
    protected final BotanyPotsDataProvider.Crop BOTANY_POTS_CROP;
    protected final BotanyPotsDataProvider.Soil BOTANY_POTS_SOIL;
    protected final CorailWoodcutterDataProvider.Sawing CORAIL_WOODCUTTER_SAWING;
    protected final CreateDataProvider.Compacting CREATE_COMPACTING;
    protected final CreateDataProvider.Crushing CREATE_CRUSHING;
    protected final CreateDataProvider.Cutting CREATE_CUTTING;
    protected final CreateDataProvider.Deploying CREATE_DEPLOYING;
    protected final CreateDataProvider.Emptying CREATE_EMPTYING;
    protected final CreateDataProvider.Filling CREATE_FILLING;
    protected final CreateDataProvider.Haunting CREATE_HAUNTING;
    protected final CreateDataProvider.ItemApplication CREATE_ITEM_APPLICATION;
    protected final CreateDataProvider.MechanicalCrafting CREATE_MECHANICAL_CRAFTING;
    protected final CreateDataProvider.Milling CREATE_MILLING;
    protected final CreateDataProvider.Mixing CREATE_MIXING;
    protected final CreateDataProvider.Pressing CREATE_PRESSING;
    protected final CreateDataProvider.SandpaperPolishing CREATE_SANDPAPER_POLISHING;
    protected final CreateDataProvider.SequencedAssembly  CREATE_SEQUENCED_ASSEMBLY;
    protected final CreateDataProvider.Splashing CREATE_SPLASHING;
    protected final ElementalcraftDataProvider.Grinding ELEMENTALCRAFT_GRINDING;
    protected final ElementalcraftDataProvider.Sawing ELEMENTALCRAFT_SAWING;
    protected final FarmersDelightDataProvider.Cooking FARMERS_DELIGHT_COOKING;
    protected final FarmersDelightDataProvider.Cutting FARMERS_DELIGHT_CUTTING;
    protected final ImmersiveEngineeringDataProvider.ArcFurnace IMMERSIVE_ENGINEERING_ARC_FURNACE;
    protected final ImmersiveEngineeringDataProvider.Cloche IMMERSIVE_ENGINEERING_CLOCHE;
    protected final ImmersiveEngineeringDataProvider.Crusher IMMERSIVE_ENGINEERING_CRUSHER;
    protected final ImmersiveEngineeringDataProvider.Sawmill IMMERSIVE_ENGINEERING_SAWMILL;
    protected final IntegratedDynamicsDataProvider.MechanicalSqueezing INTEGRATED_DYNAMICS_MECHANICAL_SQUEEZING;
    protected final IntegratedDynamicsDataProvider.Squeezing INTEGRATED_DYNAMICS_SQUEEZING;
    protected final MekanismDataProvider.Activating MEKANISM_ACTIVATING;
    protected final MekanismDataProvider.Centrifuging MEKANISM_CENTRIFUGING;
    protected final MekanismDataProvider.Combining MEKANISM_COMBINING;
    protected final MekanismDataProvider.Crushing MEKANISM_CRUSHING;
    protected final MekanismDataProvider.Enriching MEKANISM_ENRICHING;
    protected final MekanismDataProvider.GasConversion MEKANISM_GAS_CONVERSION;
    protected final MekanismDataProvider.InfusionConversion MEKANISM_INFUSION_CONVERSION;
    protected final MekanismDataProvider.Oxidizing MEKANISM_OXIDIZING;
    protected final MekanismDataProvider.PigmentExtracting MEKANISM_PIGMENT_EXTRACTING;
    protected final MekanismDataProvider.Sawing MEKANISM_SAWING;
    protected final MekanismDataProvider.Smelting MEKANISM_SMELTING;
    protected final OccultismDataProvider.Crushing OCCULTISM_CRUSHING;
    protected final ThermalDataProvider.Bottling THERMAL_BOTTLING;
    protected final ThermalDataProvider.Brewing THERMAL_BREWING;
    protected final ThermalDataProvider.Centrifuging THERMAL_CENTRIFUGING;
    protected final ThermalDataProvider.Chilling THERMAL_CHILLING;
    protected final ThermalDataProvider.Crucible THERMAL_CRUCIBLE;
    protected final ThermalDataProvider.Crystallizing THERMAL_CRYSTALLIZING;
    protected final ThermalDataProvider.Furnace THERMAL_FURNACE;
    protected final ThermalDataProvider.Insolating THERMAL_INSOLATING;
    protected final ThermalDataProvider.Pressing THERMAL_PRESSING;
    protected final ThermalDataProvider.PulverizerRecycling THERMAL_PULVERIZER_RECYCLING;
    protected final ThermalDataProvider.Pulverizing THERMAL_PULVERIZING;
    protected final ThermalDataProvider.Pyrolyzing THERMAL_PYROLYZING;
    protected final ThermalDataProvider.Refining THERMAL_REFINING;
    protected final ThermalDataProvider.Sawing THERMAL_SAWING;
    protected final ThermalDataProvider.SmelterRecycling THERMAL_SMELTER_RECYCLING;
    protected final ThermalDataProvider.Smelting THERMAL_SMELTING;
    protected final TwilightForestDataProvider.Crumbling TWILIGHT_FOREST_CRUMBLING;
    protected final TwilightForestDataProvider.Transforming TWILIGHT_FOREST_TRANSFORMING;

    /**
     * Constructs a new {@link CompatDataProvider}. Initializes the providers and calls {@link CompatDataProvider#generate()}.
     *
     * @param namespace The namespace to use. In most cases, this is your own mod id.
     * @param generator The {@link DataGenerator} to use. Get this via {@link GatherDataEvent#getGenerator()}.
     * @param runServer Whether to generate data files. Get this via {@link GatherDataEvent#includeServer()}.
     * @param runClient Whether to generate asset files. Get this via {@link GatherDataEvent#includeClient()}.
     */
    protected CompatDataProvider(String namespace, DataGenerator generator, boolean runServer, boolean runClient) {
        ARS_NOUVEAU_CRUSHING = addServer(new ArsNouveauDataProvider.Crushing(namespace, generator));
        ARS_NOUVEAU_GLYPH = addServer(new ArsNouveauDataProvider.Glyph(namespace, generator));
        ARS_NOUVEAU_IMBUEING = addServer(new ArsNouveauDataProvider.Imbueing(namespace, generator));
        BOTANIA_MANA_INFUSION = addServer(new BotaniaDataProvider.Infusing(namespace, generator));
        BOTANY_POTS_CROP = addServer(new BotanyPotsDataProvider.Crop(namespace, generator));
        BOTANY_POTS_SOIL = addServer(new BotanyPotsDataProvider.Soil(namespace, generator));
        CORAIL_WOODCUTTER_SAWING = addServer(new CorailWoodcutterDataProvider.Sawing(namespace, generator));
        CREATE_COMPACTING = addServer(new CreateDataProvider.Compacting(namespace, generator));
        CREATE_CRUSHING = addServer(new CreateDataProvider.Crushing(namespace, generator));
        CREATE_CUTTING = addServer(new CreateDataProvider.Cutting(namespace, generator));
        CREATE_DEPLOYING = addServer(new CreateDataProvider.Deploying(namespace, generator));
        CREATE_EMPTYING = addServer(new CreateDataProvider.Emptying(namespace, generator));
        CREATE_FILLING = addServer(new CreateDataProvider.Filling(namespace, generator));
        CREATE_HAUNTING = addServer(new CreateDataProvider.Haunting(namespace, generator));
        CREATE_ITEM_APPLICATION = addServer(new CreateDataProvider.ItemApplication(namespace, generator));
        CREATE_MECHANICAL_CRAFTING = addServer(new CreateDataProvider.MechanicalCrafting(namespace, generator));
        CREATE_MILLING = addServer(new CreateDataProvider.Milling(namespace, generator));
        CREATE_MIXING = addServer(new CreateDataProvider.Mixing(namespace, generator));
        CREATE_PRESSING = addServer(new CreateDataProvider.Pressing(namespace, generator));
        CREATE_SANDPAPER_POLISHING = addServer(new CreateDataProvider.SandpaperPolishing(namespace, generator));
        CREATE_SEQUENCED_ASSEMBLY  = addServer(new CreateDataProvider.SequencedAssembly(namespace, generator));
        CREATE_SPLASHING = addServer(new CreateDataProvider.Splashing(namespace, generator));
        ELEMENTALCRAFT_GRINDING = addServer(new ElementalcraftDataProvider.Grinding(namespace, generator));
        ELEMENTALCRAFT_SAWING = addServer(new ElementalcraftDataProvider.Sawing(namespace, generator));
        FARMERS_DELIGHT_COOKING = addServer(new FarmersDelightDataProvider.Cooking(namespace, generator));
        FARMERS_DELIGHT_CUTTING = addServer(new FarmersDelightDataProvider.Cutting(namespace, generator));
        IMMERSIVE_ENGINEERING_ARC_FURNACE = addServer(new ImmersiveEngineeringDataProvider.ArcFurnace(namespace, generator));
        IMMERSIVE_ENGINEERING_CLOCHE = addServer(new ImmersiveEngineeringDataProvider.Cloche(namespace, generator));
        IMMERSIVE_ENGINEERING_CRUSHER = addServer(new ImmersiveEngineeringDataProvider.Crusher(namespace, generator));
        IMMERSIVE_ENGINEERING_SAWMILL = addServer(new ImmersiveEngineeringDataProvider.Sawmill(namespace, generator));
        INTEGRATED_DYNAMICS_MECHANICAL_SQUEEZING = addServer(new IntegratedDynamicsDataProvider.MechanicalSqueezing(namespace, generator));
        INTEGRATED_DYNAMICS_SQUEEZING = addServer(new IntegratedDynamicsDataProvider.Squeezing(namespace, generator));
        MEKANISM_ACTIVATING = addServer(new MekanismDataProvider.Activating(namespace, generator));
        MEKANISM_CENTRIFUGING = addServer(new MekanismDataProvider.Centrifuging(namespace, generator));
        MEKANISM_COMBINING = addServer(new MekanismDataProvider.Combining(namespace, generator));
        MEKANISM_CRUSHING = addServer(new MekanismDataProvider.Crushing(namespace, generator));
        MEKANISM_ENRICHING = addServer(new MekanismDataProvider.Enriching(namespace, generator));
        MEKANISM_GAS_CONVERSION = addServer(new MekanismDataProvider.GasConversion(namespace, generator));
        MEKANISM_INFUSION_CONVERSION = addServer(new MekanismDataProvider.InfusionConversion(namespace, generator));
        MEKANISM_OXIDIZING = addServer(new MekanismDataProvider.Oxidizing(namespace, generator));
        MEKANISM_PIGMENT_EXTRACTING = addServer(new MekanismDataProvider.PigmentExtracting(namespace, generator));
        MEKANISM_SAWING = addServer(new MekanismDataProvider.Sawing(namespace, generator));
        MEKANISM_SMELTING = addServer(new MekanismDataProvider.Smelting(namespace, generator));
        OCCULTISM_CRUSHING = addServer(new OccultismDataProvider.Crushing(namespace, generator));
        THERMAL_BOTTLING = addServer(new ThermalDataProvider.Bottling(namespace, generator));
        THERMAL_BREWING = addServer(new ThermalDataProvider.Brewing(namespace, generator));
        THERMAL_CENTRIFUGING = addServer(new ThermalDataProvider.Centrifuging(namespace, generator));
        THERMAL_CHILLING = addServer(new ThermalDataProvider.Chilling(namespace, generator));
        THERMAL_CRUCIBLE = addServer(new ThermalDataProvider.Crucible(namespace, generator));
        THERMAL_CRYSTALLIZING = addServer(new ThermalDataProvider.Crystallizing(namespace, generator));
        THERMAL_FURNACE = addServer(new ThermalDataProvider.Furnace(namespace, generator));
        THERMAL_INSOLATING = addServer(new ThermalDataProvider.Insolating(namespace, generator));
        THERMAL_PRESSING = addServer(new ThermalDataProvider.Pressing(namespace, generator));
        THERMAL_PULVERIZER_RECYCLING = addServer(new ThermalDataProvider.PulverizerRecycling(namespace, generator));
        THERMAL_PULVERIZING = addServer(new ThermalDataProvider.Pulverizing(namespace, generator));
        THERMAL_PYROLYZING = addServer(new ThermalDataProvider.Pyrolyzing(namespace, generator));
        THERMAL_REFINING = addServer(new ThermalDataProvider.Refining(namespace, generator));
        THERMAL_SAWING = addServer(new ThermalDataProvider.Sawing(namespace, generator));
        THERMAL_SMELTER_RECYCLING = addServer(new ThermalDataProvider.SmelterRecycling(namespace, generator));
        THERMAL_SMELTING = addServer(new ThermalDataProvider.Smelting(namespace, generator));
        TWILIGHT_FOREST_CRUMBLING = addServer(new TwilightForestDataProvider.Crumbling(namespace, generator));
        TWILIGHT_FOREST_TRANSFORMING = addServer(new TwilightForestDataProvider.Transforming(namespace, generator));
        for (AbstractDataProvider<?> provider : SERVER_PROVIDERS) {
            generator.addProvider(runServer, provider);
        }
        for (AbstractDataProvider<?> provider : CLIENT_PROVIDERS) {
            generator.addProvider(runClient, provider);
        }
        for (AbstractDataProvider<?> provider : COMMON_PROVIDERS) {
            generator.addProvider(runServer || runClient, provider);
        }
        generate();
    }

    /**
     * Constructs a new {@link CompatDataProvider}. Initializes the providers and calls {@link CompatDataProvider#generate()}.
     *
     * <p>Unlike {@link CompatDataProvider#CompatDataProvider(String, DataGenerator, boolean, boolean)}, this variant extracts the necessary information from the given {@link GatherDataEvent}.</p>
     *
     * @param namespace The namespace to use. In most cases, this is your own mod id.
     * @param event The event object to get the values from.
     */
    protected CompatDataProvider(String namespace, GatherDataEvent event) {
        this(namespace, event.getGenerator(), event.includeServer(), event.includeClient());
    }

    /**
     * Override this to add your recipes.
     */
    protected abstract void generate();

    protected <T extends AbstractDataProvider<?>> T addServer(T provider) {
        SERVER_PROVIDERS.add(provider);
        return provider;
    }

    protected <T extends AbstractDataProvider<?>> T addClient(T provider) {
        CLIENT_PROVIDERS.add(provider);
        return provider;
    }

    protected <T extends AbstractDataProvider<?>> T addCommon(T provider) {
        COMMON_PROVIDERS.add(provider);
        return provider;
    }
    //endregion

    //region HELPER
    protected static final ResourceLocation AIR                  = new ResourceLocation("minecraft",      "air");
    protected static final ResourceLocation ALCHEMY_CATALYST     = new ResourceLocation("botania",        "alchemy_catalyst");
    protected static final ResourceLocation BIO_FUEL             = new ResourceLocation("mekanism",       "bio_fuel");
    protected static final ResourceLocation CHEST                = new ResourceLocation("minecraft",      "chest");
    protected static final ResourceLocation CONJURATION_CATALYST = new ResourceLocation("botania",        "conjuration_catalyst");
    protected static final ResourceLocation EXPERIENCE_NUGGET    = new ResourceLocation("create",         "experience_nugget");
    protected static final ResourceLocation GRAVEL               = new ResourceLocation("minecraft",      "gravel");
    protected static final ResourceLocation RICH_SLAG            = new ResourceLocation("thermal",        "rich_slag");
    protected static final ResourceLocation MEKANISM_SAWDUST     = new ResourceLocation("mekanism",       "sawdust");
    protected static final ResourceLocation SHROOMLIGHT          = new ResourceLocation("minecraft",      "shroomlight");
    protected static final ResourceLocation STICK                = new ResourceLocation("minecraft",      "stick");
    protected static final ResourceLocation THERMAL_SAWDUST      = new ResourceLocation("thermal",        "sawdust");
    protected static final ResourceLocation TREE_BARK            = new ResourceLocation("farmersdelight", "tree_bark");
    protected static final Ingredient AXE_DIG               = new ToolActionIngredient(ToolActions.AXE_DIG);
    protected static final Ingredient AXE_STRIP             = new ToolActionIngredient(ToolActions.AXE_STRIP);
    protected static final Ingredient COBBLESTONE           = Ingredient.of(Tags.Items.COBBLESTONE_NORMAL);
    protected static final Ingredient DEEPSLATE_COBBLESTONE = Ingredient.of(Tags.Items.COBBLESTONE_DEEPSLATE);
    protected static final Ingredient KNIVES                = Ingredient.of(TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation("forge", "tools/knives")));
    protected static final Ingredient MUSHROOM_SOIL         = Ingredient.of(Items.MYCELIUM, Items.PODZOL);
    protected static final Ingredient PRESS_PACKING_3x3_DIE = PotentiallyAbsentIngredient.of(new ResourceLocation("thermal", "press_packing_3x3_die"));
    protected static final Ingredient PRESS_UNPACKING_DIE   = PotentiallyAbsentIngredient.of(new ResourceLocation("thermal", "press_unpacking_die"));
    protected static final Ingredient SLAG                  = Ingredient.of(TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation("forge", "slag")));
    protected static final Ingredient WOOD_DUST             = Ingredient.of(TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation("forge", "dusts/wood")));

    /**
     * Shortcut to get a block's registry name.
     *
     * @param block The block to get the registry name for.
     * @return The registry name of the given block.
     */
    @SuppressWarnings("ConstantConditions")
    protected static ResourceLocation blockId(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    /**
     * Shortcut to get an item's registry name.
     *
     * @param item The item to get the registry name for.
     * @return The registry name of the given item.
     */
    @SuppressWarnings("ConstantConditions")
    protected static ResourceLocation itemId(Item item) {
        return ForgeRegistries.ITEMS.getKey(item);
    }

    /**
     * Shortcut to get a fluid's registry name.
     *
     * @param fluid The fluid to get the registry name for.
     * @return The registry name of the given fluid.
     */
    @SuppressWarnings("ConstantConditions")
    protected static ResourceLocation fluidId(Fluid fluid) {
        return ForgeRegistries.FLUIDS.getKey(fluid);
    }

    /**
     * Transforms a resource location into a string ready to be used in recipe names.
     *
     * @param rl The resource location to transform.
     * @return A string representation of the resource location.
     */
    protected static String toName(ResourceLocation rl) {
        return rl.getPath().replace('/', '_');
    }

    /**
     * Transforms a tag into a string ready to be used in recipe names.
     *
     * @param tag The tag to transform.
     * @return A string representation of the tag name.
     */
    protected static String toName(TagKey<?> tag) {
        return toName(tag.location());
    }

    /**
     * Transforms a block into a string ready to be used in recipe names.
     *
     * @param block The block to transform.
     * @return A string representation of the block name.
     */
    protected static String toName(Block block) {
        return toName(blockId(block));
    }

    /**
     * Transforms an item into a string ready to be used in recipe names.
     *
     * @param item The item to transform.
     * @return A string representation of the item name.
     */
    protected static String toName(Item item) {
        return toName(itemId(item));
    }

    /**
     * Transforms a fluid into a string ready to be used in recipe names.
     *
     * @param fluid The fluid to transform.
     * @return A string representation of the fluid name.
     */
    protected static String toName(Fluid fluid) {
        return toName(fluidId(fluid));
    }

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
    protected void addGemOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item gem, @Nullable Item block, @Nullable Item dust) {
        if (ore instanceof BlockItem bi) {
            BOTANY_POTS_SOIL.add(BOTANY_POTS_SOIL.builder(toName(ore), Ingredient.of(ore), new DisplayState.Simple(bi.getBlock().defaultBlockState()))
                    .addCategory("stone")
                    .addCategory("mushroom")
                    .addCategory("ore")
                    .addCategory(toName(ore)));
        }
        if (deepslateOre instanceof BlockItem bi) {
            BOTANY_POTS_SOIL.add(BOTANY_POTS_SOIL.builder(toName(deepslateOre), Ingredient.of(deepslateOre), new DisplayState.Simple(bi.getBlock().defaultBlockState()))
                    .addCategory("stone")
                    .addCategory("mushroom")
                    .addCategory("ore")
                    .addCategory(toName(ore))
                    .addCategory("deepslate")
                    .addCategory(toName(deepslateOre)));
        }
        CREATE_CRUSHING.add(CREATE_CRUSHING.builder(itemId(ore).getPath(), 250)
                .addInput(Ingredient.of(ore))
                .addOutput(gem)
                .addOutput(gem, 0.75f)
                .addOutput(EXPERIENCE_NUGGET, 0.75f)
                .addOutput(Items.COBBLESTONE, 0.125f));
        CREATE_CRUSHING.add(CREATE_CRUSHING.builder(itemId(deepslateOre).getPath(), 350)
                .addInput(Ingredient.of(deepslateOre))
                .addOutput(gem, 2)
                .addOutput(gem, 0.25f)
                .addOutput(EXPERIENCE_NUGGET, 0.75f)
                .addOutput(Items.COBBLED_DEEPSLATE, 0.125f));
        if (dust != null) {
            MEKANISM_COMBINING.add(MEKANISM_COMBINING.builder(toName(gem) + "_to_deepslate_ore", Ingredient.of(dust), 5, DEEPSLATE_COBBLESTONE, deepslateOre));
            MEKANISM_COMBINING.add(MEKANISM_COMBINING.builder(toName(gem) + "_to_ore", Ingredient.of(dust), 5, COBBLESTONE, ore));
            MEKANISM_CRUSHING.add(MEKANISM_CRUSHING.builder(toName(gem) + "_to_dust", Ingredient.of(gem), dust));
            MEKANISM_ENRICHING.add(MEKANISM_ENRICHING.builder(toName(gem) + "_from_dust", Ingredient.of(dust), gem));
        }
        MEKANISM_ENRICHING.add(MEKANISM_ENRICHING.builder(toName(gem) + "_from_ore", Ingredient.of(oreTag), gem, 2));
        if (block != null) {
            THERMAL_PRESSING.add(THERMAL_PRESSING.builder(toName(gem) + "_packing")
                    .setEnergy(400)
                    .addInput(Ingredient.of(gem), 9)
                    .addInput(PRESS_PACKING_3x3_DIE)
                    .addOutputItem(block));
            THERMAL_PRESSING.add(THERMAL_PRESSING.builder(toName(gem) + "_unpacking")
                    .setEnergy(400)
                    .addInput(Ingredient.of(block))
                    .addInput(PRESS_UNPACKING_DIE)
                    .addOutputItem(gem, 9));
        }
        if (dust != null) {
            THERMAL_PULVERIZING.add(THERMAL_PULVERIZING.builder(toName(gem))
                    .setEnergyModifier(0.5f)
                    .addInput(Ingredient.of(gem))
                    .addOutputItem(dust));
            THERMAL_PULVERIZING.add(THERMAL_PULVERIZING.builder(toName(oreTag), 0.2f)
                    .addInput(Ingredient.of(oreTag))
                    .addOutputItem(dust, 2.5f)
                    .addOutputItem(GRAVEL, 0.2f));
        }
        THERMAL_SMELTING.add(THERMAL_SMELTING.builder(toName(oreTag), 0.2f)
                .addInput(Ingredient.of(oreTag))
                .addOutputItem(gem, 1.5f)
                .addOutputItem(RICH_SLAG, 0.15f));
    }

    /**
     * Adds processing for a gem ore, such as diamonds or emeralds. This assumes that the ores drop one item by default, and thus does not support ores that drop multiple items, such as lapis.
     *
     * @param ore          The ore to be processed.
     * @param deepslateOre The deepslate ore to be processed.
     * @param oreTag       The ore tag to be processed.
     * @param gem          The gem item.
     * @param block        The gem block item. Can be null. If null, no recipes involving this item will be generated.
     */
    protected void addGemOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item gem, @Nullable Item block) {
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
    protected void addGemOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item gem) {
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
    protected void addMetalOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item rawOre, TagKey<Item> rawOreTag, @Nullable Item rawOreBlock, @Nullable TagKey<Item> rawOreBlockTag, Item ingot, TagKey<Item> ingotTag, @Nullable Item ingotBlock, @Nullable TagKey<Item> ingotBlockTag, @Nullable Item nugget, @Nullable TagKey<Item> nuggetTag, @Nullable Item dust, @Nullable TagKey<Item> dustTag, @Nullable ResourceLocation crushedOre, @Nullable ResourceLocation secondaryIngot, @Nullable ResourceLocation secondaryDust, @Nullable TagKey<Item> secondaryDustTag) {
        if (ore instanceof BlockItem bi) {
            BOTANY_POTS_SOIL.add(BOTANY_POTS_SOIL.builder(toName(ore), Ingredient.of(ore), new DisplayState.Simple(bi.getBlock().defaultBlockState()))
                    .addCategory("stone")
                    .addCategory("mushroom")
                    .addCategory("ore")
                    .addCategory(toName(ore)));
        }
        if (deepslateOre instanceof BlockItem bi) {
            BOTANY_POTS_SOIL.add(BOTANY_POTS_SOIL.builder(toName(deepslateOre), Ingredient.of(deepslateOre), new DisplayState.Simple(bi.getBlock().defaultBlockState()))
                    .addCategory("stone")
                    .addCategory("mushroom")
                    .addCategory("ore")
                    .addCategory(toName(ore))
                    .addCategory("deepslate")
                    .addCategory(toName(deepslateOre)));
        }
        if (crushedOre != null) {
            CREATE_CRUSHING.add(CREATE_CRUSHING.builder(toName(ore), 250)
                    .addInput(Ingredient.of(ore))
                    .addOutput(crushedOre)
                    .addOutput(crushedOre, 0.75f)
                    .addOutput(EXPERIENCE_NUGGET, 0.75f)
                    .addOutput(Items.COBBLESTONE, 0.125f));
            CREATE_CRUSHING.add(CREATE_CRUSHING.builder(toName(deepslateOre), 350)
                    .addInput(Ingredient.of(deepslateOre))
                    .addOutput(crushedOre, 2)
                    .addOutput(crushedOre, 0.25f)
                    .addOutput(EXPERIENCE_NUGGET, 0.75f)
                    .addOutput(Items.COBBLED_DEEPSLATE, 0.125f));
            CREATE_CRUSHING.add(CREATE_CRUSHING.builder(toName(rawOre), 400)
                    .addInput(Ingredient.of(rawOreTag))
                    .addOutput(crushedOre)
                    .addOutput(EXPERIENCE_NUGGET, 0.75f)
                    .addCondition(new NotCondition(new TagEmptyCondition(rawOreTag.location()))));
        }
        IMMERSIVE_ENGINEERING_ARC_FURNACE.add(IMMERSIVE_ENGINEERING_ARC_FURNACE.builder(toName(oreTag), 200, 102400, Ingredient.of(oreTag))
                .addOutput(Ingredient.of(ingotTag), 2)
                .setSlag(SLAG)
                .addCondition(new NotCondition(new TagEmptyCondition(oreTag.location())))
                .addCondition(new NotCondition(new TagEmptyCondition(ingotTag.location()))));
        IMMERSIVE_ENGINEERING_ARC_FURNACE.add(IMMERSIVE_ENGINEERING_ARC_FURNACE.builder(toName(rawOreTag), 100, 25600, Ingredient.of(rawOreTag))
                .addOutput(Ingredient.of(ingotTag))
                .addSecondaryOutput(Ingredient.of(ingotTag), 0.5f)
                .addCondition(new NotCondition(new TagEmptyCondition(rawOreTag.location())))
                .addCondition(new NotCondition(new TagEmptyCondition(ingotTag.location()))));
        if (dustTag != null) {
            IMMERSIVE_ENGINEERING_ARC_FURNACE.add(IMMERSIVE_ENGINEERING_ARC_FURNACE.builder(toName(dustTag), 100, 51200, Ingredient.of(dustTag))
                    .addOutput(Ingredient.of(ingotTag))
                    .addCondition(new NotCondition(new TagEmptyCondition(dustTag.location())))
                    .addCondition(new NotCondition(new TagEmptyCondition(ingotTag.location()))));
        }
        if (rawOreBlockTag != null) {
            IMMERSIVE_ENGINEERING_ARC_FURNACE.add(IMMERSIVE_ENGINEERING_ARC_FURNACE.builder(toName(rawOreBlockTag), 900, 230400, Ingredient.of(rawOreBlockTag))
                    .addOutput(Ingredient.of(ingotTag), 13)
                    .addSecondaryOutput(Ingredient.of(ingotTag), 0.5f)
                    .addCondition(new NotCondition(new TagEmptyCondition(rawOreBlockTag.location())))
                    .addCondition(new NotCondition(new TagEmptyCondition(ingotTag.location()))));
            IMMERSIVE_ENGINEERING_CRUSHER.add(IMMERSIVE_ENGINEERING_CRUSHER.builder(toName(rawOreBlockTag), 54000, Ingredient.of(rawOreBlockTag), Ingredient.of(rawOreTag), 12)
                    .addCondition(new NotCondition(new TagEmptyCondition(rawOreBlockTag.location())))
                    .addCondition(new NotCondition(new TagEmptyCondition(rawOreTag.location()))));
        }
        if (dustTag != null) {
            IMMERSIVE_ENGINEERING_CRUSHER.add(IMMERSIVE_ENGINEERING_CRUSHER.builder(toName(ingotTag), 3000, Ingredient.of(ingotTag), Ingredient.of(dustTag))
                    .addCondition(new NotCondition(new TagEmptyCondition(ingotTag.location())))
                    .addCondition(new NotCondition(new TagEmptyCondition(dustTag.location()))));
            if (secondaryDustTag == null) {
                IMMERSIVE_ENGINEERING_CRUSHER.add(IMMERSIVE_ENGINEERING_CRUSHER.builder(toName(oreTag), 6000, Ingredient.of(oreTag), Ingredient.of(dustTag), 2)
                        .addCondition(new NotCondition(new TagEmptyCondition(oreTag.location())))
                        .addCondition(new NotCondition(new TagEmptyCondition(dustTag.location()))));
            } else {
                IMMERSIVE_ENGINEERING_CRUSHER.add(IMMERSIVE_ENGINEERING_CRUSHER.builder(toName(oreTag), 6000, Ingredient.of(oreTag), Ingredient.of(dustTag), 2)
                        .addSecondary(Ingredient.of(secondaryDustTag), 0.1f)
                        .addCondition(new NotCondition(new TagEmptyCondition(oreTag.location())))
                        .addCondition(new NotCondition(new TagEmptyCondition(dustTag.location())))
                        .addCondition(new NotCondition(new TagEmptyCondition(secondaryDustTag.location()))));
                IMMERSIVE_ENGINEERING_CRUSHER.add(IMMERSIVE_ENGINEERING_CRUSHER.builder(toName(oreTag) + "_without_secondary", 6000, Ingredient.of(oreTag), Ingredient.of(dustTag), 2)
                        .addCondition(new NotCondition(new TagEmptyCondition(oreTag.location())))
                        .addCondition(new NotCondition(new TagEmptyCondition(dustTag.location())))
                        .addCondition(new TagEmptyCondition(secondaryDustTag.location())));
            }
            IMMERSIVE_ENGINEERING_CRUSHER.add(IMMERSIVE_ENGINEERING_CRUSHER.builder(toName(rawOreTag), 6000, Ingredient.of(rawOreTag), Ingredient.of(dustTag))
                    .addSecondary(Ingredient.of(dustTag), 0.33333334f)
                    .addCondition(new NotCondition(new TagEmptyCondition(rawOreTag.location())))
                    .addCondition(new NotCondition(new TagEmptyCondition(dustTag.location()))));
        }
        INTEGRATED_DYNAMICS_MECHANICAL_SQUEEZING.add(INTEGRATED_DYNAMICS_MECHANICAL_SQUEEZING.builder(toName(oreTag), Ingredient.of(oreTag), 40)
                .addItem(rawOre, 3)
                .addItem(rawOre, 0.5f)
                .addItem(rawOre, 0.5f)
                .addCondition(new NotCondition(new TagEmptyCondition(oreTag.location()))));
        INTEGRATED_DYNAMICS_SQUEEZING.add(INTEGRATED_DYNAMICS_SQUEEZING.builder(toName(oreTag), Ingredient.of(oreTag))
                .addItem(rawOre, 3)
                .addItem(rawOre, 0.5f)
                .addItem(rawOre, 0.5f)
                .addCondition(new NotCondition(new TagEmptyCondition(oreTag.location()))));
        if (dust != null) {
            MEKANISM_CRUSHING.add(MEKANISM_CRUSHING.builder(toName(dust) + "_from_ingot", Ingredient.of(ingot), dust));
            MEKANISM_ENRICHING.add(MEKANISM_ENRICHING.builder(toName(dust) + "_from_ore", Ingredient.of(oreTag), dust));
            MEKANISM_ENRICHING.add(MEKANISM_ENRICHING.builder(toName(dust) + "_from_raw", Ingredient.of(rawOre), 3, dust, 4));
            if (rawOreBlockTag != null) {
                MEKANISM_ENRICHING.add(MEKANISM_ENRICHING.builder(toName(dust) + "_from_raw_block", Ingredient.of(rawOreBlockTag), dust, 12));
            }
        }
        MEKANISM_COMBINING.add(MEKANISM_COMBINING.builder(toName(deepslateOre) + "_from_raw", Ingredient.of(rawOre), 8, DEEPSLATE_COBBLESTONE, deepslateOre));
        MEKANISM_COMBINING.add(MEKANISM_COMBINING.builder(toName(ore) + "_from_raw", Ingredient.of(rawOre), 8, COBBLESTONE, ore));
        if (dustTag != null) {
            OCCULTISM_CRUSHING.add(OCCULTISM_CRUSHING.builder(toName(dustTag), Ingredient.of(oreTag), Ingredient.of(dustTag), 2)
                    .addCondition(new NotCondition(new TagEmptyCondition(oreTag.location())))
                    .addCondition(new NotCondition(new TagEmptyCondition(dustTag.location()))));
        OCCULTISM_CRUSHING.add(OCCULTISM_CRUSHING.builder(toName(dustTag) + "_from_ingot", Ingredient.of(oreTag), Ingredient.of(ingotTag))
                .ignoreCrushingMultiplier()
                .addCondition(new NotCondition(new TagEmptyCondition(ingotTag.location())))
                .addCondition(new NotCondition(new TagEmptyCondition(dustTag.location()))));
        OCCULTISM_CRUSHING.add(OCCULTISM_CRUSHING.builder(toName(dustTag) + "_from_raw", Ingredient.of(rawOreTag), Ingredient.of(dustTag), 2)
                .addCondition(new NotCondition(new TagEmptyCondition(rawOreTag.location())))
                .addCondition(new NotCondition(new TagEmptyCondition(dustTag.location()))));
        }
        if (ingotBlock != null) {
            THERMAL_PRESSING.add(THERMAL_PRESSING.builder(toName(ingot) + "_packing")
                    .setEnergy(400)
                    .addInput(Ingredient.of(ingotTag), 9)
                    .addInput(PRESS_PACKING_3x3_DIE)
                    .addOutputItem(ingotBlock));
            if (ingotBlockTag != null) {
                THERMAL_PRESSING.add(THERMAL_PRESSING.builder(toName(ingot) + "_unpacking")
                        .setEnergy(400)
                        .addInput(Ingredient.of(ingotBlockTag))
                        .addInput(PRESS_UNPACKING_DIE)
                        .addOutputItem(ingot, 9));
            }
        }
        if (rawOreBlock != null) {
            THERMAL_PRESSING.add(THERMAL_PRESSING.builder(toName(rawOre) + "_packing")
                    .setEnergy(400)
                    .addInput(Ingredient.of(rawOreTag), 9)
                    .addInput(PRESS_PACKING_3x3_DIE)
                    .addOutputItem(rawOreBlock));
            if (rawOreBlockTag != null) {
                THERMAL_PRESSING.add(THERMAL_PRESSING.builder(toName(rawOre) + "_unpacking")
                        .setEnergy(400)
                        .addInput(Ingredient.of(rawOreBlockTag))
                        .addInput(PRESS_UNPACKING_DIE)
                        .addOutputItem(rawOre, 9));
            }
        }
        if (nugget != null && nuggetTag != null) {
            THERMAL_PRESSING.add(THERMAL_PRESSING.builder(toName(nugget) + "_packing")
                    .setEnergy(400)
                    .addInput(Ingredient.of(nuggetTag), 9)
                    .addInput(PRESS_PACKING_3x3_DIE)
                    .addOutputItem(ingot));
            THERMAL_PRESSING.add(THERMAL_PRESSING.builder(toName(nugget) + "_unpacking")
                    .setEnergy(400)
                    .addInput(Ingredient.of(ingotTag))
                    .addInput(PRESS_UNPACKING_DIE)
                    .addOutputItem(nugget, 9));
        }
        if (dust != null) {
            THERMAL_PULVERIZING.add(THERMAL_PULVERIZING.builder(toName(ingot))
                    .setEnergyModifier(0.5f)
                    .addInput(Ingredient.of(ingot))
                    .addOutputItem(dust));
            if (secondaryDust == null) {
                THERMAL_PULVERIZING.add(THERMAL_PULVERIZING.builder(toName(oreTag), 0.2f)
                        .addInput(Ingredient.of(oreTag))
                        .addOutputItem(dust, 2.5f)
                        .addOutputItem(GRAVEL, 0.2f));
            } else {
                THERMAL_PULVERIZING.add(THERMAL_PULVERIZING.builder(toName(oreTag), 0.2f)
                        .addInput(Ingredient.of(oreTag))
                        .addOutputItem(dust, 2)
                        .addOutputItem(secondaryDust, 0.1f)
                        .addOutputItem(GRAVEL, 0.2f));
            }
            THERMAL_SMELTING.add(THERMAL_SMELTING.builder(toName(dust))
                    .setEnergyModifier(0.5f)
                    .addInput(Ingredient.of(dust))
                    .addOutputItem(ingot));
        }
        if (secondaryIngot == null) {
            THERMAL_SMELTING.add(THERMAL_SMELTING.builder(toName(oreTag), 0.2f)
                    .addInput(Ingredient.of(oreTag))
                    .addOutputItem(ingot, 1.5f)
                    .addOutputItem(RICH_SLAG, 0.15f));
        } else {
            THERMAL_PULVERIZING.add(THERMAL_PULVERIZING.builder(toName(oreTag), 0.2f)
                    .addInput(Ingredient.of(oreTag))
                    .addOutputItem(ingot, 1)
                    .addOutputItem(secondaryIngot, 0.2f)
                    .addOutputItem(RICH_SLAG, 0.2f));
        }
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
     * @param crushedOre     The id of the crushed ore item. Used in Create crushing. Can be null. If null, no recipes involving this item will be generated.
     */
    protected void addMetalOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item rawOre, TagKey<Item> rawOreTag, @Nullable Item rawOreBlock, @Nullable TagKey<Item> rawOreBlockTag, Item ingot, TagKey<Item> ingotTag, @Nullable Item ingotBlock, @Nullable TagKey<Item> ingotBlockTag, @Nullable Item nugget, @Nullable TagKey<Item> nuggetTag, @Nullable Item dust, @Nullable TagKey<Item> dustTag, @Nullable ResourceLocation crushedOre) {
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
    protected void addMetalOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item rawOre, TagKey<Item> rawOreTag, @Nullable Item rawOreBlock, @Nullable TagKey<Item> rawOreBlockTag, Item ingot, TagKey<Item> ingotTag, @Nullable Item ingotBlock, @Nullable TagKey<Item> ingotBlockTag, @Nullable Item nugget, @Nullable TagKey<Item> nuggetTag, @Nullable Item dust, @Nullable TagKey<Item> dustTag) {
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
    protected void addMetalOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item rawOre, TagKey<Item> rawOreTag, @Nullable Item rawOreBlock, @Nullable TagKey<Item> rawOreBlockTag, Item ingot, TagKey<Item> ingotTag, @Nullable Item ingotBlock, @Nullable TagKey<Item> ingotBlockTag, @Nullable Item nugget, @Nullable TagKey<Item> nuggetTag) {
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
    protected void addMetalOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item rawOre, TagKey<Item> rawOreTag, @Nullable Item rawOreBlock, @Nullable TagKey<Item> rawOreBlockTag, Item ingot, TagKey<Item> ingotTag, @Nullable Item ingotBlock, @Nullable TagKey<Item> ingotBlockTag) {
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
    protected void addMetalOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item rawOre, TagKey<Item> rawOreTag, Item ingot, TagKey<Item> ingotTag) {
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
    protected void addFlowerProcessing(Item flower, Item output1, int count1, @Nullable ResourceLocation output2, int count2, float chance2, @Nullable ResourceLocation output3, int count3, float chance3) {
        Ingredient ingredient = Ingredient.of(flower);
        ARS_NOUVEAU_CRUSHING.add(ARS_NOUVEAU_CRUSHING.builder(toName(flower), ingredient).addOutput(output1, 2));
        if (flower instanceof BlockItem bi) {
            BOTANY_POTS_CROP.add(BOTANY_POTS_CROP.builder(toName(flower), ingredient, 1200)
                    .addCategory("dirt")
                    .addDisplay(new DisplayState.Simple(bi.getBlock().defaultBlockState()))
                    .addOutput(flower));
        }
        var milling = CREATE_MILLING.builder(toName(flower), 50)
                .addInput(ingredient)
                .addOutput(output1, count1);
        if (output2 != null) {
            milling.addOutput(output2, count2, chance2);
        }
        if (output3 != null) {
            milling.addOutput(output3, count3, chance3);
        }
        CREATE_MILLING.add(milling);
        FARMERS_DELIGHT_CUTTING.add(FARMERS_DELIGHT_CUTTING.builder(toName(flower), ingredient, KNIVES)
                .addOutput(output1, 2));
        INTEGRATED_DYNAMICS_MECHANICAL_SQUEEZING.add(INTEGRATED_DYNAMICS_MECHANICAL_SQUEEZING.builder(toName(flower), ingredient, 5)
                .addItem(output1, 4)
                .addItem(output1, 2, 0.5f));
        INTEGRATED_DYNAMICS_SQUEEZING.add(INTEGRATED_DYNAMICS_SQUEEZING.builder(toName(flower), ingredient)
                .addItem(output1, 4));
        MEKANISM_CRUSHING.add(MEKANISM_CRUSHING.builder(toName(flower), ingredient, BIO_FUEL, 5));
        MEKANISM_ENRICHING.add(MEKANISM_ENRICHING.builder(toName(flower), ingredient, output1, 2));
        if (output1 instanceof DyeItem dye) {
            MEKANISM_PIGMENT_EXTRACTING.add(MEKANISM_PIGMENT_EXTRACTING.builder(toName(flower), ingredient, new Chemical.Stack<>(Pigment.byDyeColor(dye.getDyeColor()), 768)));
        }
        THERMAL_CENTRIFUGING.add(THERMAL_CENTRIFUGING.builder(toName(flower))
                .setEnergy(1600)
                .addInput(ingredient)
                .addOutputItem(output1, 3));
        THERMAL_INSOLATING.add(THERMAL_INSOLATING.builder(toName(flower))
                .addInput(ingredient)
                .addOutputItem(flower, 1, 2));
    }

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
    protected void addFlowerProcessing(Item flower, Item output1, int count1, @Nullable ResourceLocation output2, float chance2, @Nullable ResourceLocation output3, float chance3) {
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
    protected void addFlowerProcessing(Item flower, Item output1, int count1, @Nullable ResourceLocation output2, int count2, float chance2) {
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
    protected void addFlowerProcessing(Item flower, Item output1, int count1, @Nullable ResourceLocation output2, float chance2) {
        addFlowerProcessing(flower, output1, count1, output2, 1, chance2);
    }

    /**
     * Adds processing for a small flower.
     *
     * @param flower  The flower to be processed.
     * @param output1 The primary output. Should ideally be a {@link DyeItem}.
     * @param count1  The count of the primary output. Used in Create milling. Some mods may use different, fixed amounts.
     */
    protected void addFlowerProcessing(Item flower, Item output1, int count1) {
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
    protected void addTallFlowerProcessing(Item flower, Item output1, int count1, @Nullable ResourceLocation output2, int count2, float chance2, @Nullable ResourceLocation output3, int count3, float chance3) {
        Ingredient ingredient = Ingredient.of(flower);
        ARS_NOUVEAU_CRUSHING.add(ARS_NOUVEAU_CRUSHING.builder(itemId(flower).getPath(), ingredient).addOutput(output1, 4));
        if (flower instanceof BlockItem bi && bi.getBlock() instanceof TallFlowerBlock tfb) {
            BOTANY_POTS_CROP.add(BOTANY_POTS_CROP.builder(toName(flower), ingredient, 1200)
                    .addCategory("dirt")
                    .addDisplay(new DisplayState.Simple(tfb.defaultBlockState().setValue(TallFlowerBlock.HALF, DoubleBlockHalf.LOWER)))
                    .addDisplay(new DisplayState.Simple(tfb.defaultBlockState().setValue(TallFlowerBlock.HALF, DoubleBlockHalf.UPPER)))
                    .addOutput(flower));
        }
        var milling = CREATE_MILLING.builder(itemId(flower).getPath(), 100)
                .addInput(ingredient)
                .addOutput(output1, count1);
        if (output2 != null) {
            milling.addOutput(output2, count2, chance2);
        }
        if (output3 != null) {
            milling.addOutput(output3, count3, chance3);
        }
        CREATE_MILLING.add(milling);
        INTEGRATED_DYNAMICS_MECHANICAL_SQUEEZING.add(INTEGRATED_DYNAMICS_MECHANICAL_SQUEEZING.builder(toName(flower), ingredient, 5)
                .addItem(output1, 8)
                .addItem(output1, 2, 0.5f)
                .addItem(output1, 2, 0.5f));
        INTEGRATED_DYNAMICS_SQUEEZING.add(INTEGRATED_DYNAMICS_SQUEEZING.builder(toName(flower), ingredient)
                .addItem(output1, 8));
        MEKANISM_CRUSHING.add(MEKANISM_CRUSHING.builder(toName(flower), ingredient, BIO_FUEL, 5));
        MEKANISM_ENRICHING.add(MEKANISM_ENRICHING.builder(toName(flower), ingredient, output1, 4));
        if (output1 instanceof DyeItem dye) {
            MEKANISM_PIGMENT_EXTRACTING.add(MEKANISM_PIGMENT_EXTRACTING.builder(toName(flower), ingredient, new Chemical.Stack<>(Pigment.byDyeColor(dye.getDyeColor()), 1536)));
        }
        THERMAL_CENTRIFUGING.add(THERMAL_CENTRIFUGING.builder(toName(flower))
                .setEnergy(3200)
                .addInput(ingredient)
                .addOutputItem(output1, 6));
        THERMAL_INSOLATING.add(THERMAL_INSOLATING.builder(toName(flower))
                .addInput(ingredient)
                .addOutputItem(flower, 1, 2));
    }

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
    protected void addTallFlowerProcessing(Item flower, Item output1, int count1, @Nullable ResourceLocation output2, float chance2, @Nullable ResourceLocation output3, float chance3) {
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
    protected void addTallFlowerProcessing(Item flower, Item output1, int count1, @Nullable ResourceLocation output2, int count2, float chance2) {
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
    protected void addTallFlowerProcessing(Item flower, Item output1, int count1, @Nullable ResourceLocation output2, float chance2) {
        addTallFlowerProcessing(flower, output1, count1, output2, 1, chance2);
    }

    /**
     * Adds processing for a tall flower.
     *
     * @param flower  The flower to be processed.
     * @param output1 The primary output. Should ideally be a {@link DyeItem}.
     * @param count1  The count of the primary output. Used in Create milling. Some mods may use different, fixed amounts.
     */
    protected void addTallFlowerProcessing(Item flower, Item output1, int count1) {
        addTallFlowerProcessing(flower, output1, count1, null, 0, 0, null, 0, 0);
    }

    /**
     * Adds processing for a mushroom.
     *
     * @param mushroom      The mushroom to be processed.
     * @param mushroomBlock The mushroom block to be processed. Can be null. If null, no recipes involving this item will be generated.
     */
    protected void addMushroomProcessing(Item mushroom, @Nullable Item mushroomBlock) {
        if (mushroom instanceof BlockItem bi) {
            BOTANY_POTS_CROP.add(BOTANY_POTS_CROP.builder(toName(mushroom), Ingredient.of(mushroom), 1200)
                    .addCategory("stone")
                    .addCategory("mushroom")
                    .addCategory("mulch")
                    .addDisplay(new DisplayState.Simple(bi.getBlock().defaultBlockState()))
                    .addOutput(mushroom));
        }
        IMMERSIVE_ENGINEERING_CLOCHE.add(IMMERSIVE_ENGINEERING_CLOCHE.builder(toName(mushroom), 480, Ingredient.of(mushroom), MUSHROOM_SOIL, ClocheRenderType.GENERIC, itemId(mushroom))
                .addOutput(Ingredient.of(mushroom)));
        MEKANISM_CRUSHING.add(MEKANISM_CRUSHING.builder(toName(mushroom), Ingredient.of(mushroom), BIO_FUEL, 5));
        if (mushroomBlock != null) {
            MEKANISM_CRUSHING.add(MEKANISM_CRUSHING.builder(toName(mushroomBlock), Ingredient.of(mushroomBlock), BIO_FUEL, 7));
        }
        THERMAL_INSOLATING.add(THERMAL_INSOLATING.builder(toName(mushroom))
                .setWaterModifier(1.5f)
                .setEnergyModifier(0.5f)
                .addInput(Ingredient.of(mushroom))
                .addOutputItem(mushroom, 1, 2));
    }

    /**
     * Adds processing for a mushroom.
     *
     * @param mushroom The mushroom to be processed.
     */
    protected void addMushroomProcessing(Item mushroom) {
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
    protected void addFungusAndRootsProcessing(Item fungus, Item roots, ResourceLocation nylium, ResourceLocation stem, ResourceLocation wartBlock) {
        if (fungus instanceof BlockItem bi) {
            BOTANY_POTS_CROP.add(BOTANY_POTS_CROP.builder(toName(fungus), Ingredient.of(fungus), 1200)
                    .addCategory(toName(nylium))
                    .addDisplay(new DisplayState.Simple(bi.getBlock().defaultBlockState()))
                    .addOutput(fungus)
                    .addOutput(stem)
                    .addOutput(wartBlock, 0.25f)
                    .addOutput(SHROOMLIGHT, 0.05f));
        }
        if (roots instanceof BlockItem bi) {
            BOTANY_POTS_CROP.add(BOTANY_POTS_CROP.builder(toName(roots), Ingredient.of(roots), 1200)
                    .addCategory(toName(nylium))
                    .addDisplay(new DisplayState.Simple(bi.getBlock().defaultBlockState()))
                    .addOutput(roots));
        }
        MEKANISM_CRUSHING.add(MEKANISM_CRUSHING.builder(toName(fungus), Ingredient.of(fungus), BIO_FUEL, 5));
        MEKANISM_CRUSHING.add(MEKANISM_CRUSHING.builder(toName(roots), Ingredient.of(roots), BIO_FUEL, 5));
    }

    /**
     * Adds processing for a wooden block family.
     *
     * @param family    The wooden block family to be processed.
     * @param boat      The boat item associated with the wooden block family.
     * @param chestBoat The chest boat item associated with the wooden block family.
     * @param logs      The logs tag associated with the wooden block family.
     */
    protected void addWoodenProcessing(BlockFamily family, @Nullable Item boat, @Nullable Item chestBoat, @Nullable TagKey<Item> logs) {
        Item planks = family.getBaseBlock().asItem();
        Item slab = family.getVariants().containsKey(BlockFamily.Variant.SLAB) ? family.get(BlockFamily.Variant.SLAB).asItem() : null;
        Item stairs = family.getVariants().containsKey(BlockFamily.Variant.STAIRS) ? family.get(BlockFamily.Variant.STAIRS).asItem() : null;
        Item fence = family.getVariants().containsKey(BlockFamily.Variant.FENCE) ? family.get(BlockFamily.Variant.FENCE).asItem() : null;
        Item fenceGate = family.getVariants().containsKey(BlockFamily.Variant.FENCE_GATE) ? family.get(BlockFamily.Variant.FENCE_GATE).asItem() : null;
        Item door = family.getVariants().containsKey(BlockFamily.Variant.DOOR) ? family.get(BlockFamily.Variant.DOOR).asItem() : null;
        Item trapdoor = family.getVariants().containsKey(BlockFamily.Variant.TRAPDOOR) ? family.get(BlockFamily.Variant.TRAPDOOR).asItem() : null;
        Item button = family.getVariants().containsKey(BlockFamily.Variant.BUTTON) ? family.get(BlockFamily.Variant.BUTTON).asItem() : null;
        Item pressurePlate = family.getVariants().containsKey(BlockFamily.Variant.PRESSURE_PLATE) ? family.get(BlockFamily.Variant.PRESSURE_PLATE).asItem() : null;
        Item sign = family.getVariants().containsKey(BlockFamily.Variant.SIGN) ? family.get(BlockFamily.Variant.SIGN).asItem() : null;
        if (slab != null) {
            CORAIL_WOODCUTTER_SAWING.add(CORAIL_WOODCUTTER_SAWING.builder(toName(slab) + "_from_" + toName(planks), Ingredient.of(planks), slab, 2));
            IMMERSIVE_ENGINEERING_SAWMILL.add(IMMERSIVE_ENGINEERING_SAWMILL.builder(toName(slab), 800, Ingredient.of(planks), slab, 2)
                    .addSecondaryOutput(WOOD_DUST, false));
        }
        if (stairs != null) {
            CORAIL_WOODCUTTER_SAWING.add(CORAIL_WOODCUTTER_SAWING.builder(toName(stairs) + "_from_" + toName(planks), Ingredient.of(planks), stairs));
            IMMERSIVE_ENGINEERING_SAWMILL.add(IMMERSIVE_ENGINEERING_SAWMILL.builder(toName(stairs), 1600, Ingredient.of(stairs), planks)
                    .addSecondaryOutput(WOOD_DUST, false));
        }
        if (fence != null) {
            CORAIL_WOODCUTTER_SAWING.add(CORAIL_WOODCUTTER_SAWING.builder(toName(fence) + "_from_" + toName(planks), Ingredient.of(planks), fence));
        }
        if (fenceGate != null) {
            MEKANISM_SAWING.add(MEKANISM_SAWING.builder(toName(fenceGate), Ingredient.of(fenceGate), planks, 2)
                    .setSecondaryOutput(STICK, 4));
        }
        if (door != null) {
            CORAIL_WOODCUTTER_SAWING.add(CORAIL_WOODCUTTER_SAWING.builder(toName(door) + "_from_" + toName(planks), Ingredient.of(planks), door));
            FARMERS_DELIGHT_CUTTING.add(FARMERS_DELIGHT_CUTTING.builder(toName(door), Ingredient.of(door), AXE_DIG)
                    .addOutput(planks));
            IMMERSIVE_ENGINEERING_SAWMILL.add(IMMERSIVE_ENGINEERING_SAWMILL.builder(toName(door), 800, Ingredient.of(door), planks)
                    .addSecondaryOutput(WOOD_DUST, false));
            MEKANISM_SAWING.add(MEKANISM_SAWING.builder(toName(door), Ingredient.of(door), planks, 2));
        }
        if (trapdoor != null) {
            CORAIL_WOODCUTTER_SAWING.add(CORAIL_WOODCUTTER_SAWING.builder(toName(trapdoor) + "_from_" + toName(planks), Ingredient.of(planks), trapdoor));
            FARMERS_DELIGHT_CUTTING.add(FARMERS_DELIGHT_CUTTING.builder(toName(trapdoor), Ingredient.of(trapdoor), AXE_DIG)
                    .addOutput(planks));
            MEKANISM_SAWING.add(MEKANISM_SAWING.builder(toName(trapdoor), Ingredient.of(trapdoor), planks, 3));
        }
        if (button != null) {
            CORAIL_WOODCUTTER_SAWING.add(CORAIL_WOODCUTTER_SAWING.builder(toName(button) + "_from_" + toName(planks), Ingredient.of(planks), button));
        }
        if (pressurePlate != null) {
            CORAIL_WOODCUTTER_SAWING.add(CORAIL_WOODCUTTER_SAWING.builder(toName(pressurePlate) + "_from_" + toName(planks), Ingredient.of(planks), pressurePlate));
            MEKANISM_SAWING.add(MEKANISM_SAWING.builder(toName(pressurePlate), Ingredient.of(pressurePlate), planks, 2));
        }
        if (sign != null) {
            CORAIL_WOODCUTTER_SAWING.add(CORAIL_WOODCUTTER_SAWING.builder(toName(sign) + "_from_" + toName(planks), Ingredient.of(planks), sign));
            FARMERS_DELIGHT_CUTTING.add(FARMERS_DELIGHT_CUTTING.builder(toName(sign), Ingredient.of(sign), AXE_DIG)
                    .addOutput(planks));
        }
        if (boat != null) {
            MEKANISM_SAWING.add(MEKANISM_SAWING.builder(toName(boat), Ingredient.of(boat), planks, 5));
            if (chestBoat != null) {
                MEKANISM_SAWING.add(MEKANISM_SAWING.builder(toName(chestBoat), Ingredient.of(chestBoat), boat)
                        .setSecondaryOutput(CHEST));
            }
        }
        if (logs != null) {
            CORAIL_WOODCUTTER_SAWING.add(CORAIL_WOODCUTTER_SAWING.builder(toName(planks) + "_from_" + toName(logs), Ingredient.of(logs), planks, 4));
            MEKANISM_SAWING.add(MEKANISM_SAWING.builder(toName(logs), Ingredient.of(logs), planks, 6)
                    .setSecondaryOutput(MEKANISM_SAWDUST, 0.25f));
            THERMAL_SAWING.add(THERMAL_SAWING.builder(toName(logs))
                    .setEnergy(1000)
                    .addInput(Ingredient.of(logs))
                    .addOutputItem(planks, 6)
                    .addOutputItem(THERMAL_SAWDUST, 1.25f));
            if (slab != null) {
                CORAIL_WOODCUTTER_SAWING.add(CORAIL_WOODCUTTER_SAWING.builder(toName(slab) + "_from_" + toName(logs), Ingredient.of(logs), slab, 8));
            }
            if (stairs != null) {
                CORAIL_WOODCUTTER_SAWING.add(CORAIL_WOODCUTTER_SAWING.builder(toName(stairs) + "_from_" + toName(logs), Ingredient.of(logs), stairs, 4));
            }
            if (fence != null) {
                CORAIL_WOODCUTTER_SAWING.add(CORAIL_WOODCUTTER_SAWING.builder(toName(fence) + "_from_" + toName(logs), Ingredient.of(logs), fence, 4));
            }
            if (fenceGate != null) {
                CORAIL_WOODCUTTER_SAWING.add(CORAIL_WOODCUTTER_SAWING.builder(toName(fenceGate) + "_from_" + toName(logs), Ingredient.of(logs), fenceGate));
            }
            if (door != null) {
                CORAIL_WOODCUTTER_SAWING.add(CORAIL_WOODCUTTER_SAWING.builder(toName(door) + "_from_" + toName(logs), Ingredient.of(logs), door, 4));
            }
            if (trapdoor != null) {
                CORAIL_WOODCUTTER_SAWING.add(CORAIL_WOODCUTTER_SAWING.builder(toName(trapdoor) + "_from_" + toName(logs), Ingredient.of(logs), trapdoor, 4));
            }
            if (button != null) {
                CORAIL_WOODCUTTER_SAWING.add(CORAIL_WOODCUTTER_SAWING.builder(toName(button) + "_from_" + toName(logs), Ingredient.of(logs), button, 4));
            }
            if (pressurePlate != null) {
                CORAIL_WOODCUTTER_SAWING.add(CORAIL_WOODCUTTER_SAWING.builder(toName(pressurePlate) + "_from_" + toName(logs), Ingredient.of(logs), pressurePlate, 4));
            }
            if (sign != null) {
                CORAIL_WOODCUTTER_SAWING.add(CORAIL_WOODCUTTER_SAWING.builder(toName(sign) + "_from_" + toName(logs), Ingredient.of(logs), sign, 4));
            }
            if (boat != null) {
                CORAIL_WOODCUTTER_SAWING.add(CORAIL_WOODCUTTER_SAWING.builder(toName(boat) + "_from_" + toName(logs), Ingredient.of(logs), boat));
            }
        }
    }

    /**
     * Adds processing for a wooden block family.
     *
     * @param family The wooden block family to be processed.
     * @param logs   The logs tag associated with the wooden block family.
     */
    protected void addWoodenProcessing(BlockFamily family, @Nullable TagKey<Item> logs) {
        addWoodenProcessing(family, null, null, logs);
    }

    /**
     * Adds processing for a wooden block family.
     *
     * @param family The wooden block family to be processed.
     */
    protected void addWoodenProcessing(BlockFamily family) {
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
    protected void addLogsProcessing(Item log, Item wood, Item strippedLog, Item strippedWood, ResourceLocation planks, @Nullable Item leaves, @Nullable Item sapling) {
        if (wood instanceof BlockItem bi) {
            BOTANY_POTS_SOIL.add(BOTANY_POTS_SOIL.builder(toName(wood), Ingredient.of(log, wood), new DisplayState.Simple(bi.getBlock().defaultBlockState()))
                    .addCategory("wood")
                    .addCategory("log")
                    .addCategory("mushroom")
                    .addCategory(toName(wood).replace("_wood", ""))
                    .addCategory(toName(wood)));
        }
        CREATE_CUTTING.add(CREATE_CUTTING.builder(toName(log), 50)
                .addInput(Ingredient.of(log))
                .addOutput(strippedLog));
        CREATE_CUTTING.add(CREATE_CUTTING.builder(toName(strippedLog), 50)
                .addInput(Ingredient.of(strippedLog))
                .addOutput(planks, 6));
        CREATE_CUTTING.add(CREATE_CUTTING.builder(toName(wood), 50)
                .addInput(Ingredient.of(wood))
                .addOutput(strippedWood));
        CREATE_CUTTING.add(CREATE_CUTTING.builder(toName(strippedWood), 50)
                .addInput(Ingredient.of(strippedWood))
                .addOutput(planks, 6));
        ELEMENTALCRAFT_SAWING.add(ELEMENTALCRAFT_SAWING.builder(toName(planks), Ingredient.of(strippedLog, strippedWood), planks, 6, 1000, 3));
        ELEMENTALCRAFT_SAWING.add(ELEMENTALCRAFT_SAWING.builder(toName(strippedLog), Ingredient.of(log), strippedLog, 1000, 0));
        ELEMENTALCRAFT_SAWING.add(ELEMENTALCRAFT_SAWING.builder(toName(strippedWood), Ingredient.of(wood), strippedWood, 1000, 0));
        FARMERS_DELIGHT_CUTTING.add(FARMERS_DELIGHT_CUTTING.builder(toName(log), Ingredient.of(log), AXE_STRIP)
                .setSound("minecraft:item.axe.strip")
                .addOutput(strippedLog)
                .addOutput(TREE_BARK));
        FARMERS_DELIGHT_CUTTING.add(FARMERS_DELIGHT_CUTTING.builder(toName(wood), Ingredient.of(wood), AXE_STRIP)
                .setSound("minecraft:item.axe.strip")
                .addOutput(strippedWood)
                .addOutput(TREE_BARK));
        IMMERSIVE_ENGINEERING_SAWMILL.add(IMMERSIVE_ENGINEERING_SAWMILL.builder(toName(log), 1600, Ingredient.of(log, wood), planks, 6)
                .setStrippedOutput(Ingredient.of(strippedLog))
                .addSecondaryOutput(WOOD_DUST, true)
                .addSecondaryOutput(WOOD_DUST, false));
        IMMERSIVE_ENGINEERING_SAWMILL.add(IMMERSIVE_ENGINEERING_SAWMILL.builder(toName(strippedLog), 800, Ingredient.of(strippedLog), planks, 6)
                .addSecondaryOutput(WOOD_DUST, false));
        if (leaves != null) {
            BOTANIA_MANA_INFUSION.add(BOTANIA_MANA_INFUSION.builder(toName(leaves) + "_dupe", 2000, Ingredient.of(leaves), leaves, 2)
                    .setCatalyst(CONJURATION_CATALYST));
            MEKANISM_CRUSHING.add(MEKANISM_CRUSHING.builder(toName(leaves), Ingredient.of(leaves), BIO_FUEL, 2));
        }
        if (sapling != null) {
            MEKANISM_CRUSHING.add(MEKANISM_CRUSHING.builder(toName(sapling), Ingredient.of(sapling), BIO_FUEL, 2));
            THERMAL_INSOLATING.add(THERMAL_INSOLATING.builder(toName(sapling))
                    .setWaterModifier(3)
                    .setEnergyModifier(3)
                    .addInput(Ingredient.of(sapling))
                    .addOutputItem(log, 1, 6)
                    .addOutputItem(sapling, 1, 1.1f));
        }
    }

    /**
     * Adds processing for wooden logs.
     *
     * @param log          The log item to process.
     * @param wood         The wood item to process.
     * @param strippedLog  The stripped log item to process.
     * @param strippedWood The stripped wood item to process.
     * @param planks       The id of the planks item associated with the wooden logs.
     */
    protected void addLogsProcessing(Item log, Item wood, Item strippedLog, Item strippedWood, ResourceLocation planks) {
        addLogsProcessing(log, wood, strippedLog, strippedWood, planks, null, null);
    }
    //endregion
}
