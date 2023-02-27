package com.github.minecraftschurlimods.easydatagenlib.util;

public class ModdedValues {
    public static final class Create {
        public enum HeatRequirement {
            NONE, HEATED, SUPERHEATED;

            public String toString() {
                return "create:" + name().toLowerCase();
            }
        }
    }
}
