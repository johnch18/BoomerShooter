package com.johnch18.boomer.util;

import com.johnch18.boomer.common.items.IShooter;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

public class HitScan {

    private static final float d = 0.25f;
    private final IShooter<?> shooter;
    private final Vec3 base, look;
    private final EntityPlayer player;
    private final World world;
    private int count = 0;
    private boolean dead = false;

    public HitScan(IShooter<?> shooter, EntityPlayer player, World world, double dX, double dY, double dZ) {
        this(shooter, world, player, player.getPosition(1.0f).addVector(0.0f, !world.isRemote ? 1.62f : 0.0f, 0.0f), player.getLook(1.0f).addVector(dX, dY, dZ));
    }

    public HitScan(IShooter<?> shooter, World world, EntityPlayer player, Vec3 base, Vec3 look) {
        this.shooter = shooter;
        this.world = world;
        this.player = player;
        this.base = base;
        this.look = look;
    }

    public IShooter<?> getShooter() {
        return shooter;
    }

    public boolean isDead() {
        return dead;
    }

    public void step() {
        count += 1;
    }

    public void march() {
        Vec3 vec3;
        while ((vec3 = getRay()).lengthVector() <= shooter.getRange() && !dead) {
            if (singleMarch()) {
                return;
            }
        }
    }

    public Vec3 getRay() {
        return Vec3.createVectorHelper(look.xCoord * d * count, look.yCoord * d * count, look.zCoord * d * count);
    }

    @SuppressWarnings("rawtypes")
    public boolean singleMarch() {
        Vec3 vec3 = getRay().addVector(base.xCoord, base.yCoord, base.zCoord);
        if (count % 3 == 0 && count > 2) {
            world.spawnParticle("smoke", vec3.xCoord, vec3.yCoord, vec3.zCoord, 0.0f, 0.0f, 0.0f);
        }
        List entities = world.getEntitiesWithinAABBExcludingEntity(player, shooter.getHitbox(vec3));
        for (Object o : entities) {
            if (!(o instanceof EntityLivingBase)) {
                continue;
            }
            EntityLivingBase living = (EntityLivingBase) o;
            living.attackEntityFrom(DamageSource.causePlayerDamage(player), shooter.getAttackDamage());
            dead = true;
            return true;
        }
        Block block = world.getBlock((int) vec3.xCoord, (int) vec3.yCoord, (int) vec3.zCoord);
        if (block != null && block != Blocks.air) {
            dead = true;
            return true;
        }
        step();
        return false;
    }

}
