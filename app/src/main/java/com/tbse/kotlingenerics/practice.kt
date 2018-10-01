package com.tbse.kotlingenerics

/**
 * Created by toddsmith on 9/30/18.
 * Copyright TBSE 2017
 */


// Covariance (out) and Contravariance (in)
//
// 'Variance' refers to how subtyping between Types relates to subtyping between their parameters
// 'Co' means same, and 'contra' means opposite
// the 'out' modifier specifies covariance and the 'in' modifier specifies contravariance


// the parameter T is covariant - can only be used for output
interface Production<out T> {
    fun produce(): T
}

open class Food
open class FastFood: Food()
class Burger: FastFood()

class FoodStore: Production<Food> {
    override fun produce(): Food {
        return Food()
    }
}

class FastFoodStore: Production<FastFood> {
    override fun produce(): FastFood {
        return FastFood()
    }
}

class InOutBurger: Production<Burger> {
    override fun produce(): Burger {
        return Burger()
    }
}

// FoodStore implements an interface with a covariant generalized type parameter.
// Here, FoodStore is a subtype of Production, so the parameter of FoodStore should also be a
// subtype of Production's parameter
// With a covariant generalized type parameter, we can assign a subtype with a subtype parameter
// to a supertype with a supertype parameter
val prod1: Production<Food> = FoodStore()
val prod2: Production<Food> = FastFoodStore()
val prod3: Production<Food> = InOutBurger()
// prod1, prod2, and prod3 all need to produce Food, and we are able to assign it classes that produce
// Food or subtypes of Food, which is okay
// consider  override fun giveObject(): Object {
//                   return 1 // okay
//                   return "a" // okay
//                   return 1.0f // okay
//           }



// Covariant example 2


open class Gift
open class Book: Gift()
class Novel: Book()

interface Giver<out T> {
    fun give(): T
}

class Relative: Giver<Gift> {
    override fun give(): Gift {
        return Gift()
    }
}

class Teacher: Giver<Book> {
    override fun give(): Book {
        return Book()
    }
}

val person1: Giver<Gift> = Relative()
val person2: Giver<Gift> = Teacher()

fun a() {
    person1.give()
    person2.give()
}

// out: we need to output Food. Food is okay, FastFood is okay, Burger is okay, they are all Food.
// out: we need to output Object. Int is okay, String is okay, Double is okay, they are all Object.

// ex (error): prod4 needs to produce Burgers, but I try to assign it a class that can produce more than
// just Burgers
//val prod4: Production<Burger> = FoodStore()
// out: We need to output Burger. Outputting Food is not okay.
// out: We need to output String. Outputting Object is not okay.
// consider   fun toString(): String { return Object() }


// Contravariance


// the parameter T is contravariant - can only be used for input
interface Consumer<in T> {
    fun consume(input: T)
}

class Everybody: Consumer<Food> {
    override fun consume(input: Food) {
    }
}

class Teens: Consumer<FastFood> {
    override fun consume(input: FastFood) {
    }
}

class Americans: Consumer<Burger> {
    override fun consume(input: Burger) {
    }
}

// Everybody implements an interface with a contravariant generalized type.
// Since Everybody is a subtype of Consumer, Everybody's parameter should be
// a supertype (the opposite relation) of Consumer's parameter
val cons1: Consumer<Burger> = Everybody()
val cons2: Consumer<Burger> = Teens()
val cons3: Consumer<Burger> = Americans()
// cons1, cons2, and cons3 all need to have a method that takes in the type Burger. I assign
// classes that take in Burger or supertypes of Burger, which is okay

// in: We need to input Burger. Everybody can input Burger, FastFood, and Food
// in: example: We need to input Int.  fun read(obj: Object) { objList.add(obj) }  is okay

//ex (error): cons4 needs to input Food. But I try to assign it a class that can only input Burgers.
//val cons4: Consumer<Food> = Americans()
// in: We need to input Food. Inputting Burgers is not enough.
// in:
