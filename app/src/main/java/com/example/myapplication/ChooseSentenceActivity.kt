package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.SentenceData.SentenceRowData
import kotlinx.android.synthetic.main.sentence_generic_record.view.*

class ChooseSentenceActivity: RecyclerView.Adapter<myViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val textRow = layoutInflater.inflate(R.layout.sentence_generic_record, parent, false)
        return myViewHolder(textRow)
    }

    override fun getItemCount(): Int {
        return SentenceRowData.testName.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val test = holder.view.module_name
        val author = holder.view.module_author

        test.setText(SentenceRowData.testName[position])
        author.setText(SentenceRowData.textAuthor[position])
    }

}

class myViewHolder(val view: View): RecyclerView.ViewHolder(view)