package com.github.threadcontext;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

public interface Saver {

    Runnable save();

    default void runAndRestore(Runnable runnable) {
        Runnable restore = save();
        try {
            runnable.run();
        } finally {
            restore.run();
        }
    }

    default <T> T runAndRestore(Callable<T> callable) throws Exception {
        Runnable restore = save();
        try {
            return callable.call();
        } finally {
            restore.run();
        }
    }

    default <T> T runAndRetore(Supplier<T> supplier) {
        Runnable restore = save();
        try {
            return supplier.get();
        } finally {
            restore.run();
        }
    }

}
