package com.johnch18.boomer.render.impl;


import com.johnch18.boomer.Tags;
import com.johnch18.boomer.common.items.impl.IProjectileShooter;
import com.johnch18.boomer.common.items.impl.doom.misc.BFGTracer;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;


/**
 *
 */
public class BFGProjectile extends BoomerProjectile {

    /**
     * @param shooter gun firing
     * @param player  player shooting
     * @param world   world spawned in
     */
    public BFGProjectile(final IProjectileShooter<?> shooter, final EntityPlayer player, final World world) {
        super(shooter, player, world);
        setParticleIcon(GameRegistry.findItem(Tags.MODID, BFGTracer.BFG_TRACER).getIconFromDamageForRenderPass(0, 0));
    }

    @Override
    public void killLoop() {
        final double x = 10;
        final double y = 7.5;
        final double z = 10;
        final AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(posX - x, posY - y, posZ - z, posX + x, posY + y, posZ + z);
        for (final Object o : worldObj.getEntitiesWithinAABBExcludingEntity(player, aabb)) {
            if (o instanceof EntityLivingBase) {
                final EntityLivingBase entityLivingBase = (EntityLivingBase) o;
                entityLivingBase.attackEntityFrom(DamageSource.causePlayerDamage(player), getDamage());
            }
        }
    }

    @Override
    public void renderParticle(
            final Tessellator tessellator, final float subTickTime, final float rotX, final float rotXZ, final float rotZ,
            final float rotYZ, final float rotXY
                              ) {
        float u = particleIcon.getMinU();
        float U = particleIcon.getMaxU();
        float v = particleIcon.getMinV();
        float V = particleIcon.getMaxV();
        float X = getRenderX(subTickTime);
        float Y = getRenderY(subTickTime);
        float Z = getRenderZ(subTickTime);
        float scale = 16f * particleScale;
        tessellator.setColorRGBA_F(1.0f, 1.0f, 1.0f, 1.0f);
        tessellator.addVertexWithUV(X - scale * (rotX + rotYZ), Y - scale * rotXZ, Z - scale * (rotZ + rotXY), U, V);
        tessellator.addVertexWithUV(X - scale * (rotX - rotYZ), Y + scale * rotXZ, Z - scale * (rotZ - rotXY), U, v);
        tessellator.addVertexWithUV(X + scale * (rotX + rotYZ), Y + scale * rotXZ, Z + scale * (rotZ + rotXY), u, v);
        tessellator.addVertexWithUV(X + scale * (rotX - rotYZ), Y - scale * rotXZ, Z + scale * (rotZ - rotXY), u, V);

    }

    public float getRenderX(final float subTickTime) {
        return (float) (prevPosX + (posX - prevPosX) * (double) subTickTime - EntityFX.interpPosX);
    }

    public float getRenderY(final float subTickTime) {
        return (float) (prevPosY + (posY - prevPosY) * (double) subTickTime - EntityFX.interpPosY);
    }

    public float getRenderZ(final float subTickTime) {
        return (float) (prevPosZ + (posZ - prevPosZ) * (double) subTickTime - EntityFX.interpPosZ);
    }

    @Override
    public double getVelocity() {
        return 1.0;
    }

    @Override
    public int killTimer() {
        return 5;
    }

    @Override
    public float getDamage() {
        return (float) shooter.getDoubleInRange(10.0, 25.0);
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

}
