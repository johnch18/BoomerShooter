package com.johnch18.boomer.render.impl;

import com.johnch18.boomer.common.items.impl.IProjectileShooter;
import com.johnch18.boomer.render.IBoomerProjectile;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 *
 */
public abstract class BoomerProjectile extends EntityFX implements IBoomerProjectile {
    protected final IProjectileShooter<?> shooter;
    protected final World world;
    protected final EntityPlayer player;
    private final Vec3 initialPos;
    private final Vec3 initialLook;
    private int count = 0;

    private boolean dead = false;

    protected BoomerProjectile(final IProjectileShooter<?> shooter, final EntityPlayer player, final World world) {
        super(world, player.posX, player.posY, player.posZ);
        initialPos = player.getPosition(1.0f).addVector(0.0, world.isRemote ? 0.0 : 1.62, 0.0);
        initialLook = player.getLook(1.0f);
        this.shooter = shooter;
        this.world = world;
        this.player = player;
    }

    @Override
    public void onUpdate() {
        if (dead) {
            kill();
            return;
        }
        if (count % killTimer() == 0) {
            killLoop();
        }
        advancePosition();
        count += 1;
    }

    @Override
    public void advancePosition() {
        final Vec3 look = Vec3.createVectorHelper(initialLook.xCoord * count * getVelocity(), initialLook.yCoord * count * getVelocity(), initialLook.zCoord * count * getVelocity());
        if (look.lengthVector() > shooter.getRange()) {
            dead = true;
            return;
        }
        final Vec3 pos = initialPos.addVector(look.xCoord, look.yCoord, look.zCoord);
        setPosition(pos.xCoord, pos.yCoord, pos.zCoord);
    }

    @Nonnull
    @Override
    public EntityFX asFX() {
        return this;
    }
}
