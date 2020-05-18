package com.zestworks.woebotapplication.ui.chatbot

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.zestworks.woebotapplication.dummyReplyData
import com.zestworks.woebotapplication.dummySuccessNetworkModel
import com.zestworks.woebotapplication.repository.Repository
import com.zestworks.woebotapplication.repository.RepositoryResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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
        // Arrange
        every { repository.getStartResponse() } returns RepositoryResponse.Success(
            dummySuccessNetworkModel
        )
        val dummySuccessUIModel = WoeBotUIModel(
            messages = listOf(
                Message(
                    "Want to do a quick word game?",
                    Align.TEXT_ALIGNMENT_TEXT_START
                )
            ),
            buttons = listOf(Reply("Sure", "ZVQ", false), Reply("Later", "YMB", false))
        )

        // Act
        chatBotViewModel.onUILoad()

        // Assert
        verify {
            testObserver.onChanged(ViewState.Success(dummySuccessUIModel))
        }
    }

    @Test
    fun testResponseForRouteId() {
        // Arrange
        every { repository.getStartResponse() } returns RepositoryResponse.Success(
            dummySuccessNetworkModel
        )
        every { repository.getResponseModelForRoute("ZVQ") } returns RepositoryResponse.Success(
            dummyReplyData
        )
        val firstUIModel = WoeBotUIModel(
            messages = listOf(
                Message(
                    "Want to do a quick word game?",
                    Align.TEXT_ALIGNMENT_TEXT_START
                )
            ),
            buttons = listOf(Reply("Sure", "ZVQ", false), Reply("Later", "YMB", false))
        )
        val intermediateUIModel = WoeBotUIModel(
            messages = listOf(
                Message(
                    "Want to do a quick word game?",
                    Align.TEXT_ALIGNMENT_TEXT_START
                ), Message("Sure", Align.TEXT_ALIGNMENT_TEXT_END)
            ), buttons = listOf()
        )
        val replyUIModel = WoeBotUIModel(
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

        // Act
        chatBotViewModel.onUILoad()
        chatBotViewModel.onReplyButtonClicked(
            Reply(
                "Sure",
                "ZVQ",
                false
            )
        )

        // Assert
        verifySequence {
            testObserver.onChanged(
                ViewState.Success(
                    firstUIModel
                )
            )
            testObserver.onChanged(
                ViewState.Success(
                    intermediateUIModel
                )
            )
            testObserver.onChanged(
                ViewState.Success(
                    replyUIModel
                )
            )
        }
    }

    @Test
    fun testErrorOnStart() {
        // Arrange
        every { repository.getStartResponse() } returns RepositoryResponse.Error("FileNotFound")

        // Act
        chatBotViewModel.onUILoad()

        // Assert
        verify {
            testObserver.onChanged(ViewState.Error("FileNotFound"))
        }
    }

    @Test
    fun testErrorOnReply() {
        // Arrange
        every { repository.getStartResponse() } returns RepositoryResponse.Success(
            dummySuccessNetworkModel
        )
        every { repository.getResponseModelForRoute("ZVQ") } returns RepositoryResponse.Error("FileNotFound")
        val firstUIModel = WoeBotUIModel(
            messages = listOf(
                Message(
                    "Want to do a quick word game?",
                    Align.TEXT_ALIGNMENT_TEXT_START
                )
            ),
            buttons = listOf(Reply("Sure", "ZVQ", false), Reply("Later", "YMB", false))
        )
        val intermediateUIModel = WoeBotUIModel(
            messages = listOf(
                Message(
                    "Want to do a quick word game?",
                    Align.TEXT_ALIGNMENT_TEXT_START
                ), Message("Sure", Align.TEXT_ALIGNMENT_TEXT_END)
            ), buttons = listOf()
        )

        // Act
        chatBotViewModel.onUILoad()
        chatBotViewModel.onReplyButtonClicked(
            Reply(
                "Sure",
                "ZVQ",
                false
            )
        )

        // Assert
        verifySequence {
            testObserver.onChanged(
                ViewState.Success(
                    firstUIModel
                )
            )
            testObserver.onChanged(
                ViewState.Success(
                    intermediateUIModel
                )
            )
            testObserver.onChanged(ViewState.Error("FileNotFound"))
        }
    }
}
