package com.zestworks.woebotapplication.ui.chatbot

import android.view.LayoutInflater
import android.view.View
import android.view.View.TEXT_ALIGNMENT_TEXT_END
import android.view.View.TEXT_ALIGNMENT_TEXT_START
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zestworks.woebotapplication.R

class BotRecyclerAdapter(var messages : List<Message>) :
    RecyclerView.Adapter<MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val viewHolder =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item, parent, false) as ViewGroup
        return MessageViewHolder(
            viewHolder
        )
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]

        holder.botResponse.text = message.message
        holder.botResponse.textAlignment = when (message.align) {
            Align.TEXT_ALIGNMENT_TEXT_START -> TEXT_ALIGNMENT_TEXT_START
            Align.TEXT_ALIGNMENT_TEXT_END -> TEXT_ALIGNMENT_TEXT_END
        }
    }

    fun update(message: List<Message>) {
        messages = message
        notifyItemInserted(messages.size-1)
    }
}

class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val botResponse: TextView = view.findViewById(R.id.item_text_view)
}
