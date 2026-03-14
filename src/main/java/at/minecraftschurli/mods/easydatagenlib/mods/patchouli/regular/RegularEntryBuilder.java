package at.minecraftschurli.mods.easydatagenlib.mods.patchouli.regular;

import at.minecraftschurli.mods.easydatagenlib.mods.patchouli.EntryBuilder;

public class RegularEntryBuilder extends EntryBuilder<RegularBookBuilder, RegularCategoryBuilder, RegularEntryBuilder> {
    protected RegularEntryBuilder(final String id, final String name, final String icon, final RegularCategoryBuilder parent) {
        super(id, name, icon, parent);
    }
}
