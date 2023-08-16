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

    protected final EntityPlayer player;

    private final Vec3 initialPos;

    private final Vec3 initialLook;

    private int count = 0;

    protected BoomerProjectile(final IProjectileShooter<?> shooter, final EntityPlayer player, final World world) {
        super(world, player.posX, player.posY, player.posZ);
        initialPos = player.getPosition(1.0f).addVector(0.0, world.isRemote ? 0.0 : 1.62, 0.0);
        initialLook = player.getLook(1.0f);
        setPosition(initialPos.xCoord, initialPos.yCoord, initialPos.zCoord);
        setMotion();
        this.shooter = shooter;
        this.player = player;
    }

    @Override
    public void onUpdate() {
        if (count % killTimer() == 0) {
            printDebugInfo();
            killLoop();
        }
        advancePosition();
        count += 1;
    }

    private void printDebugInfo() {
        if (!worldObj.isRemote) {
            System.out.printf("Position(%.2f, %.2f, %.2f)%n", posX, posY, posZ);
            System.out.printf("Velocity(%.2f, %.2f, %.2f)%n", motionX, motionY, motionZ);
        }
    }

    @Nonnull
    @Override
    public EntityFX asFX() {
        return this;
    }

    @Override
    public void advancePosition() {
        final Vec3 vel = setMotion();
        final Vec3 dist = moveEntity(vel).subtract(initialPos);
        if (dist.lengthVector() > shooter.getRange()) {
            kill();
        }
    }

    public Vec3 setMotion() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionX = initialLook.xCoord * getVelocity();
        this.motionY = initialLook.yCoord * getVelocity();
        this.motionZ = initialLook.zCoord * getVelocity();
        return Vec3.createVectorHelper(this.motionX, this.motionY, this.motionZ);
    }

    public Vec3 moveEntity(final Vec3 vel) {
        moveEntity(vel.xCoord, vel.yCoord, vel.zCoord);
        return Vec3.createVectorHelper(posX, posY, posZ);
    }

}
