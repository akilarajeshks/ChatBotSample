package com.zestworks.woebotapplication

import com.zestworks.woebotapplication.repository.WoebotNetworkModel
import com.zestworks.woebotapplication.ui.chatbot.Align
import com.zestworks.woebotapplication.ui.chatbot.Message
import com.zestworks.woebotapplication.ui.chatbot.Reply
import com.zestworks.woebotapplication.ui.chatbot.WoeBotUIModel

val dummySuccessNetworkModel = WoebotNetworkModel(
    id = "EIC",
    text = "Want to do a quick word game?",
    replies = listOf("Sure", "Later"),
    payloads = listOf("yes", "no"),
    routes = listOf("ZVQ", "YMB"),
    tag = "allornothing-start",
    lesson = "allornothing"
)

val dummySuccessViewModel = WoeBotUIModel(
    messages = listOf(Message("Want to do a quick word game?", Align.TEXT_ALIGNMENT_TEXT_START)),
    buttons = listOf(Reply("Sure", "ZVQ", false), Reply("Later", "YMB", false))
)

val dummyReplyData = WoebotNetworkModel(
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