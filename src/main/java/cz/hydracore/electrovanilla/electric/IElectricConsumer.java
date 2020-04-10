package cz.hydracore.electrovanilla.electric;

import cz.hydracore.electrovanilla.core.Input;
import org.jetbrains.annotations.NotNull;

public interface IElectricConsumer extends Input {

    void setSource(@NotNull IElectricSource source);

}
