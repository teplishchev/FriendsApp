package com.example.friendsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.friendsapp.app.App
import com.example.friendsapp.fragments.AllPersonsFragment
import com.example.friendsapp.models.GetData
import com.example.friendsapp.models.person.*
import com.example.friendsapp.models.setting.SettingRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private var myCompositeDisposable: CompositeDisposable? = null
    private var personsList: ArrayList<Person>? = null
    private lateinit var viewModel: FriendsViewModel

    @Inject
    lateinit var friendsViewModelFactory: FriendsViewModelFactory

    @Inject
    lateinit var settingsRepository: SettingRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (applicationContext as App).appComponent.inject(this)

        myCompositeDisposable = CompositeDisposable()

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.main_fragment_container, AllPersonsFragment())
                .commit()
        }
        viewModel = ViewModelProvider(this, friendsViewModelFactory)
            .get(FriendsViewModel::class.java)

//        runBlocking {
//            withContext(Dispatchers.IO){
//                viewModel.deleteAllPersons()
//                viewModel.deleteAllFriendsReferences()
//            }
//        }
        runBlocking {
            settingsRepository.getAllSettings().observe(this@MainActivity, Observer {
                if (it.size == 0) {
                     loadData()
                }
            })
        }

    }

    fun loadData() {

        val BASE_URL =
            "https://firebasestorage.googleapis.com/v0/b/candidates--questionnaire.appspot.com/o/"

        val requestInterface = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(GetData::class.java)

        myCompositeDisposable?.add(
            requestInterface.getData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse)
        )
    }

    private fun handleResponse(persons: List<Person>) {

        persons.stream().forEach { person ->
            runBlocking {
                withContext(Dispatchers.IO) {
                    viewModel.insertPerson(person)
                }
            }
            person.friends.stream().forEach { reference ->
                runBlocking {
                    withContext(Dispatchers.IO) {
                        viewModel.insertFriendsReference(
                            FriendReference(
                                person.id,
                                reference.id
                            )
                        )
                    }
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        myCompositeDisposable?.clear()

    }
}