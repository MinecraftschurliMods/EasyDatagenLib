package com.github.minecraftschurlimods.easydatagenlib.util.create;

public enum HeatRequirement {
    NONE, HEATED, SUPERHEATED;

    @Override
    public String toString() {
        return "create:" + name().toLowerCase();
    }
}
