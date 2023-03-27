package com.github.minecraftschurlimods.easydatagenlib.api;

import com.github.minecraftschurlimods.easydatagenlib.mods.CreateDataProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public abstract class CompatDataProvider {
    //region PROVIDERS
    //@formatter:off
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
    //@formatter:on
    //endregion

    public CompatDataProvider(String namespace, DataGenerator generator, boolean runServer, boolean runClient) {
        CREATE_COMPACTING = new CreateDataProvider.Compacting(namespace, generator);
        CREATE_CRUSHING = new CreateDataProvider.Crushing(namespace, generator);
        CREATE_CUTTING = new CreateDataProvider.Cutting(namespace, generator);
        CREATE_DEPLOYING = new CreateDataProvider.Deploying(namespace, generator);
        CREATE_EMPTYING = new CreateDataProvider.Emptying(namespace, generator);
        CREATE_FILLING = new CreateDataProvider.Filling(namespace, generator);
        CREATE_HAUNTING = new CreateDataProvider.Haunting(namespace, generator);
        CREATE_ITEM_APPLICATION = new CreateDataProvider.ItemApplication(namespace, generator);
        CREATE_MECHANICAL_CRAFTING = new CreateDataProvider.MechanicalCrafting(namespace, generator);
        CREATE_MILLING = new CreateDataProvider.Milling(namespace, generator);
        CREATE_MIXING = new CreateDataProvider.Mixing(namespace, generator);
        CREATE_PRESSING = new CreateDataProvider.Pressing(namespace, generator);
        CREATE_SANDPAPER_POLISHING = new CreateDataProvider.SandpaperPolishing(namespace, generator);
        CREATE_SEQUENCED_ASSEMBLY  = new CreateDataProvider.SequencedAssembly(namespace, generator);
        CREATE_SPLASHING = new CreateDataProvider.Splashing(namespace, generator);
        generator.addProvider(runServer, CREATE_COMPACTING);
        generator.addProvider(runServer, CREATE_CRUSHING);
        generator.addProvider(runServer, CREATE_CUTTING);
        generator.addProvider(runServer, CREATE_DEPLOYING);
        generator.addProvider(runServer, CREATE_EMPTYING);
        generator.addProvider(runServer, CREATE_FILLING);
        generator.addProvider(runServer, CREATE_HAUNTING);
        generator.addProvider(runServer, CREATE_ITEM_APPLICATION);
        generator.addProvider(runServer, CREATE_MECHANICAL_CRAFTING);
        generator.addProvider(runServer, CREATE_MILLING);
        generator.addProvider(runServer, CREATE_MIXING);
        generator.addProvider(runServer, CREATE_PRESSING);
        generator.addProvider(runServer, CREATE_SANDPAPER_POLISHING);
        generator.addProvider(runServer, CREATE_SEQUENCED_ASSEMBLY);
        generator.addProvider(runServer, CREATE_SPLASHING);
        generate();
    }

    public abstract void generate();
    //region HELPERS
    private static final ResourceLocation EXPERIENCE_NUGGET = new ResourceLocation("create", "experience_nugget");

    /**
     * Shortcut to get an item's registry name.
     *
     * @param item The item to get the registry name for.
     * @return The registry name of the given item.
     */
    @SuppressWarnings("ConstantConditions")
    protected ResourceLocation itemId(Item item) {
        return ForgeRegistries.ITEMS.getKey(item);
    }

    /**
     * Adds processing for a flower.
     *
     * @param flower  The item to be processed.
     * @param output1 The id of the primary output.
     * @param count1  The count of the primary output. Used in Create milling.
     * @param output2 The id of the secondary output. Used in Create milling.
     * @param count2  The count of the secondary output. Used in Create milling.
     * @param chance2 The chance that the secondary output will actually appear. Used in Create milling.
     * @param output3 The id of the tertiary output. Used in Create milling.
     * @param count3  The count of the tertiary output. Used in Create milling.
     * @param chance3 The chance that the tertiary output will actually appear. Used in Create milling.
     */
    protected void addFlowerProcessing(Item flower, ResourceLocation output1, int count1, @Nullable ResourceLocation output2, int count2, float chance2, @Nullable ResourceLocation output3, int count3, float chance3) {
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
    }

    /**
     * Adds processing for a flower.
     *
     * @param flower  The item to be processed.
     * @param output1 The id of the primary output.
     * @param count1  The count of the primary output. Used in Create milling.
     * @param output2 The id of the secondary output. Used in Create milling.
     * @param count2  The count of the secondary output. Used in Create milling.
     * @param chance2 The chance that the secondary output will actually appear. Used in Create milling.
     */
    protected void addFlowerProcessing(Item flower, ResourceLocation output1, int count1, @Nullable ResourceLocation output2, int count2, float chance2) {
        addFlowerProcessing(flower, output1, count1, output2, count2, chance2, null, 0, 0);
    }

    /**
     * Adds processing for a flower.
     *
     * @param flower  The item to be processed.
     * @param output1 The id of the primary output.
     * @param count1  The count of the primary output. Used in Create milling.
     */
    protected void addFlowerProcessing(Item flower, ResourceLocation output1, int count1) {
        addFlowerProcessing(flower, output1, count1, null, 0, 0, null, 0, 0);
    }

    /**
     * Adds processing for a gem ore, such as diamonds or emeralds.
     *
     * @param ore          The ore item to be processed.
     * @param deepslateOre The deepslate ore item to be processed.
     * @param output       The id of the output.
     */
    protected void addGemOreProcessing(Item ore, Item deepslateOre, ResourceLocation output) {
        CREATE_CRUSHING.add(CREATE_CRUSHING.builder(itemId(ore).getPath(), 350)
                .addIngredient(Ingredient.of(ore))
                .addResult(output)
                .addResult(output, 0.75f)
                .addResult(EXPERIENCE_NUGGET, 0.75f)
                .addResult(Items.COBBLESTONE, 0.125f));
        CREATE_CRUSHING.add(CREATE_CRUSHING.builder(itemId(deepslateOre).getPath(), 450)
                .addIngredient(Ingredient.of(deepslateOre))
                .addResult(output, 2)
                .addResult(output, 0.25f)
                .addResult(EXPERIENCE_NUGGET, 0.75f)
                .addResult(Items.COBBLED_DEEPSLATE, 0.125f));
    }
    //endregion
}
