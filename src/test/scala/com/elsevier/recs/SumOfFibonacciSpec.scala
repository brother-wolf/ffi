package com.elsevier.recs

import org.scalatest.{FlatSpec, Matchers}

class SumOfFibonacciSpec extends FlatSpec with Matchers {
  private val numCycles = 200

  ignore should "calc fibonacci sum of numbers" in {
    val cycles = (0 to numCycles).map { _ =>
      val start = System.nanoTime()
      SumOfFibonacci.sumOfFibonacci(3) should be(6)
      SumOfFibonacci.sumOfFibonacci(5) should be(19)
      SumOfFibonacci.sumOfFibonacci(10) should be(231)
      SumOfFibonacci.sumOfFibonacci(13) should be(985)
      SumOfFibonacci.sumOfFibonacci(43) should be(1836311901)
      val end = System.nanoTime()
      (end - start) / 1000
    }
    println(s"scala: $cycles")
  }

  ignore should "calc fibonacci 'index ref'd' sum of numbers" in {
    val cycles = (0 to numCycles).map { _ =>
      val start = System.nanoTime()
      SumOfFibonacciIndexReferenced.sumOfFibonacci(3) should be(6)
      SumOfFibonacciIndexReferenced.sumOfFibonacci(5) should be(19)
      SumOfFibonacciIndexReferenced.sumOfFibonacci(10) should be(231)
      SumOfFibonacciIndexReferenced.sumOfFibonacci(13) should be(985)
      SumOfFibonacciIndexReferenced.sumOfFibonacci(43) should be(1836311901)
      val end = System.nanoTime()
      (end - start) / 1000
    }
    println(s"scala index ref'd: $cycles")
  }

  ignore should "calc fibonacci sum of numbers in Rust" in {
    val cycles = (0 to numCycles).map { _ =>
      val start = System.nanoTime()
      ScalaRustFFI.sumOfFibonacci(3) should be(6)
      ScalaRustFFI.sumOfFibonacci(5) should be(19)
      ScalaRustFFI.sumOfFibonacci(10) should be(231)
      ScalaRustFFI.sumOfFibonacci(13) should be(985)
      ScalaRustFFI.sumOfFibonacci(43) should be(1836311901)
      val end = System.nanoTime()
      (end - start) / 1000
    }
    println(s"rust via scala: $cycles")
  }
}
