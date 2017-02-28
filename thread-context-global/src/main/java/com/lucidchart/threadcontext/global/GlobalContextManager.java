package com.lucidchart.threadcontext.global;

import com.lucidchart.threadcontext.ContextManager;
import com.lucidchart.threadcontext.ThreadLocalContextManager;

public final class GlobalContextManager {

    private static volatile ContextManagerProvider provider = new CachingContextManagerProvider(new ContextManagerProvider() {
        @SuppressWarnings("unchecked")
        public <T> ContextManager<T> get(Class<T> klass) {
            return (ContextManager<T>)new ThreadLocalContextManager<>();
        }
    });

    private GlobalContextManager() {
    }

    public static <T> ContextManager<T> get(Class<T> klass) {
        return provider.get(klass);
    };

    public static void setProvider(ContextManagerProvider provider) {
        GlobalContextManager.provider = new CachingContextManagerProvider(provider);
    }

}
