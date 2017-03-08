package com.github.threadcontext

import java.util.concurrent._
import org.specs2.mutable.Specification

class Test extends Specification {
  sequential

  "threadcontext" should {

    "propogate" in {
      val value = new ThreadLocal[String]
      val supplier = new ThreadLocalContextSupplier(value)
      val executor = new PropagatingExecutorService(Executors.newFixedThreadPool(2), supplier)
      try {
        val condition = new Semaphore(0)

        value.set("example1")
        val example1 = executor.submit(new Callable[String] {
          def call() = {
            condition.acquire()
            value.get()
          }
        })
        value.set("example2")
        val example2 = executor.submit(new Callable[String] {
          def call() = {
            val v = value.get()
            condition.release()
            v
          }
        })

        example1.get must_== "example1"
        example2.get must_== "example2"
      } finally {
        executor.shutdown()
        executor.awaitTermination(1, TimeUnit.MINUTES)
      }
    }
  }

}
