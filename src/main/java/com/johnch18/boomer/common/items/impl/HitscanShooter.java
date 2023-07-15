package com.johnch18.boomer.common.items.impl;

import com.johnch18.boomer.common.items.IAmmo;
import com.johnch18.boomer.common.items.IHitscanShooter;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

import javax.annotation.Nonnull;

/**
 * @param <T> Ammo type
 */
public abstract class HitscanShooter<T extends IAmmo> extends Shooter<T> implements IHitscanShooter<T> {
    /**
     * @param ammo Ammo to use
     */
    public HitscanShooter(final T ammo) {
        super(ammo);
    }

    @Nonnull
    @Override
    public AxisAlignedBB getHitbox(final Vec3 vec3) {
        return getHitbox(vec3.xCoord, vec3.yCoord, vec3.zCoord);
    }
}
