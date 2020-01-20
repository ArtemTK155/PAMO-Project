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
import edu.ufp.pamfinalproject.services.NoteService
import kotlinx.android.synthetic.main.activity_note_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NoteDetailActivity : AppCompatActivity() {

    lateinit var viewModelTest: ViewModelTest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        //setSupportActionBar(detail_toolbar)
        // Show the Up button in the action bar.
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModelTest = ViewModelProviders.of(this).get(ViewModelTest::class.java)


        val bundle: Bundle? = intent.extras

        if (bundle?.containsKey(ARG_ITEM_ID)!!) {

            val serverId = intent.getStringExtra(ARG_ITEM_ID)
            //val localId = intent.getStringExtra(ARG_ITEM_ID_LOCAL)

            Thread(Runnable {
                val note = viewModelTest.getNote(serverId)
                //val localId = note._id
                //val serverId = note.server_id

                runOnUiThread(Runnable {
                    textInputTitle.setText(note.title)
                    textInputDescription.setText(note.description)
                    textInputSubTitle.setText(note.sub_title)
                    collapsing_toolbar.title = "Details"
                    Log.e(this.javaClass.simpleName, "ID of item server ${serverId}")

                })
                //loadDetails(id)
                initUpdateButton(serverId)
                initDeleteButton(serverId)

            }).start()
        }
    }

    private fun loadDetails(id: String) {

        NoteService().getNote(id).enqueue(object : Callback<Note> {
            override fun onResponse(call: Call<Note>, response: Response<Note>) {
                Log.e(this.javaClass.simpleName, "handleMessage(): API Get Note Request")
                // Log.e(this.javaClass.simpleName, "handleMessage(): ${response.body()}")
                val note = response.body()
                note?.let {
                    textInputTitle.setText(note.title)
                    textInputDescription.setText(note.description)
                    textInputSubTitle.setText(note.sub_title)

                    collapsing_toolbar.title = note._id
                }

            }

            override fun onFailure(call: Call<Note>, t: Throwable) {
                Log.e(this.javaClass.simpleName, "handleMessage(): API Request Get Note Failed")

            }
        })


    }

    private fun initUpdateButton(id: String) {

        btn_update.setOnClickListener {

            val title = textInputTitle.text.toString()
            val description = textInputDescription.text.toString()
            val sub_title = textInputSubTitle.text.toString()
            Log.e(this.javaClass.simpleName, "handleMessage(): ${title}")
            Log.e(this.javaClass.simpleName, "handleMessage(): ${sub_title}")
            Log.e(this.javaClass.simpleName, "handleMessage(): ${description}")

            NoteService().updateNote(id, title, sub_title, description)
                .enqueue(object : Callback<Note> {
                    override fun onResponse(call: Call<Note>, response: Response<Note>) {
                        Log.e(this.javaClass.simpleName, "handleMessage(): API Request Update")
                        Log.e(this.javaClass.simpleName, "handleMessage(): ${response.body()}")
                        finish() // Move back to DestinationListActivity
                        Toast.makeText(
                            this@NoteDetailActivity,
                            "Successfully Updated",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    override fun onFailure(call: Call<Note>, t: Throwable) {
                        Log.e(
                            this.javaClass.simpleName,
                            "handleMessage(): API Request Update Failed"
                        )

                    }
                })
        }
    }

    private fun initDeleteButton(serverId: String) {

        btn_delete.setOnClickListener {

            NoteService().deleteNote(serverId)
                .enqueue(object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        Log.e(this.javaClass.simpleName, "handleMessage(): API Request Delete")
                        finish() // Move back to DestinationListActivity
                        Toast.makeText(
                            this@NoteDetailActivity,
                            "Successfully Deleted",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        Log.e(
                            this.javaClass.simpleName,
                            "handleMessage(): API Request Delete Failed"
                        )

                    }
                })

            viewModelTest.deleteNote(serverId)
        }
    }


    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetwork
        return activeNetwork != null
    }

    companion object {

        const val ARG_ITEM_ID = "item_id"
        const val ARG_ITEM_ID_LOCAL = "local_id"
    }
}
