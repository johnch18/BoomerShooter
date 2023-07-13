package com.johnch18.boomer.util;

import com.johnch18.boomer.BoomerMod;
import com.johnch18.boomer.Tags;
import com.johnch18.boomer.common.items.IBoomerItem;
import com.johnch18.boomer.common.items.impl.BoomerItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import java.util.Optional;

public class BoomerTab extends CreativeTabs {

    public BoomerTab() {
        super(Tags.MODID);
    }

    @Override
    public Item getTabIconItem() {
        Optional<IBoomerItem> testGun = BoomerMod.items.getItem("test_gun");
        return testGun.map(IBoomerItem::asItem).orElse(null);
    }

}
