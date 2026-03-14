package at.minecraftschurli.mods.easydatagenlib.mods.patchouli.page;

import at.minecraftschurli.mods.easydatagenlib.mods.patchouli.AbstractPageBuilder;
import at.minecraftschurli.mods.easydatagenlib.mods.patchouli.EntryBuilder;
import com.google.gson.JsonObject;
import net.minecraft.resources.Identifier;

public class EmptyPageBuilder extends AbstractPageBuilder<EmptyPageBuilder> {
    private final boolean drawFiller;

    public EmptyPageBuilder(boolean drawFiller, EntryBuilder<?,?,?> entryBuilder) {
        super(Identifier.fromNamespaceAndPath("patchouli", "empty"), entryBuilder);
        this.drawFiller = drawFiller;
    }

    @Override
    protected void serialize(JsonObject json) {
        json.addProperty("draw_filler", drawFiller);
    }
}
