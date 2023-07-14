package com.johnch18.boomer.common.items.impl;

import com.johnch18.boomer.common.items.IAmmo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 *
 */
@SuppressWarnings("AbstractClassWithOnlyOneDirectInheritor")
public abstract class Ammo extends BoomerItem implements IAmmo {

    /**
     *
     */
    @SuppressWarnings({"WeakerAccess", "ConstructorNotProtectedInAbstractClass"})
    public Ammo() {
        setMaxDamage(getMaxCapacity());
        setMaxStackSize(1);
    }

    @Override
    public boolean canUse(final ItemStack stack) {
        return stack.getItemDamage() > (getMaxCapacity() - getFireCost());
    }

    @Override
    public boolean isCorrectType(final Item other) {
        return other instanceof IAmmo && other.equals(asItem());
    }
}
