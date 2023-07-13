package com.johnch18.boomer.common.items;

import com.johnch18.boomer.common.items.impl.TestAmmo;
import com.johnch18.boomer.common.items.impl.TestGun;
import cpw.mods.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ModItems {

    public static final ModItems INSTANCE = new ModItems();

    private final Map<String, IBoomerItem> items = new HashMap<>();

    private ModItems() {

    }

    public void registerItems() {
        registerGun(TestGun.class, TestAmmo.class);
    }

    private IBoomerItem registerItem(IBoomerItem item) {
        System.out.println(item.getID());
        if (contains(item.getID())) {
            return items.get(item.getID());
        }
        items.put(item.getID(), item);
        GameRegistry.registerItem(item.asItem(), item.getID());
        return items.get(item.getID());
    }

    private void registerGun(Class<? extends IShooter<?>> gunClass, Class<? extends IAmmo> ammoClass) {
        IBoomerItem gAmmo;
        try {
            Constructor<?> constructor = ammoClass.getConstructor();
            gAmmo = registerItem((IBoomerItem) constructor.newInstance());
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        if (!(gAmmo instanceof IAmmo)) {
            throw new RuntimeException("Invalid ammo");
        }
        IAmmo ammo = (IAmmo) gAmmo;
        try {
            Constructor<?> constructor = gunClass.getConstructor(ammoClass);
            registerItem((IBoomerItem) constructor.newInstance(ammo));
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Nonnull
    public Map<String, IBoomerItem> getItems() {
        return items;
    }

    @Nonnull
    public Optional<IBoomerItem> getItem(String itemID) {
        if (contains(itemID)) {
            return Optional.of(items.get(itemID));
        }
        return Optional.empty();
    }

    public boolean contains(String itemID) {
        return items.containsKey(itemID);
    }

}
