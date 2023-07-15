package com.johnch18.boomer.common.items;

import com.johnch18.boomer.util.HitScan;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * @param <T> Ammo type
 */
public interface IHitscanShooter<T extends IAmmo> extends IShooter<T> {

    /**
     * @param x x center
     * @param y y center
     * @param z z center
     * @return Hitbox
     */
    @Nonnull
    AxisAlignedBB getHitbox(final double x, final double y, final double z);

    /**
     * @param player Player firing
     * @param world World fired in
     * @return Hitscan
     */
    @Nonnull
    HitScan getHitScan(final EntityPlayer player, final World world);

    /**
     * @param vec3 Dimensions of hitbox
     * @return Hitbox
     */
    @Nonnull
    AxisAlignedBB getHitbox(Vec3 vec3);
}
