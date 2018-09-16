package id.gits.gitsmvvmkotlin.mvvm.main

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import id.gits.gitsmvvmkotlin.R
import id.gits.gitsmvvmkotlin.base.BaseActivity
import id.gits.gitsmvvmkotlin.databinding.MainActivityBinding
import id.gits.gitsmvvmkotlin.util.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : BaseActivity() {

    private lateinit var viewMainBinding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewMainBinding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        viewMainBinding.apply {
            setupToolbar()
            setupMainFragment()

            // Sealed class operation example
            val operation = UserOperationSealed.AddOperation(10)
            val result = execute(10, operation)
            Log.d(MainActivity::class.java.simpleName, result.toString())

            // Enum class operation example
            UserEnum.Name.printOut()
            UserEnum.Book.printOut()

            // Data class destructuring declaration operation example
            val user = User(name = "Budi Mastur", nim = "10111404")
            val (name, nim) = user
            val newName = user.component1()
            val newNim = user.component2()
            Log.d(MainActivity::class.java.simpleName, "Nama : $newName | Nim : $newNim")
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        txt_toolbar_title.text = resources.getString(R.string.app_name)
    }

    private fun setupMainFragment() {
        replaceFragmentInActivity(MainFragment.newInstance(), R.id.frame_main_content)
    }

    fun obtainViewModel(): MainViewModel = obtainViewModel(MainViewModel::class.java)

    private fun execute(x: Int, operation: UserOperationSealed): Int = when(operation) {
        is UserOperationSealed.AddOperation -> operation.value + x
        is UserOperationSealed.DivideOperation -> operation.value / x
        is UserOperationSealed.MultiplyOperation -> operation.value * x
        is UserOperationSealed.SubstractOperation -> operation.value - x
    }
}
