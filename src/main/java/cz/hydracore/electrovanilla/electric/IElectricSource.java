package cz.hydracore.electrovanilla.electric;

import cz.hydracore.electrovanilla.core.Output;

public interface IElectricSource extends Output {

    boolean hasCharge();

    boolean isFullyCharged();

    boolean drain(int power);

    int charge(int power);

    int getCapacity();

}
