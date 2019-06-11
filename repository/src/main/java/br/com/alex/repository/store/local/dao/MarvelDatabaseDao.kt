package br.com.alex.repository.store.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.alex.repository.store.model.local.CharacterEntity
import io.reactivex.Single

@Dao
interface MarvelDatabaseDao {
    @Query("SELECT * FROM characters_table ORDER BY character_name ASC")
    fun getBookmarkedCharacters() : Single<List<CharacterEntity>>

    @Query("SELECT * FROM characters_table WHERE charactedId = :characterId")
    fun getBookMarkedCharacter(characterId: Int) : Single<CharacterEntity>

    @Insert
    fun insertCharacter(character: CharacterEntity): Single<Long>

    @Delete
    fun deleteCharacter(character: CharacterEntity): Single<Int>
}