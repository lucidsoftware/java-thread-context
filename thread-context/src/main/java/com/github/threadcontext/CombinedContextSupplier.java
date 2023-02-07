package com.github.threadcontext;

import java.util.function.Supplier;
import java.util.Iterator;

public final class CombinedContextSupplier implements Supplier<Context> {

    public final Iterable<Supplier<Context>> suppliers;

    public CombinedContextSupplier(Iterable<Supplier<Context>> suppliers) {
        this.suppliers = suppliers;
    }

    public Context get() {
        Context result = null;
        Iterator<Supplier<Context>> iterator = this.suppliers.iterator();
        while (iterator.hasNext()) {
            Supplier<Context> supplier = iterator.next();
            result = result == null ? supplier.get() : result.andThen(supplier.get());
        }
        return result == null ? NullContext.INSTANCE : result;
    }

}
