package com.github.minecraftschurlimods.easydatagenlib.util;

public class ModdedValues {
    public static final class Create {
        public enum HeatRequirement {
            NONE, HEATED, SUPERHEATED;

            @Override
            public String toString() {
                return "create:" + name().toLowerCase();
            }
        }
    }

    public static final class ImmersiveEngineering {
        public enum ClocheRenderType {
            CROP, STEM, STACKING, CHORUS, HEMP, GENERIC;

            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }
    }
}
