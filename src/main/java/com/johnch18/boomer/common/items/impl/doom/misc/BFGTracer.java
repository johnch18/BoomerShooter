package com.johnch18.boomer.common.items.impl.doom.misc;


import com.johnch18.boomer.Tags;
import com.johnch18.boomer.common.items.impl.BoomerItem;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import javax.annotation.Nonnull;


/**
 *
 */
public class BFGTracer extends BoomerItem {

    /**
     *
     */
    public static final String BFG_TRACER = "bfg_tracer";

    private static IIcon icon;

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
