package com.github.minecraftschurlimods.easydatagenlib.util;

import com.google.common.collect.ImmutableList;
import com.google.gson.*;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * {@see https://github.com/Creators-of-Create/Create/blob/mc1.18/dev/src/main/java/com/simibubi/create/foundation/fluid/FluidIngredient.java}
 */
public abstract class FluidIngredient implements Predicate<FluidStack>, JsonSerializable {
    public static final FluidIngredient EMPTY = FluidIngredient.of(new FluidStack(Fluids.EMPTY, 0));
    private static final Gson GSON = new GsonBuilder().create();
    private List<FluidStack> matchingFluidStacks;
    protected int amount;

    protected abstract void read(JsonObject json);

    protected abstract void write(JsonObject json);

    protected abstract List<FluidStack> getMatches();

    public int getAmount() {
        return amount;
    }

    public List<FluidStack> getMatchingFluidStacks() {
        return matchingFluidStacks != null ? matchingFluidStacks : (matchingFluidStacks = getMatches());
    }

    public static FluidIngredient of(TagKey<Fluid> tag, int amount) {
        FluidTagIngredient ingredient = new FluidTagIngredient();
        ingredient.tag = tag;
        ingredient.amount = amount;
        return ingredient;
    }

    public static FluidIngredient of(Fluid fluid, int amount) {
        FluidStackIngredient ingredient = new FluidStackIngredient();
        ingredient.fluid = new FluidStack(fluid, amount);
        return ingredient;
    }

    public static FluidIngredient of(FluidStack fluidStack) {
        FluidStackIngredient ingredient = new FluidStackIngredient();
        ingredient.fluid = fluidStack.copy();
        if (fluidStack.hasTag()) {
            ingredient.tag = fluidStack.getTag();
        }
        return ingredient;
    }

    public static boolean isFluidIngredient(@Nullable JsonElement je) {
        if (je == null || je.isJsonNull() || !je.isJsonObject()) return false;
        JsonObject json = je.getAsJsonObject();
        return json.has("fluidTag") || json.has("fluid");
    }

    public static FluidIngredient fromJson(@Nullable JsonElement je) {
        if (!isFluidIngredient(je)) throw new JsonSyntaxException("Invalid fluid ingredient: " + je);
        JsonObject json = je.getAsJsonObject();
        FluidIngredient ingredient = json.has("fluidTag") ? new FluidTagIngredient() : new FluidStackIngredient();
        ingredient.read(json);
        if (!json.has("amount")) throw new JsonSyntaxException("Fluid ingredient has to define an amount");
        ingredient.amount = GsonHelper.getAsInt(json, "amount");
        return ingredient;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        write(json);
        json.addProperty("amount", amount);
        return json;
    }

    public static class FluidStackIngredient extends FluidIngredient {
        protected FluidStack fluid;
        protected CompoundTag tag = new CompoundTag();

        @Override
        public boolean test(FluidStack t) {
            if (!t.getFluid().isSame(fluid.getFluid())) return false;
            if (t.getAmount() < fluid.getAmount()) return false;
            if (tag.isEmpty()) return true;
            CompoundTag tag = t.getOrCreateTag();
            return tag.copy().merge(this.tag).equals(tag);
        }

        @Override
        protected void read(JsonObject json) {
            ResourceLocation id = new ResourceLocation(GsonHelper.getAsString(json, "fluid"));
            Fluid fluid = BuiltInRegistries.FLUID.get(id);
            if (fluid == Fluids.EMPTY) throw new JsonSyntaxException("Unknown fluid '" + id + "'");
            this.fluid = new FluidStack(fluid, GsonHelper.getAsInt(json, "amount"));
            if (json.has("nbt")) {
                try {
                    JsonElement element = json.get("nbt");
                    tag = TagParser.parseTag(element.isJsonObject() ? GSON.toJson(element) : GsonHelper.convertToString(element, "nbt"));
                } catch (CommandSyntaxException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void write(JsonObject json) {
            json.addProperty("fluid", Objects.requireNonNull(BuiltInRegistries.FLUID.getKey(fluid.getFluid())).toString());
            if (!tag.isEmpty()) {
                json.add("nbt", JsonParser.parseString(tag.toString()));
            }
        }

        @Override
        protected List<FluidStack> getMatches() {
            return ImmutableList.of(tag.isEmpty() ? new FluidStack(fluid.getFluid(), amount) : new FluidStack(fluid.getFluid(), amount, tag));
        }
    }

    public static class FluidTagIngredient extends FluidIngredient {
        protected TagKey<Fluid> tag;

        @SuppressWarnings("deprecation")
        @Override
        public boolean test(FluidStack t) {
            if (tag == null) {
                for (FluidStack accepted : getMatchingFluidStacks()) {
                    if (accepted.getFluid().isSame(t.getFluid())) return true;
                }
                return false;
            }
            return t.getFluid().is(tag);
        }

        @Override
        protected void read(JsonObject json) {
            tag = FluidTags.create(new ResourceLocation(GsonHelper.getAsString(json, "fluidTag")));
        }

        @Override
        protected void write(JsonObject json) {
            json.addProperty("fluidTag", tag.location().toString());
        }

        @Override
        protected List<FluidStack> getMatches() {
            return BuiltInRegistries.FLUID.getTag(tag)
                    .stream()
                    .flatMap(HolderSet::stream)
                    .map(Holder::value)
                    .map(e -> e instanceof FlowingFluid ? ((FlowingFluid) e).getSource() : e)
                    .distinct()
                    .map(e -> new FluidStack(e, amount))
                    .toList();
        }
    }
}
