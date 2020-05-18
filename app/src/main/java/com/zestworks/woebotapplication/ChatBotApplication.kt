package com.zestworks.woebotapplication

import android.app.Application
import com.zestworks.woebotapplication.repository.MemCachedRepository
import com.zestworks.woebotapplication.repository.Repository
import com.zestworks.woebotapplication.ui.chatbot.ChatBotViewModel
import kotlinx.serialization.UnstableDefault
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

@UnstableDefault
class ChatBotApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val module = module {
            single<Repository> { MemCachedRepository(get()) }
            viewModel {
                ChatBotViewModel(
                    get()
                )
            }
        }

        startKoin {
            androidContext(this@ChatBotApplication)
            modules(module)
        }
    }
}