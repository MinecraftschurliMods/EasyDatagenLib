package at.minecraftschurli.mods.easydatagenlib.mods.patchouli.page;

import at.minecraftschurli.mods.easydatagenlib.mods.patchouli.AbstractPageBuilder;
import at.minecraftschurli.mods.easydatagenlib.mods.patchouli.EntryBuilder;
import com.google.gson.JsonObject;
import net.minecraft.resources.Identifier;

public class RecipePageBuilder extends AbstractPageBuilder<RecipePageBuilder> {
    private final String recipe;
    private String recipe2;
    private String title;
    private String text;

    public RecipePageBuilder(Identifier type, Identifier recipe, EntryBuilder<?,?,?> parent) {
        super(type, parent);
        this.recipe = recipe.toString();
    }

    protected void serialize(JsonObject json) {
        json.addProperty("recipe", this.recipe);
        if (this.recipe2 != null) {
            json.addProperty("recipe2", this.recipe2);
        }

        if (this.title != null) {
            json.addProperty("title", this.title);
        }

        if (this.text != null) {
            json.addProperty("text", this.text);
        }

    }

    public <T extends RecipePageBuilder> T setRecipe2(Identifier recipe2) {
        this.recipe2 = recipe2.toString();
        return this.self();
    }

    public <T extends RecipePageBuilder> T setTitle(String title) {
        this.title = title;
        return this.self();
    }

    public <T extends RecipePageBuilder> T setText(String text) {
        this.text = text;
        return this.self();
    }
}
