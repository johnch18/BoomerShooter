package com.johnch18.boomer.common.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IAmmo extends IBoomerItem {

    int getMaxCapacity();

    int getFireCost();

    boolean canUse(ItemStack stack);

    boolean isCorrectType(Item other);
}
