package cz.hydracore.electrovanilla;

import cz.hydracore.electrovanilla.electric.IElectricSource;

public class Battery implements IElectricSource {

    private int capacity;
    private int charge;

    public Battery(int capacity, int charge) {
        this.setCapacity(capacity);
        this.setCharge(charge);
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        assert capacity > 0;
        this.capacity = Math.max(0, capacity);
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = Math.max(0, Math.min(capacity, charge));
    }

    @Override
    public boolean isFullyCharged() {
        return this.capacity == this.charge;
    }

    @Override
    public boolean hasCharge() {
        return this.charge > 0;
    }

    @Override
    public boolean drain(int power) {
        if(power > charge) {
            return false;
        }

        charge -= power;

        return true;
    }

    @Override
    public int charge(int power) {
        int charged = charge + power;
        setCharge(charged);
        return charged - charge;
    }
}
