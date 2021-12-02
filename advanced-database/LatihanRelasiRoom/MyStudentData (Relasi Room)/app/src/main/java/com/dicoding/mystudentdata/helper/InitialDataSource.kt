package com.dicoding.mystudentdata.helper

import com.dicoding.mystudentdata.database.Course
import com.dicoding.mystudentdata.database.CourseStudentCrossRef
import com.dicoding.mystudentdata.database.Student
import com.dicoding.mystudentdata.database.University

object InitialDataSource {
    fun getUniversities(): List<University> {
        return listOf(
            University(1, "Android University"),
            University(2, "Web University"),
            University(3, "Machine Learning University"),
            University(4, "Cloud University"),
        )
    }

    fun getStudents(): List<Student> {
        return listOf(
            Student(1, "Andy Rubin", 1),
            Student(2, "Rich Miner", 1),
            Student(3, "Tim Berners-Lee", 2),
            Student(4, "Robert Cailliau", 2),
            Student(5, "Arthur Samuel", 3),
            Student(6, "JCR Licklider", 4),
        )
    }

    fun getCourses(): List<Course> {
        return listOf(
            Course(1, "Kotlin Basic"),
            Course(2, "Java Basic"),
            Course(3, "Javascript Basic"),
            Course(4, "Python Basic"),
            Course(5, "Dart Basic"),
        )
    }

    fun getCourseStudentRelation(): List<CourseStudentCrossRef> {
        return listOf(
            CourseStudentCrossRef(1, 1),
            CourseStudentCrossRef(1, 2),
            CourseStudentCrossRef(2, 2),
            CourseStudentCrossRef(2, 5),
            CourseStudentCrossRef(3, 3),
            CourseStudentCrossRef(4, 3),
            CourseStudentCrossRef(4, 4),
            CourseStudentCrossRef(5, 4),
            CourseStudentCrossRef(6, 3),
            CourseStudentCrossRef(6, 4),
        )
    }
}