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
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public abstract class BotaniaCompatHandler<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeCompatHandler<T> {
    public static final Identifier ALCHEMY_CATALYST = Identifier.fromNamespaceAndPath("botania", "alchemy_catalyst");
    public static final Identifier CONJURATION_CATALYST = Identifier.fromNamespaceAndPath("botania", "conjuration_catalyst");

    protected BotaniaCompatHandler(String folder, String namespace, PackOutput output, HolderLookup.Provider registries) {
        super(Identifier.fromNamespaceAndPath("botania", folder), namespace, output, registries);
    }
    //TODO Brew, Elven Trade, Orechid, Orechid Ignem, Petal Apothecary, Pure Daisy, Runic Altar, Terra Plate

    public static class Infusing extends BotaniaCompatHandler<Infusing.Builder> {
        public Infusing(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("mana_infusion", namespace, output, registries);
        }

        /**
         * @param id     The recipe id to use.
         * @param mana   The amount of mana to use.
         * @param input  The input ingredient to use.
         * @param output The id of the output item to use.
         * @param count  The output count to use.
         * @param patch  The output components to use.
         */
        public Builder builder(String id, int mana, Ingredient input, Identifier output, int count, DataComponentPatch patch) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), mana, input, output, count, patch);
        }

        /**
         * @param id     The recipe id to use.
         * @param mana   The amount of mana to use.
         * @param input  The input ingredient to use.
         * @param output The id of the output item to use.
         * @param count  The output count to use.
         */
        public Builder builder(String id, int mana, Ingredient input, Identifier output, int count) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), mana, input, output, count);
        }

        /**
         * @param id     The recipe id to use.
         * @param mana   The amount of mana to use.
         * @param input  The input ingredient to use.
         * @param output The id of the output item to use.
         */
        public Builder builder(String id, int mana, Ingredient input, Identifier output) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), mana, input, output);
        }

        /**
         * @param id     The recipe id to use.
         * @param mana   The amount of mana to use.
         * @param input  The input ingredient to use.
         * @param output The output item to use.
         * @param count  The output count to use.
         * @param patch  The output components to use.
         */
        public Builder builder(String id, int mana, Ingredient input, Item output, int count, DataComponentPatch patch) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), mana, input, output, count, patch);
        }

        /**
         * @param id     The recipe id to use.
         * @param mana   The amount of mana to use.
         * @param input  The input ingredient to use.
         * @param output The output item to use.
         * @param count  The output count to use.
         */
        public Builder builder(String id, int mana, Ingredient input, Item output, int count) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), mana, input, output, count);
        }

        /**
         * @param id     The recipe id to use.
         * @param mana   The amount of mana to use.
         * @param input  The input ingredient to use.
         * @param output The output item to use.
         */
        public Builder builder(String id, int mana, Ingredient input, Item output) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), mana, input, output);
        }

        @Override
        public void addLogsProcessing(Item log, Item wood, Item strippedLog, Item strippedWood, Identifier planks, @Nullable Item leaves, @Nullable Item sapling) {
            if (leaves != null) {
                builder(ICompatHandler.toName(leaves) + "_dupe", 2000, ingredient(leaves), leaves, 2)
                    .setCatalyst(CONJURATION_CATALYST).build();
            }
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final int mana;
            private final Ingredient input;
            private final PotentiallyAbsentItemStack output;
            private @Nullable String group;
            private @Nullable Identifier catalyst;

            protected Builder(Infusing provider, Identifier id, int mana, Ingredient input, Identifier output, int count, DataComponentPatch patch) {
                super(id, provider);
                this.mana = mana;
                this.input = input;
                this.output = new PotentiallyAbsentItemStack(output, count, patch);
            }

            protected Builder(Infusing provider, Identifier id, int mana, Ingredient input, Identifier output, int count) {
                this(provider, id, mana, input, output, count, DataComponentPatch.EMPTY);
            }

            protected Builder(Infusing provider, Identifier id, int mana, Ingredient input, Identifier output) {
                this(provider, id, mana, input, output, 1);
            }

            protected Builder(Infusing provider, Identifier id, int mana, Ingredient input, Item output, int count, DataComponentPatch patch) {
                this(provider, id, mana, input, itemId(output), count, patch);
            }

            protected Builder(Infusing provider, Identifier id, int mana, Ingredient input, Item output, int count) {
                this(provider, id, mana, input, output, count, DataComponentPatch.EMPTY);
            }

            protected Builder(Infusing provider, Identifier id, int mana, Ingredient input, Item output) {
                this(provider, id, mana, input, output, 1);
            }

            /**
             * Sets the group of this recipe.
             *
             * @param group The group to use.
             */
            public Builder setGroup(String group) {
                this.group = group;
                return this;
            }

            /**
             * Sets the catalyst of this recipe.
             *
             * @param catalyst The catalyst to use.
             */
            public Builder setCatalyst(Identifier catalyst) {
                this.catalyst = catalyst;
                return this;
            }

            /**
             * Sets the catalyst of this recipe.
             *
             * @param catalyst The catalyst to use.
             */
            public Builder setCatalyst(Block catalyst) {
                return setCatalyst(blockId(catalyst));
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                json.addProperty("mana", mana);
                json.add("input", JsonUtil.toJson(input, registries));
                json.add("output", output.toJson(registries));
                if (group != null) {
                    json.addProperty("group", group);
                }
                if (catalyst != null) {
                    JsonObject c = new JsonObject();
                    c.addProperty("type", "block");
                    c.addProperty("block", catalyst.toString());
                    json.add("catalyst", c);
                }
            }
        }

        public static final class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Infusing(namespace, output, registries);
            }
        }
    }
}
