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
import com.github.minecraftschurlimods.easydatagenlib.mods.OccultismDataProvider;
import com.github.minecraftschurlimods.easydatagenlib.mods.TwilightForestDataProvider;
import com.github.minecraftschurlimods.easydatagenlib.util.botanypots.DisplayState;
import com.github.minecraftschurlimods.easydatagenlib.util.farmersdelight.ToolActionIngredient;
import com.github.minecraftschurlimods.easydatagenlib.util.immersiveengineering.ClocheRenderType;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
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
    public final OccultismDataProvider.Crushing OCCULTISM_CRUSHING;
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
        OCCULTISM_CRUSHING = addServer(new OccultismDataProvider.Crushing(namespace, generator));
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
    protected static final ResourceLocation AIR = new ResourceLocation("minecraft", "air");
    protected static final ResourceLocation ALCHEMY_CATALYST = new ResourceLocation("botania", "alchemy_catalyst");
    protected static final ResourceLocation CONJURATION_CATALYST = new ResourceLocation("botania", "conjuration_catalyst");
    protected static final ResourceLocation EXPERIENCE_NUGGET = new ResourceLocation("create", "experience_nugget");
    protected static final ResourceLocation SHROOMLIGHT = new ResourceLocation("minecraft", "shroomlight");
    protected static final ResourceLocation TREE_BARK = new ResourceLocation("farmersdelight", "tree_bark");
    protected static final Ingredient AXE_DIG = new ToolActionIngredient(ToolActions.AXE_DIG);
    protected static final Ingredient AXE_STRIP = new ToolActionIngredient(ToolActions.AXE_STRIP);
    protected static final Ingredient KNIVES = Ingredient.of(TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation("forge", "tools/knives")));
    protected static final Ingredient MUSHROOM_SOIL = Ingredient.of(Items.MYCELIUM, Items.PODZOL);
    protected static final Ingredient SLAG = Ingredient.of(TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation("forge", "slag")));
    protected static final Ingredient WOOD_DUST = Ingredient.of(TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation("forge", "dusts/wood")));

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
     * Adds processing for a stone. This is mainly intended for stone types similar to vanilla's andesite, diorite and granite.
     *
     * @param stone          The stone to be processed.
     * @param polishedStone  The polished variant of the stone.
     */
    protected void addStoneProcessing(Item stone, ResourceLocation polishedStone) {
        if (stone instanceof BlockItem bi) {
            BOTANY_POTS_SOIL.add(BOTANY_POTS_SOIL.builder(toName(stone), Ingredient.of(stone), new DisplayState.Simple(bi.getBlock().defaultBlockState()))
                    .addCategory("stone")
                    .addCategory("mushroom")
                    .addCategory(toName(stone)));
            TWILIGHT_FOREST_CRUMBLING.add(TWILIGHT_FOREST_CRUMBLING.builder("dissolve_" + toName(stone), itemId(stone), AIR));
        }
        //TODO Mekanism Enriching
    }

    /**
     * Adds processing for a gem ore, such as diamonds or emeralds. This assumes that the ores drop one item by default, and thus does not support ores that drop multiple items, such as lapis.
     *
     * @param ore          The ore to be processed.
     * @param deepslateOre The deepslate ore to be processed.
     * @param gem          The id of the gem item.
     */
    protected void addGemOreProcessing(Item ore, Item deepslateOre, ResourceLocation gem) {
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
    }

    /**
     * Adds processing for a metal ore, such as iron or gold. This assumes that the ores drop one item by default, and thus does not support ores that drop multiple items, such as copper.
     *
     * @param ore              The ore to be processed.
     * @param deepslateOre     The deepslate ore to be processed.
     * @param oreTag           The ore tag to be processed.
     * @param rawOre           The raw ore item.
     * @param rawOreTag        The raw ore tag.
     * @param rawOreBlock      The raw ore block item. Can be null. If null, no recipes producing this item will be generated.
     * @param rawOreBlockTag   The raw ore block tag.
     * @param ingot            The id of the ingot item.
     * @param ingotTag         The ingot tag.
     * @param ingotBlock       The id of the ingot block item. Can be null. If null, no recipes producing this item will be generated.
     * @param ingotBlockTag    The ingot block tag.
     * @param dust             The id of the dust item. Used in various mods for ore duplication. Can be null. If null, no recipes producing this item will be generated.
     * @param dustTag          The dust tag.
     * @param crushedOre       The id of the crushed ore item. Used in Create crushing. Can be null. If null, no recipes using or producing this item will be generated.
     * @param secondaryDustTag The secondary dust tag. Used in Immersive Engineering crushing. Can be null. If null, no recipes using or producing this item will be generated.
     */
    protected void addMetalOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, ResourceLocation rawOre, TagKey<Item> rawOreTag, @Nullable ResourceLocation rawOreBlock, TagKey<Item> rawOreBlockTag, ResourceLocation ingot, TagKey<Item> ingotTag, @Nullable ResourceLocation ingotBlock, TagKey<Item> ingotBlockTag, @Nullable ResourceLocation dust, TagKey<Item> dustTag, @Nullable ResourceLocation crushedOre, @Nullable TagKey<Item> secondaryDustTag) {
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
            CREATE_CRUSHING.add(CREATE_CRUSHING.builder(itemId(ore).getPath(), 250)
                    .addIngredient(Ingredient.of(ore))
                    .addResult(crushedOre)
                    .addResult(crushedOre, 0.75f)
                    .addResult(EXPERIENCE_NUGGET, 0.75f)
                    .addResult(Items.COBBLESTONE, 0.125f));
            CREATE_CRUSHING.add(CREATE_CRUSHING.builder(itemId(deepslateOre).getPath(), 350)
                    .addIngredient(Ingredient.of(deepslateOre))
                    .addResult(crushedOre, 2)
                    .addResult(crushedOre, 0.25f)
                    .addResult(EXPERIENCE_NUGGET, 0.75f)
                    .addResult(Items.COBBLED_DEEPSLATE, 0.125f));
            CREATE_CRUSHING.add(CREATE_CRUSHING.builder(rawOre.getPath(), 400)
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
        //TODO Mekanism Crushing, Enriching
        //TODO Thermal Press, Pulverizer, Smelter
    }

    /**
     * Adds processing for a small flower.
     *
     * @param flower  The flower to be processed.
     * @param output1 The id of the primary output.
     * @param count1  The count of the primary output. Used in Create milling. Some mods may use different, fixed amounts.
     * @param output2 The id of the secondary output. Used in Create milling. Can be null. If null, no recipes using or producing this item will be generated.
     * @param count2  The count of the secondary output. Used in Create milling.
     * @param chance2 The chance that the secondary output will actually appear. Used in Create milling.
     * @param output3 The id of the tertiary output. Used in Create milling. Can be null. If null, no recipes using or producing this item will be generated.
     * @param count3  The count of the tertiary output. Used in Create milling.
     * @param chance3 The chance that the tertiary output will actually appear. Used in Create milling.
     */
    protected void addFlowerProcessing(Item flower, ResourceLocation output1, int count1, @Nullable ResourceLocation output2, int count2, float chance2, @Nullable ResourceLocation output3, int count3, float chance3) {
        ARS_NOUVEAU_CRUSHING.add(ARS_NOUVEAU_CRUSHING.builder(itemId(flower).getPath(), Ingredient.of(flower)).addOutput(output1, 2));
        if (flower instanceof BlockItem bi) {
            BOTANY_POTS_CROP.add(BOTANY_POTS_CROP.builder(toName(flower), Ingredient.of(flower), 1200)
                    .addCategory("dirt")
                    .addDisplay(new DisplayState.Simple(bi.getBlock().defaultBlockState()))
                    .addDrop(flower));
        }
        var milling = CREATE_MILLING.builder(itemId(flower).getPath(), 50)
                .addIngredient(Ingredient.of(flower))
                .addResult(output1, count1);
        if (output2 != null) {
            milling.addResult(output2, count2, chance2);
        }
        if (output3 != null) {
            milling.addResult(output3, count3, chance3);
        }
        CREATE_MILLING.add(milling);
        FARMERS_DELIGHT_CUTTING.add(FARMERS_DELIGHT_CUTTING.builder(toName(flower), Ingredient.of(flower), KNIVES)
                .addResult(output1, 2));
        INTEGRATED_DYNAMICS_MECHANICAL_SQUEEZING.add(INTEGRATED_DYNAMICS_MECHANICAL_SQUEEZING.builder(toName(flower), Ingredient.of(flower), 5)
                .addItem(output1, 4)
                .addItem(output1, 2, 0.5f));
        INTEGRATED_DYNAMICS_SQUEEZING.add(INTEGRATED_DYNAMICS_SQUEEZING.builder(toName(flower), Ingredient.of(flower))
                .addItem(output1, 4));
        //TODO Mekanism Crushing, Enriching, Extracting
        //TODO Thermal Centrifuging, Insolating
    }

    /**
     * Adds processing for a small flower.
     *
     * @param flower  The flower to be processed.
     * @param output1 The id of the primary output.
     * @param count1  The count of the primary output. Used in Create milling. Some mods may use different, fixed amounts.
     * @param output2 The id of the secondary output. Used in Create milling. Can be null. If null, no recipes using or producing this item will be generated.
     * @param count2  The count of the secondary output. Used in Create milling.
     * @param chance2 The chance that the secondary output will actually appear. Used in Create milling.
     */
    protected void addFlowerProcessing(Item flower, ResourceLocation output1, int count1, @Nullable ResourceLocation output2, int count2, float chance2) {
        addFlowerProcessing(flower, output1, count1, output2, count2, chance2, null, 0, 0);
    }

    /**
     * Adds processing for a small flower.
     *
     * @param flower  The flower to be processed.
     * @param output1 The id of the primary output.
     * @param count1  The count of the primary output. Used in Create milling. Some mods may use different, fixed amounts.
     */
    protected void addFlowerProcessing(Item flower, ResourceLocation output1, int count1) {
        addFlowerProcessing(flower, output1, count1, null, 0, 0, null, 0, 0);
    }

    /**
     * Adds processing for a tall flower.
     *
     * @param flower  The flower to be processed.
     * @param output1 The id of the primary output.
     * @param count1  The count of the primary output. Used in Create milling. Some mods may use different, fixed amounts.
     */
    protected void addTallFlowerProcessing(Item flower, ResourceLocation output1, int count1, @Nullable ResourceLocation output2, int count2, float chance2, @Nullable ResourceLocation output3, int count3, float chance3) {
        ARS_NOUVEAU_CRUSHING.add(ARS_NOUVEAU_CRUSHING.builder(itemId(flower).getPath(), Ingredient.of(flower)).addOutput(output1, 4));
        if (flower instanceof BlockItem bi && bi.getBlock() instanceof TallFlowerBlock tfb) {
            BOTANY_POTS_CROP.add(BOTANY_POTS_CROP.builder(toName(flower), Ingredient.of(flower), 1200)
                    .addCategory("dirt")
                    .addDisplay(new DisplayState.Simple(tfb.defaultBlockState().setValue(TallFlowerBlock.HALF, DoubleBlockHalf.LOWER)))
                    .addDisplay(new DisplayState.Simple(tfb.defaultBlockState().setValue(TallFlowerBlock.HALF, DoubleBlockHalf.UPPER)))
                    .addDrop(flower));
        }
        var milling = CREATE_MILLING.builder(itemId(flower).getPath(), 100)
                .addIngredient(Ingredient.of(flower))
                .addResult(output1, count1);
        if (output2 != null) {
            milling.addResult(output2, count2, chance2);
        }
        if (output3 != null) {
            milling.addResult(output3, count3, chance3);
        }
        CREATE_MILLING.add(milling);
        INTEGRATED_DYNAMICS_MECHANICAL_SQUEEZING.add(INTEGRATED_DYNAMICS_MECHANICAL_SQUEEZING.builder(toName(flower), Ingredient.of(flower), 5)
                .addItem(output1, 8)
                .addItem(output1, 2, 0.5f)
                .addItem(output1, 2, 0.5f));
        INTEGRATED_DYNAMICS_SQUEEZING.add(INTEGRATED_DYNAMICS_SQUEEZING.builder(toName(flower), Ingredient.of(flower))
                .addItem(output1, 8));
        //TODO Mekanism Crushing, Enriching, Extracting
        //TODO Thermal Centrifuging, Insolating
    }
    /**
     * Adds processing for a tall flower.
     *
     * @param flower  The flower to be processed.
     * @param output1 The id of the primary output.
     * @param count1  The count of the primary output. Used in Create milling. Some mods may use different, fixed amounts.
     * @param output2 The id of the secondary output. Used in Create milling. Can be null. If null, no recipes using or producing this item will be generated.
     * @param count2  The count of the secondary output. Used in Create milling.
     * @param chance2 The chance that the secondary output will actually appear. Used in Create milling.
     */
    protected void addTallFlowerProcessing(Item flower, ResourceLocation output1, int count1, @Nullable ResourceLocation output2, int count2, float chance2) {
        addTallFlowerProcessing(flower, output1, count1, output2, count2, chance2, null, 0, 0);
    }

    /**
     * Adds processing for a tall flower.
     *
     * @param flower  The flower to be processed.
     * @param output1 The id of the primary output.
     * @param count1  The count of the primary output. Used in Create milling. Some mods may use different, fixed amounts.
     */
    protected void addTallFlowerProcessing(Item flower, ResourceLocation output1, int count1) {
        addTallFlowerProcessing(flower, output1, count1, null, 0, 0, null, 0, 0);
    }

    /**
     * Adds processing for a mushroom.
     *
     * @param mushroom The mushroom to be processed.
     */
    protected void addMushroomProcessing(Item mushroom) {
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
        //TODO Mekanism Crushing
        //TODO Thermal Insolating
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
        //TODO Mekanism Crushing
    }

    /**
     * Adds processing for a wooden block family.
     *
     * @param family    The wooden block family to be processed.
     * @param leaves    The leaves item associated with the wooden block family.
     * @param sapling   The sapling item associated with the wooden block family.
     * @param boat      The boat item associated with the wooden block family.
     * @param chestBoat The chest boat item associated with the wooden block family.
     * @param logs      The logs tag associated with the wooden block family.
     */
    protected void addWoodenProcessing(BlockFamily family, @Nullable Item leaves, @Nullable Item sapling, @Nullable Item boat, @Nullable Item chestBoat, @Nullable TagKey<Item> logs) {
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
        if (door != null) {
            CORAIL_WOODCUTTER_WOODCUTTING.add(CORAIL_WOODCUTTER_WOODCUTTING.builder(toName(door) + "_from_" + toName(planks), Ingredient.of(planks), door));
            FARMERS_DELIGHT_CUTTING.add(FARMERS_DELIGHT_CUTTING.builder(toName(door), Ingredient.of(door), AXE_DIG)
                    .addResult(planks));
            IMMERSIVE_ENGINEERING_SAWMILL.add(IMMERSIVE_ENGINEERING_SAWMILL.builder(toName(door), 800, Ingredient.of(door), planks)
                    .addSecondary(WOOD_DUST, false));
        }
        if (trapdoor != null) {
            CORAIL_WOODCUTTER_WOODCUTTING.add(CORAIL_WOODCUTTER_WOODCUTTING.builder(toName(trapdoor) + "_from_" + toName(planks), Ingredient.of(planks), trapdoor));
            FARMERS_DELIGHT_CUTTING.add(FARMERS_DELIGHT_CUTTING.builder(toName(trapdoor), Ingredient.of(trapdoor), AXE_DIG)
                    .addResult(planks));
        }
        if (button != null) {
            CORAIL_WOODCUTTER_WOODCUTTING.add(CORAIL_WOODCUTTER_WOODCUTTING.builder(toName(button) + "_from_" + toName(planks), Ingredient.of(planks), button));
        }
        if (pressurePlate != null) {
            CORAIL_WOODCUTTER_WOODCUTTING.add(CORAIL_WOODCUTTER_WOODCUTTING.builder(toName(pressurePlate) + "_from_" + toName(planks), Ingredient.of(planks), pressurePlate));
        }
        if (sign != null) {
            CORAIL_WOODCUTTER_WOODCUTTING.add(CORAIL_WOODCUTTER_WOODCUTTING.builder(toName(sign) + "_from_" + toName(planks), Ingredient.of(planks), sign));
            FARMERS_DELIGHT_CUTTING.add(FARMERS_DELIGHT_CUTTING.builder(toName(sign), Ingredient.of(sign), AXE_DIG)
                    .addResult(planks));
        }
        if (leaves != null) {
            BOTANIA_MANA_INFUSION.add(BOTANIA_MANA_INFUSION.builder(toName(leaves) + "_dupe", 2000, Ingredient.of(leaves), leaves, 2)
                    .setCatalyst(CONJURATION_CATALYST));
        }
        if (logs != null) {
            CORAIL_WOODCUTTER_WOODCUTTING.add(CORAIL_WOODCUTTER_WOODCUTTING.builder(toName(planks) + "_from_" + toName(logs), Ingredient.of(logs), planks, 4));
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
        //TODO Mekanism Crushing, Sawing
        //TODO Thermal Insolating, Sawing
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
    }
    //endregion
}
