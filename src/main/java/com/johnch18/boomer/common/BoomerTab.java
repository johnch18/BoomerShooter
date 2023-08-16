package com.johnch18.boomer.common;


import com.johnch18.boomer.BoomerMod;
import com.johnch18.boomer.Tags;
import com.johnch18.boomer.common.items.IBoomerItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import java.util.Optional;


/**
 *
 */
public class BoomerTab extends CreativeTabs {

    /**
     *
     */
    public static final String SUPER_SHOTGUN = "super_shotgun";

    /**
     *
     */
    public BoomerTab() {
        super(Tags.MODID);
    }

    @Override
    public Item getTabIconItem() {
        final Optional<IBoomerItem> testGun = BoomerMod.items.getItem(SUPER_SHOTGUN);
        return testGun.map(IBoomerItem::asItem).orElse(null);
    }

}
