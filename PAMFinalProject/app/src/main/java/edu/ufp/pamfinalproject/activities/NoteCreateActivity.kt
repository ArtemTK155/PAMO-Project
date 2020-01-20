package edu.ufp.pamfinalproject.activities

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import edu.ufp.pamfinalproject.R
import edu.ufp.pamfinalproject.models.Note
import edu.ufp.pamfinalproject.room.Note_Entity
import edu.ufp.pamfinalproject.services.NoteService
import kotlinx.android.synthetic.main.activity_note_create.*
import kotlinx.android.synthetic.main.activity_note_create.textInputDescription
import kotlinx.android.synthetic.main.activity_note_create.textInputSubTitle
import kotlinx.android.synthetic.main.activity_note_create.textInputTitle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NoteCreateActivity : AppCompatActivity() {
    lateinit var viewModelTest: ViewModelTest
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_create)
        // Get the view model
        viewModelTest = ViewModelProviders.of(this).get(ViewModelTest::class.java)
        //setSupportActionBar(toolbar)
        val context = this

        // Show the Up button in the action bar.
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btn_add.setOnClickListener {
            val newNote = Note_Entity()
            newNote.title = textInputTitle.text.toString()
            newNote.description = textInputDescription.text.toString()
            newNote.sub_title = textInputSubTitle.text.toString()

            val newNote2 = Note()
            newNote2.title = textInputTitle.text.toString()
            newNote2.description = textInputDescription.text.toString()
            newNote2.sub_title = textInputSubTitle.text.toString()
            //viewModelTest.saveNote(newNote)

            if (isNetworkConnected((this))) {
                //viewModelTest.createNote(newNote)
                NoteService().addNote2(newNote2).enqueue(object : Callback<Note> {
                    override fun onResponse(
                        call: Call<Note>,
                        response: Response<Note>
                    ) {
                        Log.e(
                            this.javaClass.simpleName,
                            "handleMessage(): API Request Add New Note"
                        )
                        Log.e(this.javaClass.simpleName, "handleMessage(): ${response.body()}")
                    }

                    override fun onFailure(call: Call<Note>, t: Throwable) {
                        Log.e(
                            this.javaClass.simpleName,
                            "handleMessage(): API Request Failed Creating Note"
                        )
                        Log.e(this.javaClass.simpleName, "handleMessage(): ${t.printStackTrace()}")

                    }
                })


                Log.e(this.javaClass.simpleName, "API Create a note !!")
                finish() // Move back to DestinationListActivity
                Toast.makeText(context, "Successfully Added", Toast.LENGTH_SHORT).show()
            } else {

                //Somehow chash the local note to send later when internet is available

            }
        }
    }

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetwork
        return activeNetwork != null
    }
}
