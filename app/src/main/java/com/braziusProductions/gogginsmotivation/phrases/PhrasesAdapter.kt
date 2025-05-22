package com.braziusProductions.gogginsmotivation.phrases

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.braziusProductions.gogginsmotivation.PhraseData
import com.braziusProductions.gogginsmotivation.R
import com.braziusProductions.gogginsmotivation.databinding.ItemPhraseBinding
import java.util.ArrayList

class PhrasesAdapter(
    private val dataset: ArrayList<PhraseData>,
    private val listener: (PhraseData, PhraseEnum) -> Unit
) : RecyclerView.Adapter<PhrasesAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val binding: ItemPhraseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(phrase: PhraseData) {
            binding.phrase.text = phrase.phrase

            binding.play.setOnClickListener {
                listener.invoke(phrase, PhraseEnum.PLAY)
            }

            binding.options.setOnClickListener {
                val popupMenu = PopupMenu(binding.root.context, binding.options)
                popupMenu.menuInflater.inflate(R.menu.menu_item, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.action_share -> {
                            listener.invoke(phrase, PhraseEnum.SHARE)
                            true
                        }
                        R.id.action_set_ringtone -> {
                            listener.invoke(phrase, PhraseEnum.RINGTONE)
                            true
                        }
                        R.id.action_setAlarm -> {
                            listener.invoke(phrase, PhraseEnum.ALARM)
                            true
                        }
                        else -> false
                    }
                }
                popupMenu.show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemPhraseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    override fun getItemCount(): Int = dataset.size
}
