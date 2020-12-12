package com.braziusProductions.gogginsmotivation.phrases

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.braziusProductions.gogginsmotivation.PhraseData
import com.braziusProductions.gogginsmotivation.R
import com.braziusProductions.gogginsmotivation.getPhrasesData
import kotlinx.android.synthetic.main.item_phrase.view.*

class PhrasesAdapter(val myDataset: ArrayList<PhraseData>,var listener: (PhraseData,PhraseEnum) -> Unit) :
    RecyclerView.Adapter<PhrasesAdapter.MyViewHolder>() {


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(phrase: PhraseData) {
            view.phrase.text = phrase.phrase
            view.play.setOnClickListener {
                listener.invoke(phrase,PhraseEnum.PLAY)
            }
            view.options.setOnClickListener {
                val popupMenu: PopupMenu = PopupMenu(view.context, view.options)
                popupMenu.menuInflater.inflate(R.menu.menu_item, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.action_share -> {
                            listener.invoke(phrase,PhraseEnum.SHARE)
                            true
                        }

                        R.id.action_set_ringtone -> {
                            listener.invoke(phrase,PhraseEnum.RINGTONE)

                            true
                        }

                        R.id.action_setAlarm -> {
                            listener.invoke(phrase,PhraseEnum.ALARM)

                            true
                        }
                        else -> {
                            true
                        }
                    }
                }
                popupMenu.show()

            }
        }
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_phrase, parent, false)

        return MyViewHolder(
            view
        )
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(myDataset[holder.adapterPosition])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}