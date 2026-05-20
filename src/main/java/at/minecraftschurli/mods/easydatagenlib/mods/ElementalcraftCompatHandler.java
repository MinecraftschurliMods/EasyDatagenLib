package at.minecraftschurli.mods.easydatagenlib.mods;

import at.minecraftschurli.mods.easydatagenlib.api.AbstractRecipeBuilder;
import at.minecraftschurli.mods.easydatagenlib.api.AbstractRecipeCompatHandler;
import at.minecraftschurli.mods.easydatagenlib.api.ICompatHandler;
import at.minecraftschurli.mods.easydatagenlib.util.JsonUtil;
import at.minecraftschurli.mods.easydatagenlib.util.PotentiallyAbsentItemStack;
import com.google.gson.JsonObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import static at.minecraftschurli.mods.easydatagenlib.api.ICompatHandler.toName;

public abstract class ElementalcraftCompatHandler<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeCompatHandler<T> {
    protected ElementalcraftCompatHandler(String folder, String namespace, PackOutput output, HolderLookup.Provider registries) {
        super(Identifier.fromNamespaceAndPath("elementalcraft", folder), namespace, output, registries);
    }
    //TODO Binding, Crystallization, Infusion, Inscription, Pure Infusion, Spell Craft

    public static class Grinding extends IO {
        public Grinding(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("grinding", namespace, output, registries);
        }

        public static final class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Grinding(namespace, output, registries);
            }
        }
    }

    public static class Sawing extends IO {
        public Sawing(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("sawing", namespace, output, registries);
        }

        @Override
        public void addLogsProcessing(Item log, Item wood, Item strippedLog, Item strippedWood, Identifier planks, @Nullable Item leaves, @Nullable Item sapling) {
            builder(toName(planks), ingredient(strippedLog, strippedWood), planks, 6, 1000, 3).build();
            builder(toName(strippedLog), ingredient(log), strippedLog, 1000, 0).build();
            builder(toName(strippedWood), ingredient(wood), strippedWood, 1000, 0).build();
        }

        public static final class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Sawing(namespace, output, registries);
            }
        }
    }

    public static abstract class IO extends ElementalcraftCompatHandler<IO.Builder> {
        protected IO(String folder, String namespace, PackOutput output, HolderLookup.Provider registries) {
            super(folder, namespace, output, registries);
        }

        /// @param id            The recipe id to use.
        /// @param input         The input ingredient to use.
        /// @param output        The id of the output item to use.
        /// @param count         The output count to use.
        /// @param patch         The output components to use.
        /// @param elementAmount The element amount to use.
        /// @param luckRatio     The luck ratio to use.
        public Builder builder(String id, Ingredient input, Identifier output, int count, DataComponentPatch patch, int elementAmount, int luckRatio) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, output, count, patch, elementAmount, luckRatio);
        }

        /// @param id            The recipe id to use.
        /// @param input         The input ingredient to use.
        /// @param output        The id of the output item to use.
        /// @param count         The output count to use.
        /// @param elementAmount The element amount to use.
        /// @param luckRatio     The luck ratio to use.
        public Builder builder(String id, Ingredient input, Identifier output, int count, int elementAmount, int luckRatio) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, output, count, DataComponentPatch.EMPTY, elementAmount, luckRatio);
        }

        /// @param id            The recipe id to use.
        /// @param input         The input ingredient to use.
        /// @param output        The id of the output item to use.
        /// @param elementAmount The element amount to use.
        /// @param luckRatio     The luck ratio to use.
        public Builder builder(String id, Ingredient input, Identifier output, int elementAmount, int luckRatio) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, output, 1, elementAmount, luckRatio);
        }

        /// @param id            The recipe id to use.
        /// @param input         The input ingredient to use.
        /// @param output        The output item to use.
        /// @param count         The output count to use.
        /// @param patch         The output components to use.
        /// @param elementAmount The element amount to use.
        /// @param luckRatio     The luck ratio to use.
        public Builder builder(String id, Ingredient input, Item output, int count, DataComponentPatch patch, int elementAmount, int luckRatio) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, itemId(output), count, patch, elementAmount, luckRatio);
        }

        /// @param id            The recipe id to use.
        /// @param input         The input ingredient to use.
        /// @param output        The output item to use.
        /// @param count         The output count to use.
        /// @param elementAmount The element amount to use.
        /// @param luckRatio     The luck ratio to use.
        public Builder builder(String id, Ingredient input, Item output, int count, int elementAmount, int luckRatio) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, output, count, DataComponentPatch.EMPTY, elementAmount, luckRatio);
        }

        /// @param id            The recipe id to use.
        /// @param input         The input ingredient to use.
        /// @param output        The output item to use.
        /// @param elementAmount The element amount to use.
        /// @param luckRatio     The luck ratio to use.
        public Builder builder(String id, Ingredient input, Item output, int elementAmount, int luckRatio) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, output, 1, elementAmount, luckRatio);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final Ingredient input;
            private final PotentiallyAbsentItemStack output;
            private final int elementAmount;
            private final int luckRatio;

            protected Builder(IO provider, Identifier id, Ingredient input, Identifier output, int count, DataComponentPatch patch, int elementAmount, int luckRatio) {
                super(id, provider);
                this.input = input;
                this.output = new PotentiallyAbsentItemStack(output, count, patch);
                this.elementAmount = elementAmount;
                this.luckRatio = luckRatio;
            }

            protected Builder(IO provider, Identifier id, Ingredient input, Identifier output, int count, int elementAmount, int luckRatio) {
                this(provider, id, input, output, count, DataComponentPatch.EMPTY, elementAmount, luckRatio);
            }

            protected Builder(IO provider, Identifier id, Ingredient input, Identifier output, int elementAmount, int luckRatio) {
                this(provider, id, input, output, 1, elementAmount, luckRatio);
            }

            protected Builder(IO provider, Identifier id, Ingredient input, Item output, int count, DataComponentPatch patch, int elementAmount, int luckRatio) {
                this(provider, id, input, itemId(output), count, patch, elementAmount, luckRatio);
            }

            protected Builder(IO provider, Identifier id, Ingredient input, Item output, int count, int elementAmount, int luckRatio) {
                this(provider, id, input, output, count, DataComponentPatch.EMPTY, elementAmount, luckRatio);
            }

            protected Builder(IO provider, Identifier id, Ingredient input, Item output, int elementAmount, int luckRatio) {
                this(provider, id, input, output, 1, elementAmount, luckRatio);
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                json.add("input", JsonUtil.toJson(input, registries));
                json.add("output", output.toJson(registries));
                json.addProperty("element_amount", elementAmount);
                json.addProperty("luck_ratio", luckRatio);
            }
        }
    }
}
