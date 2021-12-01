package com.dicoding.mystudentdata.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mystudentdata.databinding.ItemStudentBinding
import com.dicoding.mystudentdata.database.UniversityAndStudent

class UniversityAndStudentAdapter :
    ListAdapter<UniversityAndStudent, UniversityAndStudentAdapter.WordViewHolder>(
        WordsComparator()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class WordViewHolder(private val binding: ItemStudentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: UniversityAndStudent) {
            val arrayName = arrayListOf<String>()
            data.student.forEach {
                arrayName.add(it.name)
            }
            binding.tvItemName.text = arrayName.joinToString(separator = ", ")
            binding.tvItemUniversity.text = data.university.name
            binding.tvItemUniversity.visibility = View.VISIBLE
        }
    }

    class WordsComparator : DiffUtil.ItemCallback<UniversityAndStudent>() {
        override fun areItemsTheSame(
            oldItem: UniversityAndStudent,
            newItem: UniversityAndStudent
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: UniversityAndStudent,
            newItem: UniversityAndStudent
        ): Boolean {
            return oldItem.university.name == newItem.university.name
        }
    }
}