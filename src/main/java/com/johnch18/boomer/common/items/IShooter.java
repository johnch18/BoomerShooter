package com.johnch18.boomer.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public interface IShooter<T extends IAmmo> {

    @Nonnull
    T getAmmoType();

    boolean playerCanFire(ItemStack stack, EntityPlayer player);

    void fireGun(EntityPlayer player, World world, double x, double y, double z);

    double getRange();

    int getCooldown();

    AxisAlignedBB getHitbox(double x, double y, double z);

    float getAttackDamage();

}
