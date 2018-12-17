package workshops

import workshops.WS09Futures.timeCapsule

import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success, Try}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object WS09Futures {
  def timeCapsule[T](msg: T) : T = {
    Thread.sleep(5000)
    msg
  }
  val badFuture : Future[String]= Future(throw new NullPointerException("Oh no getting the old problems again"))

  def printOutcome(future: Future[Any])  =
    future.onComplete((f: Try[Any]) => f match {
      case Success(msg) => println(msg)
      case Failure(exception) => println(exception.getMessage)
    })

  def liftIntoTheFuture[A,B](func: A => B): Future[A] => Future[B]  = {(fut: Future[A] ) => fut.map(func)}
  //
//  printOutcome(goodFuture)
//  printOutcome(badFuture)
//
//  def lengthLifted(f : Future[String]) = f.map(x => x.length)
//
//  val goodFutureLength = lengthLifted(goodFuture)
//  printOutcome(goodFutureLength)


}

object futureDemo extends App {
  {
    println("Begin demo1")
    val goodFuture: Future[String] = Future(timeCapsule("A bright future"))
    WS09Futures.printOutcome(goodFuture)
    Await.ready(goodFuture, 20 seconds)
  }
  {
    println("Begin demo2")
    val goodFuture: Future[String] = Future(timeCapsule("A bright future"))
    val intFuture = for {
      x <- goodFuture
    } yield (x.length)
    goodFuture.map(_.length)
    WS09Futures.printOutcome(intFuture)
    Await.ready(intFuture, 20 seconds)
  }
  {
    println("Begin demo3")
    val goodFuture: Future[String] = Future(timeCapsule("A bright future"))
    val lenLifted = WS09Futures.liftIntoTheFuture((s: String) => s.length)
    val intFuture = lenLifted(goodFuture)
    WS09Futures.printOutcome(intFuture)
    Await.ready(intFuture, 20 seconds)
  }
  {
    println("Begin demo4")
    val badFuture : Future[String]= Future(timeCapsule(throw new NullPointerException("Oh no getting the old problems again")))
    WS09Futures.printOutcome(badFuture)
    Await.ready(badFuture, 20 seconds)
  }
  println("Done")
}


