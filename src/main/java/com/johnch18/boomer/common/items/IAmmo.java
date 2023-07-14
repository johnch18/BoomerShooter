package com.johnch18.boomer.common.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 *
 */
public interface IAmmo extends IBoomerItem {

    /**
     * @return Gets maximum ammo an instance can hold
     */
    int getMaxCapacity();

    /**
     * @return Gets the amount to fire this
     */
    int getFireCost();

    /**
     * @param stack Ammo stack to check
     * @return Is it usable?
     */
    boolean canUse(ItemStack stack);

    /**
     * @param other Checks that the other item is the same
     * @return is same?
     */
    boolean isCorrectType(Item other);
}
