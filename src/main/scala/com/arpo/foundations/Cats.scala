package com.arpo.foundations



object Cats {



  /*

    Semigroup[A]{combine} -> Monoid{empty}

    Eq{eqv}

    Functor[F[_]]{map} ----------
                                 \
                                  Apply[F[_]]{ap} --> Applicative[F[_]}{pure} --> Monad[F[_]]{}
                                 /                \                              /
    Semigroupal[F[_]]{product} --                   FlatMap[F[_]]{flatMap} -----

   */

  trait MySemigroup[A] {
    /**
     * Associative operation which combines two values.
     *
     * Example:
     * {{{
     * scala> import cats.kernel.instances.string._
     * scala> import cats.kernel.instances.int._
     * scala> import cats.kernel.instances.option._
     *
     * scala> Semigroup[Int].combine(1, 4)
     * res0: Int = 5
     *
     * scala> Semigroup[Option[Int]].combine(None, Some(1))
     * res1: Option[Int] = Some(1)
     * }}}
     */
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

  trait MySemigroupal[F[_]] {
    /**
     * Creates product of an `F[A]` and an `F[B]` into an `F[(A, B)]` that maintains the effects of both `fa` and `fb`.
     *
     * Example:
     * {{{
     * scala> import cats.implicits._
     *
     * scala> val noneInt: Option[Int] = None
     * scala> val some3: Option[Int] = Some(3)
     * scala> val someFoo: Option[String] = Some("foo")
     *
     * scala> Semigroupal[Option].product(noneInt, someFoo)
     * res1: Option[(Int, String)] = None
     *
     * scala> Semigroupal[Option].product(some3, someFoo)
     * res3: Option[(Int, String)] = Some((3,foo))
     * }}}
     */
    def product[A, B](fa: F[A], fb: F[B]): F[(A, B)]
  }

  trait MyApply[F[_]] extends MyFunctor[F] with MySemigroupal[F] {
    /**
     * Given a value and a function in the Apply context, applies the
     * function to the value.
     *
     * Example:
     * {{{
     * scala> import cats.implicits._
     *
     * scala> val someFf: Option[Int => Long] = Some(_.toLong + 1L)
     * scala> val someInt: Option[Int] = Some(3)
     *
     * scala> Apply[Option].ap(someFf)(someInt)
     * res0: Option[Long] = Some(4)
     * }}}
     */
    def ap[A, B](ff: F[A => B])(fa: F[A]): F[B]
  }

  trait MyApplicative[F[_]] extends MyApply[F] {
    /**
     * `pure` lifts any value into the Applicative Functor.
     *
     * Example:
     * {{{
     * scala> import cats.implicits._
     *
     * scala> Applicative[Option].pure(10)
     * res0: Option[Int] = Some(10)
     * }}}
     */
    def pure[A](x: A): F[A]
  }

  trait MyFlatMap[F[_]] extends MyApply[F] {
    def flatMap[A, B](ma: F[A])(f: A => F[B]): F[B]
  }

  trait MyMonad[F[_]] extends MyFlatMap[F] with MyApplicative[F] {
  }


}
