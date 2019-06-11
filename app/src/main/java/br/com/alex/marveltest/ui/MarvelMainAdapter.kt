package br.com.alex.marveltest.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MarvelMainAdapter(fm: FragmentManager?, private val fragments:MutableList<Fragment>) : FragmentStatePagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> fragments[0]
            1 -> fragments[1]
            else -> fragments[0]
        }
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> {
                MarvelCharactersFragment.marvelCharacterTitle
            }
            1 -> {
                MarvelBookmarkedCharactersFragment.marvelBookmarkTitle
            }
            else -> ""
        }
    }
}