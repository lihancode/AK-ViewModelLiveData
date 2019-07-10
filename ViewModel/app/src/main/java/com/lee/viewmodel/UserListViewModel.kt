package com.lee.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserListViewModel : ViewModel(){

    var usersList : MutableLiveData<ArrayList<User>>

    init {
        usersList = MutableLiveData()
        var users = ArrayList<User>()
        for (i in 1..10) {
            users.add(User(i,"jack $i"))
        }
        usersList.postValue(users)
    }


}