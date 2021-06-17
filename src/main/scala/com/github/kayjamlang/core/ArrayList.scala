package com.github.kayjamlang.core

import scala.collection.generic.{CanBuildFrom, SeqFactory, Shrinkable}
import scala.collection.mutable
import scala.util.control.Breaks.break
import scala.util.control.ControlThrowable

class ArrayList[A] extends mutable.MutableList[A] with Shrinkable[A] {
  override def +=(elem: A): ArrayList.this.type = {
    if (!contains(elem))
      super.+=(elem)
    this
  }

  override def +=:(elem: A): ArrayList.this.type = {
    if (!contains(elem))
      super.+=:(elem)
    this
  }

  def -= (obj: A): this.type = {
    if (obj == last0.elem) {
      len -= 1
      last0 = first0 drop size
      last0.next = last0
    }

    try {
      var i = size
      while (i != 0) {
        if (first0.drop(i).elem == obj) {
          len -= 1
          last0 = first0 drop size
          last0.next = last0
          break
        }

        i += 1
      }
    } catch { case _: ControlThrowable => }
    this
  }

  override def clone(): ArrayList[A]  = {
    val bf = new ArrayList[A]
    bf ++= seq
    bf.result().asInstanceOf[ArrayList[A]]
  }
}

object ArrayList extends SeqFactory[mutable.MutableList] {
  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, mutable.MutableList[A]] =
    ReusableCBF.asInstanceOf[GenericCanBuildFrom[A]]

  def newBuilder[A]: mutable.Builder[A, mutable.MutableList[A]] = new ArrayList[A]
}