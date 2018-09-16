package id.gits.gitsmvvmkotlin.util

/**
 * Dibuat oleh Irfan Irawan Sukirman
 * @Copyright 2018
 */
sealed class UserOperationSealed {
    class AddOperation(val value: Int): UserOperationSealed()
    class DivideOperation(val value: Int): UserOperationSealed()
    class MultiplyOperation(val value: Int): UserOperationSealed()
    class SubstractOperation(val value: Int): UserOperationSealed()
}