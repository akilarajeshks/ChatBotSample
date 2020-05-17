package com.zestworks.woebotapplication.repository

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WoebotNetworkModel(
    @SerialName("id")
    val id: String,
    @SerialName("lesson")
    val lesson: String,
    @SerialName("payloads")
    val payloads: List<String>,
    @SerialName("replies")
    val replies: List<String>,
    @SerialName("routes")
    val routes: List<String>,
    @SerialName("tag")
    val tag: String,
    @SerialName("text")
    val text: String
) {
    fun isStart(): Boolean = tag.contains("start")
    fun isEnd(): Boolean = tag == "bye"
}