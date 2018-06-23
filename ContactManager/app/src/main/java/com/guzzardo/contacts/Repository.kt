package com.guzzardo.contacts

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.util.Collections
import java.util.HashMap

class Repository(context: Context) : EmployeeModel {

    private val employeeObject: JSONObject? = null
    private val firstName: String? = null
    private val lastName: String? = null
    private val phone: String? = null

    //@delegate:companion
    override val dataLoaded : Boolean //{ return true }
        get() {  return joe.dataLoaded  }

    override val employeeList: List<Employee>?  //{ return Repository.employeeList}
        get() = Repository.employeeList

    init {
        mContext = context
        loadJSONData()
    }

    private fun loadJSONData() {
        try {
            JsonTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, "https://s3.amazonaws.com/technical-challenge/v3/contacts.json")
        } catch (e: Exception) {
            println("Error creating JSON Employee list: $e")
        }
    }

    override fun setContext(context: Context) {
        mContext = context
    }

    override fun getEmployee(employeeId: String?): Employee? {
        val employeeMap2 = employeeMap
        return if (employeeMap2!!.containsKey(employeeId)) {
            employeeMap2.get(employeeId)
        } else {
            null
        }
    }

    override fun setEmployeeList() {
        Collections.sort(employeeList)
        setEmployeeMap()
    }

    // load the Json list from AWS
    private class JsonTask : AsyncTask<String?, String?, String?>() {

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: String?): String? {
            val queue = Volley.newRequestQueue(mContext)

            // Request a string response from the provided URL.
            val stringRequest = StringRequest(Request.Method.GET, url,
                    Response.Listener { response ->
                        try {
                            setEmployeeList(response)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }, Response.ErrorListener { Log.d("GetEmployeesFromURL", "That didn't work!") })

            // Add the request to the RequestQueue.
            queue.add(stringRequest)
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
        }
    }

    inner class Employee : Comparable<Employee> {
        internal var name: String? = null
        internal var id: String? = null
        internal var companyName: String? = null
        internal var isFavorite: String? = null
        internal var smallImageURL: String? = null
        internal var largeImageURL: String? = null
        internal var emailAddress: String? = null
        internal var birthdate: String? = null
        internal var phone: Phone? = null
        internal var address: Address? = null

        override fun compareTo(otherEmployee: Employee): Int {
            val thisNamePrefix = StringBuilder()
            val otherNamePrefix = StringBuilder()

            if (this.isFavorite == "true") {
                thisNamePrefix.insert(0, "a")
            } else {
                thisNamePrefix.insert(0, "b")
            }

            if (otherEmployee.isFavorite == "true") {
                otherNamePrefix.insert(0, "a")
            } else {
                otherNamePrefix.insert(0, "b")
            }

            val valueHere = thisNamePrefix.toString() + this.name!!
            val valueThere = otherNamePrefix.toString() + otherEmployee.name!!
            return valueHere.compareTo(valueThere)
        }
    }

    inner class Address {
        internal var street: String? = null
        internal var state: String? = null
        internal var city: String? = null
        internal var country: String? = null
        internal var zipCode: String? = null
    }

    inner class Phone {
        internal var work: String? = null
        internal var home: String? = null
        internal var mobile: String? = null
    }

    companion object joe {
        private val url = "https://s3.amazonaws.com/technical-challenge/v3/contacts.json"
        private var employeeList: List<Employee>? = null
            get() = field

        private var employeeMap: MutableMap<String?, Employee?>? = null
        private var mContext: Context? = null

        var dataLoaded = false
        init {
            false
        }

        private fun setEmployeeList(jsonData: String) {
            try {
                val collectionType = object : TypeToken<List<Employee>>() {
                }.type
                employeeList = Gson().fromJson<List<Employee>>(jsonData, collectionType)
                Collections.sort(employeeList!!)
                setEmployeeMap()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        private fun setEmployeeMap() {
            employeeMap = HashMap()
            for (i in employeeList!!) employeeMap!![i.id] = i
            dataLoaded = true
        }
    }
}


