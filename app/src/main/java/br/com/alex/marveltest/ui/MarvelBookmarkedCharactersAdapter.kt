package br.com.alex.marveltest.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.alex.domain.model.CharacterModel
import br.com.alex.marveltest.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.character_cell.view.*

class MarvelBookmarkedCharactersAdapter(private val showSelectedCharacter: (Int, Boolean) -> Unit,
                                        private val unbookCharacter: (CharacterModel) -> Unit):
    RecyclerView.Adapter<MarvelBookmarkedCharactersAdapter.MarvelCharactersViewHolder>() {

    private var characters: List<CharacterModel> = emptyList()

    init{
        characters = ArrayList()
    }

    fun setBookmarkedCharacter(bookmarkedCharacters: List<CharacterModel>){
        characters = bookmarkedCharacters
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelCharactersViewHolder {
        return MarvelCharactersViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.character_cell, parent, false))
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    override fun onBindViewHolder(holder: MarvelCharactersViewHolder, position: Int) {
        holder.createCharacter(characters[position])
    }

    inner class MarvelCharactersViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun createCharacter(characterModel: CharacterModel){
            itemView.characterName.text = characterModel.characterName
            val characterImageUrl: String = characterModel.charactedImageSource

            Glide.with(itemView.context)
                .load(characterImageUrl)
                .into(itemView.characterImage)
            itemView.characterStar.setOnClickListener{
                unbookCharacter(characterModel)
                notifyDataSetChanged()
            }
            itemView.characterImage.setOnClickListener{
                showSelectedCharacter(characterModel.charactedId, characterModel.characterBookmarked)
            }

            itemView.characterStar.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_selected_star_character))
        }
    }
}