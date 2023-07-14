package com.johnch18.boomer.common.items.impl;

import com.johnch18.boomer.util.HitScanManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class SuperShotgun extends Shotgun {

    public SuperShotgun(ShotgunShell ammo) {
        super(ammo);
    }

    @Nonnull
    @Override
    public String getID() {
        return "super_shotgun";
    }

    @Override
    public void fireGun(EntityPlayer player, World world, double x, double y, double z) {
        HitScanManager hitScans = new HitScanManager();
        for (int i = 0; i < 20; i++) {
            hitScans.add(getHitScan(player, world));
        }
        hitScans.march();
    }

    @Override
    public float getAttackDamage() {
        return (float) getDoubleInRange(10.0f, 25.0f);
    }
}
