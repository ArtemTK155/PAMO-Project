package edu.ufp.pamfinalproject.room

import android.util.Log
import androidx.lifecycle.LiveData
import edu.ufp.pamfinalproject.activities.WelcomeActivity
import edu.ufp.pamfinalproject.services.NoteService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {

    val TAG = Repository::class.java.simpleName

    fun getNotes(): LiveData<List<Note_Entity>> {
        return WelcomeActivity.myDb!!.noteDao().getAllNotes()
    }

    fun getNote(id: String): Note_Entity {
        return WelcomeActivity.myDb!!.noteDao().getNote(id)
    }

    fun saveNote(note: Note_Entity) {
        WelcomeActivity.myDb!!.noteDao().saveNote(note)
    }

    fun deleteNote(id: String) {
        Thread(Runnable {
            WelcomeActivity.myDb!!.noteDao().deleteNote(id)
        }).start()
    }

    fun deleteAll() {
        Thread(Runnable {
            WelcomeActivity.myDb!!.noteDao().deleteAll()
        }).start()
    }


    fun apiCreateNote(note: Note_Entity) {
        NoteService().addNote(note).enqueue(object : Callback<Note_Entity> {
            override fun onResponse(call: Call<Note_Entity>, response: Response<Note_Entity>) {
                Log.e(this.javaClass.simpleName, "handleMessage(): API Request")
                Log.e(this.javaClass.simpleName, "handleMessage(): ${response.body()}")
                when (response.code()) {
                    200 -> {
                        Thread(Runnable {
                            var newNote = response.body()
                            if (newNote != null) {
                                WelcomeActivity.myDb!!.noteDao().saveNote(newNote)
                            }
                            Log.e(TAG, "Thread is saving a note")
                        }).start()
                    }
                }
            }

            override fun onFailure(call: Call<Note_Entity>, t: Throwable) {
                Log.e(this.javaClass.simpleName, "handleMessage(): API Request Failed Creating Note")
                Log.e(this.javaClass.simpleName, "handleMessage(): ${t.printStackTrace()}")

            }
        })
    }


    fun apiCallandSave() {
        NoteService().getNotesList2().enqueue(object : Callback<List<Note_Entity>> {
            override fun onResponse(
                call: Call<List<Note_Entity>>,
                response: Response<List<Note_Entity>>
            ) {
                Log.e(TAG, "handleMessage(): API Request Get All Notes")
                Log.e(this.javaClass.simpleName, "handleMessage(): ${response.body()}")

                when (response.code()) {
                    200 -> {
                        Thread(Runnable {
                            var v = response.body()
                            v!!.forEach {
                                var newNote = Note_Entity()
                                newNote._id = getRandomString(20)
                                newNote.description = it.description
                                newNote.title = it.title
                                newNote.sub_title = it.sub_title
                                newNote.server_id = it._id
                                WelcomeActivity.myDb!!.noteDao().saveNote(it)
                                //Log.e(TAG, "Thread is inserting")
                                //Log.e(TAG, "Server ID ${newNote.server_id}")
                                //Log.e(TAG, "ID ${newNote._id}")
                                //Log.e(TAG, "title = ${newNote.title}")
                                //Log.e(TAG, "sub  = ${newNote.sub_title}")
                                //Log.e(TAG, "desc = ${newNote.description}")
                            }
                        }).start()
                    }
                }
            }

            override fun onFailure(call: Call<List<Note_Entity>>, t: Throwable) {
                Log.e(
                    this.javaClass.simpleName,
                    "handleMessage(): API Request Get All Notes Failed"
                )
            }

        })
    }

    fun getRandomString(length: Int): String {
        val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz"
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}