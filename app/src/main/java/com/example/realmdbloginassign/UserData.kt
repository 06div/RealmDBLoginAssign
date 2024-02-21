package com.example.realmdbloginassign

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.realmdbloginassign.databinding.ActivityUserDataBinding
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.Sort

class UserData : AppCompatActivity() {
    private lateinit var binding: ActivityUserDataBinding
    lateinit var adapter: RealmAdapter

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

//        val spinner = findViewById<Spinner>(R.id.spinner)
        val sortingOptions = arrayOf("Sort by Name", "Sort by Age", "Sort by City")

        val spinAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sortingOptions)
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = spinAdapter

        binding.textView8.text = intent.getStringExtra("User Email")

        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name("data.realm")
            .allowWritesOnUiThread(true)
            .build()

        val realm = Realm.getInstance(config)

        val person1 = RealmData(id = 1, name = "Sanj", age = 25, city = "Delhi")
        val person2 = RealmData(id = 2, name = "Manvi", age = 30, city = "Pune")
        val person3 = RealmData(id = 3, name = "Aakash", age = 27, city = "Bhopal")
        val person4 = RealmData(id = 4, name = "Vijay", age = 47, city = "Mathura")
        val person5 = RealmData(id = 5, name = "Pushpit", age = 33, city = "Jaipur")
        val person6 = RealmData(id = 6, name = "Tushar", age = 50, city = "Kolapur")
        val person7 = RealmData(id = 7, name = "Vishad", age = 10, city = "Indore")
        val person8 = RealmData(id = 8, name = "Ashvini", age = 41, city = "Guna")
        val person9 = RealmData(id = 9, name = "Jiya", age = 20, city = "Sehore")

        val personsToAdd = listOf(
            person1, person2, person3, person4, person5, person6, person7, person8, person9
        )

        realm.executeTransaction { realmInstance ->
            for (person in personsToAdd) {
                realmInstance.insertOrUpdate(person)
            }
        }


        val da = realm.where(RealmData::class.java).findAll()

        val adapter = RealmAdapter(this, da)
        recyclerView.adapter = adapter
        Log.d("Before Spinner ", "...............")

        val per = realm.where(RealmData::class.java).findAll()



        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                Log.d("Spinner Launched ", "-----______------")
                val selectedItem = sortingOptions[position]

                val sortedResults = when (selectedItem) {
                    "Sort by Name" -> realm.where(RealmData::class.java).findAll()
                        .sort("name", Sort.ASCENDING)

                    "Sort by Age" -> realm.where(RealmData::class.java).findAll()
                        .sort("age", Sort.ASCENDING)

                    "Sort by City" -> realm.where(RealmData::class.java).findAll()
                        .sort("city", Sort.ASCENDING)

                    else -> realm.where(RealmData::class.java).findAll() // Default: no sorting

                }
                val pe = realm.copyFromRealm(sortedResults)
                adapter.updateData(pe)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }


}