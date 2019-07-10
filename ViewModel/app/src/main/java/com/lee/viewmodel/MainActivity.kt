package com.lee.viewmodel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    lateinit var countViewModel: CountViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countViewModel = ViewModelProviders.of(this)[CountViewModel::class.java]
        displayCountTextView(countViewModel.count)
    }

    private fun displayCountTextView(count: Int) {
        countTextView.text = count.toString()
    }


    fun addClick(view : View){
        countViewModel.count ++
        displayCountTextView(countViewModel.count)

    }

    fun nextClick(view : View){
        startActivity(Intent(this@MainActivity,UserListActivity::class.java))
    }
}
