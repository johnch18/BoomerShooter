package com.johnch18.boomer.common.items.impl;


import com.johnch18.boomer.common.items.IAmmo;
import com.johnch18.boomer.common.items.IShooter;
import com.johnch18.boomer.render.IBoomerProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;


/**
 * @param <T> Type of ammo
 */
public interface IProjectileShooter<T extends IAmmo> extends IShooter<T> {

    /**
     * @param player Player firing
     * @param world  World fired in
     * @return Projectile
     */
    IBoomerProjectile getProjectile(EntityPlayer player, World world);

}
