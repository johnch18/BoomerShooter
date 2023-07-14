package com.johnch18.boomer.common.items.impl;

import javax.annotation.Nonnull;

public class ShotgunShell extends Ammo {
    @Override
    public int getMaxCapacity() {
        return 40;
    }

    @Override
    public int getFireCost() {
        return 1;
    }

    @Nonnull
    @Override
    public String getID() {
        return "shotgun_shell";
    }
}
