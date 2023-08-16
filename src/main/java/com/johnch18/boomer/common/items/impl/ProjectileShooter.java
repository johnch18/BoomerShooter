package com.johnch18.boomer.common.items.impl;


import com.johnch18.boomer.common.items.IAmmo;


/**
 * @param <T> Type of ammo
 */
public abstract class ProjectileShooter<T extends IAmmo> extends Shooter<T> implements IProjectileShooter<T> {


    /**
     * @param ammo Ammo to use
     */
    public ProjectileShooter(final T ammo) {
        super(ammo);
    }

}
