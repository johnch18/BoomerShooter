package com.johnch18.boomer.common.items.impl;

import javax.annotation.Nonnull;

public class TestAmmo extends Ammo {

    public TestAmmo() {

    }

    @Override
    public int getMaxCapacity() {
        return 16;
    }

    @Override
    public int getFireCost() {
        return 1;
    }

    @Nonnull
    @Override
    public String getID() {
        return "test_ammo";
    }

}
