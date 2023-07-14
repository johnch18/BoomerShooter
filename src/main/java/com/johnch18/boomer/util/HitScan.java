package com.johnch18.boomer.util;

import com.johnch18.boomer.common.items.IShooter;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

/**
 *
 */
public class HitScan {

    private static final float substep = 0.25f;
    private final IShooter<?> shooter;
    private final Vec3 base, look;
    private final EntityPlayer player;
    private final World world;
    private int count;
    private boolean dead;

    /**
     * @param shooter Gun firing
     * @param player Player firing
     * @param world World fired in
     * @param dX Bump to vector x
     * @param dY Bump to vector y
     * @param dZ Bump to vector z
     */
    public HitScan(final IShooter<?> shooter, final EntityPlayer player, final World world, final double dX, final double dY, final double dZ) {
        this(shooter, world, player, player.getPosition(1.0f).addVector(0.0, world.isRemote ? 0.0 : 1.62, 0.0), player.getLook(1.0f).addVector(dX, dY, dZ));
    }

    private HitScan(final IShooter<?> shooter, final World world, final EntityPlayer player, final Vec3 base, final Vec3 look) {
        this.shooter = shooter;
        this.world = world;
        this.player = player;
        this.base = base;
        this.look = look;
        count = 0;
        dead = false;
    }

    IShooter<?> getShooter() {
        return shooter;
    }

    boolean isDead() {
        return dead;
    }

    private void step() {
        count += 1;
    }

    Vec3 getRay() {
        return Vec3.createVectorHelper(look.xCoord * substep * count, look.yCoord * substep * count, look.zCoord * substep * count);
    }

    @SuppressWarnings("rawtypes")
    void singleMarch() {
        final Vec3 vec3 = getRay().addVector(base.xCoord, base.yCoord, base.zCoord);
        if (count % 3 == 0 && count > 2) {
            world.spawnParticle("smoke", vec3.xCoord, vec3.yCoord, vec3.zCoord, 0.0, 0.0, 0.0);
        }
        final List entities = world.getEntitiesWithinAABBExcludingEntity(player, shooter.getHitbox(vec3));
        for (final Object o : entities) {
            if (o instanceof EntityLivingBase) {
                final EntityLivingBase living = (EntityLivingBase) o;
                living.attackEntityFrom(DamageSource.causePlayerDamage(player), shooter.getAttackDamage());
                dead = true;
                return;
            }
        }
        final Block block = world.getBlock((int) vec3.xCoord, (int) vec3.yCoord, (int) vec3.zCoord);
        if (block != null && block != Blocks.air) {
            dead = true;
            return;
        }
        step();
    }

}
