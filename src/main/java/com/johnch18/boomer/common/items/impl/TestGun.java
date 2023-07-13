package com.johnch18.boomer.common.items.impl;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class TestGun extends Shooter<TestAmmo> {

    public TestGun(TestAmmo ammo) {
        super(ammo);
    }

    @Nonnull
    @Override
    public String getID() {
        return "test_gun";
    }

    @Override
    public void fireGun(EntityPlayer player, World world, double x, double y, double z) {
        MovingObjectPosition pos = player.rayTrace(getRange(), 0.05f);
        System.out.println(pos);
        if (pos.entityHit != null) {
            pos.entityHit.attackEntityFrom(DamageSource.causePlayerDamage(player), 10.0f);
        }
    }

    @Override
    public double getRange() {
        return 256;
    }

    @Override
    public int getCooldown() {
        return 15;
    }

    @Override
    public AxisAlignedBB getHitbox(double x, double y, double z) {
        float d = 1.5f;
        return AxisAlignedBB.getBoundingBox(x - d, y - d, z - d, x + d, y + d, z + d);
    }

    @Override
    public float getAttackDamage() {
        return 10;
    }
}
