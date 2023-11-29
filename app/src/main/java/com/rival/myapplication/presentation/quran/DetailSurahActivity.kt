package com.rival.myapplication.presentation.quran

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.rival.myapplication.adapter.ListAyahAdapter
import com.rival.myapplication.core.data.network.quran.AyahsItem
import com.rival.myapplication.core.data.network.quran.SurahItem
import com.rival.myapplication.databinding.ActivityDetailSurahBinding
import com.rival.myapplication.databinding.CustomViewAlertDialogBinding

class DetailSurahActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailSurahBinding
    val viewModel: QuranViewModel by viewModels()
    companion object{
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailSurahBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val surah = intent.getParcelableExtra<SurahItem>(EXTRA_DATA)
        binding.apply {
            tvDetailAyah.text = "${surah?.revelationType} - ${surah?.numberOfAyahs}Ayahs"
            tvDetailName.text = surah?.name
            tvDetailSurah.text = surah?.englishName
            tvDetailNameTranslation.text = surah?.englishNameTranslation
        }
        surah?.number?.let { viewModel.getListAyah(it) }
        val mAdapter = ListAyahAdapter()
        mAdapter.setOnItemClikCallback(object : ListAyahAdapter.OnItemClickCallback{
            override fun onItemCliked(data: AyahsItem) {
               showCustomDialog(data, surah)
            }

        })


        viewModel.listAyah.observe(this@DetailSurahActivity){ayah->
            binding.rvSurah.apply {
                mAdapter.setData(ayah.quranEdition.get(0).listAyahs, ayah.quranEdition)
                adapter = mAdapter
                layoutManager = LinearLayoutManager(this@DetailSurahActivity)
            }


        }
    }

    private fun showCustomDialog(dataAudio: AyahsItem, surah: SurahItem?) {
        val progressDialog = AlertDialog.Builder(this@DetailSurahActivity).create()
        val view = CustomViewAlertDialogBinding.inflate(layoutInflater)
        progressDialog.setView(view.root)
        
        view.apply { 
            tvSurah.text = surah?.englishName
            tvName.text = surah?.name
            tvNumberAyah.text = "${dataAudio.number}"
        }
        progressDialog.setOnShowListener {
            view.btnPlay.visibility = View.GONE
            view.loadingView.visibility = View.VISIBLE
            view.loadingView.isEnabled = false
            dataAudio?.audio?.let { sound -> loadAudio(sound,view, progressDialog) }
        }
        progressDialog.setOnDismissListener{
            binding.progressBar.visibility = View.GONE
        }
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()

    }

    private fun loadAudio(
        sound: String,
        view: CustomViewAlertDialogBinding,
        progressDialog: AlertDialog) {

    }


}