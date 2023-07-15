package com.johnch18.boomer.common.items.impl;

import com.johnch18.boomer.BoomerMod;
import com.johnch18.boomer.Tags;
import com.johnch18.boomer.common.items.IBoomerItem;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;

/**
 *
 */
public abstract class BoomerItem extends Item implements IBoomerItem {

    BoomerItem(final boolean add) {
        if (add) {
            setCreativeTab(BoomerMod.tab);
        }
        setUnlocalizedName(getID());
        setTextureName(getFullID());
    }

    BoomerItem() {
        this(true);
    }

    @Nonnull
    @Override
    public Item asItem() {
        return this;
    }

    @Nonnull
    @Override
    public String getFullID() {
        return Tags.MODID + ":" + getID();
    }
}
