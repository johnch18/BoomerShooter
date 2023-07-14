package com.johnch18.boomer.util;

import java.util.ArrayList;

public class HitScanManager extends ArrayList<HitScan> {

    public void march() {
        while (stream().anyMatch((hitScan -> !hitScan.isDead() && hitScan.getRay().lengthVector() <= hitScan.getShooter().getRange()))) {
            this.forEach(HitScan::singleMarch);
        }
    }

}
