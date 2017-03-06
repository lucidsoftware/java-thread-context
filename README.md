# Propagate thread local stuff

[![Build Status](https://travis-ci.org/lucidsoftware/java-thread-context.svg?branch=master)](https://travis-ci.org/lucidsoftware/java-thread-context)
![Maven Version](https://img.shields.io/maven-central/v/com.lucidchart/thread-context.svg)

This project provides a common mechanism for propagating thread-local data across system thread boundaries. This is
useful for logging/tracing/debugging in concurrent or asynchronous systems.

## Usage

1. Instrument your concurrency/flow primitives with `ThreadContext.runnable(Runnable)` or
`ThreadContent.callable(Callable)`.
2. Register your callables with `ThreadContext.savers`.

### ThreadLocal

`com.lucidchart:thread-context:<version>`

For example, suppose there is a task that is scheduled on a thread pool in 10 sequential tasks. You can track a
`ThreadLocal` through each of the tasks.

```java
import com.github.threadcontext.ContextManager;
import com.github.threadcontext.PropagatingExecutorService;
import com.github.threadcontext.ThreadLocalSaver;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

final class Main {

    // Step 1: instrument thread pool
    private static final ExecutorService executorService = new PropagatingExectorService(Executors.newFixedThreadPool(8));

    // Step 2: register ThreadLocal
    private static final ThreadLocalSaver example = new ThreadLocal<String>();
    static {
        ThreadContext.savers.add(new ThreadLocalSaver<>(example));
    }

    private static void countdown(int n) {
        if (n > 0) {
            executorService.submit(() -> {
                System.out.println(String.format("%d %s", n, example.get()));
                work(n - 1);
            });
        }
    }

    public void main(String[] args) {
        example.set("example1");
        countdown(10); // these tasks print example1
        example.set("example2");
        countdown(20); // these tasks print example
    }
}
```

### MDC

`com.lucidchart:thread-context-slf4j:<version>`

You can also track MDC (Mapped Diagnostic Context) values.

```java
ThreadContext.savers.add(new MdcSingleSaver("example"));
```

Now, the MDC `example` key will be propagated across thread boundaries.

## Instrumentation notes

When instrumenting a thread pool or other construct, wrap tasks at their "creation" origin with
`ThreadContent.runnable(Runnable)` or `ThreadContent.callable(Callable)`. When called, the objects produced from these
will load the saved context and undo that when finished. Look at
[`PropogatingExecutorService`](thread-context/src/main/java/com/github/threadcontext/PropagatingExecutorService.java)
for an example.
