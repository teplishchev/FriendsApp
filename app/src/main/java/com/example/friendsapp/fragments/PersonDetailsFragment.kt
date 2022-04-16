package com.example.friendsapp.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.friendsapp.R
import com.example.friendsapp.adapters.PersonAdapter
import com.example.friendsapp.app.App
import com.example.friendsapp.models.person.FriendsViewModel
import com.example.friendsapp.models.person.FriendsViewModelFactory
import com.example.friendsapp.models.person.Person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


private const val PERSON = "person"

class PersonDetailsFragment : Fragment() {

    enum class Fruits(val fruitName: String, val fruitIcon: Int) {
        APPLE("apple", R.drawable.apple),
        BANANA("banana", R.drawable.bananas),
        STRAWBERRY("strawberry", R.drawable.strawberry)
    }

    enum class EyeColor(val eyeColorName: String, val eyeColorValue: Int) {
        BLUE("blue", R.drawable.eye_blue_background),
        BROWN("brown", R.drawable.eye_brown_background),
        GREEN("green", R.drawable.eye_green_background)
    }

    private val REQUEST_PHONE_CALL = 1
    private var person: Person? = null
    @Inject
    lateinit var friendsViewModelFactory: FriendsViewModelFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity?.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            person = it.getParcelable<Person>(PERSON)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_person_details, container, false)

        val viewModel = ViewModelProvider(this, friendsViewModelFactory)
            .get(FriendsViewModel::class.java)
        val textName = view.findViewById<TextView>(R.id.person_details_name)
        val textAge = view.findViewById<TextView>(R.id.person_details_age)
        val textCompany = view.findViewById<TextView>(R.id.person_details_company)
        val textEmail = view.findViewById<TextView>(R.id.person_details_email)
        val textPhone = view.findViewById<TextView>(R.id.person_details_phone)
        val textAddress = view.findViewById<TextView>(R.id.person_details_address)
        val textAbout = view.findViewById<TextView>(R.id.person_details_about)
        val textRegistered = view.findViewById<TextView>(R.id.person_details_registered)
        val textCoordinates = view.findViewById<TextView>(R.id.person_details_coordinates)
        val recyclerFriends = view.findViewById<RecyclerView>(R.id.person_details_recycler_friends)
        val imageEyeColor = view.findViewById<ImageView>(R.id.person_details_eye_color_image)
        val imageFruit = view.findViewById<ImageView>(R.id.person_details_favorite_fruit_image)

        recyclerFriends.layoutManager = LinearLayoutManager(requireContext())

        person?.let {
            runBlocking {
                    viewModel.getPersonFriends(it.id).observe(requireActivity(), Observer{
                        val adapter = PersonAdapter(requireContext(), this@PersonDetailsFragment, it)

                        recyclerFriends.adapter = adapter
                        adapter.notifyDataSetChanged()
                    })
            }

            textName.text = it.name
            textAge.text = "" + it.age
            textCompany.text = " " + it.company
            textEmail.text = " " + it.email
            textPhone.text = " " + it.phone
            textAbout.text = " " + it.about
            textAddress.text = " " + it.address
            textRegistered.text = " " + getDateFromRegistered(it.registered)

            when(it.favoriteFruit){
                Fruits.APPLE.fruitName -> imageFruit.setImageResource(Fruits.APPLE.fruitIcon)
                Fruits.BANANA.fruitName -> imageFruit.setImageResource(Fruits.BANANA.fruitIcon)
                Fruits.STRAWBERRY.fruitName -> imageFruit.setImageResource(Fruits.STRAWBERRY.fruitIcon)
            }

            when(it.eyeColor){
                EyeColor.GREEN.eyeColorName ->
                    imageEyeColor.setBackgroundResource(EyeColor.GREEN.eyeColorValue)
                EyeColor.BLUE.eyeColorName ->
                    imageEyeColor.setBackgroundResource(EyeColor.BLUE.eyeColorValue)
                EyeColor.BROWN.eyeColorName ->
                    imageEyeColor.setBackgroundResource(EyeColor.BROWN.eyeColorValue)
            }

            textCoordinates.text = " Широта: " + it.latitude + " Долгота: " + it.longitude
        }

        textPhone.setOnClickListener(View.OnClickListener {
            val toDial = "tel:" + person?.phone
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(toDial)))
        })

        textEmail.setOnClickListener(View.OnClickListener {
            startActivity(Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + person?.email)))
        })

        textCoordinates.setOnClickListener(View.OnClickListener {
            val latitude: Float = person?.latitude ?: 0.0F
            val longitude: Float = person?.longitude ?: 0.0F
            val mapFragment = MapFragment.newInstance(latitude, longitude)
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.main_fragment_container, mapFragment)
                .addToBackStack(null)
                .commit()
        })

        return view
    }

    private fun getDateFromRegistered(registered: String) : String {

        try {
            val inputFormat = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss Z",
                Locale.ENGLISH)
            val parsedDate = inputFormat.parse(registered)
            val outputFormat = SimpleDateFormat("HH:mm dd.MM.yy")
            return outputFormat.format(parsedDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }

    companion object {

        fun newInstance(person: Person) =
            PersonDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PERSON, person)
                }
            }
    }
}