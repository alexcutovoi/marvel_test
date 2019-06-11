package br.com.alex.marveltest.ui


import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alex.domain.model.CharacterModel

import br.com.alex.marveltest.R
import br.com.alex.marveltest.model.ViewData
import br.com.alex.marveltest.util.UpdateData
import br.com.alex.repository.exception.NetworkConnectionException
import br.com.alex.repository.exception.ServerException
import kotlinx.android.synthetic.main.error_dialog.*
import kotlinx.android.synthetic.main.fragment_marvel_bookmarked_characters.*
import kotlinx.android.synthetic.main.fragment_marvel_bookmarked_characters.marvelDialogError

class MarvelBookmarkedCharactersFragment : Fragment() {
    private lateinit var marvelMainViewModel: MarvelMainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_marvel_bookmarked_characters, container, false)
    }

    private val marvelBookmarkedCharactersAdapter: MarvelBookmarkedCharactersAdapter by lazy {
        MarvelBookmarkedCharactersAdapter(
            { characterId, bookmarkedCharacter -> showCharacter(characterId, bookmarkedCharacter) },
            { character -> unbookCharacter(character) })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setListeners()
        configViewMode()
    }

    private fun configViewMode(){
        marvelMainViewModel = ViewModelProviders.of(activity!!).get(MarvelMainViewModel::class.java)
        marvelMainViewModel.marvelBookmarkedCharactersResult.observe(this, Observer{
            when(it.state){
                ViewData.State.SUCCESS -> {
                    if(it.data.isEmpty()){
                        marvelDialogError.visibility = View.VISIBLE
                        marvelBookmarkedCharacterList.visibility = View.GONE
                        marvelErrorTryAgain.visibility = View.GONE
                        marvelReload.visibility = View.GONE
                        marvelErrorTitle.text = resources.getString(R.string.empty_bookmark_list)
                    }
                    else {
                        marvelBookmarkedCharactersAdapter.setBookmarkedCharacter(it.data)
                        marvelDialogError.visibility = View.GONE
                        marvelBookmarkedCharacterList.visibility = View.VISIBLE
                    }
                }
                ViewData.State.LOADING -> {
                    marvelDialogError.visibility = View.GONE
                    marvelBookmarkedCharacterList.visibility = View.VISIBLE
                }
                ViewData.State.FAIL -> {
                    marvelBookmarkedCharacterList.visibility = View.GONE
                    marvelDialogError.visibility = View.VISIBLE
                    marvelErrorTryAgain.visibility = View.VISIBLE
                    marvelReload.visibility = View.VISIBLE

                    marvelErrorTitle.text = when(it.exception){
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
                ViewData.State.UNDEFINED -> {
                    marvelMainViewModel.getBookmarkedCharacters(0,  1)
                }
            }
        })

        marvelMainViewModel.deleteCharactersResult.observe(this, Observer {
            when(it.state){
                ViewData.State.SUCCESS -> {
                    marvelDialogError.visibility = View.GONE
                    refresh()
                }
                ViewData.State.LOADING -> {
                    marvelDialogError.visibility = View.GONE
                }
                ViewData.State.FAIL -> {
                    marvelDialogError.visibility = View.VISIBLE
                    marvelErrorTitle.text = when(it.exception){
                        is RuntimeException -> {
                            it.exception.message
                        }
                        else -> {
                            resources.getString(R.string.unknown_error)
                        }
                    }
                }
                ViewData.State.UNDEFINED -> {
                }
            }
        })
    }

    private fun showCharacter(characterId: Int, bookmarkedCharacter: Boolean){
        val intent = Intent(this.context!!, MarvelCharacterDetailActivity::class.java)
        intent.putExtra("CHARACTER_ID", characterId)
        intent.putExtra("CHARACTER_BOOKMARKED", bookmarkedCharacter)
        startActivityForResult(intent, UpdateData.UPDATE.ordinal)
    }

    private fun unbookCharacter(character: CharacterModel){
        marvelMainViewModel.deleteCharacter(character)
    }

    fun refresh(){
        marvelMainViewModel.getBookmarkedCharacters(0, 1)
    }

    private fun init(){
        marvelBookmarkedCharacterList.itemAnimator = null
        marvelBookmarkedCharacterList.layoutManager = GridLayoutManager(this.context!!, 2, RecyclerView.VERTICAL, false)
        marvelBookmarkedCharacterList.adapter = marvelBookmarkedCharactersAdapter

        marvelBookmarkedCharacterList.addItemDecoration(GridLayoutDecoration(context!!, R.dimen.mtrl_card_spacing))
    }

    private fun setListeners(){
        marvelReload.setOnClickListener{
            refresh()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MarvelBookmarkedCharactersFragment()
        const val marvelBookmarkTitle = "Favorites"
    }
}
