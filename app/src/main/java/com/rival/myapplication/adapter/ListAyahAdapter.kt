package com.rival.myapplication.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rival.myapplication.core.data.network.quran.AyahsItem
import com.rival.myapplication.core.data.network.quran.QuranEditionItem
import com.rival.myapplication.core.data.network.quran.SurahItem

import com.rival.myapplication.databinding.ItemAyahBinding


class ListAyahAdapter: RecyclerView.Adapter<ListAyahAdapter.AyahViewHolder>() {

    val listAyah = ArrayList<AyahsItem>()
    val listquranEdition = ArrayList<QuranEditionItem>()

    fun setData(dataAyahs: List<AyahsItem>, dataEdition: List<QuranEditionItem>) {
    if (dataAyahs == null || dataEdition == null)return
        listAyah.clear()
        listAyah.addAll(dataAyahs)
        listquranEdition.clear()
        listquranEdition.addAll(dataEdition)

    }


    class AyahViewHolder(val binding: ItemAyahBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAyahAdapter.AyahViewHolder =
        AyahViewHolder(
            ItemAyahBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun getItemCount(): Int = listAyah.size

    override fun onBindViewHolder(holder: AyahViewHolder, position: Int) {
        val listAyah = listAyah[position]
        val quranAudio = listquranEdition[1]
        val quranTranslateIndo = listquranEdition[2]

        holder.binding.apply {
            itemNumberAyah.text = listAyah.numberInSurah.toString()
            itemAyah.text = listAyah.text
            itemTranslation.text = quranTranslateIndo.listAyahs.get(position).text
        }
    }
}