package com.elsevier.recs

import org.scalatest.{FlatSpec, Matchers}

import java.io.{BufferedWriter, File, FileWriter}
import scala.io.Source
import scala.math.random

class ExperimentRunnerSpec extends FlatSpec with Matchers {
  private val numCycles = 200
  private def randomDataPath: String = getClass.getResource("/random-data.csv").getPath

  /**
   * TODO !!!
   * Run this test to generate the random number file
   * that will be used for all the experiments
   */
  ignore should "re-generate the static random file of numbers to run" in {
    val rands = (0 to numCycles).map { _ => (random * 43).toInt + 1 }
    writeData(randomDataPath, rands)
  }

  it should "generate rust debug times" in {
    writeData("./rust-debug-results.csv", process(ScalaRustFFI, readData(randomDataPath)))
  }

  it should "generate rust release times" in {
    writeData("./rust-release-results.csv", process(ScalaRustFFI, readData(randomDataPath)))
  }
  it should "generate scala times" in {
    writeData("./scala-results.csv", process(SumOfFibonacci, readData(randomDataPath)))
  }
  it should "generate scala improved times" in {
    writeData("./scala-improved-results.csv", process(SumOfFibonacciIndexReferenced, readData(randomDataPath)))
  }

  case class TimerResult(timeTaken: Long, answer: Int)

  def timeIt(func: FibonacciSummer, number: Int): TimerResult = {
    val start = System.nanoTime()
    val answer = func.sumOfFibonacci(number)
    val end = System.nanoTime()
    val timeTaken = (end - start) / 1000
    TimerResult(timeTaken, answer)
  }

  private def readData(filename: String): Seq[Int] = {
    val file = Source.fromFile(filename)
    file.getLines().toSeq.map(line => line.toInt)
  }

  private def writeData(filename: String, rands: Seq[AnyVal]): Unit = {
    val lines: String = rands.map(_.toString).mkString("\n")
    val file = new File(filename)
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(lines)
    bw.close()
  }

  private def process(function: FibonacciSummer, rands: Seq[Int]): Seq[Long] = {
    rands.map { rand =>
      timeIt(function, rand).timeTaken
    }
  }
}
