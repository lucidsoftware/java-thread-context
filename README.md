# Propagate thread local stuff

[![Build Status](https://travis-ci.org/lucidsoftware/java-thread-context.svg?branch=master)](https://travis-ci.org/lucidsoftware/java-thread-context)
![Maven Version](https://img.shields.io/maven-central/v/com.lucidchart/thread-context.svg)

## Install

Add these dependencies as appropriate:

* `com.lucidchart:thread-context:<version>`
* `com.lucidchart:thread-context-slf4j:<version>`

## Usage

Track a `ThreadLocal` across thread boundaries.

```java
import com.lucidchart.threadcontext.ContextManager;
import com.lucidchart.threadcontext.Executors;
import com.lucidchart.threadcontext.ThreadLocalSaver;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

final class Main {

    private static final ExecutorService executorService = new PropogatingExectorService(Executors.newFixedThreadPool(8));
    private static final ThreadLocalSaver example = new ThreadLocal<String>();

    static {
        ContextManager.savers.add(new ThreadLocalSaver<>(example));
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
        countdown(10);
        example.set("example2");
        countdown(20);
    }
}
```

Or track MDC variables.
