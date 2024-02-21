package com.example.realmdbloginassign

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RealmAdapter(val context: Context, private var datalist : MutableList<RealmData>) : RecyclerView.Adapter<RealmAdapter.viewHolder>() {

    class viewHolder(view: View): RecyclerView.ViewHolder(view){
        var name : TextView
        var age : TextView
        var city : TextView

        init {
            name= view.findViewById(R.id.tvName)
            age = view.findViewById(R.id.tvAge)
            city = view.findViewById(R.id.tvCity)

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.data_list,parent,false)
        return  viewHolder(v)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val per:RealmData = datalist[position]
        holder.name.text= per.name
        holder.age.text = per.age.toString()
        holder.city.text =per.city
    }

    override fun getItemCount(): Int {
        return datalist.size
    }


    fun updateData(newData: List<RealmData>) {

        datalist = newData as MutableList<RealmData>
        notifyDataSetChanged()
    }


}