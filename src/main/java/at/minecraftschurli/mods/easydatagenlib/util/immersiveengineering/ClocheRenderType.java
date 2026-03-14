package at.minecraftschurli.mods.easydatagenlib.util.immersiveengineering;

public enum ClocheRenderType {
    CROP, STEM, STACKING, CHORUS, HEMP, GENERIC;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
