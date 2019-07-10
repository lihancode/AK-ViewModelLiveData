package com.lee.viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_user_list.*
import kotlinx.android.synthetic.main.user_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class UserListActivity : AppCompatActivity() {

    val TAG = UserListActivity::class.java.simpleName
    lateinit var users : ArrayList<User>
    lateinit var userListViewModel: UserListViewModel
    lateinit var userAdapter : UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        users = ArrayList()
        userListViewModel = ViewModelProviders.of(this).get(UserListViewModel::class.java)
        userListViewModel.usersList.observe(this,object : Observer<ArrayList<User>>{
            override fun onChanged(arrylist: ArrayList<User>?) {
                users = arrylist!!
                userAdapter.usersList = users
                userAdapter.notifyDataSetChanged()
                Handler().postDelayed(object : Runnable{
                    override fun run() {
                        userRecycler.scrollToPosition(arrylist.size-1)
                    }
                },200)
            }
        })
        userRecycler.setHasFixedSize(true)
        userRecycler.layoutManager = LinearLayoutManager(this)
        userAdapter = UserAdapter(users)
        userRecycler.adapter = userAdapter

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.userlist_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.itemId.let {
            return when(it){
                R.id.action_add ->{
                    var number = users.size
                    number++
                    users.add(User(number,"jack $number"))
                    userListViewModel.usersList.postValue(users)
                    true
                }
                else -> true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    inner class UserAdapter(var usersList:ArrayList<User>) : RecyclerView.Adapter<UserViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
            return UserViewHolder(layoutInflater.inflate(R.layout.user_item,parent,false))
        }

        override fun getItemCount(): Int {
            return if(users==null) 0 else users.size
        }

        override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
            holder.bindto(users[position],position)
        }

    }

    inner class UserViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        val id = itemView.user_id
        val name  = itemView.user_name
        fun bindto(user : User,position : Int){
            itemView.tag = position
            id.text = user.id.toString()
            name.text = user.name
        }
    }
}
