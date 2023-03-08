package com.github.minecraftschurlimods.easydatagenlib.util;

import com.google.gson.JsonObject;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

/**
 * This class contains the necessary information for fluid stacks that will be written to disk by a data generator.
 * It contains a fluid id in {@link ResourceLocation} form, an amount and an optional {@link CompoundTag}.
 * When serializing to JSON, no {@link FluidStack} will be used. Instead, the correct JSON syntax is recreated by hand.
 * This means that you can use fluid ids that may not be valid, e.g. from other mods, in your datagen.
 */
public class PotentiallyAbsentFluidStack {
    private final ResourceLocation fluid;
    private final int amount;
    private CompoundTag tag;

    /**
     * Creates a new instance of this class. Use this if you want the output to have additional NBT data.
     *
     * @param fluid  The fluid id to use.
     * @param amount The amount to use.
     * @param tag   The NBT to use. May be null.
     */
    public PotentiallyAbsentFluidStack(ResourceLocation fluid, int amount, CompoundTag tag) {
        this.fluid = fluid;
        this.amount = amount;
        this.tag = tag;
    }

    /**
     * Creates a new instance of this class. Use this if you want the output to have additional NBT data.
     *
     * @param fluid  The fluid id to use.
     * @param tag   The NBT to use. May be null.
     */
    public PotentiallyAbsentFluidStack(ResourceLocation fluid, CompoundTag tag) {
        this(fluid, 1, tag);
    }

    /**
     * Creates a new instance of this class.
     *
     * @param fluid  The fluid id to use.
     * @param amount The amount to use.
     */
    public PotentiallyAbsentFluidStack(ResourceLocation fluid, int amount) {
        this(fluid, amount, new CompoundTag());
    }

    /**
     * Creates a new instance of this class.
     *
     * @param fluid The fluid id to use.
     */
    public PotentiallyAbsentFluidStack(ResourceLocation fluid) {
        this(fluid, 1, new CompoundTag());
    }

    /**
     * @return The id of the fluid to use.
     */
    public ResourceLocation getFluid() {
        return fluid;
    }

    /**
     * @return The amount to use.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @return The NBT tag to use.
     */
    public CompoundTag getTag() {
        return tag;
    }

    /**
     * @param tag The NBT tag to use.
     */
    public void setTag(CompoundTag tag) {
        this.tag = tag;
    }

    /**
     * @return The JSON representation of this object.
     */
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        if (fluid == null) throw new IllegalArgumentException("Cannot serialize an fluid stack without an fluid id!");
        json.addProperty("fluid", fluid.toString());
        json.addProperty("amount", amount);
        if (!tag.isEmpty()) {
            json.addProperty("nbt", tag.toString());
        }
        return json;
    }

    /**
     * Variant of {@link PotentiallyAbsentFluidStack} that adds extra chance info.
     */
    public static class WithChance extends PotentiallyAbsentFluidStack {
        public final float chance;

        /**
         * Creates a new instance of this class. Use this if you want the output to have additional NBT data.
         *
         * @param fluid  The fluid id to use.
         * @param amount The amount to use.
         * @param tag   The NBT to use. May be null.
         */
        public WithChance(ResourceLocation fluid, int amount, CompoundTag tag, float chance) {
            super(fluid, amount, tag);
            this.chance = chance;
        }

        /**
         * Creates a new instance of this class. Use this if you want the output to have additional NBT data.
         *
         * @param fluid  The fluid id to use.
         * @param tag   The NBT to use. May be null.
         */
        public WithChance(ResourceLocation fluid, CompoundTag tag, float chance) {
            this(fluid, 1, tag, chance);
        }

        /**
         * Creates a new instance of this class.
         *
         * @param fluid  The fluid id to use.
         * @param amount The amount to use.
         */
        public WithChance(ResourceLocation fluid, int amount, float chance) {
            this(fluid, amount, new CompoundTag(), chance);
        }

        /**
         * Creates a new instance of this class.
         *
         * @param fluid The fluid id to use.
         */
        public WithChance(ResourceLocation fluid, float chance) {
            this(fluid, 1, new CompoundTag(), chance);
        }

        @Override
        public JsonObject toJson() {
            JsonObject json = super.toJson();
            if (chance > 0 && chance < 1) {
                json.addProperty("chance", chance);
            }
            return json;
        }
    }
}
