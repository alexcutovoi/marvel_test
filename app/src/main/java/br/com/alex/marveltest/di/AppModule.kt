package br.com.alex.marveltest.di

import android.content.Context
import br.com.alex.marveltest.BuildConfig
import br.com.alex.marveltest.MarvelApplication
import br.com.alex.domain.SchedulerBuilder
import br.com.alex.domain.executor.ThreadPoolSchedulerBuilder
import br.com.alex.repository.store.util.NetworkUtils
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class AppModule {
    companion object {

        const val BASE_URL = "BASE_URL"

        val module = Kodein.Module(name = "appModule") {

            bind<Context>() with provider { MarvelApplication.appContext }
            bind<SchedulerBuilder>() with singleton { ThreadPoolSchedulerBuilder() }

            // Repository Needs
            // TODO: this class is needed because some data is saved wrongly by app domain
            //bind<RepositoryHelper>() with provider { RepositoryHelperImpl(instance()) }
            bind<NetworkUtils>() with singleton { NetworkUtils(instance()) }
            bind<String>(BASE_URL) with provider { BuildConfig.BASE_URL }

            import(MarvelModule.module)
        }
    }
}