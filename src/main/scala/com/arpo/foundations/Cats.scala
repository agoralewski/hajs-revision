package com.arpo.foundations

object Cats {



  /*

    Semigroup[A] -> Monoid
    Eq
    Functor

   */

  trait MySemigroup[A] {
    def combine(x: A, y: A): A
  }

  trait MyMonoid[A] extends MySemigroup[A] {
    def empty: A
  }

  trait Eq[A] {
    def eqv(x: A, y: A): Boolean // alias ===
    def neqv(x: A, y: A): Boolean // alias =!=
  }



  trait MyFunctor[F[_]] {
    def map[A, B](initialValue: F[A])(f: A => B): F[B]
  }


}
