package com.johnch18.boomer.common.items;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;


/**
 * @param <T>
 */
public interface IShooter<T extends IAmmo> {

    /**
     * @return Gets the ammo instance
     */
    @SuppressWarnings("unused")
    @Nonnull
    T getAmmo();

    /**
     * @param stack  Stack to check
     * @param player Player to check
     * @return Can fire?
     */
    boolean canPlayerFire(ItemStack stack, EntityPlayer player);

    /**
     * @param player Player firing
     * @param world  World fired in
     */
    void fireGun(EntityPlayer player, World world);

    /**
     * @return Range of weapon
     */
    double getRange();

    /**
     * @return Cooldown in ticks
     */
    int getCooldown();

    /**
     * @return Amount of damage to do this instance
     */
    float getAttackDamage();

    /**
     * @return Random number generator
     */
    @SuppressWarnings("unused")
    @Nonnull
    Random getRand();

    /**
     * @param low  lower bound
     * @param high upper bound
     * @return Random double
     */
    double getDoubleInRange(double low, double high);

    /**
     * @param player   Player to work from
     * @param variance Amount of randomness to pitch and yaw
     * @return Random vector
     */
    @Nonnull
    Vec3 getRandomMotionFromPlayerLook(EntityPlayer player, double variance);

}
