package com.github.minecraftschurlimods.easydatagenlib.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;

/**
 * The abstract parent class for anything that generates recipes.
 *
 * @param <T> The builder class associated with this provider.
 */
public abstract class AbstractRecipeProvider<T extends AbstractRecipeBuilder<?>> extends AbstractDataProvider<T> {
    private final ResourceLocation recipeType;
    private final String name;

    /**
     * @param recipeType The recipe type to use. Also determines the output folder.
     * @param namespace  The namespace to use.
     * @param generator  The data generator to use.
     */
    protected AbstractRecipeProvider(ResourceLocation recipeType, String namespace, DataGenerator generator) {
        super("recipes/" + (recipeType.getNamespace().equals(namespace) ? "" : "compat/" + recipeType.getNamespace() + "/") + recipeType.getPath(), namespace, generator);
        this.recipeType = recipeType;
        this.name = makeName(recipeType);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    protected JsonObject toJson(T builder) {
        if (!recipeType.getNamespace().equals(namespace)) {
            builder.addCondition(new ModLoadedCondition(recipeType.getNamespace()));
        }
        JsonObject json = super.toJson(builder);
        json.addProperty("type", recipeType.toString());
        if (!builder.conditions.isEmpty()) {
            JsonArray array = new JsonArray();
            for (ICondition condition : builder.conditions) {
                array.add(CraftingHelper.serialize(condition));
            }
            json.add("conditions", array);
        }
        return json;
    }

    private String makeName(ResourceLocation recipeType) {
        String s = recipeType.toString().replace(':', ' ').replace('_', ' ');
        StringBuilder builder = new StringBuilder(s.substring(0, 1).toUpperCase());
        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            builder.append(c);
            if (c == ' ') {
                builder.append((char) (s.charAt(i + 1) + 32));
                i++;
            }
        }
        return builder.append('[').append(namespace).append(']').toString();
    }
}
