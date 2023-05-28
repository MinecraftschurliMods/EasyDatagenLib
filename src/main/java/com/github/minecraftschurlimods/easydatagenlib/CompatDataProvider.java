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
public abstract class CompatDataProvider {
    //region CORE
    private final List<AbstractDataProvider<?>> SERVER_PROVIDERS = new ArrayList<>();
    private final List<AbstractDataProvider<?>> CLIENT_PROVIDERS = new ArrayList<>();
    private final List<AbstractDataProvider<?>> COMMON_PROVIDERS = new ArrayList<>();
    public final ArsNouveauDataProvider.Crushing ARS_NOUVEAU_CRUSHING;
    public final ArsNouveauDataProvider.Glyph ARS_NOUVEAU_GLYPH;
    public final ArsNouveauDataProvider.Imbuement ARS_NOUVEAU_IMBUEMENT;
    public final BotaniaDataProvider.ManaInfusion BOTANIA_MANA_INFUSION;
    public final BotanyPotsDataProvider.Crop BOTANY_POTS_CROP;
    public final BotanyPotsDataProvider.Soil BOTANY_POTS_SOIL;
    public final CorailWoodcutterDataProvider.Woodcutting CORAIL_WOODCUTTER_WOODCUTTING;
    public final CreateDataProvider.Compacting CREATE_COMPACTING;
    public final CreateDataProvider.Crushing CREATE_CRUSHING;
    public final CreateDataProvider.Cutting CREATE_CUTTING;
    public final CreateDataProvider.Deploying CREATE_DEPLOYING;
    public final CreateDataProvider.Emptying CREATE_EMPTYING;
    public final CreateDataProvider.Filling CREATE_FILLING;
    public final CreateDataProvider.Haunting CREATE_HAUNTING;
    public final CreateDataProvider.ItemApplication CREATE_ITEM_APPLICATION;
    public final CreateDataProvider.MechanicalCrafting CREATE_MECHANICAL_CRAFTING;
    public final CreateDataProvider.Milling CREATE_MILLING;
    public final CreateDataProvider.Mixing CREATE_MIXING;
    public final CreateDataProvider.Pressing CREATE_PRESSING;
    public final CreateDataProvider.SandpaperPolishing CREATE_SANDPAPER_POLISHING;
    public final CreateDataProvider.SequencedAssembly  CREATE_SEQUENCED_ASSEMBLY;
    public final CreateDataProvider.Splashing CREATE_SPLASHING;
    public final ElementalcraftDataProvider.Grinding ELEMENTALCRAFT_GRINDING;
    public final ElementalcraftDataProvider.Sawing ELEMENTALCRAFT_SAWING;
    public final FarmersDelightDataProvider.Cooking FARMERS_DELIGHT_COOKING;
    public final FarmersDelightDataProvider.Cutting FARMERS_DELIGHT_CUTTING;
    public final ImmersiveEngineeringDataProvider.ArcFurnace IMMERSIVE_ENGINEERING_ARC_FURNACE;
    public final ImmersiveEngineeringDataProvider.Cloche IMMERSIVE_ENGINEERING_CLOCHE;
    public final ImmersiveEngineeringDataProvider.Crusher IMMERSIVE_ENGINEERING_CRUSHER;
    public final ImmersiveEngineeringDataProvider.Sawmill IMMERSIVE_ENGINEERING_SAWMILL;
    public final IntegratedDynamicsDataProvider.MechanicalSqueezing INTEGRATED_DYNAMICS_MECHANICAL_SQUEEZING;
    public final IntegratedDynamicsDataProvider.Squeezing INTEGRATED_DYNAMICS_SQUEEZING;
    public final MekanismDataProvider.Activating MEKANISM_ACTIVATING;
    public final MekanismDataProvider.Centrifuging MEKANISM_CENTRIFUGING;
    public final MekanismDataProvider.Combining MEKANISM_COMBINING;
    public final MekanismDataProvider.Crushing MEKANISM_CRUSHING;
    public final MekanismDataProvider.Enriching MEKANISM_ENRICHING;
    public final MekanismDataProvider.GasConversion MEKANISM_GAS_CONVERSION;
    public final MekanismDataProvider.InfusionConversion MEKANISM_INFUSION_CONVERSION;
    public final MekanismDataProvider.Oxidizing MEKANISM_OXIDIZING;
    public final MekanismDataProvider.PigmentExtracting MEKANISM_PIGMENT_EXTRACTING;
    public final MekanismDataProvider.Sawing MEKANISM_SAWING;
    public final MekanismDataProvider.Smelting MEKANISM_SMELTING;
    public final OccultismDataProvider.Crushing OCCULTISM_CRUSHING;
    public final ThermalDataProvider.Bottling THERMAL_BOTTLING;
    public final ThermalDataProvider.Brewing THERMAL_BREWING;
    public final ThermalDataProvider.Centrifuging THERMAL_CENTRIFUGING;
    public final ThermalDataProvider.Chilling THERMAL_CHILLING;
    public final ThermalDataProvider.Crucible THERMAL_CRUCIBLE;
    public final ThermalDataProvider.Crystallizing THERMAL_CRYSTALLIZING;
    public final ThermalDataProvider.Furnace THERMAL_FURNACE;
    public final ThermalDataProvider.Insolating THERMAL_INSOLATING;
    public final ThermalDataProvider.Pressing THERMAL_PRESSING;
    public final ThermalDataProvider.PulverizerRecycling THERMAL_PULVERIZER_RECYCLING;
    public final ThermalDataProvider.Pulverizing THERMAL_PULVERIZING;
    public final ThermalDataProvider.Pyrolyzing THERMAL_PYROLYZING;
    public final ThermalDataProvider.Refining THERMAL_REFINING;
    public final ThermalDataProvider.Sawing THERMAL_SAWING;
    public final ThermalDataProvider.SmelterRecycling THERMAL_SMELTER_RECYCLING;
    public final ThermalDataProvider.Smelting THERMAL_SMELTING;
    public final TwilightForestDataProvider.Crumbling TWILIGHT_FOREST_CRUMBLING;
    public final TwilightForestDataProvider.Transforming TWILIGHT_FOREST_TRANSFORMING;

