package com.github.threadcontext;

public class ThreadLocalSaver<T> implements Saver {

    private final ThreadLocal<T> value;

    public ThreadLocalSaver(ThreadLocal<T> value) {
        this.value = value;
    }

    public Runnable save() {
        T value = this.value.get();
        return () -> this.value.set(value);
    }

}
