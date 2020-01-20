package edu.ufp.pamfinalproject.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import edu.ufp.pamfinalproject.room.Note_Entity
import edu.ufp.pamfinalproject.room.Repository

class ViewModelTest : ViewModel() {

    lateinit var repository: Repository

    init {
        repository = Repository()
    }

    fun getAllNotes() : LiveData<List<Note_Entity>>{
        return repository.getNotes()
    }

    fun getNote(id: String) : Note_Entity{
        return repository.getNote(id)
    }

    fun saveNote(note : Note_Entity){
        repository.saveNote(note)
    }


    fun getNotesFromApi(){
        repository.apiCallandSave()
    }

    fun deleteNote(id: String){
        repository.deleteNote(id)
    }

    fun deleteAll(){
        repository.deleteAll()
    }

    fun createNote(note: Note_Entity){
        return repository.apiCreateNote(note)
    }


}