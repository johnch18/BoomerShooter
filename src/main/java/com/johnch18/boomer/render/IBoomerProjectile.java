package com.johnch18.boomer.render;

import net.minecraft.client.particle.EntityFX;

import javax.annotation.Nonnull;

/**
 *
 */
public interface IBoomerProjectile {

    /**
     * @return this
     */
    @Nonnull
    EntityFX asFX();

    /**
     * Moves the entity
     */
    void advancePosition();

    /**
     * Tries to kill things
     * */
    void killLoop();

    /**
     * @return velocity
     * */
    double getVelocity();

    /**
     * @return Ticks between
     * */
    int killTimer();

    /**
     * @return Damage to do
     * */
    float getDamage();

}
