package br.com.alex.marveltest.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.alex.marveltest.R

class MarvelCharacterDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)

        val bundle = Bundle()
        bundle.putInt("CHARACTER_ID", intent.extras.getInt("CHARACTER_ID"))
        bundle.putBoolean("CHARACTER_BOOKMARKED", intent.extras.getBoolean("CHARACTER_BOOKMARKED"))

        val characterDetailFragment = MarvelCharacterDetailFragment.newInstance()
        characterDetailFragment.arguments = bundle

        supportFragmentManager.beginTransaction().replace(R.id.container, characterDetailFragment).commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(Activity.RESULT_OK, Intent())
        finish()
    }
}
