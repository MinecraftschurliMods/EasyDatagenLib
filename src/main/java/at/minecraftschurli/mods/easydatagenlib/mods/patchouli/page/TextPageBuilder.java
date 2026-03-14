package at.minecraftschurli.mods.easydatagenlib.mods.patchouli.page;

import at.minecraftschurli.mods.easydatagenlib.mods.patchouli.AbstractPageBuilder;
import at.minecraftschurli.mods.easydatagenlib.mods.patchouli.EntryBuilder;
import com.google.gson.JsonObject;
import net.minecraft.resources.Identifier;

public class TextPageBuilder extends AbstractPageBuilder<TextPageBuilder> {
    private final String text;
    private final String title;

    public TextPageBuilder(String text, String title, EntryBuilder<?,?,?> entryBuilder) {
        super(Identifier.fromNamespaceAndPath("patchouli", "text"), entryBuilder);
        this.text = text;
        this.title = title;
    }

    public TextPageBuilder(String text, EntryBuilder<?,?,?> entryBuilder) {
        super(Identifier.fromNamespaceAndPath("patchouli", "text"), entryBuilder);
        this.text = text;
        this.title = null;
    }

    @Override
    protected void serialize(JsonObject json) {
        json.addProperty("text", text);
        if (title != null) {
            json.addProperty("title", title);
        }
    }
}
