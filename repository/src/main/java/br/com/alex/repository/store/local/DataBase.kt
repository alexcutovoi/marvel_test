package br.com.alex.repository.store.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.alex.repository.store.local.dao.MarvelDatabaseDao
import br.com.alex.repository.store.model.local.CharacterEntity


@Database(version = 1, entities = arrayOf(CharacterEntity::class))
abstract class DataBase: RoomDatabase() {

    abstract fun marvelDatabaseDao(): MarvelDatabaseDao

    companion object {
        private var instance: DataBase? = null

        fun instance(context: Context): DataBase {
            if(instance == null) {
                instance = Room.databaseBuilder(context, DataBase::class.java, "database").build()
            }
            return instance!!
        }
    }

}