package at.minecraftschurli.mods.easydatagenlib.mods.patchouli.page;

import at.minecraftschurli.mods.easydatagenlib.mods.patchouli.AbstractPageBuilder;
import at.minecraftschurli.mods.easydatagenlib.mods.patchouli.EntryBuilder;
import com.google.gson.JsonObject;
import net.minecraft.resources.Identifier;

public class MultiblockPageBuilder extends AbstractPageBuilder<MultiblockPageBuilder> {
    private final String name;
    private final Identifier multiblock;
    private       String text;

    public MultiblockPageBuilder(String name, Identifier multiblock, EntryBuilder<?, ?, ?> parent) {
        super(Identifier.fromNamespaceAndPath("patchouli", "multiblock"), parent);
        this.name = name;
        this.multiblock = multiblock;
    }

    @Override
    protected void serialize(JsonObject var1) {
        var1.addProperty("name", this.name);
        var1.addProperty("multiblock_id", this.multiblock.toString());
        if (this.text != null) {
            var1.addProperty("text", this.text);
        }
    }

    public MultiblockPageBuilder setText(String text) {
        this.text = text;
        return this;
    }
}
