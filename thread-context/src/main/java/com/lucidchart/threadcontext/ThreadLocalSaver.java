package com.lucidchart.threadcontext;

public class ThreadLocalSaver<T> extends ThreadLocal<T> implements Saver {

    public Runnable save() {
        T value = get();
        return () -> set(value);
    }

}
