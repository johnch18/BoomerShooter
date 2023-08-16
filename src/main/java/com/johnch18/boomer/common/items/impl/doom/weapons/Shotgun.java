package com.johnch18.boomer.common.items.impl.doom.weapons;


import com.johnch18.boomer.common.items.impl.HitscanShooter;
import com.johnch18.boomer.common.items.impl.doom.ammo.ShotgunShell;
import com.johnch18.boomer.util.HitScan;
import com.johnch18.boomer.util.HitScanManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import javax.annotation.Nonnull;


/**
 *
 */
public class Shotgun extends HitscanShooter<ShotgunShell> {

    /**
     * @param ammo Ammo to use
     */
    @SuppressWarnings("WeakerAccess")
    public Shotgun(final ShotgunShell ammo) {
        super(ammo);
    }

    @Nonnull
    @Override
    public String getID() {
        return "shotgun";
    }

    @Override
    public void fireGun(final EntityPlayer player, final World world) {
        final HitScanManager scans = new HitScanManager();
        for (int i = 0; i < 7; i++) {
            scans.add(getHitScan(player, world));
        }
        scans.march();
    }

    @Override
    public double getRange() {
        return 256.0;
    }

    @Override
    public int getCooldown() {
        return 10;
    }

    @Override
    public float getAttackDamage() {
        return (float) getDoubleInRange(10.0, 20.0);
    }

    @Nonnull
    @Override
    public AxisAlignedBB getHitbox(final double x, final double y, final double z) {
        final double width = 0.33;
        return AxisAlignedBB.getBoundingBox(x - width, y - width, z - width, x + width, y + width, z + width);
    }

    @Nonnull
    @Override
    public HitScan getHitScan(final EntityPlayer player, final World world) {
        final Vec3 adj = getRandomMotionFromPlayerLook(player, 15.0);
        return new HitScan(this, player, world, adj.xCoord, adj.yCoord, adj.zCoord);
    }

}
