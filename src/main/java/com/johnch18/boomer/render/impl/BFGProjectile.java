package com.johnch18.boomer.render.impl;

import com.johnch18.boomer.Tags;
import com.johnch18.boomer.common.items.impl.BFGTracer;
import com.johnch18.boomer.common.items.impl.IProjectileShooter;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

/**
 *
 */
public class BFGProjectile extends BoomerProjectile {

    private final IIcon icon;

    /**
     * @param shooter gun firing
     * @param player  player shooting
     * @param world   world spawned in
     */
    public BFGProjectile(final IProjectileShooter<?> shooter, final EntityPlayer player, final World world) {
        super(shooter, player, world);
        icon = GameRegistry.findItem(Tags.MODID, BFGTracer.BFG_TRACER).getIconFromDamageForRenderPass(0, 0);
    }

    @Override
    public void killLoop() {
        final double x = 10;
        final double y = 7.5;
        final double z = 10;
        final AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(posX - x, posY - y, posZ - z, posX + x, posY + y, posZ + z);
        for (final Object o: worldObj.getEntitiesWithinAABBExcludingEntity(player, aabb)) {
            if (o instanceof EntityLivingBase) {
                final EntityLivingBase entityLivingBase = (EntityLivingBase) o;
                entityLivingBase.attackEntityFrom(DamageSource.causePlayerDamage(player), getDamage());
            }
        }
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
    public void renderParticle(final Tessellator tessellator, final float subTickTime, final float p_70539_3_, final float p_70539_4_, final float p_70539_5_, final float p_70539_6_, final float p_70539_7_) {
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDepthMask(false);

        tessellator.setColorRGBA(255, 255, 255, 255);

        if (icon == null) {
            return;
        }

        final double u, U, v, V;
        u = icon.getMinU();
        U = icon.getMaxU();
        v = icon.getMinV();
        V = icon.getMaxV();

        tessellator.addVertexWithUV(posX, posY, posZ, u, v);
        tessellator.addVertexWithUV(posX, posY, posZ, U, v);
        tessellator.addVertexWithUV(posX, posY, posZ, u, V);
        tessellator.addVertexWithUV(posX, posY, posZ, U, V);

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDepthMask(true);
    }
}
