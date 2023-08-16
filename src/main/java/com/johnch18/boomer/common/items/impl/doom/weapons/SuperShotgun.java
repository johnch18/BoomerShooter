package com.johnch18.boomer.common.items.impl.doom.weapons;


import com.johnch18.boomer.common.BoomerTab;
import com.johnch18.boomer.common.items.impl.doom.ammo.ShotgunShell;
import com.johnch18.boomer.util.HitScan;
import com.johnch18.boomer.util.HitScanManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import javax.annotation.Nonnull;


/**
 *
 */
public class SuperShotgun extends Shotgun {

    /**
     * @param ammo Ammo type to use
     */
    public SuperShotgun(final ShotgunShell ammo) {
        super(ammo);
    }


    @Nonnull
    @Override
    public String getID() {
        return BoomerTab.SUPER_SHOTGUN;
    }

    @Override
    public void fireGun(final EntityPlayer player, final World world) {
        final HitScanManager hitScans = new HitScanManager();
        for (int i = 0; i < 20; i++) {
            hitScans.add(getHitScan(player, world));
        }
        hitScans.march();
    }

    @Override
    public float getAttackDamage() {
        return (float) getDoubleInRange(10.0, 25.0);
    }

    @Nonnull
    @Override
    public HitScan getHitScan(final EntityPlayer player, final World world) {
        final Vec3 adj = getRandomMotionFromPlayerLook(player, 20);
        return new HitScan(this, player, world, adj.xCoord, adj.yCoord, adj.zCoord);
    }

}
