package com.johnch18.boomer.common.items;

import net.minecraft.item.Item;

import javax.annotation.Nonnull;

public interface IBoomerItem {

    @Nonnull
    String getID();

    @Nonnull
    Item asItem();

    @Nonnull
    String getFullID();
}
