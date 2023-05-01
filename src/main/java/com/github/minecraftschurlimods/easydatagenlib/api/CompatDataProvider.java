package com.github.minecraftschurlimods.easydatagenlib.api;

import com.github.minecraftschurlimods.easydatagenlib.mods.ArsNouveauDataProvider;
import com.github.minecraftschurlimods.easydatagenlib.mods.BotaniaDataProvider;
import com.github.minecraftschurlimods.easydatagenlib.mods.BotanyPotsDataProvider;
import com.github.minecraftschurlimods.easydatagenlib.mods.CreateDataProvider;
import com.github.minecraftschurlimods.easydatagenlib.mods.ImmersiveEngineeringDataProvider;
import com.github.minecraftschurlimods.easydatagenlib.util.botanypots.DisplayState;
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
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.common.crafting.conditions.TagEmptyCondition;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

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
    public final ImmersiveEngineeringDataProvider.ArcFurnace IMMERSIVE_ENGINEERING_ARC_FURNACE;
    public final ImmersiveEngineeringDataProvider.Cloche IMMERSIVE_ENGINEERING_CLOCHE;
    public final ImmersiveEngineeringDataProvider.Crusher IMMERSIVE_ENGINEERING_CRUSHER;
    public final ImmersiveEngineeringDataProvider.Sawmill IMMERSIVE_ENGINEERING_SAWMILL;

    public CompatDataProvider(String namespace, DataGenerator generator, boolean runServer, boolean runClient) {
        ARS_NOUVEAU_CRUSHING = addServer(new ArsNouveauDataProvider.Crushing(namespace, generator));
        ARS_NOUVEAU_GLYPH = addServer(new ArsNouveauDataProvider.Glyph(namespace, generator));
        ARS_NOUVEAU_IMBUEMENT = addServer(new ArsNouveauDataProvider.Imbuement(namespace, generator));
        BOTANIA_MANA_INFUSION = addServer(new BotaniaDataProvider.ManaInfusion(namespace, generator));
        BOTANY_POTS_CROP = addServer(new BotanyPotsDataProvider.Crop(namespace, generator));
        BOTANY_POTS_SOIL = addServer(new BotanyPotsDataProvider.Soil(namespace, generator));
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
        IMMERSIVE_ENGINEERING_ARC_FURNACE = addServer(new ImmersiveEngineeringDataProvider.ArcFurnace(namespace, generator));
        IMMERSIVE_ENGINEERING_CLOCHE = addServer(new ImmersiveEngineeringDataProvider.Cloche(namespace, generator));
        IMMERSIVE_ENGINEERING_CRUSHER = addServer(new ImmersiveEngineeringDataProvider.Crusher(namespace, generator));
        IMMERSIVE_ENGINEERING_SAWMILL = addServer(new ImmersiveEngineeringDataProvider.Sawmill(namespace, generator));
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
    protected static final ResourceLocation ALCHEMY_CATALYST = new ResourceLocation("botania", "alchemy_catalyst");
    protected static final ResourceLocation CONJURATION_CATALYST = new ResourceLocation("botania", "conjuration_catalyst");
    protected static final ResourceLocation EXPERIENCE_NUGGET = new ResourceLocation("create", "experience_nugget");
    protected static final ResourceLocation SHROOMLIGHT = new ResourceLocation("minecraft", "shroomlight");
    protected static final Ingredient SLAG = Ingredient.of(TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation("forge", "slag")));
    protected static final Ingredient WOOD_DUST = Ingredient.of(TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation("forge", "dusts/wood")));
    protected static final Ingredient MUSHROOM_SOIL = Ingredient.of(Items.MYCELIUM, Items.PODZOL);

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
        }
        //TODO Mekanism Enriching
        //TODO Twilight Forest Crumbling
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
                    .addResult(EXPERIENCE_NUGGET, 0.75f));
        }
        IMMERSIVE_ENGINEERING_ARC_FURNACE.add(IMMERSIVE_ENGINEERING_ARC_FURNACE.builder(toName(dustTag), 100, 51200, Ingredient.of(dustTag))
                .addResult(Ingredient.of(ingotTag)));
        IMMERSIVE_ENGINEERING_ARC_FURNACE.add(IMMERSIVE_ENGINEERING_ARC_FURNACE.builder(toName(oreTag), 200, 102400, Ingredient.of(oreTag))
                .addResult(Ingredient.of(ingotTag), 2)
                .setSlag(SLAG));
        IMMERSIVE_ENGINEERING_ARC_FURNACE.add(IMMERSIVE_ENGINEERING_ARC_FURNACE.builder(toName(rawOreBlockTag), 900, 230400, Ingredient.of(rawOreBlockTag))
                .addResult(Ingredient.of(ingotTag), 13)
                .addSecondary(Ingredient.of(ingotTag), 0.5f));
        IMMERSIVE_ENGINEERING_ARC_FURNACE.add(IMMERSIVE_ENGINEERING_ARC_FURNACE.builder(toName(rawOreTag), 100, 25600, Ingredient.of(rawOreTag))
                .addResult(Ingredient.of(ingotTag))
                .addSecondary(Ingredient.of(ingotTag), 0.5f));
        IMMERSIVE_ENGINEERING_CRUSHER.add(IMMERSIVE_ENGINEERING_CRUSHER.builder(toName(ingotTag), 3000, Ingredient.of(ingotTag), Ingredient.of(dustTag)));
        if (secondaryDustTag == null) {
            IMMERSIVE_ENGINEERING_CRUSHER.add(IMMERSIVE_ENGINEERING_CRUSHER.builder(toName(oreTag), 6000, Ingredient.of(oreTag), Ingredient.of(dustTag), 2));
        } else {
            IMMERSIVE_ENGINEERING_CRUSHER.add(IMMERSIVE_ENGINEERING_CRUSHER.builder(toName(oreTag), 6000, Ingredient.of(oreTag), Ingredient.of(dustTag), 2)
                    .addSecondary(Ingredient.of(secondaryDustTag), 0.1f)
                    .addCondition(new NotCondition(new TagEmptyCondition(secondaryDustTag.location()))));
            IMMERSIVE_ENGINEERING_CRUSHER.add(IMMERSIVE_ENGINEERING_CRUSHER.builder(toName(oreTag) + "_without_secondary", 6000, Ingredient.of(oreTag), Ingredient.of(dustTag), 2)
                    .addCondition(new TagEmptyCondition(secondaryDustTag.location())));
        }
        IMMERSIVE_ENGINEERING_CRUSHER.add(IMMERSIVE_ENGINEERING_CRUSHER.builder(toName(rawOreBlockTag), 54000, Ingredient.of(rawOreBlockTag), Ingredient.of(rawOreTag), 12));
        IMMERSIVE_ENGINEERING_CRUSHER.add(IMMERSIVE_ENGINEERING_CRUSHER.builder(toName(rawOreTag), 6000, Ingredient.of(rawOreTag), Ingredient.of(dustTag))
                .addSecondary(Ingredient.of(dustTag), 1 / 3f));
        //TODO Integrated Dynamics Squeezing
        //TODO Mekanism Crushing, Enriching
        //TODO Occultism Ritual Dummy
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
        //TODO Elementalcraft Grinding
        //TODO Farmer's Delight Cutting
        //TODO Integrated Dynamics Squeezing
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
        //TODO Ars Nouveau Crushing - awaiting response from mod author
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
        //TODO Elementalcraft Grinding
        //TODO Integrated Dynamics Squeezing
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
     */
    protected void addWoodenProcessing(BlockFamily family, @Nullable Item leaves, @Nullable Item sapling, @Nullable Item boat, @Nullable Item chestBoat) {
        Item planks = family.getBaseBlock().asItem();
        Item slab = family.get(BlockFamily.Variant.SLAB).asItem();
        Item stairs = family.get(BlockFamily.Variant.STAIRS).asItem();
        if (leaves != null) {
            BOTANIA_MANA_INFUSION.add(BOTANIA_MANA_INFUSION.builder(toName(leaves) + "_dupe", 2000, Ingredient.of(leaves), leaves, 2)
                    .setCatalyst(CONJURATION_CATALYST));
        }
        //TODO Corail Woodcutter Woodcutting
        //TODO Elementalcraft Cutting
        //TODO Farmer's Delight Cutting
        //TODO Hexerei Cutting
        if (family.getVariants().containsKey(BlockFamily.Variant.DOOR)) {
            Item door = family.get(BlockFamily.Variant.DOOR).asItem();
            IMMERSIVE_ENGINEERING_SAWMILL.add(IMMERSIVE_ENGINEERING_SAWMILL.builder(toName(door), 800, Ingredient.of(door), planks)
                    .addSecondary(WOOD_DUST, false));
        }
        IMMERSIVE_ENGINEERING_SAWMILL.add(IMMERSIVE_ENGINEERING_SAWMILL.builder(toName(slab), 800, Ingredient.of(planks), slab, 2)
                .addSecondary(WOOD_DUST, false));
        IMMERSIVE_ENGINEERING_SAWMILL.add(IMMERSIVE_ENGINEERING_SAWMILL.builder(toName(stairs), 1600, Ingredient.of(stairs), planks)
                .addSecondary(WOOD_DUST, false));
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
        IMMERSIVE_ENGINEERING_SAWMILL.add(IMMERSIVE_ENGINEERING_SAWMILL.builder(toName(log), 1600, Ingredient.of(log, wood), planks, 6)
                .setStripped(Ingredient.of(strippedLog))
                .addSecondary(WOOD_DUST, true)
                .addSecondary(WOOD_DUST, false));
        IMMERSIVE_ENGINEERING_SAWMILL.add(IMMERSIVE_ENGINEERING_SAWMILL.builder(toName(strippedLog), 800, Ingredient.of(strippedLog), planks, 6)
                .addSecondary(WOOD_DUST, false));
    }
    //endregion
}
