package at.minecraftschurli.mods.easydatagenlib.util.create;

public enum HeatRequirement {
    NONE, HEATED, SUPERHEATED;

    @Override
    public String toString() {
        return "create:" + name().toLowerCase();
    }
}
