package com.johnch18.boomer.common.items.impl.doom.ammo;

import com.johnch18.boomer.common.items.impl.Ammo;

import javax.annotation.Nonnull;

/**
 *
 */
public class BFGAmmo extends Ammo {
    @Override
    public int getMaxCapacity() {
        return 180;
    }

    @Override
    public int getFireCost() {
        return 60;
    }

    @Nonnull
    @Override
    public String getID() {
        return "bfg_cell";
    }
}
