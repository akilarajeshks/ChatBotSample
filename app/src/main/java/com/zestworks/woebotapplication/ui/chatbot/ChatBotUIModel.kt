package com.zestworks.woebotapplication.ui.chatbot

data class WoeBotUIModel(
    val messages: List<Message>,
    val buttons: List<Reply>
)

data class Reply(
    val text: String,
    val route: String,
    val isEnd: Boolean
)

data class Message(
    val message: String,
    val align: Align
)


sealed class ViewState{
    data class Success(val woeBotUIModel: WoeBotUIModel):ViewState()
    data class Error(val reason:String):ViewState()
}

enum class Align { TEXT_ALIGNMENT_TEXT_START, TEXT_ALIGNMENT_TEXT_END }