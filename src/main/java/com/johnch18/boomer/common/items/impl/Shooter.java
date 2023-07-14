package com.johnch18.boomer.common.items.impl;

import com.johnch18.boomer.common.items.IAmmo;
import com.johnch18.boomer.common.items.IShooter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

/**
 * @param <T> Ammo type
 */
public abstract class Shooter<T extends IAmmo> extends BoomerItem implements IShooter<T> {

    private static final String TAG_FIRE_COOLDOWN = "fire";

    private final T ammo;

    private final Random rand = new Random();

    /**
     * @param ammo Ammo to use
     */
    @SuppressWarnings("ConstructorNotProtectedInAbstractClass")
    public Shooter(final T ammo) {
        super();
        this.ammo = ammo;
    }

    @Nonnull
    @Override
    public T getAmmoType() {
        return ammo;
    }

    @Override
    public boolean canPlayerFire(final ItemStack stack, final EntityPlayer player) {
        if (checkNBT(stack)) {
            return false;
        }
        if (player.capabilities.isCreativeMode) {
            return true;
        }
        boolean found = false;
        ItemStack currentStack;
        for (int i = player.inventory.mainInventory.length - 1; 0 <= i; i--) {
            currentStack = player.inventory.mainInventory[i];
            final Item item = currentStack.getItem();
            if (ammo.isCorrectType(item)) {
                if (ammo.canUse(currentStack)) {
                    found = true;
                    currentStack.setItemDamage(currentStack.getItemDamage() + ammo.getFireCost());
                    break;
                }
            }
        }
        return found;
    }

    @Nonnull
    @Override
    public AxisAlignedBB getHitbox(final Vec3 vec) {
        return getHitbox(vec.xCoord, vec.yCoord, vec.zCoord);
    }

    @Nonnull
    @Override
    public Random getRand() {
        return rand;
    }

    @Override
    public double getDoubleInRange(final double low, final double high) {
        return low + (high - low) * rand.nextDouble();
    }

    @Nonnull
    @Override
    public Vec3 getRandomMotionFromPlayerLook(final EntityPlayer player, final double variance) {
        final double pitch = ((double) player.rotationPitch + getDoubleInRange(-variance, variance));
        final double yaw =  ((double) player.rotationYaw + getDoubleInRange(-variance, variance));
        final float f = MathHelper.cos((float) (-yaw * 0.017453292 - Math.PI));
        final float f1 = MathHelper.sin((float) (-yaw * 0.017453292 - Math.PI));
        final float f2 = -MathHelper.cos((float) (-pitch * 0.017453292));
        final float f3 = MathHelper.sin((float) (-pitch * 0.017453292));
        return Vec3.createVectorHelper((double) (f1 * f2), (double) f3, (double) (f * f2));
    }

    @Override
    public int getSpriteNumber() {
        return 0;
    }

    @Override
    public ItemStack onItemRightClick(final ItemStack stack, final World world, final EntityPlayer player) {
        if (!canPlayerFire(stack, player)) {
            return stack;
        }
        fireGun(player, world);
        final NBTTagCompound comp = stack.getTagCompound();
        comp.setInteger(TAG_FIRE_COOLDOWN, getCooldown());
        stack.setTagCompound(comp);
        player.inventory.markDirty();
        player.inventoryContainer.detectAndSendChanges();
        return stack;
    }

    @Override
    public void onUpdate(final ItemStack stack, final World world, final Entity entity, final int i1, final boolean b1) {
        checkNBT(stack);
        final NBTTagCompound compound = stack.getTagCompound();
        int cooldown = compound.getInteger(TAG_FIRE_COOLDOWN);
        if (cooldown > 0) {
            cooldown -= 1;
        } else if (cooldown < 0) {
            cooldown = 0;
        }
        compound.setInteger(TAG_FIRE_COOLDOWN, cooldown);
        stack.setTagCompound(compound);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addInformation(final ItemStack stack, final EntityPlayer player, final List lore, final boolean b1) {
        checkNBT(stack);
        final int cooldown = stack.getTagCompound().getInteger(TAG_FIRE_COOLDOWN);
        lore.add(String.format("Cooldown: %d / %d", cooldown, getCooldown()));
    }

    private static boolean checkNBT(final ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        final NBTTagCompound comp = stack.getTagCompound();
        if (!comp.hasKey(TAG_FIRE_COOLDOWN)) {
            comp.setInteger(TAG_FIRE_COOLDOWN, 0);
        }
        final int cooldown = comp.getInteger(TAG_FIRE_COOLDOWN);
        return 0 < cooldown;
    }
}
