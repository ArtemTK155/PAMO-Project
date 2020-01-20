package edu.ufp.pamfinalproject.services

import edu.ufp.pamfinalproject.models.Note
import edu.ufp.pamfinalproject.room.Note_Entity
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface NoteService {

    //localhost:4000/notes/get_all_notes
    @GET("notes/get_all_notes")
    fun getNotesList(): Call<List<Note>>

    @GET("notes/get_all_notes")
    fun getNotesList2(): Call<List<Note_Entity>>

    @GET("notes/get_note/{id}")
    fun getNote(@Path("id") id: String): Call<Note>

    @POST("notes/make")
    fun addNote(@Body newNote: Note_Entity): Call<Note_Entity>


    @POST("notes/make")
    fun addNote2(@Body newNote: Note): Call<Note>

    @FormUrlEncoded
    @POST("notes/update")
    fun updateNote(
        @Field("_id") id: String,
        @Field("title") title: String,
        @Field("sub_title") sub_title: String,
        @Field("description") description: String
    ): Call<Note>

    @DELETE("notes/delete/{id}")
    fun deleteNote(@Path("id") id: String): Call<Unit>



    companion object {
        operator fun invoke(): NoteService {
            return Retrofit.Builder()
                .baseUrl("https://882db5c1.ngrok.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NoteService::class.java)
        }
    }
}