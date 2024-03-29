package com.github.minecraftschurlimods.easydatagenlib.util.farmersdelight;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class ToolActionIngredient extends Ingredient {
    public static final Serializer SERIALIZER = new Serializer();
    public final ToolAction toolAction;

    public ToolActionIngredient(ToolAction toolAction) {
        super(ForgeRegistries.ITEMS.getValues().stream()
                .map(ItemStack::new)
                .filter(stack -> stack.canPerformAction(toolAction))
                .map(Ingredient.ItemValue::new));
        this.toolAction = toolAction;
    }

    @Override
    public boolean test(@Nullable ItemStack stack) {
        return stack != null && stack.canPerformAction(toolAction);
    }

    @Override
    public JsonElement toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("type", "farmersdelight:tool_action");
        json.addProperty("action", toolAction.name());
        return json;
    }

    @Override
    public IIngredientSerializer<? extends Ingredient> getSerializer() {
        return SERIALIZER;
    }

    public static class Serializer implements IIngredientSerializer<ToolActionIngredient> {
        @Override
        public ToolActionIngredient parse(FriendlyByteBuf buffer) {
            return new ToolActionIngredient(ToolAction.get(buffer.readUtf()));
        }

        @Override
        public ToolActionIngredient parse(JsonObject json) {
            return new ToolActionIngredient(ToolAction.get(json.get("action").getAsString()));
        }

        @Override
        public void write(FriendlyByteBuf buffer, ToolActionIngredient ingredient) {
            buffer.writeUtf(ingredient.toolAction.name());
        }
    }
}
