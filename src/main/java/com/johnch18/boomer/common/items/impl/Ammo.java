package com.johnch18.boomer.common.items.impl;

import com.johnch18.boomer.common.items.IAmmo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class Ammo extends BoomerItem implements IAmmo {

    public Ammo() {
        setMaxDamage(getMaxCapacity());
        setMaxStackSize(1);
    }

    @Override
    public boolean canUse(ItemStack stack) {
        return stack.getItemDamage() > (getMaxCapacity() - getFireCost());
    }

    @Override
    public boolean isCorrectType(Item other) {
        return other instanceof IAmmo && other.equals(asItem());
    }
}
