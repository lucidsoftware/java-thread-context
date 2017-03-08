package com.github.threadcontext.slf4j;

import com.github.threadcontext.Context;
import com.github.threadcontext.control.TryFinallyContext;
import java.util.Map;
import java.util.function.Supplier;
import org.slf4j.MDC;

public class MdcContextSupplier implements Supplier<Context> {

    public Context get() {
        Map<String, String> value = MDC.getCopyOfContextMap();
        return new TryFinallyContext(() -> {
            Map<String, String> oldValue = MDC.getCopyOfContextMap();
            if (value == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(value);
            }
            return () -> {
                if (oldValue == null) {
                    MDC.clear();
                } else {
                    MDC.setContextMap(oldValue);
                }
            };
        });
    }

}
