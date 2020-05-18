package com.zestworks.woebotapplication.repository

import io.kotest.matchers.shouldBe
import org.junit.Test

class ChatBotNetworkModelTest {

    @Test
    fun isStart() {
        val startModel = ChatBotNetworkModel(
            id = "EIC",
            text = "Want to do a quick word game?",
            replies = listOf("Sure", "Later"),
            payloads = listOf("yes", "no"),
            routes = listOf("ZVQ", "YMB"),
            tag = "allornothing-start",
            lesson = "allornothing"
        )


        val notStartModel = ChatBotNetworkModel(
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

        startModel.isStart() shouldBe true
        notStartModel.isStart() shouldBe false
    }

    @Test
    fun isEnd() {
        val endModel = ChatBotNetworkModel(
            id = "OWQ",
            text = "chat to you later 👋",
            replies = listOf(
                "Bye"
            ),
            payloads = listOf(
                "bye"
            ),
            routes = listOf(
                "CWP"
            ),
            tag = "bye",
            lesson = "allornothing"
        )

        val notEndModel = ChatBotNetworkModel(
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

        endModel.isEnd() shouldBe true
        notEndModel.isEnd() shouldBe false
    }
}