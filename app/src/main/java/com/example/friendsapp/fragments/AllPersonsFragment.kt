package com.example.friendsapp.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.friendsapp.MainActivity
import com.example.friendsapp.R
import com.example.friendsapp.adapters.PersonAdapter
import com.example.friendsapp.app.App
import com.example.friendsapp.models.person.FriendsViewModel
import com.example.friendsapp.models.person.FriendsViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject



class AllPersonsFragment : Fragment() {

    @Inject
    lateinit var friendsViewModelFactory: FriendsViewModelFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity?.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_persons, container, false)

        val personsRecycler = view.findViewById<RecyclerView>(R.id.all_persons_recycler)
        val btnRefreshData = view.findViewById<Button>(R.id.all_persons_btn_refresh)
        personsRecycler.layoutManager = LinearLayoutManager(requireContext())
        val viewModel = ViewModelProvider(this, friendsViewModelFactory)
            .get(FriendsViewModel::class.java)

        runBlocking {
                viewModel.getAllPersons().observe(requireActivity(), Observer {
                    val adapter = PersonAdapter(requireContext(), this@AllPersonsFragment, it)

                    personsRecycler.adapter = adapter
                    adapter.notifyDataSetChanged()
                })
        }

        btnRefreshData.setOnClickListener(View.OnClickListener {
            (requireActivity() as MainActivity).loadData()
        })

        return view
    }
}