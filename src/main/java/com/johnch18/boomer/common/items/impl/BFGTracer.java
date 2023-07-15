package com.johnch18.boomer.common.items.impl;

import com.johnch18.boomer.Tags;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import javax.annotation.Nonnull;

/**
 *
 */
public class BFGTracer extends BoomerItem {

    private static IIcon icon;

    /**
     *
     */
    public static final String BFG_TRACER = "bfg_tracer";

    /**
     *
     */
    public BFGTracer() {
        super(false);
    }

    @Nonnull
    @Override
    public String getID() {
        return BFG_TRACER;
    }

    @Override
    public IIcon getIconFromDamageForRenderPass(final int meta, final int pass) {
        return icon;
    }

    @Override
    public void registerIcons(final IIconRegister register) {
        icon = register.registerIcon(Tags.MODID + ":" + BFG_TRACER);
    }
}
