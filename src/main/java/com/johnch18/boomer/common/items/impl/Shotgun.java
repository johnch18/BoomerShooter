package com.johnch18.boomer.common.items.impl;

import com.johnch18.boomer.util.HitScan;
import com.johnch18.boomer.util.HitScanManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class Shotgun extends Shooter<ShotgunShell> {

    public Shotgun(ShotgunShell ammo) {
        super(ammo);
    }

    @Nonnull
    @Override
    public String getID() {
        return "shotgun";
    }

    @Override
    public void fireGun(EntityPlayer player, World world, double x, double y, double z) {
        HitScanManager scans = new HitScanManager();
        for (int i = 0; i < 7; i++) {
            scans.add(getHitScan(player, world));
        }
        scans.march();
    }

    @Override
    public double getRange() {
        return 128;
    }

    @Override
    public int getCooldown() {
        return 10;
    }

    @Override
    public AxisAlignedBB getHitbox(double x, double y, double z) {
        float d = 0.33f;
        return AxisAlignedBB.getBoundingBox(x - d, y - d, z - d, x + d, y + d, z + d);
    }

    @Override
    public float getAttackDamage() {
        return (float) getDoubleInRange(10.0f, 20.0f);
    }

    public HitScan getHitScan(EntityPlayer player, World world) {
        Vec3 adj = getRandomMotionFromPlayerLook(player, 15.0f);
        return new HitScan(this, player, world, adj.xCoord, adj.yCoord, adj.zCoord);
    }

}
