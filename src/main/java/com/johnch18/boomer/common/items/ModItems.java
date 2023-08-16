package com.johnch18.boomer.common.items;


import com.johnch18.boomer.common.items.impl.doom.misc.BFGTracer;
import com.johnch18.boomer.common.items.impl.doom.ammo.BFGAmmo;
import com.johnch18.boomer.common.items.impl.doom.ammo.ShotgunShell;
import com.johnch18.boomer.common.items.impl.doom.weapons.BFG9000;
import com.johnch18.boomer.common.items.impl.doom.weapons.Shotgun;
import com.johnch18.boomer.common.items.impl.doom.weapons.SuperShotgun;
import cpw.mods.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 *
 */
public final class ModItems {

    /**
     * Singleton
     */
    public static final ModItems INSTANCE = new ModItems();

    private final Map<String, IBoomerItem> items = new HashMap<>();

    private ModItems() {
    }

    /**
     * Registers mod items
     */
    public void registerItems() {
        registerGun(Shotgun.class, ShotgunShell.class);
        registerGun(SuperShotgun.class, ShotgunShell.class);
        registerGun(BFG9000.class, BFGAmmo.class);
        registerItem(new BFGTracer());
    }

    /**
     * @return item map
     */
    @Nonnull
    public Map<String, IBoomerItem> getItems() {
        return Collections.unmodifiableMap(items);
    }

    /**
     * @param itemID id to check
     * @return Item corresponding to it
     */
    @Nonnull
    public Optional<IBoomerItem> getItem(final String itemID) {
        if (contains(itemID)) {
            return Optional.of(items.get(itemID));
        }
        return Optional.empty();
    }

    private boolean contains(final String itemID) {
        return items.containsKey(itemID);
    }

    private IBoomerItem registerItem(final IBoomerItem item) {
        if (contains(item.getID())) {
            return items.get(item.getID());
        }
        items.put(item.getID(), item);
        GameRegistry.registerItem(item.asItem(), item.getID());
        return items.get(item.getID());
    }

    @SuppressWarnings("ProhibitedExceptionThrown")
    private void registerGun(final Class<? extends IShooter<?>> gunClass, final Class<? extends IAmmo> ammoClass) {
        final IBoomerItem gAmmo;
        try {
            final Constructor<?> constructor = ammoClass.getConstructor();
            gAmmo = registerItem((IBoomerItem) constructor.newInstance());
        } catch (final NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        if (!(gAmmo instanceof IAmmo)) {
            throw new RuntimeException("Invalid ammo");
        }
        final IAmmo ammo = (IAmmo) gAmmo;
        try {
            final Constructor<?> constructor = gunClass.getConstructor(ammoClass);
            registerItem((IBoomerItem) constructor.newInstance(ammo));
        } catch (final NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
