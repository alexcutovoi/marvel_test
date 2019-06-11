package br.com.alex.marveltest.ui

import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.alex.domain.model.CharacterModel
import br.com.alex.marveltest.R
import br.com.alex.marveltest.util.Utils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.character_cell.view.*

class MarvelCharactersAdapter(private var charactersList: MutableList<CharacterModel>,
                              private val showSelectedCharacter: (Int, Boolean) -> Unit,
                              private val insertCharacter: (CharacterModel) -> Unit,
                              private val removeCharacter: (CharacterModel) -> Unit):
    RecyclerView.Adapter<MarvelCharactersAdapter.MarvelCharactersViewHolder>(), Filterable {

    var searchEnabled = false
    private var characterFilter: CharacterFilter
    private var bookmarkedCharacters: List<Int> = emptyList()
    private var filteredCharacters: MutableList<CharacterModel>

    init{
        characterFilter = CharacterFilter(this)
        bookmarkedCharacters = ArrayList()

        charactersList = ArrayList()

        filteredCharacters = ArrayList()
        filteredCharacters.addAll(charactersList)
    }

    fun addAll(newCharacters: List<CharacterModel>){
        charactersList.addAll(newCharacters)
        filteredCharacters.addAll(newCharacters)
        notifyDataSetChanged()
    }

    fun setBookmarkedCharacters(bookMarkedCharactersList: List<CharacterModel>){
        bookmarkedCharacters = bookMarkedCharactersList.map{
            it.charactedId
        }

        notifyDataSetChanged()
    }

    fun clear(){
        charactersList.clear()
        filteredCharacters.clear()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelCharactersViewHolder {
        return MarvelCharactersViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.character_cell, parent, false))
    }

    override fun getItemCount(): Int {
        return filteredCharacters.size
    }

    override fun onBindViewHolder(holder: MarvelCharactersViewHolder, position: Int) {
        holder.createCharacter(filteredCharacters[position])
    }

    override fun getFilter(): CharacterFilter{
        return characterFilter
    }

    inner class MarvelCharactersViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun createCharacter(characterModel: CharacterModel){
            itemView.characterName.text = characterModel.characterName
            val characterImageUrl: String = characterModel.charactedImageSource

            if(!bookmarkedCharacters.contains(characterModel.charactedId)){
                itemView.characterStar.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_unselected_star_character))
            }
            else{
                itemView.characterStar.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_selected_star_character))
            }

            Glide.with(itemView.context)
                .load(characterImageUrl)
                .into(itemView.characterImage)
            itemView.characterStar.setOnClickListener{
                if(bookmarkedCharacters.contains(characterModel.charactedId)){
                    removeCharacter(characterModel)
                    itemView.characterStar.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_unselected_star_character))
                    notifyDataSetChanged()
                }
                else{
                    characterModel.characterImageData = Utils.convertBitmapToByteArray((itemView.characterImage.drawable as BitmapDrawable))
                    insertCharacter(characterModel)
                    itemView.characterStar.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_selected_star_character))
                    notifyDataSetChanged()
                }
            }
            itemView.characterImage.setOnClickListener{
                showSelectedCharacter(characterModel.charactedId, characterModel.characterBookmarked)
            }
        }
    }


    class CharacterFilter(private val marvelCharactersAdapter: MarvelCharactersAdapter): Filter(){
        var localFilteredCharacters: List<CharacterModel> = ArrayList()

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            localFilteredCharacters = emptyList()
            val filterResults = FilterResults()

            if(constraint!!.isEmpty()){
                localFilteredCharacters = marvelCharactersAdapter.charactersList
            }
            else{
                val filterPattern = constraint.toString().toLowerCase()
                localFilteredCharacters = marvelCharactersAdapter.charactersList.filter {
                    it.characterName.toLowerCase().contains(filterPattern)
                }
            }

            filterResults.values = localFilteredCharacters
            filterResults.count = localFilteredCharacters.size
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            marvelCharactersAdapter.filteredCharacters.clear()
            marvelCharactersAdapter.filteredCharacters.addAll((results!!.values as ArrayList<CharacterModel>))
            marvelCharactersAdapter.notifyDataSetChanged()
        }
    }
}