package br.com.alex.marveltest

import android.app.Application
import android.content.Context
import br.com.alex.marveltest.di.AppModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class MarvelApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(module = AppModule.module)
    }

    override fun onCreate(){
        super.onCreate()
        appContext = applicationContext
    }

    companion object {
        lateinit var marvelApplication: MarvelApplication

        lateinit var appContext: Context
            private set
    }
}