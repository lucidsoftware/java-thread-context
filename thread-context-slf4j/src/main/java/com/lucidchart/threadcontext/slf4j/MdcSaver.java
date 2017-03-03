package com.lucidchart.threadcontext.slf4j;

import com.lucidchart.threadcontext.Saver;
import java.util.Map;
import org.slf4j.MDC;

public class MdcSaver implements Saver {

    public Runnable save() {
        Map<String, String> value = MDC.getCopyOfContextMap();
        return value == null ? MDC::clear : () -> MDC.setContextMap(value);
    }

}
