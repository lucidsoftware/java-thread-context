package com.github.threadcontext;

import java.util.function.Supplier;

public final class CombinedContextSupplier implements Supplier<Context> {

    public final Iterable<Supplier<Context>> suppliers;

    public CombinedContextSupplier(Iterable<Supplier<Context>> suppliers) {
        this.suppliers = suppliers;
    }

    public Context get() {
        Context result = null;
        for (Supplier<Context> supplier : suppliers) {
            result = result == null ? supplier.get() : result.andThen(supplier.get());
        }
        return result == null ? NullContext.INSTANCE : result;
    }

}
