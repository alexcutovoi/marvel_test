package br.com.alex.marveltest.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import br.com.alex.marveltest.R
import br.com.alex.marveltest.model.ViewData
import kotlinx.android.synthetic.main.activity_marvel_main.*

class MarvelMainActivity : AppCompatActivity() {
    private lateinit var marvelMainViewModel: MarvelMainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marvel_main)
        init()
        configViewModel()
    }

    private fun init(){
        val fragments: MutableList<Fragment> = ArrayList()

        fragments.add(MarvelCharactersFragment.newInstance())
        fragments.add(MarvelBookmarkedCharactersFragment.newInstance())

        charactersContainer.adapter = MarvelMainAdapter(supportFragmentManager, fragments)
        marvelTabs.setupWithViewPager(charactersContainer)

        charactersContainer.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                when(position){
                    0 -> {
                        marvelMainTitle.text  = MarvelCharactersFragment.marvelCharacterTitle
                        (fragments[0] as  MarvelCharactersFragment).refresh()
                    }
                    1 -> {
                        marvelMainTitle.text = MarvelBookmarkedCharactersFragment.marvelBookmarkTitle
                        (fragments[1] as  MarvelBookmarkedCharactersFragment).refresh()
                    }
                    else -> {
                        ""
                    }
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK || resultCode == Activity.RESULT_CANCELED)
            marvelMainViewModel.getBookmarkedCharacters(0, 1)
    }

    private fun configViewModel(){
        marvelMainViewModel = ViewModelProviders.of(this).get(MarvelMainViewModel::class.java)
        marvelMainViewModel.getBookmarkedCharacters(0, 1)
    }
}
