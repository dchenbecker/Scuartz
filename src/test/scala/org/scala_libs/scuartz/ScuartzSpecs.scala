package org.scala_libs.scuartz

import java.util.Date

import org.quartz.{Job,JobDetail,JobExecutionContext,SimpleTrigger}
import org.quartz.impl.StdSchedulerFactory

import org.specs._
import org.specs.specification._

import Scuartz._

class ScuartzSpecs extends Specification with DetailedFailures {
  class TestJob extends Job {
    def execute(ctxt : JobExecutionContext) {
      println("test")
    }
  }
    
  "Scuartz" should {
    val sched = StdSchedulerFactory.getDefaultScheduler

    val testJob = new JobDetail("test", classOf[TestJob])
   
    sched.addJob(testJob, true)

    "implicitly convert to RichScheduler as needed" in {
        val trigger = new SimpleTrigger("test", "test", new Date)
        trigger.setJobName("test")
        val ret = sched.schedule(trigger)
        ret must haveClass[RichScheduler]
    }

    "schedule a simple timed job" in {
      (sched.schedule { "test" at (new Date) }).isExpectation
    }

    "schedule a complex timed job" in {
      val now = System.currentTimeMillis
      (sched.schedule { "test" named "test2" at (now + 5000l) every 1000l until (new Date(now + 10000l)) }).isExpectation
    }

    "schedule a job from a function" in {
      (sched.schedule {(() ⇒ { println("Tick!") }) as "ticker" every 1000l }).isExpectation
      
    }

    "schedule a closure properly" in {
      sched.start()

      // Let's actually do something in this spec
      var counter = 0
      val incrementer = () => {
        counter += 1
        println("Counter = " + counter)
      }

      sched.schedule { incrementer as "incrementer" after 1000l every 100l repeat 5 }

      Thread.sleep(3000l)

      sched.shutdown()

      counter must_== 5
    }
  }
}
