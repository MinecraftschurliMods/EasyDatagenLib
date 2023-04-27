package com.github.minecraftschurlimods.easydatagenlib.util;

public class FluidIngredientWithCount {
    public final FluidIngredient ingredient;
    public final int count;

    public FluidIngredientWithCount(FluidIngredient ingredient, int count) {
        this.ingredient = ingredient;
        this.count = count;
    }

    public FluidIngredientWithCount(FluidIngredient ingredient) {
        this(ingredient, 1);
    }

    public static class WithChance extends FluidIngredientWithCount {
        public final float chance;

        public WithChance(FluidIngredient ingredient, int count, float chance) {
            super(ingredient, count);
            this.chance = chance;
        }

        public WithChance(FluidIngredient ingredient, float chance) {
            super(ingredient);
            this.chance = chance;
        }
    }
}
