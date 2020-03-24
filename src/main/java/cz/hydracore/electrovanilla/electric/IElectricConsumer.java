package cz.hydracore.electrovanilla.electric;

import org.jetbrains.annotations.NotNull;

public interface IElectricConsumer {

    void setSource(@NotNull IElectricSource source);

}
