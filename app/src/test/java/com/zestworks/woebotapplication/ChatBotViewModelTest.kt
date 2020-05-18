package com.zestworks.woebotapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.zestworks.woebotapplication.repository.Repository
import com.zestworks.woebotapplication.repository.RepositoryResponse
import com.zestworks.woebotapplication.ui.chatbot.*
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
class ChatBotViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repository: Repository = mockk()

    private val chatBotViewModel = ChatBotViewModel(repository)

    private val testObserver = mockk<Observer<ViewState>>(relaxed = true)

    @Before
    fun setUp() {
        chatBotViewModel.viewState.observeForever(testObserver)
    }

    @Test
    fun testFirstItemRetrieved() {
        every { repository.getStartResponse() } returns RepositoryResponse.Success(
            dummySuccessNetworkModel
        )

        chatBotViewModel.onUILoad()

        verify {
            testObserver.onChanged(ViewState.Success(dummySuccessViewModel))
        }
    }

    @Test
    fun testResponseForRouteId() {
        every { repository.getStartResponse() } returns RepositoryResponse.Success(
            dummySuccessNetworkModel
        )
        every { repository.getResponseModelForRoute("ZVQ") } returns RepositoryResponse.Success(
            dummyReplyData
        )
        chatBotViewModel.onUILoad()

        chatBotViewModel.onReplyButtonClicked(
            Reply(
                "Sure",
                "ZVQ",
                false
            )
        )

        verify {
            testObserver.onChanged(
                ViewState.Success(
                    WoeBotUIModel(
                        messages = listOf(
                            Message(
                                "Want to do a quick word game?",
                                Align.TEXT_ALIGNMENT_TEXT_START
                            ), Message("Sure", Align.TEXT_ALIGNMENT_TEXT_END)
                        ), buttons = listOf()
                    )
                )
            )
            testObserver.onChanged(
                ViewState.Success(
                    WoeBotUIModel(
                        messages = listOf(
                            Message(
                                "Want to do a quick word game?",
                                Align.TEXT_ALIGNMENT_TEXT_START
                            ), Message("Sure", Align.TEXT_ALIGNMENT_TEXT_END),
                            Message(
                                "Which of these is an example of all-or-nothing thinking?|1. My classmates don't like me|2. I wish I knew more people|3. I feel lonely sometimes",
                                Align.TEXT_ALIGNMENT_TEXT_START
                            )
                        ),
                        buttons = listOf(
                            Reply("1", "CWP", false)
                            , Reply("2", "LIQ", false),
                            Reply("3", "LIQ", false),
                            Reply("All or Nothing??", "CFK", false)
                        )
                    )
                )
            )
        }
    }

    @Test
    fun testError() {
        every { repository.getStartResponse() } returns RepositoryResponse.Error("FileNotFound")

        chatBotViewModel.onUILoad()

        verify {
            testObserver.onChanged(ViewState.Error("FileNotFound"))
        }
    }
}
