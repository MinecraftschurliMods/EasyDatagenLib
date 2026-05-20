package at.minecraftschurli.mods.easydatagenlib.mods;

import at.minecraftschurli.mods.easydatagenlib.api.AbstractRecipeBuilder;
import at.minecraftschurli.mods.easydatagenlib.api.AbstractRecipeCompatHandler;
import at.minecraftschurli.mods.easydatagenlib.api.ICompatHandler;
import at.minecraftschurli.mods.easydatagenlib.util.JsonUtil;
import at.minecraftschurli.mods.easydatagenlib.util.PotentiallyAbsentItemStack;
import com.google.gson.JsonObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import static at.minecraftschurli.mods.easydatagenlib.api.ICompatHandler.toName;

public abstract class CorailWoodcutterCompatHandler<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeCompatHandler<T> {
    protected CorailWoodcutterCompatHandler(String folder, String namespace, PackOutput output, HolderLookup.Provider registries) {
        super(Identifier.fromNamespaceAndPath("corail_woodcutter", folder), namespace, output, registries);
    }

    public static class Sawing extends CorailWoodcutterCompatHandler<Sawing.Builder> {
        public Sawing(String namespace, PackOutput output, HolderLookup.Provider registries) {
            super("woodcutting", namespace, output, registries);
        }

        /// @param id     The recipe id to use.
        /// @param input  The input ingredient to use.
        /// @param output The id of the output item to use.
        /// @param count  The output count to use.
        public Builder builder(String id, Ingredient input, Identifier output, int count) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, output, count);
        }

        /// @param id     The recipe id to use.
        /// @param input  The input ingredient to use.
        /// @param output The id of the output item to use.
        public Builder builder(String id, Ingredient input, Identifier output) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, output);
        }

        /// @param id     The recipe id to use.
        /// @param input  The input ingredient to use.
        /// @param output The output item to use.
        /// @param count  The output count to use.
        public Builder builder(String id, Ingredient input, Item output, int count) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, output, count);
        }

        /// @param id     The recipe id to use.
        /// @param input  The input ingredient to use.
        /// @param output The output item to use.
        public Builder builder(String id, Ingredient input, Item output) {
            return new Builder(this, Identifier.fromNamespaceAndPath(namespace, id), input, output);
        }

        @Override
        public void addWoodenProcessing(BlockFamily family, @Nullable Item boat, @Nullable Item chestBoat, @Nullable TagKey<Item> logs) {
            Item planks = family.getBaseBlock().asItem();
            if (family.getVariants().containsKey(BlockFamily.Variant.SLAB)) {
                Item slab = family.get(BlockFamily.Variant.SLAB).asItem();
                builder(toName(slab) + "_from_" + toName(planks), ingredient(planks), slab, 2).build();
                if (logs != null) {
                    builder(toName(slab) + "_from_" + toName(logs), ingredient(logs), slab, 8).build();
                }
            }
            if (family.getVariants().containsKey(BlockFamily.Variant.STAIRS)) {
                Item stairs = family.get(BlockFamily.Variant.STAIRS).asItem();
                builder(toName(stairs) + "_from_" + toName(planks), ingredient(planks), stairs).build();
                if (logs != null) {
                    builder(toName(stairs) + "_from_" + toName(logs), ingredient(logs), stairs, 4).build();
                }
            }
            if (family.getVariants().containsKey(BlockFamily.Variant.FENCE)) {
                Item fence = family.get(BlockFamily.Variant.FENCE).asItem();
                builder(toName(fence) + "_from_" + toName(planks), ingredient(planks), fence).build();
                if (logs != null) {
                    builder(toName(fence) + "_from_" + toName(logs), ingredient(logs), fence, 4).build();
                }
            }
            if (family.getVariants().containsKey(BlockFamily.Variant.FENCE_GATE)) {
                Item fenceGate = family.get(BlockFamily.Variant.FENCE_GATE).asItem();
                if (logs != null) {
                    builder(toName(fenceGate) + "_from_" + toName(logs), ingredient(logs), fenceGate).build();
                }
            }
            if (family.getVariants().containsKey(BlockFamily.Variant.DOOR)) {
                Item door = family.get(BlockFamily.Variant.DOOR).asItem();
                builder(toName(door) + "_from_" + toName(planks), ingredient(planks), door).build();
                if (logs != null) {
                    builder(toName(door) + "_from_" + toName(logs), ingredient(logs), door, 4).build();
                }
            }
            if (family.getVariants().containsKey(BlockFamily.Variant.TRAPDOOR)) {
                Item trapdoor = family.get(BlockFamily.Variant.TRAPDOOR).asItem();
                builder(toName(trapdoor) + "_from_" + toName(planks), ingredient(planks), trapdoor).build();
                if (logs != null) {
                    builder(toName(trapdoor) + "_from_" + toName(logs), ingredient(logs), trapdoor, 4).build();
                }
            }
            if (family.getVariants().containsKey(BlockFamily.Variant.BUTTON)) {
                Item button = family.get(BlockFamily.Variant.BUTTON).asItem();
                builder(toName(button) + "_from_" + toName(planks), ingredient(planks), button).build();
                if (logs != null) {
                    builder(toName(button) + "_from_" + toName(logs), ingredient(logs), button, 4).build();
                }
            }
            if (family.getVariants().containsKey(BlockFamily.Variant.PRESSURE_PLATE)) {
                Item pressurePlate = family.get(BlockFamily.Variant.PRESSURE_PLATE).asItem();
                builder(toName(pressurePlate) + "_from_" + toName(planks), ingredient(planks), pressurePlate).build();
                if (logs != null) {
                    builder(toName(pressurePlate) + "_from_" + toName(logs), ingredient(logs), pressurePlate, 4).build();
                }
            }
            if (family.getVariants().containsKey(BlockFamily.Variant.SIGN)) {
                Item sign = family.get(BlockFamily.Variant.SIGN).asItem();
                builder(toName(sign) + "_from_" + toName(planks), ingredient(planks), sign).build();
                if (logs != null) {
                    builder(toName(sign) + "_from_" + toName(logs), ingredient(logs), sign, 4).build();
                }
            }
            if (logs != null) {
                builder(toName(planks) + "_from_" + toName(logs), ingredient(logs), planks, 4).build();
                if (boat != null) {
                    builder(toName(boat) + "_from_" + toName(logs), ingredient(logs), boat).build();
                }
            }
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final Ingredient input;
            private final PotentiallyAbsentItemStack output;

            protected Builder(Sawing provider, Identifier id, Ingredient input, Identifier output, int count) {
                super(id, provider);
                this.input = input;
                this.output = new PotentiallyAbsentItemStack(output, count); // doesn't support NBT
            }

            protected Builder(Sawing provider, Identifier id, Ingredient input, Identifier output) {
                this(provider, id, input, output, 1);
            }

            protected Builder(Sawing provider, Identifier id, Ingredient input, Item output, int count) {
                this(provider, id, input, itemId(output), count);
            }

            protected Builder(Sawing provider, Identifier id, Ingredient input, Item output) {
                this(provider, id, input, output, 1);
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                json.add("ingredient", JsonUtil.toJson(input, registries));
                json.addProperty("result", output.item.toString());
                json.addProperty("count", output.count);
            }
        }

        public static final class Factory implements ICompatHandler.Factory {
            @Override
            public ICompatHandler create(String namespace, PackOutput output, HolderLookup.Provider registries) {
                return new Sawing(namespace, output, registries);
            }
        }
    }
}
