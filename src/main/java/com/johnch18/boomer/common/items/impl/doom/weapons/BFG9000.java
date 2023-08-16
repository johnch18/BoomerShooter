package com.johnch18.boomer.common.items.impl.doom.weapons;


import com.johnch18.boomer.common.items.impl.ProjectileShooter;
import com.johnch18.boomer.common.items.impl.doom.ammo.BFGAmmo;
import com.johnch18.boomer.render.IBoomerProjectile;
import com.johnch18.boomer.render.impl.BFGProjectile;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import javax.annotation.Nonnull;


/**
 *
 */
public class BFG9000 extends ProjectileShooter<BFGAmmo> {

    /**
     * @param ammo Ammo to use
     */
    public BFG9000(final BFGAmmo ammo) {
        super(ammo);
    }

    @Nonnull
    @Override
    public String getID() {
        return "bfg_9000";
    }

    @Override
    public void fireGun(final EntityPlayer player, final World world) {
        Minecraft.getMinecraft().effectRenderer.addEffect(getProjectile(player, world).asFX());
    }

    @Override
    public double getRange() {
        return 384;
    }

    @Override
    public int getCooldown() {
        return 30;
    }

    @Override
    public float getAttackDamage() {
        return 0;
    }

    @Override
    public IBoomerProjectile getProjectile(final EntityPlayer player, final World world) {
        return new BFGProjectile(this, player, world);
    }

}
