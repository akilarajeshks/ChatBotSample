package com.zestworks.woebotapplication

import com.zestworks.woebotapplication.repository.ChatBotNetworkModel
import com.zestworks.woebotapplication.ui.chatbot.Align
import com.zestworks.woebotapplication.ui.chatbot.Message
import com.zestworks.woebotapplication.ui.chatbot.Reply
import com.zestworks.woebotapplication.ui.chatbot.WoeBotUIModel

val dummySuccessNetworkModel = ChatBotNetworkModel(
    id = "EIC",
    text = "Want to do a quick word game?",
    replies = listOf("Sure", "Later"),
    payloads = listOf("yes", "no"),
    routes = listOf("ZVQ", "YMB"),
    tag = "allornothing-start",
    lesson = "allornothing"
)



val dummyReplyData = ChatBotNetworkModel(
    id = "ZVQ",
    text = "Which of these is an example of all-or-nothing thinking?|1. My classmates don't like me|2. I wish I knew more people|3. I feel lonely sometimes",
    replies = listOf(
        "1",
        "2",
        "3",
        "All or Nothing??"
    ),
    payloads = listOf(
        "1",
        "2",
        "3",
        "huh"
    ),
    routes = listOf(
        "CWP",
        "LIQ",
        "LIQ",
        "CFK"
    ),
    tag = "",
    lesson = "allornothing"

)