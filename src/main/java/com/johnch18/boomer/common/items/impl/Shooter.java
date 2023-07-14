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

public abstract class Shooter<T extends IAmmo> extends BoomerItem implements IShooter<T> {

    private static final String TAG_FIRE_COOLDOWN = "fire";

    private final T ammo;

    private final Random rand = new Random();

    public Shooter(T ammo) {
        super();
        this.ammo = ammo;
    }

    private static boolean checkNBT(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        NBTTagCompound comp = stack.getTagCompound();
        if (!comp.hasKey(TAG_FIRE_COOLDOWN)) {
            comp.setInteger(TAG_FIRE_COOLDOWN, 0);
        }
        int cooldown = comp.getInteger(TAG_FIRE_COOLDOWN);
        return cooldown > 0;
    }

    @Override
    public boolean playerCanFire(ItemStack stack, EntityPlayer player) {
        if (checkNBT(stack)) {
            return false;
        }
        if (player.capabilities.isCreativeMode) {
            return true;
        }
        boolean found = false;
        ItemStack currentStack;
        for (int i = player.inventory.mainInventory.length - 1; i >= 0; i--) {
            currentStack = player.inventory.mainInventory[i];
            Item item = currentStack.getItem();
            if (getAmmoType().isCorrectType(item)) {
                IAmmo ammo = (IAmmo) item;
                if (ammo.canUse(currentStack)) {
                    found = true;
                    currentStack.setItemDamage(currentStack.getItemDamage() + ammo.getFireCost());
                    break;
                }
            }
        }
        return found;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!playerCanFire(stack, player)) {
            return stack;
        }
        fireGun(player, world, player.posX, player.posY, player.posZ);
        NBTTagCompound comp = stack.getTagCompound();
        comp.setInteger(TAG_FIRE_COOLDOWN, getCooldown());
        stack.setTagCompound(comp);
        player.inventory.markDirty();
        player.inventoryContainer.detectAndSendChanges();
        return stack;
    }

    @Nonnull
    @Override
    public T getAmmoType() {
        return ammo;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int i1, boolean b1) {
        checkNBT(stack);
        NBTTagCompound compound = stack.getTagCompound();
        int cooldown = compound.getInteger(TAG_FIRE_COOLDOWN);
        if (cooldown > 0) {
            cooldown -= 1;
        } else if (cooldown < 0) {
            cooldown = 0;
        }
        compound.setInteger(TAG_FIRE_COOLDOWN, cooldown);
        stack.setTagCompound(compound);
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List lore, boolean b1) {
        checkNBT(stack);
        int cooldown = stack.getTagCompound().getInteger(TAG_FIRE_COOLDOWN);
        lore.add(String.format("Cooldown: %d / %d", cooldown, getCooldown()));
    }

    @Override
    public AxisAlignedBB getHitbox(Vec3 vec) {
        return getHitbox(vec.xCoord, vec.yCoord, vec.zCoord);
    }

    @Override
    public Random getRand() {
        return rand;
    }

    @Override
    public double getDoubleInRange(double x, double y) {
        return x + (y - x) * getRand().nextDouble();
    }

    @Override
    public Vec3 getRandomMotionFromPlayerLook(EntityPlayer player, float d) {
        float pitch = (float) (player.rotationPitch + getDoubleInRange(-d, d));
        float yaw = (float) (player.rotationYaw + getDoubleInRange(-d, d));
        float f = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
        float f1 = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
        float f2 = -MathHelper.cos(-pitch * 0.017453292F);
        float f3 = MathHelper.sin(-pitch * 0.017453292F);
        return Vec3.createVectorHelper(f1 * f2, f3, f * f2);
    }
}
