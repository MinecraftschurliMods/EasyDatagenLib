package com.github.minecraftschurlimods.easydatagenlib.util;

import net.minecraft.world.item.crafting.Ingredient;

public class IngredientWithCount {
    public final Ingredient ingredient;
    public final int count;

    public IngredientWithCount(Ingredient ingredient, int count) {
        this.ingredient = ingredient;
        this.count = count;
    }

    public IngredientWithCount(Ingredient ingredient) {
        this(ingredient, 1);
    }

    public static class WithChance extends IngredientWithCount {
        public final float chance;

        public WithChance(Ingredient ingredient, int count, float chance) {
            super(ingredient, count);
            this.chance = chance;
        }

        public WithChance(Ingredient ingredient, float chance) {
            super(ingredient);
            this.chance = chance;
        }
    }
}
