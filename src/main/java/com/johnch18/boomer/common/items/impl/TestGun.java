package com.johnch18.boomer.common.items.impl;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class TestGun extends Shooter<TestAmmo> {

    public TestGun(TestAmmo ammo) {
        super(ammo);
    }

    @Nonnull
    @Override
    public String getID() {
        return "test_gun";
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void fireGun(EntityPlayer player, World world, double x, double y, double z) {
        double dX, dY, dZ;
        dX = getDoubleInRange(-0.025f, 0.025f);
        dY = getDoubleInRange(-0.025f, 0.025f);
        dZ = getDoubleInRange(-0.025f, 0.025f);
        Vec3 lookVec = player.getLookVec().addVector(dX, dY, dZ);
        Vec3 startVec = player.getPosition(1.0f);
        if (!world.isRemote) {
            startVec = startVec.addVector(0.0f, 1.62f, 0.0f);
        }
        Vec3 lenVec;
        int i = 0;
        do {
            boolean found = false;
            double d = i * 0.25f;
            lenVec = Vec3.createVectorHelper(lookVec.xCoord * d, lookVec.yCoord * d, lookVec.zCoord * d);
            Vec3 finalVec = lenVec.addVector(startVec.xCoord, startVec.yCoord, startVec.zCoord);
            List entities = world.getEntitiesWithinAABBExcludingEntity(player, getHitbox(finalVec));
            for (Object o : entities) {
                if (!(o instanceof EntityLivingBase)) {
                    continue;
                }
                EntityLivingBase entity = (EntityLivingBase) o;
                entity.attackEntityFrom(DamageSource.causePlayerDamage(player), getAttackDamage());
                found = true;
                break;
            }
            if (found) {
                break;
            }
        } while (lenVec.lengthVector() <= getRange() && i++ < 100000000);
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
        float d = 0.15f;
        return AxisAlignedBB.getBoundingBox(x - d, y - d, z - d, x + d, y + d, z + d);
    }

    @Override
    public float getAttackDamage() {
        return (float) getDoubleInRange(7.5f, 12.5f);
    }
}
