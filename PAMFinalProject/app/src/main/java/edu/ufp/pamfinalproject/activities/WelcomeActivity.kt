package edu.ufp.pamfinalproject.activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.room.Room
import edu.ufp.pamfinalproject.R
import edu.ufp.pamfinalproject.room.AppDB
import kotlinx.android.synthetic.main.activity_welcome.*


class WelcomeActivity : AppCompatActivity() {

    lateinit var viewModelTest: ViewModelTest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        // To be replaced by retrofit code
        message.text = "Welcome to my app"
        viewModelTest = ViewModelProviders.of(this).get(ViewModelTest::class.java)

        myDb = Room.databaseBuilder(applicationContext, AppDB::class.java, "notes.db")
            .fallbackToDestructiveMigration().build()

        //viewModelTest.deleteAll()

        if (isNetworkConnected(this)) {
            Log.e(WelcomeActivity::class.java.simpleName, "Have interent")
            viewModelTest.getNotesFromApi()
        } else {
            Log.e(WelcomeActivity::class.java.simpleName, "Have no interent")
        }


    }

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetwork
        return activeNetwork != null
    }

    fun testButton(view: View) {
        viewModelTest.getNotesFromApi()
    }

    fun getStarted(view: View) {
        val intent = Intent(this, NoteListActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        var myDb: AppDB? = null
    }
}
