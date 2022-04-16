package com.example.friendsapp.models

import com.example.friendsapp.models.person.POJOPerson
import com.example.friendsapp.models.person.Person
import io.reactivex.Observable
import retrofit2.http.GET

interface GetData {

    @GET("users.json?alt=media&token=e3672c23-b1a5-4ca7-bb77-b6580d75810c")
    fun getData() : Observable<List<Person>>

}