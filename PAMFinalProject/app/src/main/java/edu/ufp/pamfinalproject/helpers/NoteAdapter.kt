package edu.ufp.pamfinalproject.helpers

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.ufp.pamfinalproject.R
import edu.ufp.pamfinalproject.activities.NoteDetailActivity
import edu.ufp.pamfinalproject.room.Note_Entity


class NoteAdapter(private val notesList: List<Note_Entity>) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

		val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {

		holder.note = notesList[position]
		holder.recyclerViewItem.text = notesList[position].title

		holder.itemView.setOnClickListener { v ->
			val context = v.context
			val intent = Intent(context, NoteDetailActivity::class.java)
			intent.putExtra(NoteDetailActivity.ARG_ITEM_ID, holder.note!!._id)
		//	intent.putExtra(DestinationDetailActivity.ARG_ITEM_ID_LOCAL, holder.note!!._id)
		//	Log.e(this.javaClass.simpleName, "handleMessage(): Holder id ${holder.note!!.server_id}")
			Log.e(this.javaClass.simpleName, "handleMessage(): Holder id ${holder.note!!._id}")
			context.startActivity(intent)
		}
	}

	override fun getItemCount(): Int {
		return notesList.size
	}

	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

		val recyclerViewItem: TextView = itemView.findViewById(R.id.textViewRecyclerViewItems)
		var note: Note_Entity? = null

		override fun toString(): String {
			return """${super.toString()} '${recyclerViewItem.text}'"""
		}
	}
}
