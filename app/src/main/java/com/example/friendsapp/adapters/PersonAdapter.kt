package com.example.friendsapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Color.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.friendsapp.R
import com.example.friendsapp.models.person.Person

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.friendsapp.fragments.PersonDetailsFragment


class PersonAdapter(val context: Context, val fragment: Fragment, var persons: List<Person>)
    : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    enum class PersonStatus(val color: String) {
        ACTIVE ("#73E678"),
        PASSIVE("#615D5D")
    }

    inner class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val personName = itemView.findViewById<TextView>(R.id.person_item_name)
        val personEmail = itemView.findViewById<TextView>(R.id.person_item_email)
        val personStatus = itemView.findViewById<ImageView>(R.id.person_item_status)
        val personCard = itemView.findViewById<CardView>(R.id.person_item_card)

        @SuppressLint("ResourceType")
        fun bind(person: Person) {
            personName.setText(person.name)
            personEmail.setText(person.email)
            if (person.isActive) {
                personStatus.setBackgroundColor(parseColor(PersonStatus.ACTIVE.color))
            } else {
                personStatus.setBackgroundColor(parseColor(PersonStatus.PASSIVE.color))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.person_item, parent, false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = persons.get(position)
        holder.bind(person)

        holder.personCard.setOnClickListener(View.OnClickListener {
            if (person.isActive) {
                val personFragment = PersonDetailsFragment.newInstance(person)
                fragment.parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_fragment_container, personFragment)
                    .addToBackStack(null)
                    .commit()

            } else {
                Toast.makeText(context, "Пользователен не активен в данный момент...", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun getItemCount(): Int {
        return persons.size
    }

}