package com.dicoding.mystudentdata

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.dicoding.mystudentdata.database.Student
import com.dicoding.mystudentdata.database.StudentAndUniversity
import com.dicoding.mystudentdata.database.StudentWithCourse
import com.dicoding.mystudentdata.database.UniversityAndStudent
import com.dicoding.mystudentdata.helper.SortType

class MainViewModel(private val studentRepository: StudentRepository) : ViewModel() {
    private val _sort = MutableLiveData<SortType>()

    init {
        _sort.value = SortType.ASCENDING
    }

    fun changeSortType(sortType: SortType) {
        _sort.value = sortType
    }

    fun getAllStudent(): LiveData<PagedList<Student>> = _sort.switchMap {
        studentRepository.getAllStudent(it)
    }
    fun getAllStudentAndUniversity(): LiveData<List<StudentAndUniversity>> = studentRepository.getAllStudentAndUniversity()
    fun getAllUniversityAndStudent(): LiveData<List<UniversityAndStudent>> = studentRepository.getAllUniversityAndStudent()
    fun getAllStudentWithCourse(): LiveData<List<StudentWithCourse>> = studentRepository.getAllStudentWithCourse()
}

class ViewModelFactory(private val repository: StudentRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}