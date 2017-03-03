package com.lucidchart.threadcontext;

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

}
