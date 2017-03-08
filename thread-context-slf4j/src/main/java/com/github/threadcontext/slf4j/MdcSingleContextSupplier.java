package com.github.threadcontext.slf4j;

import com.github.threadcontext.Context;
import com.github.threadcontext.control.TryFinallyContext;
import java.util.function.Supplier;
import org.slf4j.MDC;

public class MdcSingleContextSupplier implements Supplier<Context> {

    private final String key;

    public MdcSingleContextSupplier(String key) {
        this.key = key;
    }

    public Context get() {
        String value = MDC.get(key);
        return new TryFinallyContext(() -> {
            String oldValue = MDC.get(key);
            if (value == null) {
                MDC.remove(key);
            } else {
                MDC.put(key, value);
            }
            return () -> {
                if (oldValue == null) {
                    MDC.remove(key);
                } else {
                    MDC.put(key, oldValue);
                }
            };
        });
    }

}
