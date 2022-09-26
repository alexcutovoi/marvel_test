package br.com.alex.marveltest.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.alex.domain.model.CharacterModel
import br.com.alex.marveltest.R
import br.com.alex.marveltest.model.ViewData
import br.com.alex.marveltest.util.UpdateData
import br.com.alex.repository.exception.NetworkConnectionException
import br.com.alex.repository.exception.ServerException
import kotlinx.android.synthetic.main.error_dialog.*
import kotlinx.android.synthetic.main.fragment_marvel_characters.*
import kotlinx.android.synthetic.main.top_bar.*

class MarvelCharactersFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener, PagingListener.PagingDataListener {
    private lateinit var marvelCharactersViewModel: MarvelCharactersViewModel
    private lateinit var marvelMainViewModel: MarvelMainViewModel

    private lateinit var pagingListener: PagingListener

    private val marvelCharactersAdapter: MarvelCharactersAdapter by lazy {
        MarvelCharactersAdapter(
            ArrayList(),
            { characterId, bookmarkedCharacter -> showCharacter(characterId, bookmarkedCharacter) },
            { character -> insertCharacter(character) },
            { character -> deleteCharacter(character) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_marvel_characters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setListeners()
        configViewMode()
    }

    private fun configViewMode(){
        marvelMainViewModel = ViewModelProviders.of(activity!!).get(MarvelMainViewModel::class.java)

        marvelCharactersViewModel = ViewModelProviders.of(this).get(MarvelCharactersViewModel::class.java)
        marvelCharactersViewModel.marvelCharactersResult.observe(this, Observer{
            when(it.state){
                ViewData.State.SUCCESS -> {
                    if(characterContainer.isRefreshing) {
                        characterContainer.isRefreshing = false
                    }

                    if(it.data.isEmpty()){
                        marvelDialogError.visibility = View.VISIBLE
                        marvelErrorTitle.text = resources.getString(R.string.empty_list)
                    }
                    else {
                        marvelCharactersAdapter.addAll(it.data)
                        marvelDialogError.visibility = View.GONE
                        marvelCharacterList.visibility = View.VISIBLE
                    }
                    loadingDialog.visibility = View.GONE
                }
                ViewData.State.LOADING -> {
                    if(characterContainer.isRefreshing) {
                        loadingDialog.visibility = View.GONE
                    }
                    else{
                        loadingDialog.visibility = View.VISIBLE
                    }
                    marvelDialogError.visibility = View.GONE
                }
                ViewData.State.FAIL -> {
                    if(characterContainer.isRefreshing) {
                        characterContainer.isRefreshing = false
                    }

                    loadingDialog.visibility = View.GONE
                    marvelCharacterList.visibility = View.GONE
                    marvelDialogError.visibility = View.VISIBLE
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
                    marvelCharactersViewModel.getCharacters(0, 0)
                    loadingDialog.visibility = View.VISIBLE
                }
            }
        })

        marvelMainViewModel.insertCharactersResult.observe(this, Observer {
            when(it.state){
                ViewData.State.SUCCESS -> {
                    marvelDialogError.visibility = View.GONE
                    marvelMainViewModel.getBookmarkedCharacters(0, 1)
                }
                ViewData.State.LOADING -> {
                }
                ViewData.State.FAIL -> {
                    marvelDialogError.visibility = View.VISIBLE
                    marvelCharacterList.visibility = View.GONE
                    marvelErrorTitle.text = when(it.exception){
                        is ServerException -> {
                            it.exception.message
                        }
                        is NetworkConnectionException -> {
                            it.exception.message
                        }
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

        marvelMainViewModel.deleteCharactersResult.observe(this, Observer {
            when(it.state){
                ViewData.State.SUCCESS -> {
                    marvelDialogError.visibility = View.GONE
                    marvelMainViewModel.getBookmarkedCharacters(0, 1)
                }
                ViewData.State.LOADING -> {
                }
                ViewData.State.FAIL -> {
                    marvelDialogError.visibility = View.VISIBLE
                }
                ViewData.State.UNDEFINED -> {
                }
            }
        })

        marvelMainViewModel.marvelBookmarkedCharactersResult.observe(this, Observer {
            when(it.state){
                ViewData.State.SUCCESS -> {
                    marvelCharactersAdapter.setBookmarkedCharacters(it.data)
                }
                else -> {

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

    private fun insertCharacter(character: CharacterModel){
        marvelMainViewModel.insertCharacter(character)
    }

    private fun deleteCharacter(character: CharacterModel){
        marvelMainViewModel.deleteCharacter(character)
    }

    private fun init(){
        backButton.visibility = View.GONE

        marvelCharacterList.itemAnimator = null
        marvelCharacterList.layoutManager = GridLayoutManager(this.context!!, 2, RecyclerView.VERTICAL, false)
        marvelCharacterList.adapter = marvelCharactersAdapter

        marvelCharacterList.addItemDecoration(GridLayoutDecoration(context!!, R.dimen.mtrl_card_spacing))
    }

    private fun setListeners(){
        characterContainer.setOnRefreshListener(this)
        characterButton.setOnClickListener{
            marvelCharactersAdapter.searchEnabled = !marvelCharactersAdapter.searchEnabled

            if(marvelCharactersAdapter.searchEnabled){
                constraintLayout2.background = ContextCompat.getDrawable(this.context!!, R.drawable.border_bottom_red)
                characterButton.setImageDrawable(ContextCompat.getDrawable(this.context!!, R.drawable.ic_close_search))
                titleBarText.visibility = View.GONE
                characterText.visibility = View.VISIBLE
                characterText.requestFocus()
                backButton.visibility = View.GONE
                showKeyboard()
            }
            else{
                characterButton.setImageDrawable(ContextCompat.getDrawable(this.context!!, (R.drawable.ic_magnifier)))
                constraintLayout2.background = ContextCompat.getDrawable(this.context!!, R.drawable.top_bar_background)
                titleBarText.visibility = View.VISIBLE
                characterText.visibility = View.GONE
                backButton.visibility = View.GONE
                characterText.text.clear()
                closeKeyboard()
            }
        }

        characterText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence , start: Int, before: Int, count: Int) {
                marvelCharactersAdapter.filter.filter(s.toString())
            }

            override fun afterTextChanged(s: Editable) {
            }
        })

        marvelReload.setOnClickListener{
            refresh()
        }

        pagingListener = PagingListener(marvelCharacterList.layoutManager as GridLayoutManager, 20)
        pagingListener.addPagingListener(this)
        marvelCharacterList.addOnScrollListener(pagingListener)
    }

    private fun closeKeyboard(){
        val inputMethodManager = this.requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(characterText.windowToken, 0)
    }

    private fun showKeyboard(){
        val inputMethodManager = this.requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(characterText, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun onRefresh() {
        refresh()
    }

    override fun loadData() {
        marvelCharactersViewModel.getCharacters(pagingListener.offset, 0)
    }

    fun refresh(){
        marvelCharactersAdapter.clear()
        marvelCharacterList.scrollToPosition(0)
        pagingListener.clearPaging()
        marvelCharactersViewModel.getCharacters(0, 0)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MarvelCharactersFragment()
        const val marvelCharacterTitle = "Characters"
    }
}
