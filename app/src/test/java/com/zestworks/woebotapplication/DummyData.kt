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

