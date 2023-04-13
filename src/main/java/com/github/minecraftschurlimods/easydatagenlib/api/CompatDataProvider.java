package com.github.minecraftschurlimods.easydatagenlib.api;

import com.github.minecraftschurlimods.easydatagenlib.mods.ArsNouveauDataProvider;
import com.github.minecraftschurlimods.easydatagenlib.mods.CreateDataProvider;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;
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

    public CompatDataProvider(String namespace, DataGenerator generator, boolean runServer, boolean runClient) {
        ARS_NOUVEAU_CRUSHING = addServer(new ArsNouveauDataProvider.Crushing(namespace, generator));
        ARS_NOUVEAU_GLYPH = addServer(new ArsNouveauDataProvider.Glyph(namespace, generator));
        ARS_NOUVEAU_IMBUEMENT = addServer(new ArsNouveauDataProvider.Imbuement(namespace, generator));
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
    protected static final ResourceLocation EXPERIENCE_NUGGET = new ResourceLocation("create", "experience_nugget");

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
     * Adds processing for a stone. This is mainly intended for stone types similar to vanilla's andesite, diorite and granite.
     *
     * @param stone          The stone to be processed.
     * @param polishedStone  The polished variant of the stone.
     */
    protected void addStoneProcessing(Item stone, ResourceLocation polishedStone) {
        //TODO Botany Pots Soil
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
        //TODO Botany Pots Soil
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
     * @param ore          The ore to be processed.
     * @param deepslateOre The deepslate ore to be processed.
     * @param rawOre       The raw ore item.
     * @param rawOreBlock  The raw ore block item. Can be null. If null, no recipes using or producing this item will be generated.
     * @param ingot        The id of the ingot item.
     * @param ingotBlock   The id of the ingot block item. Can be null. If null, no recipes using or producing this item will be generated.
     * @param dust         The id of the dust item. Used in various mods for ore duplication. Can be null. If null, no recipes using or producing this item will be generated.
     * @param crushedOre   The id of the crushed ore item. Used in Create crushing. Can be null. If null, no recipes using or producing this item will be generated.
     */
    protected void addMetalOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, ResourceLocation rawOre, TagKey<Item> rawOreTag, @Nullable ResourceLocation rawOreBlock, TagKey<Item> rawOreBlockTag, ResourceLocation ingot, TagKey<Item> ingotTag, @Nullable ResourceLocation ingotBlock, TagKey<Item> ingotBlockTag, @Nullable ResourceLocation dust, TagKey<Item> dustTag, @Nullable ResourceLocation crushedOre) {
        //TODO Botany Pots Soil
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
        //TODO Immersive Engineering Arc Furnace, Crusher
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
        //TODO Botany Pots Crop
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
        //TODO Botany Pots Crop
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
        //TODO Botany Pots Crop
        //TODO Immersive Engineering Cloche
        //TODO Mekanism Crushing
        //TODO Thermal Insolating
    }

    /**
     * Adds processing for a fungus and roots type.
     *
     * @param fungus The fungus to be processed.
     * @param roots  The roots to be processed.
     */
    protected void addFungusAndRootsProcessing(Item fungus, Item roots) {
        //TODO Botany Pots Crop
        //TODO Mekanism Crushing
    }

    /**
     * Adds processing for a wooden block family.
     *
     * @param family       The wooden block family to be processed.
     * @param log          The log item associated with the wooden block family.
     * @param wood         The wood item associated with the wooden block family.
     * @param strippedLog  The stripped log item associated with the wooden block family.
     * @param strippedWood The stripped wood item associated with the wooden block family.
     * @param logs         The logs tag associated with the wooden block family.
     * @param leaves       The leaves item associated with the wooden block family.
     * @param sapling      The sapling item associated with the wooden block family.
     * @param boat         The boat item associated with the wooden block family.
     * @param chestBoat    The chest boat item associated with the wooden block family.
     */
    protected void addWoodenProcessing(BlockFamily family, @Nullable Item log, @Nullable Item wood, @Nullable Item strippedLog, @Nullable Item strippedWood, TagKey<Item> logs, @Nullable Item leaves, @Nullable Item sapling, @Nullable Item boat, @Nullable Item chestBoat) {
        //TODO Botania Mana Infusion
        //TODO Botany Pots Crop
        //TODO Corail Woodcutter Woodcutting
        if (strippedLog != null) {
            if (log != null) {
                CREATE_CUTTING.add(CREATE_CUTTING.builder(itemId(log).getPath(), 50)
                        .addIngredient(Ingredient.of(log))
                        .addResult(strippedLog));
            }
            CREATE_CUTTING.add(CREATE_CUTTING.builder(itemId(strippedLog).getPath(), 50)
                    .addIngredient(Ingredient.of(strippedLog))
                    .addResult(family.getBaseBlock().asItem(), 6));
        }
        if (strippedWood != null) {
            if (wood != null) {
                CREATE_CUTTING.add(CREATE_CUTTING.builder(itemId(wood).getPath(), 50)
                        .addIngredient(Ingredient.of(wood))
                        .addResult(strippedWood));
            }
            CREATE_CUTTING.add(CREATE_CUTTING.builder(itemId(strippedWood).getPath(), 50)
                    .addIngredient(Ingredient.of(strippedWood))
                    .addResult(family.getBaseBlock().asItem(), 6));
        }
        //TODO Elementalcraft Cutting
        //TODO Farmer's Delight Cutting
        //TODO Hexerei Cutting
        //TODO Immersive Engineering Sawmill
        //TODO Mekanism Crushing, Sawing
        //TODO Thermal Insolating, Sawing
    }
    //endregion
}
