package cz.hydracore.electrovanilla.electric;

public interface IElectricSource {

    boolean hasCharge();

    boolean isFullyCharged();

    boolean drain(int power);

    int charge(int power);

    int getCapacity();

}
