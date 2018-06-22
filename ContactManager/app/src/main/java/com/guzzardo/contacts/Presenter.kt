package com.guzzardo.contacts

import android.content.Context

interface Presenter<V> {
    val context: Context //get() = context
    var employeeList: List<Repository.Employee>?
    val model: Repository
    fun attachView(view: V)
    fun detachView()
    fun setEmployeeList()
}