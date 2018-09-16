package id.gits.gitsmvvmkotlin.util

import android.util.Log

/**
 * Dibuat oleh Irfan Irawan Sukirman
 * @Copyright 2018
 */
enum class UserEnum(val field: String) {
    Name("IRFAN IRAWAN SUKIRMAN") {
        override fun printOut() {
            Log.d(UserEnum::class.java.simpleName, enumValues<UserEnum>()[0].field)
        }
    },
    Book("IF-9 Teknik Informatika UNIKOM") {
        override fun printOut() {
            Log.d(UserEnum::class.java.simpleName, enumValues<UserEnum>()[1].field)
        }
    };

    abstract fun printOut()
}