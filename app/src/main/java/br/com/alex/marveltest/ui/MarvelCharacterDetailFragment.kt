package br.com.alex.marveltest.ui

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alex.domain.model.CharacterModel

import br.com.alex.marveltest.R
import br.com.alex.marveltest.model.ViewData
import br.com.alex.marveltest.util.Utils
import br.com.alex.repository.exception.NetworkConnectionException
import br.com.alex.repository.exception.ServerException
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.error_dialog.*
import kotlinx.android.synthetic.main.fragment_character_detail.*
import kotlinx.android.synthetic.main.top_bar.*

class MarvelCharacterDetailFragment : Fragment() {
    private lateinit var marvelCharacterViewModel: MarvelCharacterViewModel

    private var characterId: Int = 0
    private var characterBookmarked: Boolean = false
    private lateinit var character: CharacterModel
    private enum class FunctionType{REFRESH, INSERT, DELETE}

    private var currentFunction: FunctionType = FunctionType.REFRESH

    private val marvelComicsAdapter: MarvelCharacterDetailAdapter by lazy {
        MarvelCharacterDetailAdapter()
    }

    private val marvelSeriesAdapter: MarvelCharacterDetailAdapter by lazy {
        MarvelCharacterDetailAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        characterId = arguments!!.getInt("CHARACTER_ID")
        characterBookmarked = arguments!!.getBoolean("CHARACTER_BOOKMARKED")
        return inflater.inflate(R.layout.fragment_character_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setListeners()
        configViewModel()
    }

    private fun configViewModel(){
        marvelCharacterViewModel = ViewModelProviders.of(this).get(MarvelCharacterViewModel::class.java)
        marvelCharacterViewModel.marvelCharactersResult.observe(this,  Observer {
            when(it.state){
                ViewData.State.SUCCESS -> {
                    character = it.data
                    character.characterBookmarked = characterBookmarked
                    titleBarText.text = it.data.characterName
                    marvelDialogError.visibility = View.GONE
                    characterDetailContainer.visibility = View.VISIBLE

                    if(it.data.characterDescription.isNotEmpty()) {
                        characterBio.text = it.data.characterDescription
                        characterBio.visibility = View.VISIBLE
                    }
                    else{
                        characterBio.visibility = View.GONE
                    }

                    setBookmarked(characterBookmarked)
                    characterButton.visibility = View.VISIBLE

                    Glide.with(this)
                        .load(it.data.charactedImageSourceHighRes)
                        .into(characterBigImage)
                }
                ViewData.State.FAIL -> {
                    currentFunction = FunctionType.REFRESH
                    marvelDialogError.visibility = View.VISIBLE
                    characterDetailContainer.visibility = View.GONE
                    marvelErrorTitle.text = when(it.exception) {
                        is ServerException -> {
                            it.exception.message
                        }
                        is NetworkConnectionException -> {
                            it.exception.message
                        }
                        else -> {
                            resources.getString(R.string.unknown_error)
                        }
                    }
                }
                ViewData.State.LOADING -> {
                    marvelDialogError.visibility = View.GONE
                    loadingDialog.visibility = View.VISIBLE
                }
                ViewData.State.UNDEFINED -> {
                    marvelCharacterViewModel.getCharacter(characterId)
                }
            }
        })

        marvelCharacterViewModel.marvelComicsAndSeriesResult.observe(this,  Observer {
            when(it.state){
                ViewData.State.SUCCESS -> {
                    if(it.data.first.isEmpty()){
                        characterComicsTitle.visibility = View.GONE
                        characterComics.visibility = View.GONE
                    }
                    else {
                        characterComicsTitle.visibility = View.VISIBLE
                        characterComics.visibility = View.VISIBLE
                        marvelComicsAdapter.addAll(it.data.first)
                    }

                    if(it.data.second.isEmpty()){
                        characterSeriesTitle.visibility = View.GONE
                        characterSeries.visibility = View.GONE
                    }
                    else {
                        characterSeriesTitle.visibility = View.VISIBLE
                        characterSeries.visibility = View.VISIBLE
                        marvelSeriesAdapter.addAll(it.data.second)
                    }

                    loadingDialog.visibility = View.GONE
                 }
                ViewData.State.FAIL -> {
                    characterComicsTitle.visibility = View.GONE
                    characterComics.visibility = View.GONE
                    characterSeriesTitle.visibility = View.GONE
                    characterSeries.visibility = View.GONE
                    loadingDialog.visibility = View.GONE
                    marvelErrorTitle.text = when(it.exception) {
                        is ServerException -> {
                            it.exception.message
                        }
                        is NetworkConnectionException -> {
                            it.exception.message
                        }
                        else -> {
                            resources.getString(R.string.unknown_error)
                        }
                    }
                }
                ViewData.State.LOADING -> {
                }
                ViewData.State.UNDEFINED -> {
                    marvelCharacterViewModel.getComicsAndSeries(characterId,0)
                    loadingDialog.visibility = View.VISIBLE
                }
            }
        })

        marvelCharacterViewModel.insertCharacterResult.observe(this, Observer {
            when(it.state){
                ViewData.State.SUCCESS -> {
                    marvelDialogError.visibility = View.GONE
                    characterDetailContainer.visibility = View.VISIBLE
                }
                ViewData.State.LOADING -> {
                }
                ViewData.State.FAIL -> {
                    marvelErrorTitle.text = when(it.exception){
                        is RuntimeException -> {
                            it.exception.message
                        }
                        else -> {
                            resources.getString(R.string.unknown_error)
                        }
                    }
                    marvelDialogError.visibility = View.VISIBLE
                    characterDetailContainer.visibility = View.GONE
                    currentFunction = FunctionType.INSERT
                }
                ViewData.State.UNDEFINED -> {
                }
            }
        })

        //deleteCharacterViewModel = ViewModelProviders.of(this).get(DeleteCharacterViewModel::class.java)
        marvelCharacterViewModel.deleteCharactersResult.observe(this, Observer {
            when(it.state){
                ViewData.State.SUCCESS -> {
                    setBookmarked(false)
                    marvelDialogError.visibility = View.GONE
                    characterDetailContainer.visibility = View.VISIBLE
                }
                ViewData.State.LOADING -> {
                }
                ViewData.State.FAIL -> {
                    marvelErrorTitle.text = when(it.exception){
                        is RuntimeException -> {
                            it.exception.message
                        }
                        else -> {
                            resources.getString(R.string.unknown_error)
                        }
                    }
                    marvelDialogError.visibility = View.VISIBLE
                    characterDetailContainer.visibility = View.GONE
                    currentFunction = FunctionType.DELETE
                }
                ViewData.State.UNDEFINED -> {
                }
            }
        })
    }

    private fun init(){
        titleBarText.text = resources.getString(R.string.character_name)
        characterButton.visibility = View.INVISIBLE

        characterComics.itemAnimator = null
        characterComics.layoutManager = GridLayoutManager(this.context!!, 1, RecyclerView.HORIZONTAL, false)
        characterComics.adapter = marvelComicsAdapter
        characterComics.addItemDecoration(GridLayoutDecoration(context!!, R.dimen.mtrl_card_spacing))

        characterSeries.itemAnimator = null
        characterSeries.layoutManager = GridLayoutManager(this.context!!, 1, RecyclerView.HORIZONTAL, false)
        characterSeries.adapter = marvelSeriesAdapter
        characterSeries.addItemDecoration(GridLayoutDecoration(context!!, R.dimen.mtrl_card_spacing))
    }

    private fun setListeners(){
        backButton.setOnClickListener{
            activity!!.setResult(RESULT_OK, Intent())
            activity!!.finish()
        }

        characterButton.setOnClickListener{
            if(character.characterBookmarked){
                deleteCharacter(character)
            }
            else{
                insertCharacter(character)
            }
            setBookmarked(character.characterBookmarked)
        }

        marvelReload.setOnClickListener{
            when(currentFunction){
                FunctionType.REFRESH ->{
                    marvelCharacterViewModel.getCharacter(characterId)
                    marvelCharacterViewModel.getComicsAndSeries(characterId,0)
                }
                FunctionType.INSERT -> {
                    marvelCharacterViewModel.insertCharacter(character)
                }
                FunctionType.DELETE -> {
                    marvelCharacterViewModel.deleteCharacter(character)
                }
            }
        }
    }

    private fun setBookmarked(bookmarkedCharacter: Boolean){
        if(!bookmarkedCharacter){
            characterButton.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.ic_unselected_star_character))
        }
        else{
            characterButton.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.ic_selected_star_character))
        }
    }

    private fun insertCharacter(character: CharacterModel){
        character.characterImageData = Utils.convertBitmapToByteArray((characterBigImage.drawable as BitmapDrawable))
        marvelCharacterViewModel.insertCharacter(character)
    }

    private fun deleteCharacter(character: CharacterModel){
        marvelCharacterViewModel.deleteCharacter(character)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MarvelCharacterDetailFragment()
    }
}
