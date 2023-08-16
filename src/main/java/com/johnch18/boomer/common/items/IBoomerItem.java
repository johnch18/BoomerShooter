package com.johnch18.boomer.common.items;


import net.minecraft.item.Item;

import javax.annotation.Nonnull;


/**
 *
 */
public interface IBoomerItem {

    /**
     * @return ID of the item
     */
    @Nonnull
    String getID();

    /**
     * @return Literally just the object, this lets me treat IBoomerItem instances as items
     */
    @Nonnull
    Item asItem();

    /**
     * @return Gets the modid:id
     */
    @Nonnull
    String getFullID();

}
