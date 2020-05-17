package com.zestworks.woebotapplication.ui.chatbot

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zestworks.woebotapplication.R
import kotlinx.android.synthetic.main.fragment_bot.*
import org.koin.android.viewmodel.ext.android.viewModel

class ChatBotFragment : Fragment(R.layout.fragment_bot) {

    private val chatBotViewModel: ChatBotViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chatBotViewModel.viewState.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is ViewState.Success -> {
                    error_text.visibility = View.GONE
                    success_group.visibility = View.VISIBLE
                    if (recycler_list.adapter == null) {
                        recycler_list.apply {
                            adapter =
                                BotRecyclerAdapter(
                                    response.woeBotUIModel.messages
                                )
                            layoutManager =
                                LinearLayoutManager(this@ChatBotFragment.context).apply {
                                    stackFromEnd = true
                                }
                        }
                    } else {
                        (recycler_list.adapter as BotRecyclerAdapter).update(response.woeBotUIModel.messages)
                        (recycler_list.adapter as BotRecyclerAdapter).registerAdapterDataObserver(
                            object :
                                RecyclerView.AdapterDataObserver() {
                                override fun onItemRangeInserted(
                                    positionStart: Int,
                                    itemCount: Int
                                ) {
                                    recycler_list.layoutManager?.smoothScrollToPosition(
                                        recycler_list,
                                        null,
                                        (recycler_list.adapter as BotRecyclerAdapter).itemCount
                                    )
                                }
                            })

                    }
                    response.woeBotUIModel.buttons.forEach { reply ->
                        val button = Button(context)
                        button.text = reply.text
                        button.tag = reply.route
                        button.setOnClickListener {
                            linear_layout_buttons.removeAllViews()
                            chatBotViewModel.onReplyButtonClicked(reply)
                        }
                        linear_layout_buttons.addView(button)
                    }
                }
                is ViewState.Error -> {
                    success_group.visibility = View.GONE
                    error_text.text = response.reason
                    error_text.visibility = View.VISIBLE
                }
            }

        })
        chatBotViewModel.onUILoad()
    }
}