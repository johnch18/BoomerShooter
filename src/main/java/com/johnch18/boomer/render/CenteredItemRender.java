package com.johnch18.boomer.render;


import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;


public class CenteredItemRender implements IItemRenderer {

    protected static void standardRendering(final Tessellator tessellator, final IIcon icon) {
        ItemRenderer.renderItemIn2D(
                tessellator, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625f);
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        final IIcon icon = item.getItem().getIcon(item, 0);
        final Tessellator tessellator = Tessellator.instance;
        //
        glPre();
        //
        doRendering(tessellator, icon);
        //
        glPost();
    }

    protected static void glPre() {

        GL11.glPushMatrix();
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
        GL11.glLoadIdentity();
        GL11.glScalef(0.5f, 0.5f, 0.5f);

    }

    protected void doRendering(final Tessellator tessellator, final IIcon icon) {
        final double u, U, v, V;
        u = icon.getMinU();
        U = icon.getMaxU();
        v = icon.getMinV();
        V = icon.getMaxV();
        //
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0f, 0.0f, 1.0f);
        tessellator.addVertexWithUV(0.0, 0.0, 0.0, U, V);
        tessellator.addVertexWithUV(1.0, 0.0, 0.0, u, V);
        tessellator.addVertexWithUV(1.0, 1.0, 0.0, u, v);
        tessellator.addVertexWithUV(0.0, 1.0, 0.0, U, v);
        tessellator.draw();
    }

    protected static void glPost() {
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

}
