package com.zestworks.woebotapplication.ui.chatbot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zestworks.woebotapplication.repository.Repository
import com.zestworks.woebotapplication.repository.RepositoryResponse

class ChatBotViewModel(private val repository: Repository) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState = _viewState as LiveData<ViewState>

    fun onUILoad() {
        if (_viewState.value == null) {
            when (val repositoryResponse = repository.getStartResponse()) {
                is RepositoryResponse.Success -> {
                    repositoryResponse.chatBotNetworkModel.apply {
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
                            ViewState.Success(
                                WoeBotUIModel(
                                    messages = listOf(message),
                                    buttons = replyButtons
                                )
                            )
                        )
                    }
                }
                is RepositoryResponse.Error -> {
                    _viewState.postValue(ViewState.Error(repositoryResponse.reason))
                }
            }
        }
    }

    fun onReplyButtonClicked(reply: Reply) {
        if (_viewState.value is ViewState.Success) {
            _viewState.value?.let {

                val messages = mutableListOf<Message>()
                messages.addAll((it as ViewState.Success).woeBotUIModel.messages)
                messages.add(
                    Message(
                        reply.text,
                        align = Align.TEXT_ALIGNMENT_TEXT_END
                    )
                )
                _viewState.postValue(
                    ViewState.Success(
                        WoeBotUIModel(
                            messages = messages,
                            buttons = listOf()
                        )
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
                                    model.chatBotNetworkModel.text,
                                    Align.TEXT_ALIGNMENT_TEXT_START
                                )
                            )
                            for (i in 0 until model.chatBotNetworkModel.replies.count()) {
                                replies.add(
                                    Reply(
                                        model.chatBotNetworkModel.replies[i],
                                        model.chatBotNetworkModel.routes[i],
                                        model.chatBotNetworkModel.isEnd()
                                    )
                                )
                            }

                            _viewState.postValue(
                                ViewState.Success(
                                    WoeBotUIModel(
                                        messages = listOfMessages,
                                        buttons = replies
                                    )
                                )
                            )
                        }
                        Unit
                    }
                    is RepositoryResponse.Error -> {
                        _viewState.postValue(ViewState.Error(model.reason))
                    }
                }
            }
        }
    }
}