package at.minecraftschurli.mods.easydatagenlib.mods;

import at.minecraftschurli.mods.easydatagenlib.api.AbstractRecipeBuilder;
import at.minecraftschurli.mods.easydatagenlib.api.AbstractRecipeCompatHandler;
import at.minecraftschurli.mods.easydatagenlib.api.ICompatHandler;
import at.minecraftschurli.mods.easydatagenlib.util.JsonUtil;
import com.google.gson.JsonObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.conditions.NotCondition;
import net.neoforged.neoforge.common.conditions.TagEmptyCondition;
import org.jetbrains.annotations.Nullable;

import static at.minecraftschurli.mods.easydatagenlib.api.ICompatHandler.toName;

public abstract class OccultismCompatHandler<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeCompatHandler<T> {
    protected OccultismCompatHandler(String folder, String namespace, PackOutput output, HolderLookup.Provider registries) {
        super(Identifier.fromNamespaceAndPath("occultism", folder), namespace, output, registries);
    }
    //TODO Miner, Ritual, Spirit Fire, Spirit Trade

    public static class Crushing extends OccultismCompatHandler<Crushing.Builder> {
        public Crushing(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("crushing", namespace, output, registries);
        }

        @Override
        public void addMetalOreProcessing(Item ore, Item deepslateOre, TagKey<Item> oreTag, Item rawOre, TagKey<Item> rawOreTag, @Nullable Item rawOreBlock, @Nullable TagKey<Item> rawOreBlockTag, Item ingot, TagKey<Item> ingotTag, @Nullable Item ingotBlock, @Nullable TagKey<Item> ingotBlockTag, @Nullable Item nugget, @Nullable TagKey<Item> nuggetTag, @Nullable Item dust, @Nullable TagKey<Item> dustTag, @Nullable Identifier crushedOre, @Nullable Identifier secondaryIngot, @Nullable Identifier secondaryDust, @Nullable TagKey<Item> secondaryDustTag) {
            if (dustTag != null) {
                builder(toName(dustTag), ingredient(oreTag), ingredient(dustTag), 2)
                    .addCondition(new NotCondition(new TagEmptyCondition<>(oreTag)))
                    .addCondition(new NotCondition(new TagEmptyCondition<>(dustTag))).build();
                builder(toName(dustTag) + "_from_ingot", ingredient(oreTag), ingredient(ingotTag))
                    .ignoreCrushingMultiplier()
                    .addCondition(new NotCondition(new TagEmptyCondition<>(ingotTag)))
                    .addCondition(new NotCondition(new TagEmptyCondition<>(dustTag))).build();
                builder(toName(dustTag) + "_from_raw", ingredient(rawOreTag), ingredient(dustTag), 2)
                    .addCondition(new NotCondition(new TagEmptyCondition<>(rawOreTag)))
                    .addCondition(new NotCondition(new TagEmptyCondition<>(dustTag))).build();
            }
        }

        /// @param id     The recipe id to use.
        /// @param input  The input ingredient to use.
        /// @param output The output ingredient to use.
        /// @param count  The output count to use.
        public Builder builder(String id, Ingredient input, Ingredient output, int count) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, output, count);
        }

        /// @param id     The recipe id to use.
        /// @param input  The input ingredient to use.
        /// @param output The output ingredient to use.
        public Builder builder(String id, Ingredient input, Ingredient output) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, output);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final Ingredient input;
            private final Ingredient output;
            private final int count;
            private int duration = 200;
            private int minTier = -1;
            private boolean ignoreCrushingMultiplier = false;

            protected Builder(Crushing provider, Identifier id, Ingredient input, Ingredient output, int count) {
                super(id, provider);
                this.input = input;
                this.output = output;
                this.count = count;
            }

            protected Builder(Crushing provider, Identifier id, Ingredient input, Ingredient output) {
                this(provider, id, input, output, 1);
            }

            /// Sets the duration of this recipe.
            ///
            /// @param duration The duration to use.
            public Builder setDuration(int duration) {
                this.duration = duration;
                return this;
            }

            /// Sets the min tier of this recipe.
            ///
            /// @param minTier The min tier to use.
            public Builder setMinTier(int minTier) {
                this.minTier = minTier;
                return this;
            }

            /// Sets this recipe's ignoreCrushingMultiplier property to true.
            public Builder ignoreCrushingMultiplier() {
                ignoreCrushingMultiplier = true;
                return this;
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                json.addProperty("crushing_time", duration);
                if (minTier > -1) {
                    json.addProperty("min_tier", minTier);
                }
                if (ignoreCrushingMultiplier) {
                    json.addProperty("ignore_crushing_multiplier", true);
                }
                json.add("ingredient", JsonUtil.toJson(input, registries));
                JsonObject output = JsonUtil.toJson(this.output, registries).getAsJsonObject();
                output.addProperty("count", count);
                json.add("result", output);
            }
        }

        public static class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Crushing(namespace, output, registries);
            }
        }
    }
}
