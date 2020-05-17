package com.zestworks.woebotapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.zestworks.woebotapplication.repository.Repository
import com.zestworks.woebotapplication.repository.RepositoryResponse
import com.zestworks.woebotapplication.ui.chatbot.ChatBotViewModel
import com.zestworks.woebotapplication.ui.chatbot.ViewState
import com.zestworks.woebotapplication.ui.chatbot.WoeBotUIModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ChatBotUnitTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repository: Repository = mockk()

    private val chatBotViewModel = ChatBotViewModel(repository)

    private val testObserver = mockk<Observer<ViewState>>(relaxed = true)

    @Before
    fun setUp(){
        chatBotViewModel.viewState.observeForever(testObserver)
    }
    @Test
    fun testFirstItemRetrieved() {
        every { repository.getStartResponse() } returns RepositoryResponse.Success(dummySuccessNetworkModel)

        chatBotViewModel.onUILoad()

        verify {
            testObserver.onChanged(ViewState.Success(dummySuccessViewModel))
        }
    }
}
