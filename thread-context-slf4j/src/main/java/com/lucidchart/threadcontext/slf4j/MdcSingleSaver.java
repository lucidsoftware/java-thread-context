package com.lucidchart.threadcontext.slf4j;

import com.lucidchart.threadcontext.Saver;
import org.slf4j.MDC;

public class MdcSingleSaver implements Saver {

    private final String key;

    public MdcSingleSaver(String key) {
        this.key = key;
    }

    public Runnable save() {
        String value = MDC.get(key);
        return value == null ? () -> MDC.remove(key) : () -> MDC.put(key, value);
    }

}
