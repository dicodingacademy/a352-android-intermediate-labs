package com.dicoding.mystudentdata

import androidx.lifecycle.*
import com.dicoding.mystudentdata.database.*
import com.dicoding.mystudentdata.helper.SortType
import com.dicoding.mystudentdata.helper.SortUtils

class MainViewModel(private val studentDao: StudentDao) : ViewModel() {
    private val _filter = MutableLiveData<SortType>()

    init {
        _filter.value = SortType.ASCENDING
    }

    fun filter(sortType: SortType) {
        _filter.value = sortType
    }

    fun getAllStudent(): LiveData<List<Student>> = _filter.switchMap {
        studentDao.getAllStudent(SortUtils.getSortedQuery(it))
    }
    fun getAllStudentAndUniversity(): LiveData<List<StudentAndUniversity>> = studentDao.getAllStudentAndUniversity()
    fun getAllUniversityAndStudent(): LiveData<List<UniversityAndStudent>> = studentDao.getAllUniversityAndStudent()
    fun getAllStudentWithCourse(): LiveData<List<StudentWithCourse>> = studentDao.getAllStudentWithCourse()
}

class ViewModelFactory(private val dao: StudentDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}