    /**
     * Constructs a new {@link CompatDataProvider}. Initializes the providers and calls {@link CompatDataProvider#generate()}.
     *
     * @param namespace The namespace to use. In most cases, this is your own mod id.
     * @param generator The {@link DataGenerator} to use. Get this via {@link GatherDataEvent#getGenerator()}.
     * @param runServer Whether to generate data files. Get this via {@link GatherDataEvent#includeServer()}.
     * @param runClient Whether to generate asset files. Get this via {@link GatherDataEvent#includeClient()}.
     */
    public CompatDataProvider(String namespace, DataGenerator generator, boolean runServer, boolean runClient) {
        ARS_NOUVEAU_CRUSHING = addServer(new ArsNouveauDataProvider.Crushing(namespace, generator));
        ARS_NOUVEAU_GLYPH = addServer(new ArsNouveauDataProvider.Glyph(namespace, generator));
        ARS_NOUVEAU_IMBUEMENT = addServer(new ArsNouveauDataProvider.Imbuement(namespace, generator));
        BOTANIA_MANA_INFUSION = addServer(new BotaniaDataProvider.ManaInfusion(namespace, generator));
        BOTANY_POTS_CROP = addServer(new BotanyPotsDataProvider.Crop(namespace, generator));
        BOTANY_POTS_SOIL = addServer(new BotanyPotsDataProvider.Soil(namespace, generator));
        CORAIL_WOODCUTTER_WOODCUTTING = addServer(new CorailWoodcutterDataProvider.Woodcutting(namespace, generator));
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
    public CompatDataProvider(String namespace, GatherDataEvent event) {
        this(namespace, event.getGenerator(), event.includeServer(), event.includeClient());
    }

    /**
     * Override this to add your recipes.
     */
    public abstract void generate();

    private <T extends AbstractDataProvider<?>> T addServer(T provider) {
        SERVER_PROVIDERS.add(provider);
        return provider;
    }

    private <T extends AbstractDataProvider<?>> T addClient(T provider) {
        CLIENT_PROVIDERS.add(provider);
        return provider;
    }

    private <T extends AbstractDataProvider<?>> T addCommon(T provider) {
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
    protected static final Ingredient COBBLESTONE           = Ingredient.of(TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation("forge", "cobblestone/normal")));
    protected static final Ingredient DEEPSLATE_COBBLESTONE = Ingredient.of(TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation("forge", "cobblestone/deepslate")));
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
                .addIngredient(Ingredient.of(ore))
                .addResult(gem)
                .addResult(gem, 0.75f)
                .addResult(EXPERIENCE_NUGGET, 0.75f)
                .addResult(Items.COBBLESTONE, 0.125f));
        CREATE_CRUSHING.add(CREATE_CRUSHING.builder(itemId(deepslateOre).getPath(), 350)
                .addIngredient(Ingredient.of(deepslateOre))
                .addResult(gem, 2)
                .addResult(gem, 0.25f)
                .addResult(EXPERIENCE_NUGGET, 0.75f)
                .addResult(Items.COBBLED_DEEPSLATE, 0.125f));
        if (dust != null) {
            MEKANISM_COMBINING.add(MEKANISM_COMBINING.builder(toName(gem) + "_to_deepslate_ore", Ingredient.of(dust), 5, DEEPSLATE_COBBLESTONE, deepslateOre));
            MEKANISM_COMBINING.add(MEKANISM_COMBINING.builder(toName(gem) + "_to_ore", Ingredient.of(dust), 5, COBBLESTONE, deepslateOre));
            MEKANISM_CRUSHING.add(MEKANISM_CRUSHING.builder(toName(gem) + "_to_dust", Ingredient.of(gem), dust));
            MEKANISM_ENRICHING.add(MEKANISM_ENRICHING.builder(toName(gem) + "_from_dust", Ingredient.of(dust), gem));
        }
        MEKANISM_ENRICHING.add(MEKANISM_ENRICHING.builder(toName(gem) + "_from_ore", Ingredient.of(oreTag), gem, 2));
        if (block != null) {
            THERMAL_PRESSING.add(THERMAL_PRESSING.builder(toName(gem) + "_packing")
                    .setEnergy(400)
                    .addInputItem(Ingredient.of(gem), 9)
                    .addInputItem(PRESS_PACKING_3x3_DIE)
                    .addOutputItem(block));
            THERMAL_PRESSING.add(THERMAL_PRESSING.builder(toName(gem) + "_unpacking")
                    .setEnergy(400)
                    .addInputItem(Ingredient.of(block))
                    .addInputItem(PRESS_UNPACKING_DIE)
                    .addOutputItem(gem, 9));
        }
        if (dust != null) {
            THERMAL_PULVERIZING.add(THERMAL_PULVERIZING.builder(toName(gem))
                    .setEnergyModifier(0.5f)
                    .addInputItem(Ingredient.of(gem))
                    .addOutputItem(dust));
            THERMAL_PULVERIZING.add(THERMAL_PULVERIZING.builder(toName(oreTag), 0.2f)
                    .addInputItem(Ingredient.of(oreTag))
                    .addOutputItem(dust, 2.5f)
                    .addOutputItem(GRAVEL, 0.2f));
        }
        THERMAL_SMELTING.add(THERMAL_SMELTING.builder(toName(oreTag), 0.2f)
                .addInputItem(Ingredient.of(oreTag))
                .addOutputItem(gem, 1.5f)
                .addOutputItem(RICH_SLAG, 0.15f));
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
    protected void addMetalOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item rawOre, TagKey<Item> rawOreTag, @Nullable Item rawOreBlock, TagKey<Item> rawOreBlockTag, Item ingot, TagKey<Item> ingotTag, @Nullable Item ingotBlock, TagKey<Item> ingotBlockTag, @Nullable Item nugget, TagKey<Item> nuggetTag, @Nullable Item dust, TagKey<Item> dustTag, @Nullable ResourceLocation crushedOre, @Nullable ResourceLocation secondaryIngot, @Nullable ResourceLocation secondaryDust, @Nullable TagKey<Item> secondaryDustTag) {
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
                    .addIngredient(Ingredient.of(ore))
                    .addResult(crushedOre)
                    .addResult(crushedOre, 0.75f)
                    .addResult(EXPERIENCE_NUGGET, 0.75f)
                    .addResult(Items.COBBLESTONE, 0.125f));
            CREATE_CRUSHING.add(CREATE_CRUSHING.builder(toName(deepslateOre), 350)
                    .addIngredient(Ingredient.of(deepslateOre))
                    .addResult(crushedOre, 2)
                    .addResult(crushedOre, 0.25f)
                    .addResult(EXPERIENCE_NUGGET, 0.75f)
                    .addResult(Items.COBBLED_DEEPSLATE, 0.125f));
            CREATE_CRUSHING.add(CREATE_CRUSHING.builder(toName(rawOre), 400)
                    .addIngredient(Ingredient.of(rawOreTag))
                    .addResult(crushedOre)
                    .addResult(EXPERIENCE_NUGGET, 0.75f)
                    .addCondition(new NotCondition(new TagEmptyCondition(rawOreTag.location()))));
        }
        IMMERSIVE_ENGINEERING_ARC_FURNACE.add(IMMERSIVE_ENGINEERING_ARC_FURNACE.builder(toName(dustTag), 100, 51200, Ingredient.of(dustTag))
                .addResult(Ingredient.of(ingotTag))
                .addCondition(new NotCondition(new TagEmptyCondition(dustTag.location())))
                .addCondition(new NotCondition(new TagEmptyCondition(ingotTag.location()))));
        IMMERSIVE_ENGINEERING_ARC_FURNACE.add(IMMERSIVE_ENGINEERING_ARC_FURNACE.builder(toName(oreTag), 200, 102400, Ingredient.of(oreTag))
                .addResult(Ingredient.of(ingotTag), 2)
                .setSlag(SLAG)
                .addCondition(new NotCondition(new TagEmptyCondition(oreTag.location())))
                .addCondition(new NotCondition(new TagEmptyCondition(ingotTag.location()))));
        IMMERSIVE_ENGINEERING_ARC_FURNACE.add(IMMERSIVE_ENGINEERING_ARC_FURNACE.builder(toName(rawOreBlockTag), 900, 230400, Ingredient.of(rawOreBlockTag))
                .addResult(Ingredient.of(ingotTag), 13)
                .addSecondary(Ingredient.of(ingotTag), 0.5f)
                .addCondition(new NotCondition(new TagEmptyCondition(rawOreBlockTag.location())))
                .addCondition(new NotCondition(new TagEmptyCondition(ingotTag.location()))));
        IMMERSIVE_ENGINEERING_ARC_FURNACE.add(IMMERSIVE_ENGINEERING_ARC_FURNACE.builder(toName(rawOreTag), 100, 25600, Ingredient.of(rawOreTag))
                .addResult(Ingredient.of(ingotTag))
                .addSecondary(Ingredient.of(ingotTag), 0.5f)
                .addCondition(new NotCondition(new TagEmptyCondition(rawOreTag.location())))
                .addCondition(new NotCondition(new TagEmptyCondition(ingotTag.location()))));
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
        IMMERSIVE_ENGINEERING_CRUSHER.add(IMMERSIVE_ENGINEERING_CRUSHER.builder(toName(rawOreBlockTag), 54000, Ingredient.of(rawOreBlockTag), Ingredient.of(rawOreTag), 12)
                .addCondition(new NotCondition(new TagEmptyCondition(rawOreBlockTag.location())))
                .addCondition(new NotCondition(new TagEmptyCondition(rawOreTag.location()))));
        IMMERSIVE_ENGINEERING_CRUSHER.add(IMMERSIVE_ENGINEERING_CRUSHER.builder(toName(rawOreTag), 6000, Ingredient.of(rawOreTag), Ingredient.of(dustTag))
                .addSecondary(Ingredient.of(dustTag), 0.33333334f)
                .addCondition(new NotCondition(new TagEmptyCondition(rawOreTag.location())))
                .addCondition(new NotCondition(new TagEmptyCondition(dustTag.location()))));
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
            MEKANISM_ENRICHING.add(MEKANISM_ENRICHING.builder(toName(dust) + "_from_raw_block", Ingredient.of(rawOreBlockTag), dust, 12));
        }
        MEKANISM_COMBINING.add(MEKANISM_COMBINING.builder(toName(deepslateOre) + "_from_raw", Ingredient.of(rawOre), 8, DEEPSLATE_COBBLESTONE, deepslateOre));
        MEKANISM_COMBINING.add(MEKANISM_COMBINING.builder(toName(ore) + "_from_raw", Ingredient.of(rawOre), 8, COBBLESTONE, ore));
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
        if (ingotBlock != null) {
            THERMAL_PRESSING.add(THERMAL_PRESSING.builder(toName(ingot) + "_packing")
                    .setEnergy(400)
                    .addInputItem(Ingredient.of(ingotTag), 9)
                    .addInputItem(PRESS_PACKING_3x3_DIE)
                    .addOutputItem(ingotBlock));
            THERMAL_PRESSING.add(THERMAL_PRESSING.builder(toName(ingot) + "_unpacking")
                    .setEnergy(400)
                    .addInputItem(Ingredient.of(ingotBlockTag))
                    .addInputItem(PRESS_UNPACKING_DIE)
                    .addOutputItem(ingot, 9));
        }
        if (rawOreBlock != null) {
            THERMAL_PRESSING.add(THERMAL_PRESSING.builder(toName(rawOre) + "_packing")
                    .setEnergy(400)
                    .addInputItem(Ingredient.of(rawOreTag), 9)
                    .addInputItem(PRESS_PACKING_3x3_DIE)
                    .addOutputItem(rawOreBlock));
            THERMAL_PRESSING.add(THERMAL_PRESSING.builder(toName(rawOre) + "_unpacking")
                    .setEnergy(400)
                    .addInputItem(Ingredient.of(rawOreBlockTag))
                    .addInputItem(PRESS_UNPACKING_DIE)
                    .addOutputItem(rawOre, 9));
        }
        if (nugget != null) {
            THERMAL_PRESSING.add(THERMAL_PRESSING.builder(toName(nugget) + "_packing")
                    .setEnergy(400)
                    .addInputItem(Ingredient.of(nuggetTag), 9)
                    .addInputItem(PRESS_PACKING_3x3_DIE)
                    .addOutputItem(ingot));
            THERMAL_PRESSING.add(THERMAL_PRESSING.builder(toName(nugget) + "_unpacking")
                    .setEnergy(400)
                    .addInputItem(Ingredient.of(ingotTag))
                    .addInputItem(PRESS_UNPACKING_DIE)
                    .addOutputItem(nugget, 9));
        }
        if (dust != null) {
            THERMAL_PULVERIZING.add(THERMAL_PULVERIZING.builder(toName(ingot))
                    .setEnergyModifier(0.5f)
                    .addInputItem(Ingredient.of(ingot))
                    .addOutputItem(dust));
            if (secondaryDust == null) {
                THERMAL_PULVERIZING.add(THERMAL_PULVERIZING.builder(toName(oreTag), 0.2f)
                        .addInputItem(Ingredient.of(oreTag))
                        .addOutputItem(dust, 2.5f)
                        .addOutputItem(GRAVEL, 0.2f));
            } else {
                THERMAL_PULVERIZING.add(THERMAL_PULVERIZING.builder(toName(oreTag), 0.2f)
                        .addInputItem(Ingredient.of(oreTag))
                        .addOutputItem(dust, 2)
                        .addOutputItem(secondaryDust, 0.1f)
                        .addOutputItem(GRAVEL, 0.2f));
            }
            THERMAL_SMELTING.add(THERMAL_SMELTING.builder(toName(dust))
                    .setEnergyModifier(0.5f)
                    .addInputItem(Ingredient.of(dust))
                    .addOutputItem(ingot));
        }
        if (secondaryIngot == null) {
            THERMAL_SMELTING.add(THERMAL_SMELTING.builder(toName(oreTag), 0.2f)
                    .addInputItem(Ingredient.of(oreTag))
                    .addOutputItem(ingot, 1.5f)
                    .addOutputItem(RICH_SLAG, 0.15f));
        } else {
            THERMAL_PULVERIZING.add(THERMAL_PULVERIZING.builder(toName(oreTag), 0.2f)
                    .addInputItem(Ingredient.of(oreTag))
                    .addOutputItem(ingot, 1)
                    .addOutputItem(secondaryIngot, 0.2f)
                    .addOutputItem(RICH_SLAG, 0.2f));
        }
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
                    .addDrop(flower));
        }
        var milling = CREATE_MILLING.builder(toName(flower), 50)
                .addIngredient(ingredient)
                .addResult(output1, count1);
        if (output2 != null) {
            milling.addResult(output2, count2, chance2);
        }
        if (output3 != null) {
            milling.addResult(output3, count3, chance3);
        }
        CREATE_MILLING.add(milling);
        FARMERS_DELIGHT_CUTTING.add(FARMERS_DELIGHT_CUTTING.builder(toName(flower), ingredient, KNIVES)
                .addResult(output1, 2));
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
                .addInputItem(ingredient)
                .addOutputItem(output1, 3));
        THERMAL_INSOLATING.add(THERMAL_INSOLATING.builder(toName(flower))
                .addInputItem(ingredient)
                .addOutputItem(flower, 1, 2));
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
                    .addDrop(flower));
        }
        var milling = CREATE_MILLING.builder(itemId(flower).getPath(), 100)
                .addIngredient(ingredient)
                .addResult(output1, count1);
        if (output2 != null) {
            milling.addResult(output2, count2, chance2);
        }
        if (output3 != null) {
            milling.addResult(output3, count3, chance3);
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
                .addInputItem(ingredient)
                .addOutputItem(output1, 6));
        THERMAL_INSOLATING.add(THERMAL_INSOLATING.builder(toName(flower))
                .addInputItem(ingredient)
                .addOutputItem(flower, 1, 2));
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
                    .addDrop(mushroom));
        }
        IMMERSIVE_ENGINEERING_CLOCHE.add(IMMERSIVE_ENGINEERING_CLOCHE.builder(toName(mushroom), 480, Ingredient.of(mushroom), MUSHROOM_SOIL, ClocheRenderType.GENERIC, itemId(mushroom))
                .addResult(Ingredient.of(mushroom)));
        MEKANISM_CRUSHING.add(MEKANISM_CRUSHING.builder(toName(mushroom), Ingredient.of(mushroom), BIO_FUEL, 5));
        if (mushroomBlock != null) {
            MEKANISM_CRUSHING.add(MEKANISM_CRUSHING.builder(toName(mushroomBlock), Ingredient.of(mushroomBlock), BIO_FUEL, 7));
        }
        THERMAL_INSOLATING.add(THERMAL_INSOLATING.builder(toName(mushroom))
                .setWaterModifier(1.5f)
                .setEnergyModifier(0.5f)
                .addInputItem(Ingredient.of(mushroom))
                .addOutputItem(mushroom, 1, 2));
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
                    .addDrop(fungus)
                    .addDrop(stem)
                    .addDrop(wartBlock, 0.25f)
                    .addDrop(SHROOMLIGHT, 0.05f));
        }
        if (roots instanceof BlockItem bi) {
            BOTANY_POTS_CROP.add(BOTANY_POTS_CROP.builder(toName(roots), Ingredient.of(roots), 1200)
                    .addCategory(toName(nylium))
                    .addDisplay(new DisplayState.Simple(bi.getBlock().defaultBlockState()))
                    .addDrop(roots));
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
            CORAIL_WOODCUTTER_WOODCUTTING.add(CORAIL_WOODCUTTER_WOODCUTTING.builder(toName(slab) + "_from_" + toName(planks), Ingredient.of(planks), slab, 2));
            IMMERSIVE_ENGINEERING_SAWMILL.add(IMMERSIVE_ENGINEERING_SAWMILL.builder(toName(slab), 800, Ingredient.of(planks), slab, 2)
                    .addSecondary(WOOD_DUST, false));
        }
        if (stairs != null) {
            CORAIL_WOODCUTTER_WOODCUTTING.add(CORAIL_WOODCUTTER_WOODCUTTING.builder(toName(stairs) + "_from_" + toName(planks), Ingredient.of(planks), stairs));
            IMMERSIVE_ENGINEERING_SAWMILL.add(IMMERSIVE_ENGINEERING_SAWMILL.builder(toName(stairs), 1600, Ingredient.of(stairs), planks)
                    .addSecondary(WOOD_DUST, false));
        }
        if (fence != null) {
            CORAIL_WOODCUTTER_WOODCUTTING.add(CORAIL_WOODCUTTER_WOODCUTTING.builder(toName(fence) + "_from_" + toName(planks), Ingredient.of(planks), fence));
        }
        if (fenceGate != null) {
            MEKANISM_SAWING.add(MEKANISM_SAWING.builder(toName(fenceGate), Ingredient.of(fenceGate), planks, 2)
                    .setSecondaryOutput(STICK, 4));
        }
        if (door != null) {
            CORAIL_WOODCUTTER_WOODCUTTING.add(CORAIL_WOODCUTTER_WOODCUTTING.builder(toName(door) + "_from_" + toName(planks), Ingredient.of(planks), door));
            FARMERS_DELIGHT_CUTTING.add(FARMERS_DELIGHT_CUTTING.builder(toName(door), Ingredient.of(door), AXE_DIG)
                    .addResult(planks));
            IMMERSIVE_ENGINEERING_SAWMILL.add(IMMERSIVE_ENGINEERING_SAWMILL.builder(toName(door), 800, Ingredient.of(door), planks)
                    .addSecondary(WOOD_DUST, false));
            MEKANISM_SAWING.add(MEKANISM_SAWING.builder(toName(door), Ingredient.of(door), planks, 2));
        }
        if (trapdoor != null) {
            CORAIL_WOODCUTTER_WOODCUTTING.add(CORAIL_WOODCUTTER_WOODCUTTING.builder(toName(trapdoor) + "_from_" + toName(planks), Ingredient.of(planks), trapdoor));
            FARMERS_DELIGHT_CUTTING.add(FARMERS_DELIGHT_CUTTING.builder(toName(trapdoor), Ingredient.of(trapdoor), AXE_DIG)
                    .addResult(planks));
            MEKANISM_SAWING.add(MEKANISM_SAWING.builder(toName(trapdoor), Ingredient.of(trapdoor), planks, 3));
        }
        if (button != null) {
            CORAIL_WOODCUTTER_WOODCUTTING.add(CORAIL_WOODCUTTER_WOODCUTTING.builder(toName(button) + "_from_" + toName(planks), Ingredient.of(planks), button));
        }
        if (pressurePlate != null) {
            CORAIL_WOODCUTTER_WOODCUTTING.add(CORAIL_WOODCUTTER_WOODCUTTING.builder(toName(pressurePlate) + "_from_" + toName(planks), Ingredient.of(planks), pressurePlate));
            MEKANISM_SAWING.add(MEKANISM_SAWING.builder(toName(pressurePlate), Ingredient.of(pressurePlate), planks, 2));
        }
        if (sign != null) {
            CORAIL_WOODCUTTER_WOODCUTTING.add(CORAIL_WOODCUTTER_WOODCUTTING.builder(toName(sign) + "_from_" + toName(planks), Ingredient.of(planks), sign));
            FARMERS_DELIGHT_CUTTING.add(FARMERS_DELIGHT_CUTTING.builder(toName(sign), Ingredient.of(sign), AXE_DIG)
                    .addResult(planks));
        }
        if (boat != null) {
            MEKANISM_SAWING.add(MEKANISM_SAWING.builder(toName(boat), Ingredient.of(boat), planks, 5));
            if (chestBoat != null) {
                MEKANISM_SAWING.add(MEKANISM_SAWING.builder(toName(chestBoat), Ingredient.of(chestBoat), boat)
                        .setSecondaryOutput(CHEST));
            }
        }
        if (logs != null) {
            CORAIL_WOODCUTTER_WOODCUTTING.add(CORAIL_WOODCUTTER_WOODCUTTING.builder(toName(planks) + "_from_" + toName(logs), Ingredient.of(logs), planks, 4));
            MEKANISM_SAWING.add(MEKANISM_SAWING.builder(toName(logs), Ingredient.of(logs), planks, 6)
                    .setSecondaryOutput(MEKANISM_SAWDUST, 0.25f));
            THERMAL_SAWING.add(THERMAL_SAWING.builder(toName(logs))
                    .setEnergy(1000)
                    .addInputItem(Ingredient.of(logs))
                    .addOutputItem(planks, 6)
                    .addOutputItem(THERMAL_SAWDUST, 1.25f));
            if (slab != null) {
                CORAIL_WOODCUTTER_WOODCUTTING.add(CORAIL_WOODCUTTER_WOODCUTTING.builder(toName(slab) + "_from_" + toName(logs), Ingredient.of(planks), slab, 8));
            }
            if (stairs != null) {
                CORAIL_WOODCUTTER_WOODCUTTING.add(CORAIL_WOODCUTTER_WOODCUTTING.builder(toName(stairs) + "_from_" + toName(logs), Ingredient.of(planks), stairs, 4));
            }
            if (fence != null) {
                CORAIL_WOODCUTTER_WOODCUTTING.add(CORAIL_WOODCUTTER_WOODCUTTING.builder(toName(fence) + "_from_" + toName(logs), Ingredient.of(planks), fence, 4));
            }
            if (fenceGate != null) {
                CORAIL_WOODCUTTER_WOODCUTTING.add(CORAIL_WOODCUTTER_WOODCUTTING.builder(toName(fenceGate) + "_from_" + toName(logs), Ingredient.of(planks), fenceGate));
            }
            if (door != null) {
                CORAIL_WOODCUTTER_WOODCUTTING.add(CORAIL_WOODCUTTER_WOODCUTTING.builder(toName(door) + "_from_" + toName(logs), Ingredient.of(planks), door, 4));
            }
            if (trapdoor != null) {
                CORAIL_WOODCUTTER_WOODCUTTING.add(CORAIL_WOODCUTTER_WOODCUTTING.builder(toName(trapdoor) + "_from_" + toName(logs), Ingredient.of(planks), trapdoor, 4));
            }
            if (button != null) {
                CORAIL_WOODCUTTER_WOODCUTTING.add(CORAIL_WOODCUTTER_WOODCUTTING.builder(toName(button) + "_from_" + toName(logs), Ingredient.of(planks), button, 4));
            }
            if (pressurePlate != null) {
                CORAIL_WOODCUTTER_WOODCUTTING.add(CORAIL_WOODCUTTER_WOODCUTTING.builder(toName(pressurePlate) + "_from_" + toName(logs), Ingredient.of(planks), pressurePlate, 4));
            }
            if (sign != null) {
                CORAIL_WOODCUTTER_WOODCUTTING.add(CORAIL_WOODCUTTER_WOODCUTTING.builder(toName(sign) + "_from_" + toName(logs), Ingredient.of(planks), sign, 4));
            }
            if (boat != null) {
                CORAIL_WOODCUTTER_WOODCUTTING.add(CORAIL_WOODCUTTER_WOODCUTTING.builder(toName(boat) + "_from_" + toName(logs), Ingredient.of(planks), boat));
            }
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
                .addIngredient(Ingredient.of(log))
                .addResult(strippedLog));
        CREATE_CUTTING.add(CREATE_CUTTING.builder(toName(strippedLog), 50)
                .addIngredient(Ingredient.of(strippedLog))
                .addResult(planks, 6));
        CREATE_CUTTING.add(CREATE_CUTTING.builder(toName(wood), 50)
                .addIngredient(Ingredient.of(wood))
                .addResult(strippedWood));
        CREATE_CUTTING.add(CREATE_CUTTING.builder(toName(strippedWood), 50)
                .addIngredient(Ingredient.of(strippedWood))
                .addResult(planks, 6));
        ELEMENTALCRAFT_SAWING.add(ELEMENTALCRAFT_SAWING.builder(toName(planks), Ingredient.of(strippedLog, strippedWood), planks, 6, 1000, 3));
        ELEMENTALCRAFT_SAWING.add(ELEMENTALCRAFT_SAWING.builder(toName(strippedLog), Ingredient.of(log), strippedLog, 1000, 0));
        ELEMENTALCRAFT_SAWING.add(ELEMENTALCRAFT_SAWING.builder(toName(strippedWood), Ingredient.of(wood), strippedWood, 1000, 0));
        FARMERS_DELIGHT_CUTTING.add(FARMERS_DELIGHT_CUTTING.builder(toName(log), Ingredient.of(log), AXE_STRIP)
                .setSound("minecraft:item.axe.strip")
                .addResult(strippedLog)
                .addResult(TREE_BARK));
        FARMERS_DELIGHT_CUTTING.add(FARMERS_DELIGHT_CUTTING.builder(toName(wood), Ingredient.of(wood), AXE_STRIP)
                .setSound("minecraft:item.axe.strip")
                .addResult(strippedWood)
                .addResult(TREE_BARK));
        IMMERSIVE_ENGINEERING_SAWMILL.add(IMMERSIVE_ENGINEERING_SAWMILL.builder(toName(log), 1600, Ingredient.of(log, wood), planks, 6)
                .setStripped(Ingredient.of(strippedLog))
                .addSecondary(WOOD_DUST, true)
                .addSecondary(WOOD_DUST, false));
        IMMERSIVE_ENGINEERING_SAWMILL.add(IMMERSIVE_ENGINEERING_SAWMILL.builder(toName(strippedLog), 800, Ingredient.of(strippedLog), planks, 6)
                .addSecondary(WOOD_DUST, false));
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
                    .addInputItem(Ingredient.of(sapling))
                    .addOutputItem(log, 1, 6)
                    .addOutputItem(sapling, 1, 1.1f));
        }
    }
    //endregion
}
