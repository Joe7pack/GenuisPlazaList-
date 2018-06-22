package com.guzzardo.contacts

import android.content.Context

interface EmployeeModel {
    val dataLoaded : Boolean //get() = dataLoaded
    val employeeList: List<Repository.Employee>? //get() = employeeList
    fun setEmployeeList()
    fun getEmployee(employeeId: String?): Repository.Employee?
    fun setContext(context: Context)
}
