package com.johnch18.boomer.util;

import java.util.ArrayList;

/**
 *
 */
public class HitScanManager {

    private final ArrayList<HitScan> arrayList = new ArrayList<>();

    /**
     *
     */
    public void march() {
        while (arrayList.stream().anyMatch((hitScan -> !hitScan.isDead() && hitScan.getRay().lengthVector() <= hitScan.getShooter().getRange()))) {
            arrayList.forEach(HitScan::singleMarch);
        }
    }

    /**
     * @param hitScan hitscan to add
     */
    public void add(final HitScan hitScan) {
        arrayList.add(hitScan);
    }

}
