package at.minecraftschurli.mods.easydatagenlib.mods.patchouli.regular;

import at.minecraftschurli.mods.easydatagenlib.mods.patchouli.BookBuilder;
import at.minecraftschurli.mods.easydatagenlib.mods.patchouli.PatchouliBookProvider;
import at.minecraftschurli.mods.easydatagenlib.mods.patchouli.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public class RegularBookBuilder extends BookBuilder<RegularBookBuilder, RegularCategoryBuilder, RegularEntryBuilder> {
    public RegularBookBuilder(Identifier id, String name, String landingText, PatchouliBookProvider provider, HolderLookup.Provider registries) {
        super(id, name, landingText, provider, registries);
    }

    @Override
    public RegularCategoryBuilder addCategory(final String id, final String name, final String description, final ItemStack icon) {
        return this.addCategory(id, name, description, Util.serializeStack(icon, getRegistries()));
    }

    @Override
    public RegularCategoryBuilder addCategory(final String id, final String name, final String description, final String icon) {
        return this.addCategory(new RegularCategoryBuilder(id, name, description, icon, this));
    }
}
