package edu.ufp.pamfinalproject.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Note_Entity {
    @PrimaryKey()
    var _id: String = ""
    @ColumnInfo(name = "title")
    var title: String = ""
    @ColumnInfo(name = "sub_title")
    var sub_title: String = ""
    @ColumnInfo(name = "description")
    var description: String = ""
    @ColumnInfo(name = "server_id")
    var server_id: String = ""

}