package com.elsevier.recs

import scala.annotation.tailrec

object SumOfFibonacci extends FibonacciSummer {
  override def sumOfFibonacci(input: Int): Int = {

    def rec(idx: Seq[Int], input: Int): Seq[Int] = {
      if (idx.length >= input) idx
      else {
        val newIdx: Int = idx.reverse.take(2).sum
        rec(idx :+ newIdx, input)
      }
    }

    val idx = (1 to (if (input > 1) 2 else input)).toArray

    rec(idx, input).sum
  }
}

object SumOfFibonacciIndexReferenced extends FibonacciSummer {
  override def sumOfFibonacci(input: Int): Int = {

    def rec(idx: Array[Int], input: Int): Array[Int] = {
      if (idx.length >= input) idx
      else {
        val i = idx.length
        val newIdx: Int = idx(i - 1) + idx(i - 2)
        rec(idx :+ newIdx, input)
      }
    }
    val idx = (1 to (if (input > 1) 2 else input)).toArray

    rec(idx, input).sum
  }
}

object SumOfFibonacciIndexReferencedTailRec extends FibonacciSummer {
  override def sumOfFibonacci(input: Int): Int = {

    @tailrec
    def rec(idx: Array[Int], input: Int): Array[Int] = {
      if (idx.length >= input) idx
      else {
        val i = idx.length
        val newIdx: Int = idx(i - 1) + idx(i - 2)
        rec(idx :+ newIdx, input)
      }
    }

    val idx = (1 to (if (input > 1) 2 else input)).toArray

    rec(idx, input).sum
  }
}
