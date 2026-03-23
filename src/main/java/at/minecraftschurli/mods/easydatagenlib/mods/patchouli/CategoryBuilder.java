package at.minecraftschurli.mods.easydatagenlib.mods.patchouli;

import com.google.gson.JsonObject;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class CategoryBuilder<B extends BookBuilder<B, C, E>, C extends CategoryBuilder<B, C, E>, E extends EntryBuilder<B, C, E>> {
    protected final B bookBuilder;
    private final Identifier id;
    private final String name;
    private final String description;
    private final String icon;
    private final List<E> entries = new ArrayList<>();
    private String parent;
    private String flag;
    private Integer sortnum;
    private Boolean secret;

    protected CategoryBuilder(String id, String name, String description, String icon, B bookBuilder) {
        this.bookBuilder = bookBuilder;
        this.id = Identifier.fromNamespaceAndPath(bookBuilder.getId().getNamespace(), id);
        this.name = name;
        this.description = description;
        this.icon = icon;
    }

    public Identifier getBookId() {
        return this.bookBuilder.getId();
    }

    public String getLocale() {
        return this.bookBuilder.getLocale();
    }

    JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("name", this.name);
        json.addProperty("description", this.description);
        json.addProperty("icon", this.icon);
        if (this.parent != null) {
            json.addProperty("parent", this.parent);
        }

        if (this.flag != null) {
            json.addProperty("flag", this.flag);
        }

        if (this.sortnum != null) {
            json.addProperty("sortnum", this.sortnum);
        }

        if (this.secret != null) {
            json.addProperty("secret", this.secret);
        }

        this.serialize(json);
        return json;
    }

    protected void serialize(JsonObject json) {
    }

    protected List<E> getEntries() {
        return Collections.unmodifiableList(this.entries);
    }

    public B build() {
        return this.bookBuilder;
    }

    public abstract C addSubCategory(String id, String name, String description, ItemStackTemplate icon);

    public abstract C addSubCategory(String id, String name, String description, String icon);

    protected C addSubCategory(C builder) {
        return this.bookBuilder.addCategory(builder).setParent(this.self());
    }

    public abstract E addEntry(String id, String name, ItemStackTemplate icon);

    public abstract E addEntry(String id, String name, String icon);

    public E addEntry(E builder) {
        this.entries.add(builder);
        return builder;
    }

    public C setParent(String parent) {
        this.parent = parent;
        return this.self();
    }

    public C setParent(C parent) {
        this.parent = parent.getId().toString();
        return this.self();
    }

    public C setFlag(String flag) {
        this.flag = flag;
        return this.self();
    }

    public C setSortnum(Integer sortnum) {
        this.sortnum = sortnum;
        return this.self();
    }

    public C setSecret(Boolean secret) {
        this.secret = secret;
        return this.self();
    }

    @SuppressWarnings("unchecked")
    private <T extends CategoryBuilder<B, C, E>> T self() {
        return (T)this;
    }

    public Identifier getId() {
        return this.id;
    }

    public B getBookBuilder() {
        return this.bookBuilder;
    }
}
