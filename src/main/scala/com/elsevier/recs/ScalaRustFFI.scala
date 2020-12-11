package com.elsevier.recs

import jnr.ffi.LibraryLoader

import java.io.File
import java.lang.System.mapLibraryName

trait RustFFI {
  def sum_of_fibonacci(input: Int): Int
}

object ScalaRustFFI extends FibonacciSummer {
  private val rustLib: RustFFI = getRustLib

  override def sumOfFibonacci(input: Int): Int = {
    rustLib.sum_of_fibonacci(input)
  }

  private def getLibraryPath(dylib: String): String = {
    val f = new File(classOf[RustFFI]
      .getClassLoader
      .getResource(mapLibraryName(dylib))
      .getFile)
    f.getParent
  }

  private def getRustLib: RustFFI = {
    val dylib = "rust_ffi"
    System.setProperty("jnr.ffi.library.path", getLibraryPath(dylib))
    LibraryLoader.create(classOf[RustFFI]).load(dylib)
  }
}
