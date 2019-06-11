package br.com.alex.marveltest.ui

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alex.domain.model.CharacterModel
import br.com.alex.domain.model.CharacterDetailResultModel
import br.com.alex.marveltest.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.character_cell.view.*
import java.io.ByteArrayOutputStream

class MarvelCharacterDetailAdapter:
    RecyclerView.Adapter<MarvelCharacterDetailAdapter.MarvelCharacterDetailViewHolder>() {

    private var characterDetailList: MutableList<CharacterDetailResultModel>

    init {
        characterDetailList = ArrayList()
    }

    fun addAll(newCharacters: List<CharacterDetailResultModel>) {
        characterDetailList.addAll(newCharacters)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelCharacterDetailViewHolder {
        return MarvelCharacterDetailViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.character_cell,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return characterDetailList.size
    }

    override fun onBindViewHolder(holder: MarvelCharacterDetailViewHolder, position: Int) {
        holder.createCharacter(characterDetailList[position])
    }

    inner class MarvelCharacterDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun createCharacter(characterDetailModel: CharacterDetailResultModel) {
            itemView.characterName.text = characterDetailModel.title

            Glide.with(itemView.context)
                .load(characterDetailModel.thumbnail)
                .into(itemView.characterImage)
            itemView.characterStar.visibility = View.GONE
        }
    }
}