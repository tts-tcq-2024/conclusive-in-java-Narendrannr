package TypewiseAlert;

import TypewiseAlert.cooler.CoolerInterface;

public class BatteryCharacter {
    private final CoolerInterface mCooler;
    private final String mBrand;

    public String getBrand() {
        return mBrand;
    }

    public CoolerInterface getCooler() {
        return mCooler;
    }

    public BatteryCharacter(CoolerInterface cooler, String brand) {
        mCooler = cooler;
        mBrand = brand;
    }
}
