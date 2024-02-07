package com.github.minecraftschurlimods.easydatagenlib.util.farmersdelight;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.ToolAction;
import org.jetbrains.annotations.Nullable;

public class ToolActionIngredient extends Ingredient {
    public final ToolAction toolAction;

    public ToolActionIngredient(ToolAction toolAction) {
        super(BuiltInRegistries.ITEM.stream()
                .map(ItemStack::new)
                .filter(stack -> stack.canPerformAction(toolAction))
                .map(Ingredient.ItemValue::new));
        this.toolAction = toolAction;
    }

    @Override
    public boolean test(@Nullable ItemStack stack) {
        return stack != null && stack.canPerformAction(toolAction);
    }

    public JsonElement toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("type", "farmersdelight:tool_action");
        json.addProperty("action", toolAction.name());
        return json;
    }
}
