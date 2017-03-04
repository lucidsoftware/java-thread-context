package com.lucidchart.threadcontext

import java.util.concurrent.{Callable, Executors, TimeUnit}
import org.specs2.mutable.Specification

class Test extends Specification {
  sequential

  "threadcontext" should {

    "propogate" in {
      val value = new ThreadLocal[String]
      val saver = new ThreadLocalSaver(value)
      ContextManager.savers.add(saver)
      try {
        val executor = new PropogatingExecutorService(Executors.newFixedThreadPool(8))
        try {
          val condition = new AnyRef
          @volatile var notified = false

          value.set("example1")
          val example1 = executor.submit(new Callable[String] {
            def call() = {
              condition.synchronized {
                while (!notified) {
                  condition.wait()
                }
              }
              value.get()
            }
          })
          value.set("example2")
          val example2 = executor.submit(new Callable[String] {
            def call() = {
              val v = value.get()
              notified = true
              condition.synchronized { condition.notify() }
              v
            }
          })

          example1.get must_== "example1"
          example2.get must_== "example2"
        } finally {
          executor.shutdownNow()
          executor.awaitTermination(1, TimeUnit.MINUTES)
        }
      } finally {
        ContextManager.savers.remove(saver)
      }
    }
  }

}