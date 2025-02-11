package cz.hydracore.utils;

import cz.hydracore.electrovanilla.ElectroVanilla;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.function.Consumer;

public class RunnableHelper {

    private static boolean isStopping;

    public static void setServerStopping() {
        isStopping = true;
    }

    public static void runTask(Runnable runnable) {
        if(isStopping) {
            runnable.run();
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTask(ElectroVanilla.getInstance());
    }

    public static void runTaskAsynchronously(Runnable runnable) {
        if(isStopping) {
            runnable.run();
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskAsynchronously(ElectroVanilla.getInstance());
    }

    public static void runTaskLater(Runnable runnable, long delay) {
        if(isStopping) {
            runnable.run();
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskLater(ElectroVanilla.getInstance(), delay);
    }

    public static void runTaskLater(Runnable runnable) {
        runTaskLater(runnable, 1);
    }

    public static void runTaskLaterAsynchronously(Runnable runnable, long delay) {
        if(isStopping) {
            runnable.run();
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskLaterAsynchronously(ElectroVanilla.getInstance(), delay);
    }

    public static void runTaskLaterAsynchronously(Runnable runnable) {
        runTaskLaterAsynchronously(runnable, 1);
    }

    public static void runTaskTimer(Runnable runnable, long delay, long period) {
        if(isStopping) {
            runnable.run();
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskTimer(ElectroVanilla.getInstance(), (period <= 20 * 10) ? (delay + RunnableDelay.getDelay()) : delay, period);
    }

    public static void runTaskTimer(Runnable runnable, long period) {
        runTaskTimer(runnable, 0, period);
    }

    public static void runTaskTimerAsynchronously(Runnable runnable, long delay, long period) {
        if(isStopping) {
            runnable.run();
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskTimerAsynchronously(ElectroVanilla.getInstance(), (period <= 20 * 10) ? (delay + RunnableDelay.getDelay()) : delay, period);
    }

    public static void runTaskTimerAsynchronously(Runnable runnable, long period) {
        runTaskTimerAsynchronously(runnable, 0, period);
    }

}
