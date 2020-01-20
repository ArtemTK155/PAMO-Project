package edu.ufp.pamfinalproject.activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import edu.ufp.pamfinalproject.R
import edu.ufp.pamfinalproject.helpers.NoteAdapter
import edu.ufp.pamfinalproject.room.Note_Entity
import edu.ufp.pamfinalproject.services.NoteService
import kotlinx.android.synthetic.main.activity_note_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoteListActivity : AppCompatActivity() {

    lateinit var viewModelTest: ViewModelTest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        viewModelTest = ViewModelProviders.of(this).get(ViewModelTest::class.java)

        //setSupportActionBar(toolbar)
        //toolbar.title = title

        fab.setOnClickListener {
            val intent = Intent(this@NoteListActivity, NoteCreateActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onResume() {
        super.onResume()

        if (isNetworkConnected(this)) {
            Log.e(WelcomeActivity::class.java.simpleName, "Have interent")
            viewModelTest.getNotesFromApi()
        } else {
            viewModelTest.getAllNotes().observe(this, Observer<List<Note_Entity>> { notesList ->
                destiny_recycler_view.adapter = notesList?.let { NoteAdapter(it) }
            })
        }

        loadDestinations()
    }

    private fun loadDestinations() {

        NoteService().getNotesList2().enqueue(object : Callback<List<Note_Entity>> {
            override fun onResponse(call: Call<List<Note_Entity>>, response: Response<List<Note_Entity>>) {
                Log.e(this.javaClass.simpleName, "handleMessage(): API Request Get All Notes")
                //Log.e(this.javaClass.simpleName, "handleMessage(): ${response.body()}")
                val notesList = response.body()
                destiny_recycler_view.adapter = notesList?.let { NoteAdapter(it) }
            }

            override fun onFailure(call: Call<List<Note_Entity>>, t: Throwable) {
                Log.e(
                    this.javaClass.simpleName,
                    "handleMessage(): API Request Get All Notes Failed"
                )
            }

        })
    }

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetwork
        return activeNetwork != null
    }


}
