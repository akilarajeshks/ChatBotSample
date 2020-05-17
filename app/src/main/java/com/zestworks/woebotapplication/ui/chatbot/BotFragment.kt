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
import kotlinx.serialization.UnstableDefault
import org.koin.android.viewmodel.ext.android.viewModel

@UnstableDefault
class BotFragment : Fragment(R.layout.fragment_bot) {

    private val botViewModel: BotViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        botViewModel.viewState.observe(viewLifecycleOwner, Observer { response ->
            if (recycler_list.adapter == null) {
                recycler_list.apply {
                    adapter =
                        BotRecyclerAdapter(
                            response.messages
                        )
                    layoutManager =
                        LinearLayoutManager(this@BotFragment.context).apply {
                            stackFromEnd = true
                        }
                }
            } else {
                (recycler_list.adapter as BotRecyclerAdapter).update(response.messages)
                (recycler_list.adapter as BotRecyclerAdapter).registerAdapterDataObserver(object :
                    RecyclerView.AdapterDataObserver() {
                    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                        recycler_list.layoutManager?.smoothScrollToPosition(
                            recycler_list,
                            null,
                            (recycler_list.adapter as BotRecyclerAdapter).itemCount
                        )
                    }
                })

            }

            if (response.buttons.isNotEmpty()) {
                response.buttons.forEach { reply ->
                    val button = Button(context)
                    button.text = reply.text
                    button.tag = reply.route
                    button.setOnClickListener {
                        linear_layout_buttons.removeAllViews()
                        botViewModel.onReplyButtonClicked(reply)
                    }
                    linear_layout_buttons.addView(button)
                }
            }
        })
        botViewModel.onUILoad()
    }
}