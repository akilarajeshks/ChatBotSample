package com.zestworks.woebotapplication

import android.app.Application
import com.zestworks.woebotapplication.repository.InMemCachedRepository
import com.zestworks.woebotapplication.repository.Repository
import com.zestworks.woebotapplication.ui.chatbot.BotViewModel
import kotlinx.serialization.UnstableDefault
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

@UnstableDefault
class WoebotApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val module = module {
            single<Repository> { InMemCachedRepository(get()) }
            viewModel {
                BotViewModel(
                    get()
                )
            }
        }

        startKoin {
            androidContext(this@WoebotApplication)
            modules(module)
        }
    }
}