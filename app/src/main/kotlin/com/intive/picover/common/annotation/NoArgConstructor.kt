package com.intive.picover.common.annotation

/**
 * Needed, when a class has to define a no-argument constructor.
 * Works configured with [no-arg compiler plugin](https://kotlinlang.org/docs/no-arg-plugin.html).
 *
 * Example usage: avoid Firebase DatabaseException, when deserializing object from realtime database to data class.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class NoArgConstructor
