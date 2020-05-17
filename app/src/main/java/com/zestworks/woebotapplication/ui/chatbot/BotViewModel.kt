package com.zestworks.woebotapplication.ui.chatbot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zestworks.woebotapplication.repository.Repository
import com.zestworks.woebotapplication.repository.RepositoryResponse
import kotlinx.serialization.UnstableDefault

@UnstableDefault
class BotViewModel(private val repository: Repository) : ViewModel() {

    private val _viewState = MutableLiveData<WoeBotUIModel>()
    val viewState = _viewState as LiveData<WoeBotUIModel>

    fun onUILoad() {
        if (_viewState.value == null) {
            when (val repositoryResponse = repository.getStartResponse()) {
                is RepositoryResponse.Success -> {
                    repositoryResponse.woebotNetworkModel.apply {
                        val text = text
                        val align =
                            Align.TEXT_ALIGNMENT_TEXT_START
                        val message =
                            Message(
                                text,
                                align
                            )
                        val replyButtons = mutableListOf<Reply>()
                        for (i in 0 until replies.count()) {
                            replyButtons.add(
                                Reply(
                                    replies[i],
                                    routes[i],
                                    isEnd()
                                )
                            )
                        }
                        _viewState.postValue(
                            WoeBotUIModel(
                                messages = listOf(message),
                                buttons = replyButtons
                            )
                        )
                    }
                }
                is RepositoryResponse.Error -> {

                }
            }
        }
    }

    fun onReplyButtonClicked(reply: Reply) {
        _viewState.value?.let {
            val messages = mutableListOf<Message>()
            messages.addAll(it.messages)
            messages.add(
                Message(
                    reply.text,
                    align = Align.TEXT_ALIGNMENT_TEXT_END
                )
            )
            _viewState.postValue(
                WoeBotUIModel(
                    messages = messages,
                    buttons = listOf()
                )
            )
            when (val model = repository.getResponseModelForRoute(reply.route)) {
                is RepositoryResponse.Success -> {
                    if (!reply.isEnd) {
                        val replies = mutableListOf<Reply>()
                        val listOfMessages = mutableListOf<Message>()
                        listOfMessages.addAll(messages)
                        listOfMessages.add(
                            Message(
                                model.woebotNetworkModel.text,
                                Align.TEXT_ALIGNMENT_TEXT_START
                            )
                        )
                        for (i in 0 until model.woebotNetworkModel.replies.count()) {
                            replies.add(
                                Reply(
                                    model.woebotNetworkModel.replies[i],
                                    model.woebotNetworkModel.routes[i],
                                    model.woebotNetworkModel.isEnd()
                                )
                            )
                        }

                        _viewState.postValue(
                            WoeBotUIModel(
                                messages = listOfMessages,
                                buttons = replies
                            )
                        )
                    }
                }
                is RepositoryResponse.Error -> {

                }
            }
        }
    }
}