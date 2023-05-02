package com.github.threadcontext;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;
import java.util.function.Supplier;

public class MutableContextSupplier implements Supplier<Context> {

    public final List<Supplier<Context>> suppliers;

    public MutableContextSupplier() {
        // Use CopyOnWriteArray to prevent ConcurrentModificationException
        // if something tries to consume the suppplier while another thread is
        // adding new suppliers.
        //
        // This shouldn't hurt performance too  much, since the list of suppliers should be
        // small, and setting suppliers should just be done once early in the lifecycle of the context.
        // And this prevents us to need to synchronize whenever we read or iterate over the suppliers,
        // which will be much more common than writing to the list.
        suppliers = new CopyOnWriteArrayList<>();
    }

    public Context get() {
        return new CombinedContextSupplier(suppliers).get();
    }

}
