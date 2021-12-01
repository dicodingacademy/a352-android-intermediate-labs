package com.dicoding.mystudentdata.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mystudentdata.database.StudentAndUniversity
import com.dicoding.mystudentdata.databinding.ItemStudentBinding

class StudentAndUniversityAdapter :
    ListAdapter<StudentAndUniversity, StudentAndUniversityAdapter.WordViewHolder>(WordsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class WordViewHolder(private val binding: ItemStudentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: StudentAndUniversity) {
            binding.tvItemName.text = data.student.name
            binding.tvItemUniversity.text = data.university?.name
            binding.tvItemUniversity.visibility = View.VISIBLE        }
    }

    class WordsComparator : DiffUtil.ItemCallback<StudentAndUniversity>() {
        override fun areItemsTheSame(oldItem: StudentAndUniversity, newItem: StudentAndUniversity): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: StudentAndUniversity, newItem: StudentAndUniversity): Boolean {
            return oldItem.student.name == newItem.student.name
        }
    }
}