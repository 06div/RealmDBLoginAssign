package com.example.realmdbloginassign

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class RealmData(@PrimaryKey
                     var id: Int = 0,
                     var name:String = "",
                     var age:Int = 0,
                     var city:String = "") : RealmObject()
