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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AllPersonsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AllPersonsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    @Inject
    lateinit var friendsViewModelFactory: FriendsViewModelFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity?.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AllPersonsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AllPersonsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}